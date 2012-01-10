package fis.service.gwk;

import fis.common.gwk.constant.RtnTagKey;
import fis.common.gwk.constant.CardSendFlg;
import fis.repository.gwk.dao.GwkCardbaseinfoMapper;
import fis.repository.gwk.model.GwkCardbaseinfo;
import fis.repository.gwk.model.GwkCardbaseinfoExample;
import gov.mof.fasp.service.BankService;
import gov.mof.fasp.service.adapter.client.FaspServiceAdapter;
import org.apache.commons.lang.StringUtils;
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
    @Resource
    private GwkCardbaseinfoMapper gwkCardbaseinfoMapper;

    //待发送卡信息查询
    public List<GwkCardbaseinfo> selectCardinfos(String bofcode, String sendflag) {
        GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andSentflagEqualTo(sendflag).andStatusEqualTo("1");
        return gwkCardbaseinfoMapper.selectByExample(example);
    }

    //发送卡信息
    public String sendCardinfos(GwkCardbaseinfo[] gwkCardbaseinfos, String bofcode) throws Exception {
        List cardList = getSendList(gwkCardbaseinfos);
        List rtnlist = new ArrayList();
        try {
            SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            String nowYear = yearsdf.format(dt);
            String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
            String branchbankcode = PropertyManager.getProperty("gwk.branchbankcode");
            String finorgcode = PropertyManager.getProperty("gwk.finorgcode");
            BankService service = FaspServiceAdapter.getBankService();
            rtnlist = service.writeOfficeCard(applicationid, branchbankcode, nowYear, finorgcode, cardList);
        } catch (Exception ex) {
            throw new RuntimeException("发送卡信息失败:" + ex.getMessage());
        }
        //返回信息处理
        if (rtnlist != null && rtnlist.size() > 0) {
            Map m = (Map)rtnlist.get(0);
            String msg = m.get(RtnTagKey.MESSAGE).toString();
            try {
                if (m.get(RtnTagKey.RESULT).toString().equalsIgnoreCase(RtnTagKey.RESULT_SUCCESS)) {
                    //返回成功  更新 sendflag＝1
                    updateSendSuc(gwkCardbaseinfos, bofcode);
                    return RtnTagKey.RESULT_SUCCESS.toString();
                } else {
                    //返回失败 更新重复id
                    if (m.size() > 2) {
                        updateSameData(rtnlist,bofcode);
                    }
                    return msg;
                }
            } catch (Exception ex) {
                throw new RuntimeException("卡信息发送成功后更新本地数据失败:" + ex.getMessage());
            }
        }
        return RtnTagKey.RESULT_SUCCESS.toString();
    }

    private List getSendList(GwkCardbaseinfo[] gwkCardbaseinfos) {
        List cardList = new ArrayList();       //发送数据
        for (GwkCardbaseinfo record : gwkCardbaseinfos) {
            Map m = new HashMap();
            String account = record.getAccount() == null ? "" : record.getAccount();
            String cardname = record.getCardname() == null ? "" : record.getCardname();
            String bdgagency = record.getBdgagency() == null ? "" : record.getBdgagency();
            String GATHERINGBANKACCTNAME = record.getGatheringbankacctname() == null ? "" : record.getGatheringbankacctname();
            String GATHERINGBANKNAME = record.getGatheringbankname() == null ? "" : record.getGatheringbankname();
            String GATHERINGBANKACCTCODE = record.getGatheringbankacctcode() == null ? "" : record.getGatheringbankacctcode();
            String IDNUMBER = record.getIdnumber() == null ? "" : record.getIdnumber();
            String DIGEST = record.getDigest() == null ? "" : record.getDigest();
            String BANK = record.getBank() == null ? "" : String.valueOf(record.getBank());
            String CREATEDATE = record.getCreatedate() == null ? "" : record.getCreatedate();
            String Startdate = record.getStartdate() == null ? "" : record.getStartdate();           // 有效截止日期？
            String enddate = record.getEnddate() == null ? "" : record.getEnddate();                 // 转换格式 yyyy-mm-dd
            if (!StringUtils.isEmpty(enddate)) {
//                Cal
                enddate = "2016-09-30";
            }
            String action = record.getAction() == null ? "" : record.getAction();
            m.put("ACCOUNT", account);
            m.put("CARDNAME", cardname);
            m.put("Bdgagency", bdgagency);
            m.put("GATHERINGBANKACCTNAME ", GATHERINGBANKACCTNAME);
            m.put("GATHERINGBANKNAME ", GATHERINGBANKNAME);
            m.put("GATHERINGBANKACCTCODE ", GATHERINGBANKACCTCODE);
            m.put("IDNUMBER", IDNUMBER);
            m.put("DIGEST", DIGEST);
            m.put("BANK", BANK);
            m.put("CREATEDATE", CREATEDATE);
            m.put("Startdate", Startdate);
            m.put("enddate", enddate);
            m.put("action", action);
            cardList.add(m);
        }
        return cardList;
    }

    @Transactional
    private void updateSendSuc(GwkCardbaseinfo[] gwkCardbaseinfos, String bofcode) {
        if (gwkCardbaseinfos.length > 0) {
            GwkCardbaseinfo updata = new GwkCardbaseinfo();
            updata.setSentflag(CardSendFlg.SEND_YES.getCode());
            updata.setOperdate(new Date());
            for (GwkCardbaseinfo record : gwkCardbaseinfos) {
                GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
                example.clear();
                example.createCriteria().andAccountEqualTo(record.getAccount()).andIdnumberEqualTo(record.getIdnumber())
                        .andAreacodeEqualTo(bofcode);
                gwkCardbaseinfoMapper.updateByExampleSelective(updata, example);
            }
        }
    }
    
    @Transactional
    private void updateSameData(List<Map> rtnlist,String bofcode) {
        GwkCardbaseinfo updata = new GwkCardbaseinfo();
        updata.setSentflag(CardSendFlg.SEND_YES.getCode());
        updata.setOperdate(new Date());
        for (Map record : rtnlist) {
            GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
            example.clear();
            example.createCriteria().andAccountEqualTo(record.get(RtnTagKey.SAMEACCOUNT).toString())
                    .andIdnumberEqualTo(record.get(RtnTagKey.SAMEIDNUM).toString()).andAreacodeEqualTo(bofcode);
            gwkCardbaseinfoMapper.updateByExampleSelective(updata, example);
        }
    }

    //按条件查询卡信息
    public List<GwkCardbaseinfo> selectCardinfoForQry(String bdgagencyname,String account,String bofcode) {
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
