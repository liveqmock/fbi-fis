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
 * Time: ����1:15
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
    private String parambofcode;  //�����ֱ���
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
                MessageUtil.addWarn("û�����ݣ�����ɿ������Ƿ���ȷ��");
                return null;
            } else {
                fsPaymentinfo = fsPaymentinfoList.get(0);
                if (!fsPaymentinfo.getProcessstatus().equals(ProcessStatus.PROCESS_INIT.getCode())
                        && !fsPaymentinfo.getProcessstatus().equals(ProcessStatus.PROCESS_CONFIRMFAIL.getCode())) {
                    String processstsname = processStatus.getAliasEnums().get(fsPaymentinfo.getProcessstatus()).getTitle();
                    MessageUtil.addWarn("�ɿ��鴦��״̬Ϊ " + processstsname + "���޷�����ȷ���տ");
                    setButtonDisabled(true);
                    return null;
                }
                setButtonDisabled(false);
            }
        } catch (Exception ex) {
            logger.error("��ȡ�ɿ�����Ϣʧ��:���=" + paynotescode + ";" + ex.getMessage());
            logger.error("��ȡ�ɿ�����Ϣʧ��:���=" + paynotescode + ";" + ex.getMessage());
            MessageUtil.addError("��ȡ�ɿ�����Ϣʧ��:���=" + paynotescode + ";" + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    /*����button״̬*/
    private void setButtonDisabled(boolean disabled) {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        HtmlCommandButton btnPay = (HtmlCommandButton) viewRoot.findComponent("editForm:btnPay");
        btnPay.setDisabled(disabled);
    }

    public String onBtnPayClick() {
        try {
            if (fsPaymentinfo.getPaynotescode() == null
                    || StringUtils.isEmpty(fsPaymentinfo.getPaynotescode().toString())) {
                MessageUtil.addWarn("��������ɿ����Ż�ȡ��Ϣ��");
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
        MessageUtil.addInfo("�տ���ȷ�ϳɹ���");
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
