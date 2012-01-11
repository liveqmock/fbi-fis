package fis.service.gwk;

import fis.common.gwk.constant.RtnTagKey;
import fis.common.gwk.constant.ConsumeInfoSts;
import fis.repository.gwk.dao.GwkConsumeinfoMapper;
import fis.repository.gwk.model.GwkConsumeinfo;
import fis.repository.gwk.model.GwkConsumeinfoExample;
import gov.mof.fasp.service.BankService;
import gov.mof.fasp.service.adapter.client.FaspServiceAdapter;
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

    //获取待发消费信息
    public List<GwkConsumeinfo> selectConsumeSendNo(String bofcode) {
        String status = ConsumeInfoSts.SEND_SUC.getCode();
        GwkConsumeinfoExample example = new GwkConsumeinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andStatusNotEqualTo(status);
        return gwkConsumeinfoMapper.selectByExample(example);
    }

    //发送
    public String sendConsumeinfo(GwkConsumeinfo[] gwkConsumeinfos, String bofcode) throws Exception {
        List consumeList = getSendList(gwkConsumeinfos);       //发送数据
        List rtnlist = new ArrayList();
        try {
            //send
            SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            String nowYear = yearsdf.format(dt);
            String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
            String branchbankcode = PropertyManager.getProperty("gwk.sub_branchbankcode");
            String finorgcode = PropertyManager.getProperty("gwk.finorgcode");
            BankService service = FaspServiceAdapter.getBankService();
            rtnlist = service.writeConsumeInfo(applicationid, branchbankcode, nowYear, finorgcode, consumeList);
        } catch (Exception ex) {
            throw new RuntimeException("发送消费信息失败:" + ex.getMessage());
        }
        //返回信息处理
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
                throw new RuntimeException("消费信息发送成功后更新本地数据失败:" + ex.getMessage());
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
        List consumeList = getSendList(gwkConsumeinfos);       //发送数据
        List rtnlist = new ArrayList();
        try {
            //send
            SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            String nowYear = yearsdf.format(dt);
            String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
            String branchbankcode = PropertyManager.getProperty("gwk.sub_branchbankcode");
            String finorgcode = PropertyManager.getProperty("gwk.finorgcode");
            BankService service = FaspServiceAdapter.getBankService();
//            rtnlist = service.writeConsumeInfo(applicationid, branchbankcode, nowYear, finorgcode, consumeList);
        } catch (Exception ex) {
            throw new RuntimeException("发送消费信息失败:" + ex.getMessage());
        }
        //返回信息处理
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
                throw new RuntimeException("消费信息发送成功后更新本地数据失败:" + ex.getMessage());
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

    @Transactional
    private void updateSendSuc(GwkConsumeinfo[] gwkConsumeinfos, String bofcode) {
        if (gwkConsumeinfos.length > 0) {
            GwkConsumeinfo updata = new GwkConsumeinfo();
            updata.setStatus(ConsumeInfoSts.SEND_SUC.getCode());
            updata.setOperdate(new Date());
            for (GwkConsumeinfo record : gwkConsumeinfos) {
                GwkConsumeinfoExample example = new GwkConsumeinfoExample();
                example.clear();
                example.createCriteria().andAreacodeEqualTo(bofcode).andAccountEqualTo(record.getAccount()).andLshEqualTo(record.getLsh());
                gwkConsumeinfoMapper.updateByExampleSelective(updata, example);
            }
        }
    }

    @Transactional
    private void updateSendSuc(List<GwkConsumeinfo> gwkConsumeinfos, String bofcode) {
        if (gwkConsumeinfos.size() > 0) {
            GwkConsumeinfo updata = new GwkConsumeinfo();
            updata.setStatus(ConsumeInfoSts.SEND_SUC.getCode());
            updata.setOperdate(new Date());
            for (GwkConsumeinfo record : gwkConsumeinfos) {
                GwkConsumeinfoExample example = new GwkConsumeinfoExample();
                example.clear();
                example.createCriteria().andAreacodeEqualTo(bofcode).andAccountEqualTo(record.getAccount()).andLshEqualTo(record.getLsh());
                gwkConsumeinfoMapper.updateByExampleSelective(updata, example);
            }
        }
    }

    @Transactional
    private void updateSameData(List<Map> rtnlist, String bofcode) {
        GwkConsumeinfo updata = new GwkConsumeinfo();
        updata.setStatus(ConsumeInfoSts.SEND_SUC.getCode());
        updata.setOperdate(new Date());
        for (Map record : rtnlist) {
            GwkConsumeinfoExample example = new GwkConsumeinfoExample();
            example.clear();
            example.createCriteria().andAreacodeEqualTo(bofcode).andAccountEqualTo(record.get(RtnTagKey.SAMEACCOUNT).toString()).andLshEqualTo(record.get(RtnTagKey.SAMEID).toString());
            gwkConsumeinfoMapper.updateByExampleSelective(updata, example);
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
