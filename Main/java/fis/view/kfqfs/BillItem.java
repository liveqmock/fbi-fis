package fis.view.kfqfs;

import org.apache.commons.lang.StringUtils;

/**
 * �ɿ��ϸ��Ϣ
 * chr_id	�ɿ�����ϸID
   main_id	�ɿ���ID
   in_bis_code	������Ŀҵ����
   in_bis_name	������Ŀ����
   measure	���յ�λ
   chargenum	��������
   chargestandard	�շѱ�׼
   chargemoney	������
   item_chkcode	��λ��ĿУ����
 */
public class BillItem {

    private String chr_id = "";
    private String main_id = "";
    private String in_bis_code = "";
    private String in_bis_name = "";
    private String measure = "";
    private String chargenum = "";
    private String chargestandard = "";
    private String chargemoney = "";
    private String item_chkcode = "";

    public void assemble(String msg) {
        String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(msg, ",");
        chr_id = fieldArray[0];
        main_id = fieldArray[1];
        in_bis_code = fieldArray[2];
        in_bis_name = fieldArray[3];
        measure = fieldArray[4];
        chargenum = fieldArray[5];
        chargestandard = fieldArray[6];
        chargemoney = fieldArray[7];
        item_chkcode = fieldArray[8];
    }

    public String getChr_id() {
        return chr_id;
    }

    public void setChr_id(String chr_id) {
        this.chr_id = chr_id;
    }

    public String getMain_id() {
        return main_id;
    }

    public void setMain_id(String main_id) {
        this.main_id = main_id;
    }

    public String getIn_bis_code() {
        return in_bis_code;
    }

    public void setIn_bis_code(String in_bis_code) {
        this.in_bis_code = in_bis_code;
    }

    public String getIn_bis_name() {
        return in_bis_name;
    }

    public void setIn_bis_name(String in_bis_name) {
        this.in_bis_name = in_bis_name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getChargenum() {
        return chargenum;
    }

    public void setChargenum(String chargenum) {
        this.chargenum = chargenum;
    }

    public String getChargestandard() {
        return chargestandard;
    }

    public void setChargestandard(String chargestandard) {
        this.chargestandard = chargestandard;
    }

    public String getChargemoney() {
        return chargemoney;
    }

    public void setChargemoney(String chargemoney) {
        this.chargemoney = chargemoney;
    }

    public String getItem_chkcode() {
        return item_chkcode;
    }

    public void setItem_chkcode(String item_chkcode) {
        this.item_chkcode = item_chkcode;
    }
}
