package fis.view;

import fis.common.constant.ProcessStatus;
import fis.common.constant.RecfeeFlag;
import fis.repository.model.FsPaymentinfo;
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
public class PaymentQryAction {
    private static final Logger logger = LoggerFactory.getLogger(PaymentQryAction.class);
    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;
    private List<FsPaymentinfo> fsPaymentinfoList;
    private ProcessStatus processStatus = ProcessStatus.PROCESS_INIT;
    private RecfeeFlag recfeeFlag = RecfeeFlag.RECFEE_NOTOACT;

    @PostConstruct
    public void init() {
        try {
            fsPaymentinfoList = paymentService.selectPayinfo();
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

    public List<FsPaymentinfo> getFsPaymentinfoList() {
        return fsPaymentinfoList;
    }

    public void setFsPaymentinfoList(List<FsPaymentinfo> fsPaymentinfoList) {
        this.fsPaymentinfoList = fsPaymentinfoList;
    }

    public ProcessStatus getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(ProcessStatus processStatus) {
        this.processStatus = processStatus;
    }

    public RecfeeFlag getRecfeeFlag() {
        return recfeeFlag;
    }

    public void setRecfeeFlag(RecfeeFlag recfeeFlag) {
        this.recfeeFlag = recfeeFlag;
    }
}
