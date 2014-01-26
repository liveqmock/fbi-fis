package fis.view.hdfs;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ���ܽɿ��Ϣ
 */
public class BatchWarrantInfo {

    private String billId;            // �ɿ�֪ͨ���
    private String instCode;          // ��λ����
    private String payerName;         // �ɿ���
    private String notifyDate;        // ֪ͨ����
    private String latestDate;        // �������
    private String overdueRatio;      // ���ɽ����
    private String overdueAmt;        // ���ɽ���
    private String verifyNo;          // У����

    private String voucherType;       // Ʊ������
    private String billType;          // ֪ͨ������
    private String contractNo;        // ��ͬ��
    private String remark;            // ��ע

    private int itemNum;              // ��ϸ��
    private List<BatchWarrantItem> items = new ArrayList<BatchWarrantItem>();

    public void assemble(String infoMsg) {
        // ����������
        String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(infoMsg, "|");
        billId = fieldArray[0];
        instCode = fieldArray[1];
        payerName = fieldArray[2];
        notifyDate = fieldArray[3];
        latestDate = fieldArray[4];
        overdueRatio = fieldArray[5];
        overdueAmt = fieldArray[6];
        verifyNo = fieldArray[7];

        itemNum = Integer.parseInt(fieldArray[8]); // ��ϸ��
        items.clear();
        for (int i = 0; i < itemNum; i++) {
            BatchWarrantItem item = new BatchWarrantItem();
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

    public List<BatchWarrantItem> getItems() {
        return items;
    }

    public void setItems(List<BatchWarrantItem> items) {
        this.items = items;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
