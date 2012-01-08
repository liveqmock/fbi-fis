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
    public void sendCardinfos(GwkCardbaseinfo[] gwkCardbaseinfos) throws Exception {
        List cardList = new ArrayList();       //发送数据
        for (GwkCardbaseinfo record : gwkCardbaseinfos) {
            Map m = new HashMap();
            String account = record.getAccount().trim();
            String cardname = record.getCardname().trim();
            String bdgagency = record.getBdgagency().trim();
            String GATHERINGBANKACCTNAME = record.getGatheringbankacctname().trim();
            String GATHERINGBANKNAME = record.getGatheringbankname().trim();
            String GATHERINGBANKACCTCODE = record.getGatheringbankacctcode().trim();
            String IDNUMBER = record.getIdnumber().trim();
            String DIGEST = record.getDigest().trim();
            String BANK = String.valueOf(record.getBank()).trim();
            String CREATEDATE = record.getCreatedate().trim();
            String Startdate = record.getStartdate().trim();
            String enddate = record.getEnddate().trim();
            String action = record.getAction().trim();

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
        try {
            //todo send
            //todo update status
        } catch (Exception ex) {
            throw new RuntimeException("发送卡信息失败:" + ex.getMessage());
        }
    }
}
