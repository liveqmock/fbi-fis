package fis.view;

import fis.common.constant.ProcessStatus;
import fis.common.constant.RecfeeFlag;
import fis.repository.model.FsPaymentinfo;
import fis.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PaymentQryAction {
    private static final Logger logger = LoggerFactory.getLogger(PaymentQryAction.class);
    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    private List<FsPaymentinfo> fsPaymentinfoList;
    private ProcessStatus processStatus = ProcessStatus.PROCESS_INIT;
    private RecfeeFlag recfeeFlag = RecfeeFlag.RECFEE_NOTOACT;
    private String parambofcode;
    private List<SelectItem> procStsSelects;
    private String strProcesssts;
    private String notescode;
    private String startdate;
    private String enddate;

    @PostConstruct
    public void init() {
        try {
            procStsSelects = toolsService.getEnuSelectItemList("PROCESSSTS", true, false);
            parambofcode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bofcode").toString();
            if (strProcesssts != null && !strProcesssts.equals(ProcessStatus.PROCESS_TOACTSUC.getCode())) {
                fsPaymentinfoList = paymentService.selectPayinfo(strProcesssts, parambofcode, notescode, startdate, enddate, null, null);
            } else {
                fsPaymentinfoList = paymentService.selectPayinfo(strProcesssts, parambofcode, notescode, null, null, startdate, enddate);
            }
        } catch (Exception ex) {
            logger.error("查询缴款书信息失败:" + ex.getMessage());
            MessageUtil.addError("查询缴款书信息失败:" + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            ex.printStackTrace();
        }
    }

    public String onBtnQueryClick() {
        if (strProcesssts != null && !strProcesssts.equals(ProcessStatus.PROCESS_TOACTSUC.getCode())) {
            fsPaymentinfoList = paymentService.selectPayinfo(strProcesssts, parambofcode, notescode, startdate, enddate, null, null);
        } else {
            fsPaymentinfoList = paymentService.selectPayinfo(strProcesssts, parambofcode, notescode, null, null, startdate, enddate);
        }
        return null;
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

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public List<SelectItem> getProcStsSelects() {
        return procStsSelects;
    }

    public void setProcStsSelects(List<SelectItem> procStsSelects) {
        this.procStsSelects = procStsSelects;
    }

    public String getStrProcesssts() {
        return strProcesssts;
    }

    public void setStrProcesssts(String strProcesssts) {
        this.strProcesssts = strProcesssts;
    }

    public String getNotescode() {
        return notescode;
    }

    public void setNotescode(String notescode) {
        this.notescode = notescode;
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
}
