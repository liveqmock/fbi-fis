package fis.view.kfqfs;

import org.apache.commons.lang.StringUtils;

/**
 序号 执收单位编码	执收单位名称	票据号码	收费项目编码	收费项目名称	金额
 */
public class ReportItem {

    private String seqId = "";
    private String unitCode = "";
    private String unitName = "";
    private String billNo = "";
    private String itemCode = "";
    private String itemName = "";
    private String amt = "";

    public void assemble(String msg) {
        String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(msg, ",");
        seqId = fieldArray[0];
        unitCode = fieldArray[1];
        unitName = fieldArray[2];
        billNo = fieldArray[3];
        itemCode = fieldArray[4];
        itemName = fieldArray[5];
        amt = fieldArray[6];
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}
