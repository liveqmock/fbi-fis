package fis.view.pay.pay;

import fis.common.pay.PayType;
import fis.common.pay.ProcessFlag;
import fis.common.pay.VoucherType;
import fis.repository.pay.model.PayPayvoucher;
import fis.service.pay.PayProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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
 * Time: ����3:02
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ClearanceProcessAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ManagedProperty(value = "#{payProcessService}")
    private PayProcessService payProcessService;

    private List<PayPayvoucher> payvoucherListFortab1;
    private List<PayPayvoucher> payvoucherListFortab2;
    private PayPayvoucher[] payPayvouchers;
    private ProcessFlag processFlag = ProcessFlag.PROCESS_INIT;
    private VoucherType voucherType = VoucherType.VCHTYPE_NORMAL;
    private PayType payType = PayType.PAY_DIRECT;

    @PostConstruct
    public void init() {
        try {
            queryPayvch();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("��ѯ�������¼ʧ�ܡ�");
            MessageUtil.addError("��ѯ�������¼ʧ�ܡ�");
        }
    }

    /*��ѯ*/
    private void queryPayvch() throws ParseException {
        FacesContext context = FacesContext.getCurrentInstance();
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String paytype = paramMap.get("paytype").toString();
        Date todaydt = new Date();
        List<String> procflgPaysuc = new ArrayList<String>();
        procflgPaysuc.add("1");        //֧����д�ɹ�
        this.payvoucherListFortab1 = payProcessService.selectedPayvchForProcflag(procflgPaysuc, null, paytype);
        List<String> procflgClearance = new ArrayList<String>();
        procflgClearance.add("4");        //�����д�ɹ���������
        this.payvoucherListFortab2 = payProcessService.selectedPayvchForProcflag(procflgClearance, todaydt, paytype);
    }

    /*�������ɹ���¼���ʹ���*/
    public String onMultipleReback() {
        if (this.payPayvouchers.length < 1) {
            MessageUtil.addWarn("��ѡ������һ�����ݡ�");
            return null;
        }
        //todo ������֧������
        //todo �޸� ƾ֤������״̬��4=�����д�ɹ� ����=8�����дʧ��
        // ����ƾ֤��ʷ��
        for (PayPayvoucher record : this.payPayvouchers) {
            try {
                payProcessService.clearanceSucProcess(record, "4");
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("�����д-�������ݴ���" + ex.getMessage());
                MessageUtil.addError("ƾ֤���=" + record.getPrintid() + " �������ݴ���" + ex.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
                return null;
            }
        }
        try {
            queryPayvch();
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("�����д-���ݲ�ѯ����" + e.getMessage());
            MessageUtil.addError("�����д-���ݲ�ѯ����" + e.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        MessageUtil.addInfo("���ͳɹ���");
        return null;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public PayPayvoucher[] getPayPayvouchers() {
        return payPayvouchers;
    }

    public void setPayPayvouchers(PayPayvoucher[] payPayvouchers) {
        this.payPayvouchers = payPayvouchers;
    }

    public PayProcessService getPayProcessService() {
        return payProcessService;
    }

    public void setPayProcessService(PayProcessService payProcessService) {
        this.payProcessService = payProcessService;
    }

    public List<PayPayvoucher> getPayvoucherListFortab1() {
        return payvoucherListFortab1;
    }

    public void setPayvoucherListFortab1(List<PayPayvoucher> payvoucherListFortab1) {
        this.payvoucherListFortab1 = payvoucherListFortab1;
    }

    public List<PayPayvoucher> getPayvoucherListFortab2() {
        return payvoucherListFortab2;
    }

    public void setPayvoucherListFortab2(List<PayPayvoucher> payvoucherListFortab2) {
        this.payvoucherListFortab2 = payvoucherListFortab2;
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
}
