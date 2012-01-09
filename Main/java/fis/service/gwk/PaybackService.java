package fis.service.gwk;

import fis.common.gwk.constant.ConfirmPayFlg;
import fis.repository.gwk.dao.GwkPaybackinfoMapper;
import fis.repository.gwk.model.GwkPaybackinfo;
import fis.repository.gwk.model.GwkPaybackinfoExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: 下午11:12
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PaybackService {
    @Resource
    private GwkPaybackinfoMapper gwkPaybackinfoMapper;

    //获取数据
    public List<GwkPaybackinfo> getPaybackinfoByVch(String bofcode, String vchid) throws Exception {
        List<GwkPaybackinfo> dataList = new ArrayList<GwkPaybackinfo>();
        GwkPaybackinfoExample example = new GwkPaybackinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andVoucheridEqualTo(vchid);
        dataList = gwkPaybackinfoMapper.selectByExample(example);
        if (dataList == null || dataList.size() < 1) {
            try {
                Map m = new HashMap();
                m.put("VOUCHERID", vchid);
                //todo 获取
                //todo 插入
                dataList = gwkPaybackinfoMapper.selectByExample(example);
            } catch (Exception ex) {
                throw new RuntimeException("获取支付凭证信息失败:" + ex.getMessage());
            }
        }
        return dataList;
    }

    //更新确认支付标志
    @Transactional
    public void updatePaybackinfoByVch(String bofcode,String vchid) throws Exception {
        GwkPaybackinfoExample example = new GwkPaybackinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andVoucheridEqualTo(vchid);
        GwkPaybackinfo record = new GwkPaybackinfo();
        record.setConfirmpayflag(ConfirmPayFlg.CONFIRMPAY_VALID.getCode());
        gwkPaybackinfoMapper.updateByExampleSelective(record,example);
    }
}
