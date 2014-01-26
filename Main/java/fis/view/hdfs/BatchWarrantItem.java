package fis.view.hdfs;

import org.apache.commons.lang.StringUtils;

/*
  ���ܽɿ��ϸ��Ϣ
 */
public class BatchWarrantItem {

    private String prjCode;                 // ��Ŀ����
    private String prjName;                 // ��Ŀ����
    private String measure;                 // ������λ
    private String handleNum;               // ����
    private String txnAmt;                  // ���

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
