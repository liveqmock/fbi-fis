package fis.view.pay.pay;

import fis.common.pay.PayType;
import fis.common.pay.ProcessFlag;
import fis.common.pay.VoucherType;
import fis.repository.pay.model.PayPayvoucher;
import fis.service.pay.PayProcessService;
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
import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: ����3:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PayProcessAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ManagedProperty(value = "#{payProcessService}")
    private PayProcessService payProcessService;
    private PayPayvoucher payPayvoucher;
    private ProcessFlag processFlag = ProcessFlag.PROCESS_INIT;
    private VoucherType voucherType = VoucherType.VCHTYPE_NORMAL;
    private PayType payType = PayType.PAY_DIRECT;
    private String printid;

    @PostConstruct
    public void init() {
        payPayvoucher = new PayPayvoucher();
    }

    /*֧����ȡ����*/
    public String onBtnAcceptClick() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Map paramMap = context.getExternalContext().getRequestParameterMap();
            String paytype = paramMap.get("paytype").toString();
            payPayvoucher = payProcessService.selectedPayvchByPrintid(printid);
            if (payPayvoucher == null) {
                MessageUtil.addWarn("û�����ݣ�����ƾ֤���Ƿ���ȷ��");
                payPayvoucher = new PayPayvoucher();
                return null;
            } else if (!payPayvoucher.getPaytype().equals(paytype)) {
                //todo ֧����ʽ�ж�  ��ʱ����
                String paptypename = payType.getAliasEnums().get(payPayvoucher.getPaytype()).getTitle();
                MessageUtil.addWarn("��ƾ֤֧����ʽΪ" + paptypename + "���뵽" + paptypename + "ҳ�������");
                payPayvoucher = new PayPayvoucher();
                return null;
            } else if (!payPayvoucher.getVouchertype().equals("01")) {
                //����button������
                setButtonDisabled(true);
                String vchtypename = voucherType.getAliasEnums().get(payPayvoucher.getVouchertype()).getTitle();
                MessageUtil.addWarn("��ƾ֤����Ϊ" + vchtypename + "���޷�����֧����");
                return null;
            } else if (!payPayvoucher.getProcessflag().equals("0") && !payPayvoucher.getProcessflag().equals("5")
                    && !payPayvoucher.getProcessflag().equals("6")) {
                //����button������
                setButtonDisabled(true);
                String processflagname = processFlag.getAliasEnums().get(payPayvoucher.getProcessflag()).getTitle();
                MessageUtil.addWarn("��ƾ֤����״̬Ϊ" + processflagname + "���޷�����֧����");
                return null;
            }
            //����button����
            setButtonDisabled(false);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("��ȡ����ʧ�ܣ�" + ex.getMessage());
            MessageUtil.addError("��ȡ����ʧ�ܣ�" + ex.getMessage());
            return null;
        }

        return null;
    }

    /*֧��*/
    public String btnPayClick() {
        if (payPayvoucher.getPrintid() == null || StringUtils.isEmpty(payPayvoucher.getPrintid().toString())) {
            MessageUtil.addWarn("��������ƾ֤��Ż�ȡ��Ϣ��");
            return null;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String paytype = paramMap.get("paytype").toString();
        //todo ͨ���ӿڷ��ʹ���ɹ���Ϣ
        //todo �޸��˻���Ȩ���
        //�޸�ƾ֤������״̬���ֶ� 1=֧����д�ɹ�
        // ����ƾ֤��ʷ������
        try {
            payProcessService.paySucProcess(payPayvoucher, "1", paytype);
            setButtonDisabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("֧���ɹ�-�������ݴ���" + ex.getMessage());
            MessageUtil.addError("�������ݴ���" + ex.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        MessageUtil.addInfo("֧����д�ɹ���");
        return null;
    }

    /*��Ʊ*/
    public String btnRefuseClick() {
        if (payPayvoucher.getPrintid() == null || StringUtils.isEmpty(payPayvoucher.getPrintid().toString().toString())) {
            MessageUtil.addWarn("������ƾ֤��Ż�ȡ��Ϣ��");
            return null;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String paytype = paramMap.get("paytype").toString();
        //todo ͨ���ӿڷ�����Ʊ��Ϣ
        // �޸�ƾ֤��ƾ֤���͡� 02-����֧����������״̬���ֶ� 3=��д�ɹ�
        // ����ƾ֤��ʷ������
        try {
            payProcessService.refuseSucProcess(payPayvoucher, "2", paytype);
            setButtonDisabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("��Ʊ-�������ݴ���" + ex.getMessage());
            MessageUtil.addError("�������ݴ���" + ex.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        MessageUtil.addInfo("��Ʊ��д�ɹ���");
        return null;
    }

    /*����button״̬*/
    private void setButtonDisabled(boolean disabled) {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        HtmlCommandButton btnPay = (HtmlCommandButton) viewRoot.findComponent("editForm:btnPay");
        HtmlCommandButton btnRefuse = (HtmlCommandButton) viewRoot.findComponent("editForm:btnRefuse");
        btnPay.setDisabled(disabled);
        btnRefuse.setDisabled(disabled);
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

    public String getPrintid() {
        return printid;
    }

    public void setPrintid(String printid) {
        this.printid = printid;
    }

    public PayPayvoucher getPayPayvoucher() {
        return payPayvoucher;
    }

    public void setPayPayvoucher(PayPayvoucher payPayvoucher) {
        this.payPayvoucher = payPayvoucher;
    }

    public PayProcessService getPayProcessService() {
        return payProcessService;
    }

    public void setPayProcessService(PayProcessService payProcessService) {
        this.payProcessService = payProcessService;
    }

    public ProcessFlag getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(ProcessFlag processFlag) {
        this.processFlag = processFlag;
    }
}
