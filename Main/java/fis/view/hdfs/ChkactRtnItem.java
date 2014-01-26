package fis.view.hdfs;

import org.apache.commons.lang.StringUtils;

/*
  ���˷�����ϸ��Ϣ
 */
public class ChkactRtnItem {

    private String notifyDate;                 // ֪ͨ����
    private String payerName;                  // �ɿ���
    private String billId;                     // Ʊ�ݺ�
    private String fisBizId;                   // ����ҵ��ID��
    private String payAmt;                     // ���
    private String rtnMsg;                     // ������Ϣ

    public void assemble(String itemMsg) {
        String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(itemMsg, ",");
        notifyDate = fieldArray[0];
        payerName = fieldArray[1];
        billId = fieldArray[2];
        fisBizId = fieldArray[3];
        payAmt = fieldArray[4];
        rtnMsg = fieldArray[5];
    }

    public String getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(String notifyDate) {
        this.notifyDate = notifyDate;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getFisBizId() {
        return fisBizId;
    }

    public void setFisBizId(String fisBizId) {
        this.fisBizId = fisBizId;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }
}
