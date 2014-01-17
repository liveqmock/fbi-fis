package fis.view.hdfs;

import org.apache.commons.lang.StringUtils;

/*
  缴款单明细信息
 */
public class WarrantItem {

    private String prjCode;                 // 项目代码
    private String measure;                 // 计量单位
    private String handleNum;               // 数量
    private String txnAmt;                  // 金额

    public void assemble(String itemMsg) {
        String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(itemMsg, ",");
        prjCode = fieldArray[0];
        measure = fieldArray[1];
        handleNum = fieldArray[2];
        txnAmt = fieldArray[3];
    }

    public String getPrjCode() {
        return prjCode;
    }

    public void setPrjCode(String prjCode) {
        this.prjCode = prjCode;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getHandleNum() {
        return handleNum;
    }

    public void setHandleNum(String handleNum) {
        this.handleNum = handleNum;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }
}
