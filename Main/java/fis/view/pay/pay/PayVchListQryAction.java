package fis.view.pay.pay;

import fis.common.pay.PayType;
import fis.common.pay.ProcessFlag;
import fis.common.pay.VoucherType;
import fis.repository.pay.model.PayPayvoucher;
import fis.service.pay.BasicinfoQryService;
import fis.service.pay.PayProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PayVchListQryAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProcessFlag processFlag = ProcessFlag.PROCESS_INIT;
    private VoucherType voucherType = VoucherType.VCHTYPE_NORMAL;
    private PayType payType = PayType.PAY_DIRECT;
    @ManagedProperty(value = "#{payProcessService}")
    private PayProcessService payProcessService;
    @ManagedProperty(value = "#{basicinfoQryService}")
    private BasicinfoQryService basicinfoQryService;
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    private List<PayPayvoucher> payPayvoucherList;
    private PayPayvoucher payPayvoucher;
    private Date updatedt;
    private List<SelectItem> bdgagencyList;
    private List<SelectItem> payTypeList;
    private List<SelectItem> voucherTypeList;
    private List<SelectItem> processFlagList;

    @PostConstruct
    public void init() {
        payPayvoucher = new PayPayvoucher();
        bdgagencyList = basicinfoQryService.getBdgagencyItems(true);
        payTypeList = toolsService.getEnuSelectItemList("PAYTYPE", true, false);
        voucherTypeList = toolsService.getEnuSelectItemList("VOUCHERTYPE", true, false);
        processFlagList = toolsService.getEnuSelectItemList("PROCESSFLAG", true, false);
    }

    public String onBtnQryClick() {
        try {
            payPayvoucherList = payProcessService.selectedPayvchByCondition(payPayvoucher.getPrintid(),
                    payPayvoucher.getBdgagency(), payPayvoucher.getPaytype(), payPayvoucher.getVouchertype(),
                    payPayvoucher.getProcessflag(), updatedt);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            MessageUtil.addError("支付业务查询错误：" + e.getMessage());
            return null;
        }
        return null;
    }

    public BasicinfoQryService getBasicinfoQryService() {
        return basicinfoQryService;
    }

    public void setBasicinfoQryService(BasicinfoQryService basicinfoQryService) {
        this.basicinfoQryService = basicinfoQryService;
    }

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public List<SelectItem> getPayTypeList() {
        return payTypeList;
    }

    public void setPayTypeList(List<SelectItem> payTypeList) {
        this.payTypeList = payTypeList;
    }

    public List<SelectItem> getVoucherTypeList() {
        return voucherTypeList;
    }

    public void setVoucherTypeList(List<SelectItem> voucherTypeList) {
        this.voucherTypeList = voucherTypeList;
    }

    public List<SelectItem> getProcessFlagList() {
        return processFlagList;
    }

    public void setProcessFlagList(List<SelectItem> processFlagList) {
        this.processFlagList = processFlagList;
    }

    public Date getUpdatedt() {
        return updatedt;
    }

    public void setUpdatedt(Date updatedt) {
        this.updatedt = updatedt;
    }

    public List<SelectItem> getBdgagencyList() {
        return bdgagencyList;
    }

    public void setBdgagencyList(List<SelectItem> bdgagencyList) {
        this.bdgagencyList = bdgagencyList;
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

    public List<PayPayvoucher> getPayPayvoucherList() {
        return payPayvoucherList;
    }

    public void setPayPayvoucherList(List<PayPayvoucher> payPayvoucherList) {
        this.payPayvoucherList = payPayvoucherList;
    }

    public PayPayvoucher getPayPayvoucher() {
        return payPayvoucher;
    }

    public void setPayPayvoucher(PayPayvoucher payPayvoucher) {
        this.payPayvoucher = payPayvoucher;
    }
}
