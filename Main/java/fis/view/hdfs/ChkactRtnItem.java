package fis.view.hdfs;

import org.apache.commons.lang.StringUtils;

/*
  对账返回明细信息
 */
public class ChkactRtnItem {

    private String notifyDate;                 // 通知日期
    private String payerName;                  // 缴款人
    private String billId;                     // 票据号
    private String fisBizId;                   // 财政业务ID号
    private String payAmt;                     // 金额
    private String rtnMsg;                     // 返回信息

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
