package fis.service.gwk;

import fis.common.gwk.constant.CardSendFlg;
import fis.repository.gwk.dao.GwkCardbaseinfoMapper;
import fis.repository.gwk.model.GwkCardbaseinfo;
import fis.repository.gwk.model.GwkCardbaseinfoExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //发送卡信息查询
    public List<GwkCardbaseinfo> selectCardinfos(String bofcode, String sendflag) {
        GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andSentflagEqualTo(sendflag).andStatusEqualTo("1");
        return gwkCardbaseinfoMapper.selectByExample(example);
    }

    //发送卡信息
    public void sendCardinfos(GwkCardbaseinfo[] gwkCardbaseinfos,String bofcode) throws Exception {
        List cardList = getSendList(gwkCardbaseinfos);
        try {
            //todo send
            //todo update status
        } catch (Exception ex) {
            throw new RuntimeException("发送卡信息失败:" + ex.getMessage());
        }
    }

    private List getSendList(GwkCardbaseinfo[] gwkCardbaseinfos) {
        List cardList = new ArrayList();       //发送数据
        for (GwkCardbaseinfo record : gwkCardbaseinfos) {
            Map m = new HashMap();
            String account = record.getAccount()==null?"":record.getAccount();
            String cardname = record.getCardname()==null?"":record.getCardname();
            String bdgagency = record.getBdgagency()==null?"":record.getBdgagency();
            String GATHERINGBANKACCTNAME = record.getGatheringbankacctname()==null?"":record.getGatheringbankacctname();
            String GATHERINGBANKNAME = record.getGatheringbankname()==null?"":record.getGatheringbankname();
            String GATHERINGBANKACCTCODE = record.getGatheringbankacctcode()==null?"":record.getGatheringbankacctcode();
            String IDNUMBER = record.getIdnumber()==null?"":record.getIdnumber();
            String DIGEST = record.getDigest()==null?"":record.getDigest();
            String BANK = record.getBank()==null?"":String.valueOf(record.getBank());
            String CREATEDATE = record.getCreatedate()==null?"":record.getCreatedate();
            String Startdate = record.getStartdate()==null?"":record.getStartdate();
            String enddate = record.getEnddate()==null?"":record.getEnddate();
            String action = record.getAction()==null?"":record.getAction();
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
}
