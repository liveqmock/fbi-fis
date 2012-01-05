package fis.view.fs;

import fis.common.constant.ProcessStatus;
import fis.common.constant.RecfeeFlag;
import fis.repository.fs.model.FsPaymentinfo;
import fis.service.fs.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-12-23
 * Time: 上午11:40
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PaymentToactAction {
    private static final Logger logger = LoggerFactory.getLogger(PaymentToactAction.class);
    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;
    private List<FsPaymentinfo> fsPaymentinfoList1;
    private FsPaymentinfo[] fsPaymentinfos;
    private List<FsPaymentinfo> fsPaymentinfoList2;
    private ProcessStatus processStatus = ProcessStatus.PROCESS_INIT;
    private RecfeeFlag recfeeFlag = RecfeeFlag.RECFEE_NOTOACT;

    private String parambofcode;

    @PostConstruct
    public void init() {
        try {
            parambofcode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bofcode").toString();
            queryPayinfo();
        } catch (Exception ex) {
            logger.error("查询到账信息错误：" + ex.getMessage());
            MessageUtil.addError("查询到账信息错误：" + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
    }

    public String onMultipleSend() {
        if (this.fsPaymentinfos.length < 1) {
            MessageUtil.addWarn("请选择至少一条数据。");
            return null;
        }
        try {
            paymentService.sendPayinfoToact(fsPaymentinfos, ProcessStatus.PROCESS_TOACTSUC.getCode(),parambofcode);
        } catch (Exception ex) {
            logger.error("发送到账信息失败：" + ex.getMessage());
            MessageUtil.addError("发送到账信息失败：" + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        try {
            queryPayinfo();
        } catch (Exception ex) {
            logger.error("查询到账信息错误：" + ex.getMessage());
            MessageUtil.addError("查询到账信息错误：" + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }

        MessageUtil.addInfo("到账已确认成功。");

        return null;
    }

    private void queryPayinfo() {
        fsPaymentinfoList1 = paymentService.selectPayinfoNoToact(parambofcode);
        fsPaymentinfoList2 = paymentService.selectPayinfoToact(parambofcode);
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public List<FsPaymentinfo> getFsPaymentinfoList1() {
        return fsPaymentinfoList1;
    }

    public void setFsPaymentinfoList1(List<FsPaymentinfo> fsPaymentinfoList1) {
        this.fsPaymentinfoList1 = fsPaymentinfoList1;
    }

    public List<FsPaymentinfo> getFsPaymentinfoList2() {
        return fsPaymentinfoList2;
    }

    public void setFsPaymentinfoList2(List<FsPaymentinfo> fsPaymentinfoList2) {
        this.fsPaymentinfoList2 = fsPaymentinfoList2;
    }

    public FsPaymentinfo[] getFsPaymentinfos() {
        return fsPaymentinfos;
    }

    public void setFsPaymentinfos(FsPaymentinfo[] fsPaymentinfos) {
        this.fsPaymentinfos = fsPaymentinfos;
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
