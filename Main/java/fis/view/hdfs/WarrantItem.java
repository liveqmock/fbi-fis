package fis.view.hdfs;

import org.apache.commons.lang.StringUtils;

/*
  �ɿ��ϸ��Ϣ
 */
public class WarrantItem {

    private String prjCode;                 // ��Ŀ����
    private String measure;                 // ������λ
    private String handleNum;               // ����
    private String txnAmt;                  // ���

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
