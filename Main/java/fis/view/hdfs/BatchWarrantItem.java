package fis.view.hdfs;

import org.apache.commons.lang.StringUtils;

/*
  汇总缴款单明细信息
 */
public class BatchWarrantItem {

    private String prjCode;                 // 项目代码
    private String prjName;                 // 项目名称
    private String measure;                 // 计量单位
    private String handleNum;               // 数量
    private String txnAmt;                  // 金额

    public void assemble(String itemMsg) {
        String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(itemMsg, ",");
        prjCode = fieldArray[0];
        prjName = fieldArray[1];
        measure = fieldArray[2];
        handleNum = fieldArray[3];
        txnAmt = fieldArray[4];
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

    public String getPrjName() {
        return prjName;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }
}
