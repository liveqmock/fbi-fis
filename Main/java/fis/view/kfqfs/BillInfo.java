package fis.view.kfqfs;

import org.apache.commons.lang.StringUtils;
import pub.platform.advance.utils.PropertyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * �ɿ��Ϣ
 */
public class BillInfo {

    private String chr_id = "";
    private String billtype_code = "";
    private String billtype_name = "";
    private String bill_no = "";
    private String makedate = "";
    private String ien_code = "";
    private String ien_name = "";
    private String consign_ien_code = "";
    private String consign_ien_name = "";
    private String pm_code = "";
    private String pm_name = "";
    private String cheque_no = "";
    private String payer = "";
    private String payerbank = "";
    private String payeraccount = "";
    //    private String receiver = PropertyManager.getProperty("hdfs.recever");
    private String receiver = "";
    private String receiverbank = "";
    private String receiveraccount = "";
    private String verify_no = "";
    private String rg_code = "";
    private String receivetype = "";
    private String inputername = "";
    private String is_consign = "";
    private String lateflag = "";
    private String nosource_ids = "";
    private String supplytemplet_id = "";
    private String remark = "";

    // �ֹ�Ʊ�����ֶ�
    private String set_year = "";
    private String bank_user = "";
    private String bank_no = PropertyManager.getProperty("hdfs.bank_no");

    private int itemCnt = 0;

    private List<BillItem> items = new ArrayList<BillItem>();

    public void assemble(String msg) {
        // ����������
        String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(msg, "|");
        chr_id = fieldArray[0];                       // �ɿ���ID
        billtype_code = fieldArray[1];                // �ɿ�����ʽ����
        billtype_name = fieldArray[2];                // �ɿ�����ʽ����
        bill_no = fieldArray[3];                      // Ʊ��
        makedate = fieldArray[4];                     // ��Ʊ����
        ien_code = fieldArray[5];                     // ִ�յ�λҵ����
        ien_name = fieldArray[6];                     // ִ�յ�λ����
        consign_ien_code = fieldArray[7];             // ί�е�λҵ����
        consign_ien_name = fieldArray[8];             // ί�е�λ����
        pm_code = fieldArray[9];                      // �ɿʽ����
        pm_name = fieldArray[10];                     // �ɿʽ����
        cheque_no = fieldArray[11];                   // �����
        payer = fieldArray[12];                       // �ɿ���ȫ��
        payerbank = fieldArray[13];                   // �ɿ����˻�������

        payeraccount = fieldArray[14];                // �ɿ����˺�
//        receiver = fieldArray[15];                    // �տ���ȫ��
//        receiverbank = fieldArray[16];                // �տ����˻�������
//        receiveraccount = fieldArray[17];             // �տ����˺�
        verify_no = fieldArray[18];                   // ȫƱ��У����

        rg_code = fieldArray[19];                     // ������
        receivetype = fieldArray[20];                 // ���շ�ʽ
        inputername = fieldArray[21];                 // ����������
        is_consign = fieldArray[22];                  // �Ƿ�ί��
        lateflag = fieldArray[23];                    // �Ƿ�¼
        nosource_ids = fieldArray[24];                // ��������ID����
        supplytemplet_id = fieldArray[25];            // ��������ģ��ID
        remark = fieldArray[26];                      // ��ע

        itemCnt = Integer.parseInt(fieldArray[27]); // ��ϸ��

        items.clear();
        for (int i = 0; i < itemCnt; i++) {
            BillItem item = new BillItem();
            item.assemble(fieldArray[28 + i]);
            items.add(item);
        }
    }

    public String getChr_id() {
        return chr_id;
    }

    public void setChr_id(String chr_id) {
        this.chr_id = chr_id;
    }

    public String getBilltype_code() {
        return billtype_code;
    }

    public void setBilltype_code(String billtype_code) {
        this.billtype_code = billtype_code;
    }

    public String getBilltype_name() {
        return billtype_name;
    }

    public void setBilltype_name(String billtype_name) {
        this.billtype_name = billtype_name;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getIen_code() {
        return ien_code;
    }

    public void setIen_code(String ien_code) {
        this.ien_code = ien_code;
    }

    public String getIen_name() {
        return ien_name;
    }

    public void setIen_name(String ien_name) {
        this.ien_name = ien_name;
    }

    public String getConsign_ien_code() {
        return consign_ien_code;
    }

    public void setConsign_ien_code(String consign_ien_code) {
        this.consign_ien_code = consign_ien_code;
    }

    public String getConsign_ien_name() {
        return consign_ien_name;
    }

    public void setConsign_ien_name(String consign_ien_name) {
        this.consign_ien_name = consign_ien_name;
    }

    public String getPm_code() {
        return pm_code;
    }

    public void setPm_code(String pm_code) {
        this.pm_code = pm_code;
    }

    public String getPm_name() {
        return pm_name;
    }

    public void setPm_name(String pm_name) {
        this.pm_name = pm_name;
    }

    public String getCheque_no() {
        return cheque_no;
    }

    public void setCheque_no(String cheque_no) {
        this.cheque_no = cheque_no;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayerbank() {
        return payerbank;
    }

    public void setPayerbank(String payerbank) {
        this.payerbank = payerbank;
    }

    public String getPayeraccount() {
        return payeraccount;
    }

    public void setPayeraccount(String payeraccount) {
        this.payeraccount = payeraccount;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverbank() {
        return receiverbank;
    }

    public void setReceiverbank(String receiverbank) {
        this.receiverbank = receiverbank;
    }

    public String getReceiveraccount() {
        return receiveraccount;
    }

    public void setReceiveraccount(String receiveraccount) {
        this.receiveraccount = receiveraccount;
    }

    public String getVerify_no() {
        return verify_no;
    }

    public void setVerify_no(String verify_no) {
        this.verify_no = verify_no;
    }

    public String getRg_code() {
        return rg_code;
    }

    public void setRg_code(String rg_code) {
        this.rg_code = rg_code;
    }

    public String getReceivetype() {
        return receivetype;
    }

    public void setReceivetype(String receivetype) {
        this.receivetype = receivetype;
    }

    public String getInputername() {
        return inputername;
    }

    public void setInputername(String inputername) {
        this.inputername = inputername;
    }

    public String getIs_consign() {
        return is_consign;
    }

    public void setIs_consign(String is_consign) {
        this.is_consign = is_consign;
    }

    public String getLateflag() {
        return lateflag;
    }

    public void setLateflag(String lateflag) {
        this.lateflag = lateflag;
    }

    public String getNosource_ids() {
        return nosource_ids;
    }

    public void setNosource_ids(String nosource_ids) {
        this.nosource_ids = nosource_ids;
    }

    public String getSupplytemplet_id() {
        return supplytemplet_id;
    }

    public void setSupplytemplet_id(String supplytemplet_id) {
        this.supplytemplet_id = supplytemplet_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getItemCnt() {
        return itemCnt;
    }

    public void setItemCnt(int itemCnt) {
        this.itemCnt = itemCnt;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setItems(List<BillItem> items) {
        this.items = items;
    }

    public String getSet_year() {
        return set_year;
    }

    public void setSet_year(String set_year) {
        this.set_year = set_year;
    }

    public String getBank_user() {
        return bank_user;
    }

    public void setBank_user(String bank_user) {
        this.bank_user = bank_user;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }
}
