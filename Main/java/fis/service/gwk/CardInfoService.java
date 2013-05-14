package fis.service.gwk;

import fis.common.gwk.constant.CardSendFlg;
import fis.common.gwk.constant.RtnTagKey;
import fis.repository.fs.dao.SysJoblogMapper;
import fis.repository.fs.model.SysJoblog;
import fis.repository.gwk.dao.GwkCardbaseinfoMapper;
import fis.repository.gwk.model.GwkCardbaseinfo;
import fis.repository.gwk.model.GwkCardbaseinfoExample;
import gateway.txn.GwkServiceFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.advance.utils.PropertyManager;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: 下午7:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CardInfoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private GwkCardbaseinfoMapper gwkCardbaseinfoMapper;
    @Resource
    private SysJoblogMapper sysJoblogMapper;
    @Resource
    private GwkServiceFactory factory;

    //待发送卡信息查询
    public List<GwkCardbaseinfo> selectCardinfos(String bofcode, String sendflag) {
        GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andSentflagEqualTo(sendflag).andStatusEqualTo("1");
        return gwkCardbaseinfoMapper.selectByExample(example);
    }

    //发送卡信息
    public String sendCardinfos(GwkCardbaseinfo[] gwkCardbaseinfos, String bofcode) throws Exception {
        List cardList = null;
        List rtnlist = null;
        String applicationid = "";
        String branchbankcode = "";
        String finorgcode = "";
        String longtuVer = "";
        String admdivCode = "";
        try {
            SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            String nowYear = yearsdf.format(dt);
//            String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
//            String branchbankcode = PropertyManager.getProperty("gwk.branchbankcode");
//            String finorgcode = PropertyManager.getProperty("gwk.finorgcode");
            //2012-12-06
            applicationid = PropertyManager.getProperty("gwk.application.id."+bofcode);
            branchbankcode = PropertyManager.getProperty("gwk.branch.bank.code."+bofcode);
            finorgcode = PropertyManager.getProperty("gwk.fin.org.code."+bofcode);
            //新增变量longtuver（龙图接口版本号），admdivCode（行政区划编码） 2012-12-06
            longtuVer = PropertyManager.getProperty("gwk.longtu.version."+bofcode);
            admdivCode = PropertyManager.getProperty("gwk.admdiv.code."+bofcode);
            //自写接口
            gateway.txn.t266001.gwk.BankService service = factory.getBankServiceForArea(bofcode);
//            BankService service = FaspServiceAdapter.getBankService();
            //根据接口版本号，调用不同的接口 2012-12-06
            if("v1".equals(longtuVer)){
                cardList = getSendList(gwkCardbaseinfos);
                rtnlist = service.writeOfficeCard(applicationid, branchbankcode, nowYear, finorgcode, cardList);
            }else{
                cardList = getSendListV2(gwkCardbaseinfos);
                rtnlist = service.writeOfficeCard(applicationid, branchbankcode, nowYear, admdivCode,finorgcode, cardList);
            }

        } catch (Exception ex) {
            throw new RuntimeException("发送卡信息失败:" + ex);
        }
        //返回信息处理
        if("v1".equals(longtuVer)){
            if (rtnlist != null && rtnlist.size() > 0) {
                Map m = (Map) rtnlist.get(0);
                String msg = m.get(RtnTagKey.MESSAGE).toString();
                try {
                    if (m.get(RtnTagKey.RESULT).toString().equalsIgnoreCase(RtnTagKey.RESULT_SUCCESS)) {
                        //返回成功  更新 sendflag＝1
                        updateSendSuc(gwkCardbaseinfos, bofcode);
                        return RtnTagKey.RESULT_SUCCESS.toString();
                    } else {
                        //返回失败 更新重复id
                        if (m.size() > 2) {
                            updateSameData(rtnlist, bofcode);
                        }
                        return msg;
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("卡信息发送成功后更新本地数据失败:" + ex);
                }
            }
        }else {
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
                            updateSendSuc(gwkCardbaseinfos, bofcode);
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

    /*发送所有待发送卡信息*/
    public String sendAllCardinfos(String bofcode) throws Exception {
        GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andSentflagEqualTo(CardSendFlg.SEND_NO.getCode()).andStatusEqualTo("1");
        List<GwkCardbaseinfo> gwkCardbaseinfos = gwkCardbaseinfoMapper.selectByExample(example);
        if (gwkCardbaseinfos.size() < 1) {
            return RtnTagKey.RESULT_SUCCESS;
        }
        List cardList = null;
        List rtnlist = new ArrayList();
        String applicationid = "";
        String branchbankcode = "";
        String finorgcode = "";
        String longtuVer = "";
        String admdivCode = "";
        try {
            SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            String nowYear = yearsdf.format(dt);
//            String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
//            String branchbankcode = PropertyManager.getProperty("gwk.branchbankcode");
//            String finorgcode = PropertyManager.getProperty("gwk.finorgcode");
            //2012-12-06
            applicationid = PropertyManager.getProperty("gwk.application.id."+bofcode);
            branchbankcode = PropertyManager.getProperty("gwk.branch.bank.code."+bofcode);
            finorgcode = PropertyManager.getProperty("gwk.fin.org.code."+bofcode);
            //新增变量longtuver（龙图接口版本号），admdivCode（行政区划编码） 2012-12-06
            longtuVer = PropertyManager.getProperty("gwk.longtu.version."+bofcode);
            admdivCode = PropertyManager.getProperty("gwk.admdiv.code."+bofcode);
            //自写接口
            gateway.txn.t266001.gwk.BankService service = factory.getBankServiceForArea(bofcode);
//            BankService service = FaspServiceAdapter.getBankService();
            //根据接口版本号，调用不同的接口 2012-12-06
            if("v1".equals(longtuVer)){
                cardList = getSendList(gwkCardbaseinfos);
                rtnlist = service.writeOfficeCard(applicationid, branchbankcode, nowYear, finorgcode, cardList);
            }else{
                cardList = getSendListV2(gwkCardbaseinfos);
                rtnlist = service.writeOfficeCard(applicationid, branchbankcode, nowYear, admdivCode,finorgcode, cardList);
            }
        } catch (Exception ex) {
            throw new RuntimeException("发送卡信息失败:" + ex);
        }

        //返回信息处理
        if("v1".equals(longtuVer)){
            if (rtnlist != null && rtnlist.size() > 0) {
                Map m = (Map) rtnlist.get(0);
                String msg = m.get(RtnTagKey.MESSAGE).toString();
                try {
                    if (m.get(RtnTagKey.RESULT).toString().equalsIgnoreCase(RtnTagKey.RESULT_SUCCESS)) {
                        //返回成功  更新 sendflag＝1
                        updateSendSuc(gwkCardbaseinfos, bofcode);
                        return RtnTagKey.RESULT_SUCCESS.toString();
                    } else {
                        //返回失败 更新重复id
                        if (m.size() > 2) {
                            updateSameData(rtnlist, bofcode);
                        }
                        return msg;
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("卡信息发送成功后更新本地数据失败:" + ex);
                }
            }
        }else {
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
                            updateSendSuc(gwkCardbaseinfos, bofcode);
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

    private List getSendList(GwkCardbaseinfo[] gwkCardbaseinfos) {
        List cardList = new ArrayList();       //发送数据
        for (GwkCardbaseinfo record : gwkCardbaseinfos) {
            Map m = new HashMap();
            String account = (record.getAccount() == null ? "" : record.getAccount().toString());
            String cardname = record.getCardname() == null ? "" : record.getCardname().toString();
            String bdgagency = record.getBdgagency() == null ? "" : record.getBdgagency().toString();
            String GATHERINGBANKACCTNAME = (record.getGatheringbankacctname() == null ? "" : record.getGatheringbankacctname().toString());
            String GATHERINGBANKNAME = (record.getGatheringbankname() == null ? "" : record.getGatheringbankname().toString());
            String GATHERINGBANKACCTCODE = (record.getGatheringbankacctcode() == null ? "" : record.getGatheringbankacctcode().toString());
            String IDNUMBER = (record.getIdnumber() == null ? "" : record.getIdnumber().toString());
            String DIGEST = (record.getDigest() == null ? "" : record.getDigest().toString());
            String BANK = (record.getBank() == null ? "" : record.getBank().toString());
            String CREATEDATE = record.getCreatedate() == null ? "" : record.getCreatedate().toString();
            String Startdate = record.getStartdate() == null ? "" : record.getStartdate().toString();           // 有效截止日
            String enddate = record.getEnddate() == null ? "" : record.getEnddate().toString();                 // 转换格式 yyyy-mm-dd
            String action = record.getAction() == null ? "" : record.getAction().toString();
            m.put("ACCOUNT", account);
            m.put("CARDNAME", cardname);
            m.put("BDGAGENCY", bdgagency);
            m.put("GATHERINGBANKACCTNAME", GATHERINGBANKACCTNAME);
            m.put("GATHERINGBANKNAME", GATHERINGBANKNAME);
            m.put("GATHERINGBANKACCTCODE", GATHERINGBANKACCTCODE);
            m.put("IDNUMBER", IDNUMBER);
            m.put("DIGEST", DIGEST);
            m.put("BANK", BANK);
            m.put("CREATEDATE", CREATEDATE);
            m.put("STARTDATE", Startdate);
            m.put("ENDDATE", enddate);
            m.put("ACTION", action);

            cardList.add(m);
            logger.info("发送卡信息:" + "ACCOUNT=" + account + ";CARDNAME=" + cardname + ";预算单位=" + bdgagency
                    + ";还款帐户名=" + GATHERINGBANKACCTNAME + ";还款账户开户行=" + GATHERINGBANKNAME + ";还款帐号=" + GATHERINGBANKACCTCODE
                    + ";证件号=" + IDNUMBER + ";用途=" + DIGEST + ";财政银行编码=" + BANK + ";开卡日期=" + CREATEDATE
                    + ";有效起始=" + Startdate + ";有效终止=" + enddate + ";卡状态=" + action);
        }
        return cardList;
    }
    private List getSendListV2(GwkCardbaseinfo[] gwkCardbaseinfos) {
        List cardList = new ArrayList();       //发送数据
        for (GwkCardbaseinfo record : gwkCardbaseinfos) {
            Map m = new HashMap();
            String account = (record.getAccount() == null ? "" : record.getAccount().toString());
            String cardname = record.getCardname() == null ? "" : record.getCardname().toString();
            String bdgagency = record.getBdgagency() == null ? "" : record.getBdgagency().toString();
            String GATHERINGBANKACCTNAME = (record.getGatheringbankacctname() == null ? "" : record.getGatheringbankacctname().toString());
            String GATHERINGBANKNAME = (record.getGatheringbankname() == null ? "" : record.getGatheringbankname().toString());
            String GATHERINGBANKACCTCODE = (record.getGatheringbankacctcode() == null ? "" : record.getGatheringbankacctcode().toString());
            String IDNUMBER = (record.getIdnumber() == null ? "" : record.getIdnumber().toString());
            String DIGEST = (record.getDigest() == null ? "" : record.getDigest().toString());
            String BANK = (record.getBank() == null ? "" : record.getBank().toString());
            String CREATEDATE = record.getCreatedate() == null ? "" : record.getCreatedate().toString();
            String Startdate = record.getStartdate() == null ? "" : record.getStartdate().toString();           // 有效截止日
            String enddate = record.getEnddate() == null ? "" : record.getEnddate().toString();                 // 转换格式 yyyy-mm-dd
            String action = record.getAction() == null ? "" : record.getAction().toString();

            m.put("account", account);
            m.put("cardname", cardname);
            m.put("bdgagency", bdgagency);
            m.put("gatheringbankacctname", GATHERINGBANKACCTNAME);
            m.put("gatheringbankname", GATHERINGBANKNAME);
            m.put("gatheringbankacctcode", GATHERINGBANKACCTCODE);
            m.put("idnumber", IDNUMBER);
            m.put("digest", DIGEST);
            m.put("bank", BANK);
            m.put("createdate", CREATEDATE);
            m.put("startdate", Startdate);
            m.put("enddate", enddate);
            m.put("action", action);
            cardList.add(m);
            logger.info("发送卡信息:" + "ACCOUNT=" + account + ";CARDNAME=" + cardname + ";预算单位=" + bdgagency
                    + ";还款帐户名=" + GATHERINGBANKACCTNAME + ";还款账户开户行=" + GATHERINGBANKNAME + ";还款帐号=" + GATHERINGBANKACCTCODE
                    + ";证件号=" + IDNUMBER + ";用途=" + DIGEST + ";财政银行编码=" + BANK + ";开卡日期=" + CREATEDATE
                    + ";有效起始=" + Startdate + ";有效终止=" + enddate + ";卡状态=" + action);
        }
        return cardList;
    }

    private List getSendList(List<GwkCardbaseinfo> gwkCardbaseinfos) {
        List cardList = new ArrayList();       //发送数据
        for (GwkCardbaseinfo record : gwkCardbaseinfos) {
            Map m = new HashMap();
            String account = (record.getAccount() == null ? "" : record.getAccount().toString());
            String cardname = record.getCardname() == null ? "" : record.getCardname().toString();
            String bdgagency = record.getBdgagency() == null ? "" : record.getBdgagency().toString();
            String GATHERINGBANKACCTNAME = (record.getGatheringbankacctname() == null ? "" : record.getGatheringbankacctname().toString());
            String GATHERINGBANKNAME = (record.getGatheringbankname() == null ? "" : record.getGatheringbankname().toString());
            String GATHERINGBANKACCTCODE = (record.getGatheringbankacctcode() == null ? "" : record.getGatheringbankacctcode().toString());
            String IDNUMBER = (record.getIdnumber() == null ? "" : record.getIdnumber().toString());
            String DIGEST = (record.getDigest() == null ? "" : record.getDigest().toString());
            String BANK = (record.getBank() == null ? "" : record.getBank().toString());
            String CREATEDATE = record.getCreatedate() == null ? "" : record.getCreatedate().toString();
            String Startdate = record.getStartdate() == null ? "" : record.getStartdate().toString();           // 有效截止日
            String enddate = record.getEnddate() == null ? "" : record.getEnddate().toString();                 // 转换格式 yyyy-mm-dd
            String action = record.getAction() == null ? "" : record.getAction().toString();
            m.put("ACCOUNT", account);
            m.put("CARDNAME", cardname);
            m.put("BDGAGENCY", bdgagency);
            m.put("GATHERINGBANKACCTNAME", GATHERINGBANKACCTNAME);
            m.put("GATHERINGBANKNAME", GATHERINGBANKNAME);
            m.put("GATHERINGBANKACCTCODE", GATHERINGBANKACCTCODE);
            m.put("IDNUMBER", IDNUMBER);
            m.put("DIGEST", DIGEST);
            m.put("BANK", BANK);
            m.put("CREATEDATE", CREATEDATE);
            m.put("STARTDATE", Startdate);
            m.put("ENDDATE", enddate);
            m.put("ACTION", action);
            cardList.add(m);
            logger.info("发送卡信息:" + "ACCOUNT=" + account + ";CARDNAME=" + cardname + ";预算单位=" + bdgagency
                    + ";还款帐户名=" + GATHERINGBANKACCTNAME + ";还款账户开户行=" + GATHERINGBANKNAME + ";还款帐号=" + GATHERINGBANKACCTCODE
                    + ";证件号=" + IDNUMBER + ";用途=" + DIGEST + ";财政银行编码=" + BANK + ";开卡日期=" + CREATEDATE
                    + ";有效起始=" + Startdate + ";有效终止=" + enddate + ";卡状态=" + action);
        }
        return cardList;
    }

    private List  getSendListV2(List<GwkCardbaseinfo> gwkCardbaseinfos) {
        List cardList = new ArrayList();       //发送数据
        for (GwkCardbaseinfo record : gwkCardbaseinfos) {
            Map m = new HashMap();
            String account = (record.getAccount() == null ? "" : record.getAccount().toString());
            String cardname = record.getCardname() == null ? "" : record.getCardname().toString();
            String bdgagency = record.getBdgagency() == null ? "" : record.getBdgagency().toString();
            String GATHERINGBANKACCTNAME = (record.getGatheringbankacctname() == null ? "" : record.getGatheringbankacctname().toString());
            String GATHERINGBANKNAME = (record.getGatheringbankname() == null ? "" : record.getGatheringbankname().toString());
            String GATHERINGBANKACCTCODE = (record.getGatheringbankacctcode() == null ? "" : record.getGatheringbankacctcode().toString());
            String IDNUMBER = (record.getIdnumber() == null ? "" : record.getIdnumber().toString());
            String DIGEST = (record.getDigest() == null ? "" : record.getDigest().toString());
            String BANK = (record.getBank() == null ? "" : record.getBank().toString());
            String CREATEDATE = record.getCreatedate() == null ? "" : record.getCreatedate().toString();
            String Startdate = record.getStartdate() == null ? "" : record.getStartdate().toString();           // 有效截止日
            String enddate = record.getEnddate() == null ? "" : record.getEnddate().toString();                 // 转换格式 yyyy-mm-dd
            String action = record.getAction() == null ? "" : record.getAction().toString();
            m.put("account", account);
            m.put("cardname", cardname);
            m.put("bdgagency", bdgagency);
            m.put("gatheringbankacctname", GATHERINGBANKACCTNAME);
            m.put("gatheringbankname", GATHERINGBANKNAME);
            m.put("gatheringbankacctcode", GATHERINGBANKACCTCODE);
            m.put("idnumber", IDNUMBER);
            m.put("digest", DIGEST);
            m.put("bank", BANK);
            m.put("createdate", CREATEDATE);
            m.put("startdate", Startdate);
            m.put("enddate", enddate);
            m.put("action", action);
            cardList.add(m);
            logger.info("发送卡信息:" + "ACCOUNT=" + account + ";CARDNAME=" + cardname + ";预算单位=" + bdgagency
                    + ";还款帐户名=" + GATHERINGBANKACCTNAME + ";还款账户开户行=" + GATHERINGBANKNAME + ";还款帐号=" + GATHERINGBANKACCTCODE
                    + ";证件号=" + IDNUMBER + ";用途=" + DIGEST + ";财政银行编码=" + BANK + ";开卡日期=" + CREATEDATE
                    + ";有效起始=" + Startdate + ";有效终止=" + enddate + ";卡状态=" + action);
        }
        return cardList;
    }

    @Transactional
    private void updateSendSuc(GwkCardbaseinfo[] gwkCardbaseinfos, String bofcode) {
        if (gwkCardbaseinfos.length > 0) {
            Date dt = new Date();
            GwkCardbaseinfo updata = new GwkCardbaseinfo();
            updata.setSentflag(CardSendFlg.SEND_YES.getCode());
            updata.setOperdate(new Date());
            for (GwkCardbaseinfo record : gwkCardbaseinfos) {
                GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
                example.clear();
                example.createCriteria().andAccountEqualTo(record.getAccount()).andIdnumberEqualTo(record.getIdnumber())
                        .andAreacodeEqualTo(bofcode);
                gwkCardbaseinfoMapper.updateByExampleSelective(updata, example);
                //日志表插入
                SysJoblog sysJoblog = new SysJoblog();
                sysJoblog.setTablename("gwk_cardbaseinfo");
                sysJoblog.setRowpkid(record.getPkid());
                sysJoblog.setJobname("更新");
                sysJoblog.setJobdesc("发送后更新:发送状态=" + updata.getSentflag());
                sysJoblog.setJobtime(dt);
                //插入日志
                sysJoblogMapper.insert(sysJoblog);
            }
        }
    }

    @Transactional
    private void updateSendSuc(List<GwkCardbaseinfo> gwkCardbaseinfos, String bofcode) {
        if (gwkCardbaseinfos.size() > 0) {
            Date dt = new Date();
            GwkCardbaseinfo updata = new GwkCardbaseinfo();
            updata.setSentflag(CardSendFlg.SEND_YES.getCode());
            updata.setOperdate(new Date());
            for (GwkCardbaseinfo record : gwkCardbaseinfos) {
                GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
                example.clear();
                example.createCriteria().andAccountEqualTo(record.getAccount()).andIdnumberEqualTo(record.getIdnumber())
                        .andAreacodeEqualTo(bofcode);
                gwkCardbaseinfoMapper.updateByExampleSelective(updata, example);
                //日志表插入
                SysJoblog sysJoblog = new SysJoblog();
                sysJoblog.setTablename("gwk_cardbaseinfo");
                sysJoblog.setRowpkid(record.getPkid());
                sysJoblog.setJobname("更新");
                sysJoblog.setJobdesc("发送后更新:发送状态=" + updata.getSentflag());
                sysJoblog.setJobtime(dt);
                //插入日志
                sysJoblogMapper.insert(sysJoblog);
            }
        }
    }

    @Transactional
    private void updateSameData(List<Map> rtnlist, String bofcode) {
        Date dt = new Date();
        GwkCardbaseinfo updata = new GwkCardbaseinfo();
        updata.setSentflag(CardSendFlg.SEND_YES.getCode());
        updata.setOperdate(dt);
        for (Map record : rtnlist) {
            GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
            example.clear();
            example.createCriteria().andAccountEqualTo(record.get(RtnTagKey.SAMEACCOUNT).toString())
                    .andIdnumberEqualTo(record.get(RtnTagKey.SAMEIDNUM).toString()).andAreacodeEqualTo(bofcode);
            gwkCardbaseinfoMapper.updateByExampleSelective(updata, example);
            //日志表插入
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_cardbaseinfo");
            sysJoblog.setRowpkid(record.get(RtnTagKey.SAMEACCOUNT).toString());
            sysJoblog.setJobname("更新重复发送");
            sysJoblog.setJobdesc("发送后更新:发送状态=" + updata.getSentflag());
            sysJoblog.setJobtime(dt);
            //插入日志
            sysJoblogMapper.insert(sysJoblog);
        }
    }

    //按条件查询卡信息
    public List<GwkCardbaseinfo> selectCardinfoForQry(String bdgagencyname, String account, String bofcode) {
        GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
        GwkCardbaseinfoExample.Criteria criteria = example.createCriteria();
        if (bdgagencyname != null && !StringUtils.isEmpty(bdgagencyname)) {
            criteria.andBdgagencynameLike("%" + bdgagencyname + "%");
        }
        if (account != null && !StringUtils.isEmpty(account)) {
            criteria.andAccountEqualTo(account);
        }
        criteria.andAreacodeEqualTo(bofcode);
        return gwkCardbaseinfoMapper.selectByExample(example);
    }
}
