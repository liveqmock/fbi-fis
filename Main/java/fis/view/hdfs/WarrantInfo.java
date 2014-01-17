package fis.view.hdfs;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 缴款单信息
 */
public class WarrantInfo {

    private String billId;            // 缴款通知书号
    private String instCode;          // 单位代码
    private String payerName;         // 缴款人
    private String notifyDate;        // 通知日期
    private String latestDate;        // 最迟日期
    private String overdueRatio;      // 滞纳金比例
    private String overdueAmt;        // 滞纳金金额
    private String verifyNo;          // 校验码

    private int itemNum;              // 明细数
    private List<WarrantItem> items = new ArrayList<WarrantItem>();

    public void assemble(String infoMsg) {
        // 解析报文体
        String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(infoMsg, "|");
        billId = fieldArray[0];
        instCode = fieldArray[1];
        payerName = fieldArray[2];
        notifyDate = fieldArray[3];
        latestDate = fieldArray[4];
        overdueRatio = fieldArray[5];
        overdueAmt = fieldArray[6];
        verifyNo = fieldArray[7];

        itemNum = Integer.parseInt(fieldArray[8]); // 明细数

        for (int i = 0; i < itemNum; i++) {
            WarrantItem item = new WarrantItem();
            item.assemble(fieldArray[9 + i]);
            items.add(item);
        }
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(String notifyDate) {
        this.notifyDate = notifyDate;
    }

    public String getLatestDate() {
        return latestDate;
    }

    public void setLatestDate(String latestDate) {
        this.latestDate = latestDate;
    }

    public String getOverdueRatio() {
        return overdueRatio;
    }

    public void setOverdueRatio(String overdueRatio) {
        this.overdueRatio = overdueRatio;
    }

    public String getOverdueAmt() {
        return overdueAmt;
    }

    public void setOverdueAmt(String overdueAmt) {
        this.overdueAmt = overdueAmt;
    }

    public String getVerifyNo() {
        return verifyNo;
    }

    public void setVerifyNo(String verifyNo) {
        this.verifyNo = verifyNo;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public List<WarrantItem> getItems() {
        return items;
    }

    public void setItems(List<WarrantItem> items) {
        this.items = items;
    }
}
