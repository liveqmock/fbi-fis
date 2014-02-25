package fis.view.hdfs;

import gateway.client.KarafLinkingSocketClient;
import gateway.domain.LFixedLengthProtocol;
import gateway.domain.ProtocolFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.security.OperatorManager;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * �Ƶ���˰��ѯ���ɿ�
 */
@ManagedBean
@ViewScoped
public class BatchWarrantAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(BatchWarrantAction.class);

    private String billType;                             // ֪ͨ������
    private String billId;                               // �ɿ�֪ͨ���
    private BatchWarrantInfo warrantInfo = new BatchWarrantInfo(); // �ɿ�֪ͨ����Ϣ

    private String voucherType;                          // Ʊ������
    private String fisBatchSn = "000001";                // ���κ���
    private BigDecimal payAmt;                           // ���

    private boolean payable = false;                     // �Ƿ�ɽɿ�

    @PostConstruct
    public void init() {


    }

    // ���ܽɿ��ѯ
    public String onQuery() {

        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1533000";
        tia.msgBody = (billId + "|" + voucherType + "|" + fisBatchSn + "|").getBytes();   // Ʊ��
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            KarafLinkingSocketClient client = new KarafLinkingSocketClient();
            toa = client.onRequest(tia);
            toamsg = new String(toa.msgBody);
            logger.info("���ر����壺" + toamsg);
        } catch (Exception e) {
            logger.error("����ͨ���쳣.", e);
            MessageUtil.addError("����ͨ���쳣��");
            return null;
        }
        if ("0000".equals(toa.rtnCode)) {
            payAmt = new BigDecimal("0.00");
            warrantInfo.assemble(toamsg);
            for (BatchWarrantItem item : warrantInfo.getItems()) {
                payAmt = payAmt.add(new BigDecimal(item.getTxnAmt()));
            }
            payable = true;
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    // ����Ʊ�ɿ�
    public String onPay() {

        if (StringUtils.isEmpty(voucherType)) {
            MessageUtil.addError("��������Ʊ������");
            return null;
        }
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1533010";
        tia.msgBody = (billId + "|" + voucherType + "|" + payAmt + "|" + fisBatchSn + "|").getBytes();
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            KarafLinkingSocketClient client = new KarafLinkingSocketClient();
            toa = client.onRequest(tia);
            toamsg = new String(toa.msgBody);
            logger.info("���ر����壺" + toamsg);
        } catch (Exception e) {
            logger.error("����ͨ���쳣.", e);
            MessageUtil.addError("����ͨ���쳣��");
            return null;
        }
        if ("0000".equals(toa.rtnCode)) {
            MessageUtil.addInfo("�ɿ�ɹ�");
            billId = null;
            voucherType = null;
            payable = false;

        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    // ����Ʊ����
    public String onReverse() {
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1533090";
        tia.msgBody = (billId + "|" + voucherType + "|" + payAmt + "|" + fisBatchSn + "|").getBytes();
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            KarafLinkingSocketClient client = new KarafLinkingSocketClient();
            toa = client.onRequest(tia);
            toamsg = new String(toa.msgBody);
            logger.info("���ر����壺" + toamsg);
        } catch (Exception e) {
            logger.error("����ͨ���쳣.", e);
            MessageUtil.addError("����ͨ���쳣��");
            return null;
        }
        if ("0000".equals(toa.rtnCode)) {
            MessageUtil.addInfo("�ɿ�����ɹ�");
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    private LFixedLengthProtocol newFixedLengthProtocol() {
        LFixedLengthProtocol proto = new LFixedLengthProtocol();
        proto.appID = "FISHD";
        String date14 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        proto.txnTime = date14;
        proto.serialNo = "HDFS" + date14.substring(8);
        proto.ueserID = PropertyManager.getProperty("linking.wsys.userid");
        OperatorManager om = ProtocolFactory.getOperatorManager();
        proto.branchID = om.getOperator().getDeptid();
        proto.tellerID = om.getOperatorId();
        return proto;
    }


    // ================================


    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public boolean isPayable() {
        return payable;
    }

    public void setPayable(boolean payable) {
        this.payable = payable;
    }

    public BatchWarrantInfo getWarrantInfo() {
        return warrantInfo;
    }

    public void setWarrantInfo(BatchWarrantInfo warrantInfo) {
        this.warrantInfo = warrantInfo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getFisBatchSn() {
        return fisBatchSn;
    }

    public void setFisBatchSn(String fisBatchSn) {
        this.fisBatchSn = fisBatchSn;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }
}

