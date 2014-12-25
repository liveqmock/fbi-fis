package fis.view.kfqfs;

import org.apache.commons.lang.StringUtils;
import pub.platform.advance.utils.PropertyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 缴款单信息
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

    // 手工票多余字段
    private String set_year = "";
    private String bank_user = "";
    private String bank_no = PropertyManager.getProperty("hdfs.bank_no");

    private int itemCnt = 0;

    private List<BillItem> items = new ArrayList<BillItem>();

    public void assemble(String msg) {
        // 解析报文体
        String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(msg, "|");
        chr_id = fieldArray[0];                       // 缴款书ID
        billtype_code = fieldArray[1];                // 缴款书样式编码
        billtype_name = fieldArray[2];                // 缴款书样式名称
        bill_no = fieldArray[3];                      // 票号
        makedate = fieldArray[4];                     // 开票日期
        ien_code = fieldArray[5];                     // 执收单位业务码
        ien_name = fieldArray[6];                     // 执收单位名称
        consign_ien_code = fieldArray[7];             // 委托单位业务码
        consign_ien_name = fieldArray[8];             // 委托单位名称
        pm_code = fieldArray[9];                      // 缴款方式编码
        pm_name = fieldArray[10];                     // 缴款方式名称
        cheque_no = fieldArray[11];                   // 结算号
        payer = fieldArray[12];                       // 缴款人全称
        payerbank = fieldArray[13];                   // 缴款人账户开户行

        payeraccount = fieldArray[14];                // 缴款人账号
//        receiver = fieldArray[15];                    // 收款人全称
//        receiverbank = fieldArray[16];                // 收款人账户开户行
//        receiveraccount = fieldArray[17];             // 收款人账号
        verify_no = fieldArray[18];                   // 全票面校验码

        rg_code = fieldArray[19];                     // 区划码
        receivetype = fieldArray[20];                 // 征收方式
        inputername = fieldArray[21];                 // 经办人姓名
        is_consign = fieldArray[22];                  // 是否委托
        lateflag = fieldArray[23];                    // 是否补录
        nosource_ids = fieldArray[24];                // 待查收入ID集合
        supplytemplet_id = fieldArray[25];            // 批量代扣模板ID
        remark = fieldArray[26];                      // 备注

        itemCnt = Integer.parseInt(fieldArray[27]); // 明细数

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
