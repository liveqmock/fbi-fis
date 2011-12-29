package fis.view;

import fis.common.constant.ProcessStatus;
import fis.repository.model.FsPaymentinfo;
import fis.service.PaymentService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-12-21
 * Time: 下午1:15
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PaymentProcessAction {
    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessAction.class);

    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;
    private List<FsPaymentinfo> fsPaymentinfoList;
    private FsPaymentinfo fsPaymentinfo;
    private String paynotescode;
    private String checkcode;
    private String parambofcode;  //财政局编码
    private ProcessStatus processStatus = ProcessStatus.PROCESS_INIT;

    @PostConstruct
    public void init() {
        parambofcode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bofcode").toString();
        fsPaymentinfo = new FsPaymentinfo();
    }

    public String onBtnAccept() {
        try {
            fsPaymentinfoList = paymentService.selectPayinfoByPaynotescd(parambofcode, paynotescode, checkcode);
            if (fsPaymentinfoList == null || fsPaymentinfoList.size() < 1) {
                MessageUtil.addWarn("没有数据，请检查缴款书编号是否正确。");
                return null;
            } else {
                fsPaymentinfo = fsPaymentinfoList.get(0);
                if (!fsPaymentinfo.getProcessstatus().equals(ProcessStatus.PROCESS_INIT.getCode())
                        && !fsPaymentinfo.getProcessstatus().equals(ProcessStatus.PROCESS_CONFIRMFAIL.getCode())) {
                    String processstsname = processStatus.getAliasEnums().get(fsPaymentinfo.getProcessstatus()).getTitle();
                    MessageUtil.addWarn("缴款书处理状态为 " + processstsname + "，无法进行确认收款。");
                    setButtonDisabled(true);
                    return null;
                }
                setButtonDisabled(false);
            }
        } catch (Exception ex) {
            logger.error("获取缴款书信息失败:编号=" + paynotescode + ";" + ex.getMessage());
            logger.error("获取缴款书信息失败:编号=" + paynotescode + ";" + ex.getMessage());
            MessageUtil.addError("获取缴款书信息失败:编号=" + paynotescode + ";" + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    /*设置button状态*/
    private void setButtonDisabled(boolean disabled) {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        HtmlCommandButton btnPay = (HtmlCommandButton) viewRoot.findComponent("editForm:btnPay");
        btnPay.setDisabled(disabled);
    }

    public String onBtnPayClick() {
        try {
            if (fsPaymentinfo.getPaynotescode() == null
                    || StringUtils.isEmpty(fsPaymentinfo.getPaynotescode().toString())) {
                MessageUtil.addWarn("请先输入缴款书编号获取信息。");
                return null;
            }
            List<FsPaymentinfo> fsPaymentinfoListsend = new ArrayList<FsPaymentinfo>();
            fsPaymentinfoListsend.add(fsPaymentinfo);
            paymentService.sendPayinfoConfirm(fsPaymentinfoListsend, ProcessStatus.PROCESS_CONFIRMSUC.getCode(), parambofcode);
            setButtonDisabled(true);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            MessageUtil.addError(ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            ex.printStackTrace();
            return null;
        }
        MessageUtil.addInfo("收款已确认成功。");
        return null;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public FsPaymentinfo getFsPaymentinfo() {
        return fsPaymentinfo;
    }

    public void setFsPaymentinfo(FsPaymentinfo fsPaymentinfo) {
        this.fsPaymentinfo = fsPaymentinfo;
    }

    public String getPaynotescode() {
        return paynotescode;
    }

    public void setPaynotescode(String paynotescode) {
        this.paynotescode = paynotescode;
    }

    public ProcessStatus getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(ProcessStatus processStatus) {
        this.processStatus = processStatus;
    }

    public List<FsPaymentinfo> getFsPaymentinfoList() {
        return fsPaymentinfoList;
    }

    public void setFsPaymentinfoList(List<FsPaymentinfo> fsPaymentinfoList) {
        this.fsPaymentinfoList = fsPaymentinfoList;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
}
