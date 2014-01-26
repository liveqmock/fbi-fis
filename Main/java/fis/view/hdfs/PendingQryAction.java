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
 * �Ƶ���˰����
 */
@ManagedBean
@ViewScoped
public class PendingQryAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(PendingQryAction.class);
    private String payerActno;    //�ɿ����˺�
    private String payerName;     //�ɿ���
    private String remark;        //��ע
    private String payerBank;     //��������
    private BigDecimal payAmt;    //���
    private String notifyDate;    //֪ͨ����  ��ʽYYYYMMDD

    private String rtnCode;      //����������
    private String rtnMsg;       //�����������Ӧ����Ϣ
    private String fisBizId;     //����ҵ��ID��

    @PostConstruct
    public void init() {
        notifyDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    // ��ѯ
    public String onQuery() {

        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1534000";
        tia.msgBody = (payerActno + "|" + payerName + "|" +
                remark + "|" + payerBank + "|" + payAmt + "|" + notifyDate + "|").getBytes();
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
            String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(toamsg, "|");
            rtnCode = fieldArray[0];
            rtnMsg = fieldArray[1];
            fisBizId = fieldArray[2];
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    // ��ѯ
    public String onReverseQuery() {

        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1534010";
        tia.msgBody = (fisBizId + "|" + payerActno + "|" + payerName + "|" +
                remark + "|" + payerBank + "|" + payAmt + "|" + notifyDate + "|").getBytes();
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
            String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(toamsg, "|");
            rtnCode = fieldArray[0];
            rtnMsg = fieldArray[1];
            fisBizId = fieldArray[2];
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


    public String getPayerActno() {
        return payerActno;
    }

    public void setPayerActno(String payerActno) {
        this.payerActno = payerActno;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayerBank() {
        return payerBank;
    }

    public void setPayerBank(String payerBank) {
        this.payerBank = payerBank;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public String getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(String notifyDate) {
        this.notifyDate = notifyDate;
    }

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public String getFisBizId() {
        return fisBizId;
    }

    public void setFisBizId(String fisBizId) {
        this.fisBizId = fisBizId;
    }
}
