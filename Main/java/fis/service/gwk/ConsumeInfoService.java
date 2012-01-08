package fis.service.gwk;

import fis.common.gwk.constant.ConsumeInfoSts;
import fis.repository.gwk.dao.GwkConsumeinfoMapper;
import fis.repository.gwk.model.GwkConsumeinfo;
import fis.repository.gwk.model.GwkConsumeinfoExample;
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
 * Time: 下午10:02
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ConsumeInfoService {

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
    public void sendConsumeinfo(GwkConsumeinfo[] gwkConsumeinfos, String bofcode) throws Exception {
        List consumeList = getSendList(gwkConsumeinfos);       //发送数据
        try {
            //todo send
            //todo update status
        } catch (Exception ex) {
            throw new RuntimeException("发送卡信息失败:" + ex.getMessage());
        }
    }

    private List getSendList(GwkConsumeinfo[] gwkConsumeinfos) {
        List consumeList = new ArrayList();       //发送数据
        for (GwkConsumeinfo record:gwkConsumeinfos) {
            Map m = new HashMap();
            String lsh = record.getLsh()==null?"":record.getLsh();
            String account = record.getAccount()==null?"":record.getAccount();
            String cardname = record.getCardname()==null?"":record.getCardname();
            String busidate = record.getBusidate()==null?"":record.getBusidate();
            Double busimoney = record.getBusimoney().doubleValue();
            String businame = record.getBusiname()==null?"":record.getBusiname();
            String limitdate = record.getCleardate()==null?"":record.getCleardate();
            //todo ? 交易处理码
            String tx_cd = record.getTxCd()==null?"":record.getTxCd();

            m.put("ID", lsh);
            m.put("ACCOUNT", account);
            m.put("CARDNAME", cardname);
            m.put("BUSIDATE", busidate);
            m.put("BUSIMONEY", busimoney);
            m.put("BUSINAME", businame);
            m.put("Limitdate", limitdate);
            consumeList.add(m);
        }
        return consumeList;
    }
}
