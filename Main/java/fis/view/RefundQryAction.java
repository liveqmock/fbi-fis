package fis.view;

import fis.common.constant.RefundProcessSts;
import fis.repository.model.FsRefundinfo;
import fis.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.Message;
import skyline.common.utils.MessageUtil;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    private List<FsRefundinfo> fsRefundinfoList;
    private RefundProcessSts refundProcessSts = RefundProcessSts.PROCESS_INIT;
    private List<SelectItem> procStsSelects;
    private String refundAppcode;
    private String strRefundProcessSts;
    private String startdate;
    private String enddate;
    private String parambofcode;

    @PostConstruct
    public void init() {
        try {
            parambofcode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bofcode").toString();
            procStsSelects = toolsService.getEnuSelectItemList("REFUNDPROCESSSTS",true,false);
            fsRefundinfoList = paymentService.selectRefundinfo(parambofcode,refundAppcode,strRefundProcessSts,startdate,enddate);
        } catch (Exception ex) {
            logger.error("查询缴款书信息失败:" + ex.getMessage());
            MessageUtil.addError("查询缴款书信息失败:" + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
    }

    public String onBtnQueryClick() {
        try{
            fsRefundinfoList = paymentService.selectRefundinfo(parambofcode,refundAppcode,strRefundProcessSts,startdate,enddate);
        } catch (Exception ex) {
            MessageUtil.addError("查询退付信息失败:" + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        return null;
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

    public String getRefundAppcode() {
        return refundAppcode;
    }

    public void setRefundAppcode(String refundAppcode) {
        this.refundAppcode = refundAppcode;
    }

    public String getStrRefundProcessSts() {
        return strRefundProcessSts;
    }

    public void setStrRefundProcessSts(String strRefundProcessSts) {
        this.strRefundProcessSts = strRefundProcessSts;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public List<SelectItem> getProcStsSelects() {
        return procStsSelects;
    }

    public void setProcStsSelects(List<SelectItem> procStsSelects) {
        this.procStsSelects = procStsSelects;
    }

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }
}
