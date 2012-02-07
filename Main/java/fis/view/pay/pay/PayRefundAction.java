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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: ����3:10
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PayRefundAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ManagedProperty(value = "#{payProcessService}")
    private PayProcessService payProcessService;

    private List<PayPayvoucher> payPayvoucherList;
    private String printid;
    private PayPayvoucher payPayvoucher;
    private ProcessFlag processFlag = ProcessFlag.PROCESS_INIT;
    private VoucherType voucherType = VoucherType.VCHTYPE_NORMAL;
    private PayType payType = PayType.PAY_DIRECT;

    @PostConstruct
    public void init() {
        try {
            payPayvoucher = new PayPayvoucher();
            qryRefundsuc();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("��ȡ�����˿��¼���ݴ���:" + ex.getMessage());
            MessageUtil.addError("��ȡ�����˿��¼���ݴ���:" + ex.getMessage());
        }

    }

    private void qryRefundsuc() throws ParseException {
        FacesContext context = FacesContext.getCurrentInstance();
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String paytype = paramMap.get("paytype").toString();
        List<String> procflgRefundsuc = new ArrayList<String>();
        procflgRefundsuc.add("3");        //�˿��д�ɹ�
        payPayvoucherList = payProcessService.selectedPayvchForProcflag(procflgRefundsuc, new Date(), paytype);
    }

    public String onBtnAcceptClick() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Map paramMap = context.getExternalContext().getRequestParameterMap();
            String paytype = paramMap.get("paytype").toString();
            payPayvoucher = payProcessService.selectedPayvchByPrintid(this.printid);
            if (payPayvoucher == null) {
                MessageUtil.addWarn("û�����ݣ�����ƾ֤����Ƿ���ȷ��");
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
                UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
                HtmlCommandButton btnRefund = (HtmlCommandButton) viewRoot.findComponent("tabView1:editForm:btnRefund");
                btnRefund.setDisabled(true);
                String vchtypename = voucherType.getAliasEnums().get(payPayvoucher.getVouchertype()).getTitle();
                MessageUtil.addWarn("��ƾ֤����Ϊ" + vchtypename + "���޷��˿");
                return null;
            } else if (!payPayvoucher.getProcessflag().equals("1") && !payPayvoucher.getProcessflag().equals("4")) {
                //����button������
                UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
                HtmlCommandButton btnRefund = (HtmlCommandButton) viewRoot.findComponent("tabView1:editForm:btnRefund");
                btnRefund.setDisabled(true);
                String vchtypename = voucherType.getAliasEnums().get(payPayvoucher.getVouchertype()).getTitle();
                MessageUtil.addWarn("��ƾ֤��ǰ�Ĵ���״̬�޷��˿");
                return null;
            }
            //����button����
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            HtmlCommandButton btnRefund = (HtmlCommandButton) viewRoot.findComponent("tabView1:editForm:btnRefund");
            btnRefund.setDisabled(false);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("�˿�-ƾ֤����=" + this.printid + " ��ȡ����:" + ex.getMessage());
            MessageUtil.addError("�˿�-ƾ֤����=" + this.printid + " ��ȡ����:" + ex.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }


        return null;
    }

    public String btnRefundClick() {
        if (payPayvoucher.getPrintid() == null || StringUtils.isEmpty(payPayvoucher.getPrintid().toString().toString())) {
            MessageUtil.addWarn("���Ȼ�ȡƾ֤��Ż�ȡ��Ϣ��");
            return null;
        } else if (!payPayvoucher.getVouchertype().equals("01")) {
            //����button������
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            HtmlCommandButton btnRefund = (HtmlCommandButton) viewRoot.findComponent("tabView1:editForm:btnRefund");
            btnRefund.setDisabled(true);
            String vchtypename = voucherType.getAliasEnums().get(payPayvoucher.getVouchertype()).getTitle();
            MessageUtil.addWarn("��ƾ֤����Ϊ " + vchtypename + "���޷��˿");
            return null;
        } else if (!payPayvoucher.getProcessflag().equals("1") && !payPayvoucher.getProcessflag().equals("4")) {
            //����button������
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            HtmlCommandButton btnRefund = (HtmlCommandButton) viewRoot.findComponent("tabView1:editForm:btnRefund");
            btnRefund.setDisabled(true);
            String vchtypename = voucherType.getAliasEnums().get(payPayvoucher.getVouchertype()).getTitle();
            MessageUtil.addWarn("��ƾ֤��ǰ����״̬�޷��˿");
            return null;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String paytype = paramMap.get("paytype").toString(); //����֧����ʽ�ж����������
        //todo ������֧�������˿�ƾ֤����
        //todo (�������Ȩ֧��)������Ȩ���
        //todo ����ƾ֤����Ϊ���˿� ����״̬�ֶ� 3=�˿��д�ɹ�
        //todo ������ʷ����
        try {
            payProcessService.refundSucProcess(payPayvoucher, "3", paytype);
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            HtmlCommandButton btnRefund = (HtmlCommandButton) viewRoot.findComponent("tabView1:editForm:btnRefund");
            btnRefund.setDisabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("�˿�-�������ݴ���" + ex.getMessage());
            MessageUtil.addError("�������ݴ���" + ex.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        MessageUtil.addInfo("�˿��д�ɹ���");
        try {
            qryRefundsuc();
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("�˿��д-���ݲ�ѯ����" + e.getMessage());
            MessageUtil.addError("�˿��д-���ݲ�ѯ����" + e.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        return null;
    }

    public PayProcessService getPayProcessService() {
        return payProcessService;
    }

    public void setPayProcessService(PayProcessService payProcessService) {
        this.payProcessService = payProcessService;
    }

    public String getPrintid() {
        return printid;
    }

    public void setPrintid(String printid) {
        this.printid = printid;
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
}
