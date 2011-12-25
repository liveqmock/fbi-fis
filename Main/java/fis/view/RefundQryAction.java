package fis.view;

import fis.common.constant.RefundProcessSts;
import fis.repository.model.FsRefundinfo;
import fis.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-12-25
 * Time: 下午9:30
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class RefundQryAction {
    private static final Logger logger = LoggerFactory.getLogger(RefundQryAction.class);
    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;
    private List<FsRefundinfo> fsRefundinfoList;
    private RefundProcessSts refundProcessSts = RefundProcessSts.PROCESS_INIT;

    @PostConstruct
    public void init() {
        try {
            fsRefundinfoList = paymentService.selectRefundinfo();
        } catch (Exception ex) {
            logger.error("查询缴款书信息失败:" + ex.getCause().getMessage());
            MessageUtil.addError("查询缴款书信息失败:" + ex.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public List<FsRefundinfo> getFsRefundinfoList() {
        return fsRefundinfoList;
    }

    public void setFsRefundinfoList(List<FsRefundinfo> fsRefundinfoList) {
        this.fsRefundinfoList = fsRefundinfoList;
    }

    public RefundProcessSts getRefundProcessSts() {
        return refundProcessSts;
    }

    public void setRefundProcessSts(RefundProcessSts refundProcessSts) {
        this.refundProcessSts = refundProcessSts;
    }
}
