package fis.view.hdfs;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 14-2-20
 * Time: ����7:50
 * To change this template use File | Settings | File Templates.
 */
public class Voucher {
    private String vchType = "1";                 // Ʊ������ ���ݣ�1�����շ��վݡ���2��������ƾ֤��
    private String vchNum;                  // Ʊ�ݺ�
    private String vchAmt;                  // ��Ʊ���
    private String vchSts = "0";                // Ʊ��״̬ ���ݣ�0��Ʊ����ȷ��1��Ʊ������

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
