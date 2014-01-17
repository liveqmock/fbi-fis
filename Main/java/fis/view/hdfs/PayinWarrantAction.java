package fis.view.hdfs;

import gateway.client.SyncSocketClient;
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
public class PayinWarrantAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(PayinWarrantAction.class);

    private String billId;                               // �ɿ�֪ͨ���
    private WarrantInfo warrantInfo = new WarrantInfo(); // �ɿ�֪ͨ����Ϣ

    private String voucherType;                          // Ʊ������
    private BigDecimal payAmt;                           // ���
    private boolean payable = false;                     // �Ƿ�ɽɿ�

    @PostConstruct
    public void init() {


    }

    public String onQuery() {

        LFixedLengthProtocol tia = newFixedLengthProtocol();
        // TODO ������
        tia.txnCode = "1000";
        tia.msgBody = (billId + "|").getBytes();   // Ʊ��
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            SyncSocketClient client = new SyncSocketClient();
            toa = client.onRequest(tia);
            toamsg = new String(toa.msgBody);
            logger.info("���ر����壺" + toamsg);
        } catch (Exception e) {
            logger.error("����ͨ���쳣.", e);
            MessageUtil.addError("����ͨ���쳣��");
            return null;
        }
        if ("0000".equals(toa.rtnCode)) {
            warrantInfo.assemble(toamsg);
            payable = true;
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    public String onPay() {

        if (StringUtils.isEmpty(voucherType)) {
            MessageUtil.addError("��������Ʊ������");
            return null;
        }
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        // TODO ������
        tia.txnCode = "1010";
        tia.msgBody = (billId + "|" + voucherType + "|" + warrantInfo.getOverdueAmt() + "|").getBytes();
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            SyncSocketClient client = new SyncSocketClient();
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

    public String onReverse() {
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        // TODO ������
        tia.txnCode = "1090";
        tia.msgBody = (billId + "|" + voucherType + "|" + payAmt + "|").getBytes();
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            SyncSocketClient client = new SyncSocketClient();
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

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public WarrantInfo getWarrantInfo() {
        return warrantInfo;
    }

    public void setWarrantInfo(WarrantInfo warrantInfo) {
        this.warrantInfo = warrantInfo;
    }
}
