package fis.view.fs;

import fis.common.constant.RefundProcessSts;
import fis.repository.fs.model.FsRefundinfo;
import fis.service.fs.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;
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
 * Date: 11-12-25
 * Time: ����5:58
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class RefundAction {
    private static final Logger logger = LoggerFactory.getLogger(RefundAction.class);
    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;
    private List<FsRefundinfo> fsRefundinfoList;
    private String refundapplcode;
    private RefundProcessSts refundProcessSts = RefundProcessSts.PROCESS_INIT;
    private String parambofcode;  //�����ֱ���
    private String performdept;   //ִ�յ�λ���
    private String banksign;      //���б�ʶ

    @PostConstruct
    public void init() {
        banksign = PropertyManager.getProperty("fbifis.sys.bank.code");
    }

    public String onBtnAcceptClick() {
        try{
            parambofcode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bofcode").toString();
            fsRefundinfoList = paymentService.selectRefundinfoByAppcd(refundapplcode,parambofcode,performdept);
            if (fsRefundinfoList == null || fsRefundinfoList.size() < 1) {
                MessageUtil.addWarn("û�����ݣ�������������Ƿ���ȷ��");
                return null;
            }
            FsRefundinfo fsRefundinfo = fsRefundinfoList.get(0);
            if (!fsRefundinfo.getProcessstatus().equals(RefundProcessSts.PROCESS_INIT.getCode())
                    && !fsRefundinfo.getProcessstatus().equals(RefundProcessSts.PROCESS_CONFIRMFAIL.getCode())) {
                String processStsName = refundProcessSts.getAliasEnums().get(fsRefundinfo.getProcessstatus()).getTitle();
                MessageUtil.addWarn("�����鴦��״̬Ϊ " + processStsName + "���޷������˸�ȷ�ϡ�");
                setButtonDisabled(true);
                return null;
            }
            setButtonDisabled(false);
        } catch (Exception ex) {
            logger.error("��ȡ�˸���Ϣʧ�ܣ��˸������=" + refundapplcode + "��" + ex.getMessage());
            MessageUtil.addError("��ȡ�˸���Ϣʧ�ܣ��˸������=" + refundapplcode + "��"
                    + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        return null;
    }

    public String onBtnRefundClick() {
        try {
            if (fsRefundinfoList == null || fsRefundinfoList.size() < 1) {
                MessageUtil.addWarn("���������������Ż�ȡ��Ϣ��");
                return null;
            }

            FsRefundinfo fsRefundinfo = fsRefundinfoList.get(0);
            List<FsRefundinfo> refundList = new ArrayList<FsRefundinfo>();
            refundList.add(fsRefundinfo);
            paymentService.sendRefundConfirm(refundList,RefundProcessSts.PROCESS_CONFIRMSUC.getCode(),parambofcode);
            setButtonDisabled(true);
        } catch (Exception ex) {
            logger.error("�˸�ʧ�ܣ��˸������=" + refundapplcode + "��" + ex.getMessage());
            MessageUtil.addError("�˸���Ϣ���˸������=" + refundapplcode + "��"
                    + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        try {
            fsRefundinfoList = paymentService.selectRefundinfoByAppcd(fsRefundinfoList.get(0).getRefundapplycode(),
                    parambofcode,fsRefundinfoList.get(0).getPerformdept().toString());
        }  catch (Exception ex) {
            logger.error("��ȡ�˸���Ϣʧ�ܣ��˸������=" + refundapplcode + "��" + ex.getMessage());
            MessageUtil.addError("��ȡ�˸���Ϣʧ�ܣ��˸������=" + refundapplcode + "��"
                    + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        MessageUtil.addInfo("�˸�ȷ�ϳɹ���");
        return null;
    }

    /*����button״̬*/
    private void setButtonDisabled(boolean disabled) {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        HtmlCommandButton btnPay = (HtmlCommandButton) viewRoot.findComponent("editForm:btnRefund");
        btnPay.setDisabled(disabled);
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

    public String getRefundapplcode() {
        return refundapplcode;
    }

    public void setRefundapplcode(String refundapplcode) {
        this.refundapplcode = refundapplcode;
    }

    public RefundProcessSts getRefundProcessSts() {
        return refundProcessSts;
    }

    public void setRefundProcessSts(RefundProcessSts refundProcessSts) {
        this.refundProcessSts = refundProcessSts;
    }

    public String getPerformdept() {
        return performdept;
    }

    public void setPerformdept(String performdept) {
        this.performdept = performdept;
    }

    public String getBanksign() {
        return banksign;
    }

    public void setBanksign(String banksign) {
        this.banksign = banksign;
    }
}
