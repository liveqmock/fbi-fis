package fis.service.qdf;

import fis.repository.qdf.dao.FsQdfPaymentInfoMapper;
import fis.repository.qdf.dao.FsQdfPaymentItemMapper;
import fis.repository.qdf.model.FsQdfPaymentInfo;
import fis.repository.qdf.model.FsQdfPaymentInfoExample;
import fis.repository.qdf.model.FsQdfPaymentItem;
import fis.repository.qdf.model.FsQdfPaymentItemExample;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 13-5-19
 * Time: ÏÂÎç9:31
 * To change this template use File | Settings | File Templates.
 */
public class PayBillService {

    @Resource
    private FsQdfPaymentInfoMapper fsQdfPaymentInfoMapper;
    private FsQdfPaymentItemMapper fsQdfPaymentItemMapper;
    public FsQdfPaymentInfo selectPayBillInfo(String pjzl,String jksbh){
        FsQdfPaymentInfoExample example = new FsQdfPaymentInfoExample();
        example.createCriteria().andPjzlEqualTo(pjzl).andJksbhEqualTo(jksbh);
        List<FsQdfPaymentInfo> fsQdfPaymentInfoList = fsQdfPaymentInfoMapper.selectByExample(example);
        return fsQdfPaymentInfoList.size()>0?fsQdfPaymentInfoList.get(0):null;
    }

    public List<FsQdfPaymentItem> selectPayBillDetailList(String pjzl,String jksbh){
        FsQdfPaymentItemExample example = new FsQdfPaymentItemExample();
        example.createCriteria().andPjzlEqualTo(pjzl).andJksbhEqualTo(jksbh);
        List<FsQdfPaymentItem> fsQdfPaymentItemList = fsQdfPaymentItemMapper.selectByExample(example);
        return fsQdfPaymentItemList;
    }


}
