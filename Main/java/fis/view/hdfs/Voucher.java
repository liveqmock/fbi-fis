package fis.view.hdfs;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 14-2-20
 * Time: 下午7:50
 * To change this template use File | Settings | File Templates.
 */
public class Voucher {
    private String vchType = "1";                 // 票据种类 内容：1—“收费收据”，2—“结算凭证”
    private String vchNum;                  // 票据号
    private String vchAmt;                  // 开票金额
    private String vchSts = "0";                // 票据状态 内容：0—票据正确，1—票据作废

    public String getVchType() {
        return vchType;
    }

    public void setVchType(String vchType) {
        this.vchType = vchType;
    }

    public String getVchNum() {
        return vchNum;
    }

    public void setVchNum(String vchNum) {
        this.vchNum = vchNum;
    }

    public String getVchAmt() {
        return vchAmt;
    }

    public void setVchAmt(String vchAmt) {
        this.vchAmt = vchAmt;
    }

    public String getVchSts() {
        return vchSts;
    }

    public void setVchSts(String vchSts) {
        this.vchSts = vchSts;
    }
}
