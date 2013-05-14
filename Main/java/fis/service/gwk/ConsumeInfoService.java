package fis.service.gwk;

import fis.common.gwk.constant.ConsumeInfoSts;
import fis.common.gwk.constant.RtnTagKey;
import fis.repository.fs.dao.SysJoblogMapper;
import fis.repository.fs.model.SysJoblog;
import fis.repository.gwk.dao.GwkConsumeinfoMapper;
import fis.repository.gwk.model.GwkConsumeinfo;
import fis.repository.gwk.model.GwkConsumeinfoExample;
import gateway.txn.GwkServiceFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.advance.utils.PropertyManager;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: 下午10:02
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ConsumeInfoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private GwkConsumeinfoMapper gwkConsumeinfoMapper;
    @Resource
    private SysJoblogMapper sysJoblogMapper;
    @Resource
    private GwkServiceFactory factory;

    //获取待发消费信息
    public List<GwkConsumeinfo> selectConsumeSendNo(String bofcode) {
        String status = ConsumeInfoSts.SEND_SUC.getCode();
        GwkConsumeinfoExample example = new GwkConsumeinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andStatusNotEqualTo(status);
        return gwkConsumeinfoMapper.selectByExample(example);
    }

    //发送
    public String sendConsumeinfo(GwkConsumeinfo[] gwkConsumeinfos, String bofcode) throws Exception {
        List consumeList = null;       //发送数据
        List rtnlist = new ArrayList();
        String applicationid = "";
        String branchbankcode = "";
        String finorgcode = "";
        String longtuVer = "";
        String admdivCode = "";
        try {
            //send
            SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            String nowYear = yearsdf.format(dt);
//            String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
//            String branchbankcode = PropertyManager.getProperty("gwk.sub_branchbankcode");
//            String finorgcode = PropertyManager.getProperty("gwk.finorgcode");
            //2012-12-06
            applicationid = PropertyManager.getProperty("gwk.application.id."+bofcode);
            branchbankcode = PropertyManager.getProperty("gwk.sub.branch.bank.code."+bofcode);
            finorgcode = PropertyManager.getProperty("gwk.fin.org.code."+bofcode);
            //新增变量longtuver（龙图接口版本号），admdivCode（行政区划编码） 2012-12-06
            longtuVer = PropertyManager.getProperty("gwk.longtu.version."+bofcode);
            admdivCode = PropertyManager.getProperty("gwk.admdiv.code."+bofcode);
            //自写接口
            gateway.txn.t266001.gwk.BankService service = factory.getBankServiceForArea(bofcode);
//            BankService service = FaspServiceAdapter.getBankService();
            //根据接口版本号，调用不同的接口 2012-12-06
            if("v1".equals(longtuVer)){
                consumeList = getSendList(gwkConsumeinfos);
                rtnlist = service.writeConsumeInfo(applicationid, branchbankcode, nowYear, finorgcode, consumeList);
            }else{
                consumeList = getSendListV2(gwkConsumeinfos);
                rtnlist = service.writeConsumeInfo(applicationid, branchbankcode, nowYear,admdivCode,finorgcode, consumeList);
            }

        } catch (Exception ex) {
            throw new RuntimeException("发送消费信息失败:" + ex);
        }

        //返回信息处理
        if("v1".equals(longtuVer)){
            if (rtnlist != null && rtnlist.size() > 0) {
                Map m = (Map) rtnlist.get(0);
                String msg = m.get(RtnTagKey.MESSAGE).toString();
                try {
                    if (m.get(RtnTagKey.RESULT).toString().equalsIgnoreCase(RtnTagKey.RESULT_SUCCESS)) {
                        //返回成功  更新 sendflag＝1
                        updateSendSuc(gwkConsumeinfos, bofcode);
                        return RtnTagKey.RESULT_SUCCESS;
                    } else {
                        //返回失败 更新重复id
                        if (m.size() > 2) {
                            updateSameData(rtnlist, bofcode);
                        }
                        return msg;
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("消费信息发送成功后更新本地数据失败:" + ex);
                }
            }
        }else{
            if (rtnlist != null && rtnlist.size() > 0) {
                for (int i = 0; i < rtnlist.size(); i++) {
                    Map m1 = (Map) rtnlist.get(i);
                    String result = (String) m1.get(RtnTagKey.RESULT);
                    if (result == null){
                        result = (String) m1.get("result");
                    }
                    // 通过判断result的值判断是否发送成功，若全部成功则返回一个result==success的值
                    if (RtnTagKey.RESULT_SUCCESS.equalsIgnoreCase(result)) {
                        try {
                            updateSendSuc(gwkConsumeinfos, bofcode);
                            return RtnTagKey.RESULT_SUCCESS.toString();
                        } catch (Exception e) {
                            logger.error("发送数据后更新本地数据为已发送状态出现异常，请查看系统日志。");
                            return RtnTagKey.RESULT_FAIL.toString();
                        }
                    } else if (RtnTagKey.RESULT_FAIL.equalsIgnoreCase(result)){
                        return RtnTagKey.RESULT_FAIL.toString();
                    } else {
                        // 判断是否有重复帐号和身份证号，若有，则更改记录状态为已发送
                        String sameid = (String) m1.get("sameidnumber");
                        String sameaccount = (String) m1.get("sameaccount");
                        if ((sameid != null && !"".equals(sameid.trim()))
                                && (sameaccount != null && !"".equals(sameaccount.trim()))) {
                            try {
                                updateSameData(rtnlist, bofcode);
                                return RtnTagKey.RESULT_SUCCESS.toString();
                            } catch (Exception e) {
                                logger.error("发送数据后更新重复发送的数据时出现异常，请查看系统日志。");
                                return RtnTagKey.RESULT_FAIL.toString();
                            }
                        }
                    }
                }
            }
        }
        return RtnTagKey.RESULT_SUCCESS.toString();
    }

    /*发送所有待发送消费数据*/
    public String sendAllConsumeinfos(String bofcode) throws Exception {
        String status = ConsumeInfoSts.SEND_SUC.getCode();
        GwkConsumeinfoExample example = new GwkConsumeinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andStatusNotEqualTo(status);
        List<GwkConsumeinfo> gwkConsumeinfos = gwkConsumeinfoMapper.selectByExample(example);
        if (gwkConsumeinfos.size() < 1) {
            return RtnTagKey.RESULT_SUCCESS;
        }
        List consumeList = null;       //发送数据
        List rtnlist = new ArrayList();
        String applicationid = "";
        String branchbankcode = "";
        String finorgcode = "";
        String longtuVer = "";
        String admdivCode = "";
        try {
            //send
            SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            String nowYear = yearsdf.format(dt);
//            String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
//            String branchbankcode = PropertyManager.getProperty("gwk.sub_branchbankcode");
//            String finorgcode = PropertyManager.getProperty("gwk.finorgcode");
            //2012-12-06
            applicationid = PropertyManager.getProperty("gwk.application.id."+bofcode);
            branchbankcode = PropertyManager.getProperty("gwk.sub.branch.bank.code."+bofcode);
            finorgcode = PropertyManager.getProperty("gwk.fin.org.code."+bofcode);
            //新增变量longtuver（龙图接口版本号），admdivCode（行政区划编码） 2012-12-06
            longtuVer = PropertyManager.getProperty("gwk.longtu.version."+bofcode);
            admdivCode = PropertyManager.getProperty("gwk.admdiv.code."+bofcode);
            //自写接口
            gateway.txn.t266001.gwk.BankService service = factory.getBankServiceForArea(bofcode);
//            BankService service = FaspServiceAdapter.getBankService();
            //根据接口版本号，调用不同的接口 2012-12-06
            if("v1".equals(longtuVer)){
                consumeList = getSendList(gwkConsumeinfos);
                rtnlist = service.writeConsumeInfo(applicationid, branchbankcode, nowYear, finorgcode, consumeList);
            }else{
                consumeList = getSendListV2(gwkConsumeinfos);
                rtnlist = service.writeConsumeInfo(applicationid, branchbankcode, nowYear,admdivCode,finorgcode, consumeList);
            }
        } catch (Exception ex) {
            throw new RuntimeException("发送消费信息失败:" + ex);
        }
        //返回信息处理
        if("v1".equals(longtuVer)){
            if (rtnlist != null && rtnlist.size() > 0) {
                Map m = (Map) rtnlist.get(0);
                String msg = m.get(RtnTagKey.MESSAGE).toString();
                try {
                    if (m.get(RtnTagKey.RESULT).toString().equalsIgnoreCase(RtnTagKey.RESULT_SUCCESS)) {
                        //返回成功  更新 sendflag＝1
                        updateSendSuc(gwkConsumeinfos, bofcode);
                        return RtnTagKey.RESULT_SUCCESS;
                    } else {
                        //返回失败 更新重复id
                        if (m.size() > 2) {
                            updateSameData(rtnlist, bofcode);
                        }
                        return msg;
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("消费信息发送成功后更新本地数据失败:" + ex);
                }
            }
        }else{
            if (rtnlist != null && rtnlist.size() > 0) {
                for (int i = 0; i < rtnlist.size(); i++) {
                    Map m1 = (Map) rtnlist.get(i);
                    String result = (String) m1.get(RtnTagKey.RESULT);
                    if (result == null){
                        result = (String) m1.get("result");
                    }
                    // 通过判断result的值判断是否发送成功，若全部成功则返回一个result==success的值
                    if (RtnTagKey.RESULT_SUCCESS.equalsIgnoreCase(result)) {
                        try {
                            updateSendSuc(gwkConsumeinfos, bofcode);
                            return RtnTagKey.RESULT_SUCCESS.toString();
                        } catch (Exception e) {
                            logger.error("发送数据后更新本地数据为已发送状态出现异常，请查看系统日志。");
                            return RtnTagKey.RESULT_FAIL.toString();
                        }
                    } else if (RtnTagKey.RESULT_FAIL.equalsIgnoreCase(result)){
                        return RtnTagKey.RESULT_FAIL.toString();
                    } else {
                        // 判断是否有重复帐号和身份证号，若有，则更改记录状态为已发送
                        String sameid = (String) m1.get("sameidnumber");
                        String sameaccount = (String) m1.get("sameaccount");
                        if ((sameid != null && !"".equals(sameid.trim()))
                                && (sameaccount != null && !"".equals(sameaccount.trim()))) {
                            try {
                                updateSameData(rtnlist, bofcode);
                                return RtnTagKey.RESULT_SUCCESS.toString();
                            } catch (Exception e) {
                                logger.error("发送数据后更新重复发送的数据时出现异常，请查看系统日志。");
                                return RtnTagKey.RESULT_FAIL.toString();
                            }
                        }
                    }
                }
            }
        }
        return RtnTagKey.RESULT_SUCCESS.toString();
    }

    private List getSendList(GwkConsumeinfo[] gwkConsumeinfos) throws ParseException {
        List consumeList = new ArrayList();       //发送数据
        SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf6 = new SimpleDateFormat("yyyyMM");
        for (GwkConsumeinfo record : gwkConsumeinfos) {
            Map m = new HashMap();
            String lsh = record.getLsh() == null ? "" : record.getLsh();
            String account = record.getAccount() == null ? "" : record.getAccount();
            String cardname = record.getCardname() == null ? "" : record.getCardname();
            String busidate = record.getBusidate() == null ? "" : record.getBusidate();
            Double busimoney = record.getBusimoney().doubleValue();
            String businame = record.getBusiname() == null ? "" : record.getBusiname();
            String limitdate = record.getLimitdate() == null ? "" : record.getLimitdate();      //修改 核心交易日期的下个月20号

            m.put("ID", lsh);
            m.put("ACCOUNT", account);
            m.put("CARDNAME", cardname);
            m.put("BUSIDATE", busidate);
            m.put("BUSIMONEY", busimoney);
            m.put("BUSINAME", businame);
            m.put("Limitdate", limitdate);

            consumeList.add(m);
            logger.info("发送消费明细:流水号=" + lsh + ";卡号=" + account + ";持卡人=" + cardname + ";交易日期=" + busidate + ";交易金额=" + busimoney
                    + ";商户=" + businame + ";最迟还款=" + limitdate);
        }
        return consumeList;
    }

    private List getSendListV2(GwkConsumeinfo[] gwkConsumeinfos) throws ParseException {
        List consumeList = new ArrayList();       //发送数据
        SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf6 = new SimpleDateFormat("yyyyMM");
        for (GwkConsumeinfo record : gwkConsumeinfos) {
            Map m = new HashMap();
            String lsh = record.getLsh() == null ? "" : record.getLsh();
            String account = record.getAccount() == null ? "" : record.getAccount();
            String cardname = record.getCardname() == null ? "" : record.getCardname();
            String busidate = record.getBusidate() == null ? "" : record.getBusidate();
            Double busimoney = record.getBusimoney().doubleValue();
            String businame = record.getBusiname() == null ? "" : record.getBusiname();
            String limitdate = record.getLimitdate() == null ? "" : record.getLimitdate();      //修改 核心交易日期的下个月20号

            m.put("id", lsh);
            m.put("account", account);
            m.put("cardname", cardname);
            m.put("busidate", busidate);
            m.put("busimoney", busimoney);
            m.put("businame", businame);
            m.put("limitdate", limitdate);
            consumeList.add(m);
            logger.info("发送消费明细:流水号=" + lsh + ";卡号=" + account + ";持卡人=" + cardname + ";交易日期=" + busidate + ";交易金额=" + busimoney
                    + ";商户=" + businame + ";最迟还款=" + limitdate);
        }
        return consumeList;
    }

    private List getSendList(List<GwkConsumeinfo> gwkConsumeinfos) throws ParseException {
        List consumeList = new ArrayList();       //发送数据
        SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf6 = new SimpleDateFormat("yyyyMM");
        for (GwkConsumeinfo record : gwkConsumeinfos) {
            Map m = new HashMap();
            String lsh = record.getLsh() == null ? "" : record.getLsh();
            String account = record.getAccount() == null ? "" : record.getAccount();
            String cardname = record.getCardname() == null ? "" : record.getCardname();
            String busidate = record.getBusidate() == null ? "" : record.getBusidate();
            Double busimoney = record.getBusimoney().doubleValue();
            String businame = record.getBusiname() == null ? "" : record.getBusiname();
            String limitdate = record.getLimitdate() == null ? "" : record.getLimitdate();      //修改 核心交易日期的下个月20号

            m.put("ID", lsh);
            m.put("ACCOUNT", account);
            m.put("CARDNAME", cardname);
            m.put("BUSIDATE", busidate);
            m.put("BUSIMONEY", busimoney);
            m.put("BUSINAME", businame);
            m.put("Limitdate", limitdate);

            consumeList.add(m);
            logger.info("发送消费明细:流水号=" + lsh + ";卡号=" + account + ";持卡人=" + cardname + ";交易日期=" + busidate + ";交易金额=" + busimoney
                    + ";商户=" + businame + ";最迟还款=" + limitdate);
        }
        return consumeList;
    }

    private List getSendListV2(List<GwkConsumeinfo> gwkConsumeinfos) throws ParseException {
        List consumeList = new ArrayList();       //发送数据
        SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf6 = new SimpleDateFormat("yyyyMM");
        for (GwkConsumeinfo record : gwkConsumeinfos) {
            Map m = new HashMap();
            String lsh = record.getLsh() == null ? "" : record.getLsh();
            String account = record.getAccount() == null ? "" : record.getAccount();
            String cardname = record.getCardname() == null ? "" : record.getCardname();
            String busidate = record.getBusidate() == null ? "" : record.getBusidate();
            Double busimoney = record.getBusimoney().doubleValue();
            String businame = record.getBusiname() == null ? "" : record.getBusiname();
            String limitdate = record.getLimitdate() == null ? "" : record.getLimitdate();      //修改 核心交易日期的下个月20号

            m.put("id", lsh);
            m.put("account", account);
            m.put("cardname", cardname);
            m.put("busidate", busidate);
            m.put("busimoney", busimoney);
            m.put("businame", businame);
            m.put("limitdate", limitdate);
            consumeList.add(m);
            logger.info("发送消费明细:流水号=" + lsh + ";卡号=" + account + ";持卡人=" + cardname + ";交易日期=" + busidate + ";交易金额=" + busimoney
                    + ";商户=" + businame + ";最迟还款=" + limitdate);
        }
        return consumeList;
    }


    @Transactional
    private void updateSendSuc(GwkConsumeinfo[] gwkConsumeinfos, String bofcode) {
        if (gwkConsumeinfos.length > 0) {
            Date dt = new Date();
            GwkConsumeinfo updata = new GwkConsumeinfo();
            updata.setStatus(ConsumeInfoSts.SEND_SUC.getCode());
            updata.setOperdate(new Date());
            for (GwkConsumeinfo record : gwkConsumeinfos) {
                GwkConsumeinfoExample example = new GwkConsumeinfoExample();
                example.clear();
                example.createCriteria().andAreacodeEqualTo(bofcode).andAccountEqualTo(record.getAccount()).andLshEqualTo(record.getLsh());
                gwkConsumeinfoMapper.updateByExampleSelective(updata, example);
                //日志表插入
                SysJoblog sysJoblog = new SysJoblog();
                sysJoblog.setTablename("gwk_consumeinfo");
                sysJoblog.setRowpkid(record.getPkid());
                sysJoblog.setJobname("更新");
                sysJoblog.setJobdesc("发送后更新:发送状态=" + updata.getStatus());
                sysJoblog.setJobtime(dt);
                //插入日志
                sysJoblogMapper.insert(sysJoblog);
            }
        }
    }

    @Transactional
    private void updateSendSuc(List<GwkConsumeinfo> gwkConsumeinfos, String bofcode) {
        if (gwkConsumeinfos.size() > 0) {
            Date dt = new Date();
            GwkConsumeinfo updata = new GwkConsumeinfo();
            updata.setStatus(ConsumeInfoSts.SEND_SUC.getCode());
            updata.setOperdate(new Date());
            for (GwkConsumeinfo record : gwkConsumeinfos) {
                GwkConsumeinfoExample example = new GwkConsumeinfoExample();
                example.clear();
                example.createCriteria().andAreacodeEqualTo(bofcode).andAccountEqualTo(record.getAccount()).andLshEqualTo(record.getLsh());
                gwkConsumeinfoMapper.updateByExampleSelective(updata, example);
                //日志表插入
                SysJoblog sysJoblog = new SysJoblog();
                sysJoblog.setTablename("gwk_consumeinfo");
                sysJoblog.setRowpkid(record.getPkid());
                sysJoblog.setJobname("更新");
                sysJoblog.setJobdesc("发送后更新:发送状态=" + updata.getStatus());
                sysJoblog.setJobtime(dt);
                //插入日志
                sysJoblogMapper.insert(sysJoblog);
            }
        }
    }

    @Transactional
    private void updateSameData(List<Map> rtnlist, String bofcode) {
        Date dt = new Date();
        GwkConsumeinfo updata = new GwkConsumeinfo();
        updata.setStatus(ConsumeInfoSts.SEND_SUC.getCode());
        updata.setOperdate(dt);
        for (Map record : rtnlist) {
            GwkConsumeinfoExample example = new GwkConsumeinfoExample();
            example.clear();
            example.createCriteria().andAreacodeEqualTo(bofcode).andAccountEqualTo(record.get(RtnTagKey.SAMEACCOUNT).toString()).andLshEqualTo(record.get(RtnTagKey.SAMEID).toString());
            gwkConsumeinfoMapper.updateByExampleSelective(updata, example);
            //日志表插入
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_consumeinfo");
            sysJoblog.setRowpkid(record.get(RtnTagKey.SAMEACCOUNT).toString());
            sysJoblog.setJobname("更新重复发送");
            sysJoblog.setJobdesc("发送后更新:发送状态=" + updata.getStatus());
            sysJoblog.setJobtime(dt);
            //插入日志
            sysJoblogMapper.insert(sysJoblog);
        }
    }

    //按条件查询消费信息
    public List<GwkConsumeinfo> selectConsumeForQry(String status, String account, String busistartdate, String busienddate, String bofcode) {
        GwkConsumeinfoExample example = new GwkConsumeinfoExample();
        GwkConsumeinfoExample.Criteria criteria = example.createCriteria();
        if (status != null && !StringUtils.isEmpty(status)) {
            criteria.andStatusEqualTo(status);
        }
        if (account != null && !StringUtils.isEmpty(account)) {
            criteria.andAccountEqualTo(account);
        }
        if (busistartdate != null && !StringUtils.isEmpty(busistartdate)) {
            criteria.andBusidateGreaterThanOrEqualTo(busistartdate);
        }
        if (busienddate != null && !StringUtils.isEmpty(busienddate)) {
            criteria.andBusidateLessThanOrEqualTo(busienddate);
        }
        criteria.andAreacodeEqualTo(bofcode);
        return gwkConsumeinfoMapper.selectByExample(example);
    }
}
