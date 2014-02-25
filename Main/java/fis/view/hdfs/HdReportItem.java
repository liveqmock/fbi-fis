package fis.view.hdfs;

import org.apache.commons.lang.StringUtils;

/**
 黄岛非税日报明细
 */
public class HdReportItem {

    private String seqId = "";
    private String notifyDate = "";
    private String payerName = "";
    private String billId = "";
    private String fisBizId = "";
    private String payAmt = "";
    private String rtnMsg = "";

    public void assemble(String msg) {
        String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(msg, ",");
        notifyDate = fieldArray[0];
        payerName = fieldArray[1];
        billId = fieldArray[2];
        fisBizId = fieldArray[3];
        payAmt = fieldArray[4];
        rtnMsg = fieldArray[5];
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
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
