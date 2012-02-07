package fis.view.pay.pay;

import fis.common.pay.PayType;
import fis.common.pay.ProcessFlag;
import fis.common.pay.VoucherType;
import fis.repository.pay.model.PayPayvoucher;
import fis.service.pay.PayProcessService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: ÏÂÎç3:09
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PayVchQryAction implements Serializable {
    private ProcessFlag processFlag = ProcessFlag.PROCESS_INIT;
    private VoucherType voucherType = VoucherType.VCHTYPE_NORMAL;
    private PayType payType = PayType.PAY_DIRECT;

    private PayPayvoucher payPayvoucherDetl;

    @ManagedProperty(value = "#{payProcessService}")
    private PayProcessService payProcessService;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String guid = paramMap.get("guid").toString();
        payPayvoucherDetl = payProcessService.selectedPayvchByguid(guid);
    }

    public PayPayvoucher getPayPayvoucherDetl() {
        return payPayvoucherDetl;
    }

    public void setPayPayvoucherDetl(PayPayvoucher payPayvoucherDetl) {
        this.payPayvoucherDetl = payPayvoucherDetl;
    }

    public ProcessFlag getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(ProcessFlag processFlag) {
        this.processFlag = processFlag;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public PayProcessService getPayProcessService() {
        return payProcessService;
    }

    public void setPayProcessService(PayProcessService payProcessService) {
        this.payProcessService = payProcessService;
    }
}
