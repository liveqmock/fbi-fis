package fis.view.kfqfs;

import gateway.client.KarafLinkingSocketClient;
import gateway.domain.LFixedLengthProtocol;
import gateway.domain.ProtocolFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.security.OperatorManager;
import skyline.common.utils.MessageUtil;
import skyline.service.SystemService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查询、缴款
 */
@ManagedBean
@ViewScoped
public class BillPayAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(BillPayAction.class);
    // 4010 查询
    private String billTypeCode;          // 缴款书样式编码
    private String billNo;                // 票号
    private String verifyNo;              // 全票面校验码
    private BigDecimal billMoney;         // 收款金额
    private String setYear;               // 年度

    private BillInfo billInfo = new BillInfo();   // 缴款单信息

    private boolean checkPassed = false;          // 标志：查询到票据明细
    private List<SelectItem> payMethodOptions;
    private List<SelectItem> consignOptions;

    private String payType;
    private boolean payed = false;

    private List<BillItem> items = new ArrayList<BillItem>();
    private BillItem item1 = new BillItem();
    private BillItem item2 = new BillItem();
    private BillItem item3 = new BillItem();
    private BillItem item4 = new BillItem();
    private BillItem item5 = new BillItem();

    private String payDate;

    @PostConstruct
    public void init() {

        setYear = new SimpleDateFormat("yyyy").format(new Date());
        billInfo.setSet_year(setYear);
        payMethodOptions = new ArrayList<SelectItem>();
        consignOptions = new ArrayList<SelectItem>();
        payMethodOptions.add(new SelectItem("1", "现金"));
        payMethodOptions.add(new SelectItem("2", "转账"));
        payMethodOptions.add(new SelectItem("3", "电汇"));


        consignOptions.add(new SelectItem("false", "否"));
        consignOptions.add(new SelectItem("true", "是"));

    }

    public String onQuery() {

        /*
          缴款书样式编码 票号 全票面校验码  收款金额  年度
         */
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1534010";
        tia.msgBody = (billTypeCode + "|" + billNo + "|" + verifyNo + "|" + billMoney.toString() + "|" + setYear + "|").getBytes();
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            KarafLinkingSocketClient client = new KarafLinkingSocketClient();
            toa = client.onRequest(tia);
            toamsg = new String(toa.msgBody);
            logger.info("返回报文体：" + toamsg);
        } catch (Exception e) {
            logger.error("网络通信异常.", e);
            MessageUtil.addError("网络通信异常。");
            return null;
        }
        if ("0000".equals(toa.rtnCode)) {
            billInfo.assemble(toamsg);
            checkPassed = true;
            billTypeCode = "";
            billNo = "";
            verifyNo = "";
            billMoney = null;
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    public String onPay() {

         /*
          缴款书样式编码 票号 全票面校验码  收款金额  年度
         */
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1534011";
        StringBuffer msgbuf = new StringBuffer();
        msgbuf.append(billInfo.getChr_id()).append("|");
        msgbuf.append(billInfo.getBilltype_code()).append("|");
        msgbuf.append(billInfo.getBill_no()).append("|");
        msgbuf.append(billMoney).append("|");
        msgbuf.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date())).append("|");
        msgbuf.append("5|");                                               // 收款状态  5-实收
        msgbuf.append(payType).append("|");                                // 缴款方式编码
        msgbuf.append(billInfo.getCheque_no()).append("|");                // 结算号
        msgbuf.append(billInfo.getPayerbank()).append("|");
        msgbuf.append(billInfo.getPayeraccount()).append("|");

        msgbuf.append(setYear).append("|").append("|").append("|");
        msgbuf.append(tia.serialNo).append("|");
        tia.msgBody = msgbuf.toString().getBytes();
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            KarafLinkingSocketClient client = new KarafLinkingSocketClient();
            toa = client.onRequest(tia);
            toamsg = new String(toa.msgBody);
            logger.info("返回报文体：" + toamsg);
        } catch (Exception e) {
            logger.error("网络通信异常.", e);
            MessageUtil.addError("网络通信异常。");
        }
        if ("0000".equals(toa.rtnCode)) {
            MessageUtil.addInfo("缴款成功!");

            checkPassed = false;
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    public String onHandWritenPay() {
        items.clear();
        if (!StringUtils.isEmpty(item1.getIn_bis_code())) items.add(item1);
        if (!StringUtils.isEmpty(item2.getIn_bis_code())) items.add(item2);
        if (!StringUtils.isEmpty(item3.getIn_bis_code())) items.add(item3);
        if (!StringUtils.isEmpty(item4.getIn_bis_code())) items.add(item4);
        if (!StringUtils.isEmpty(item5.getIn_bis_code())) items.add(item5);


         /*
          手工票
         */
        billInfo.setBank_user(SystemService.getOperatorManager().getOperatorId());
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1534013";
        StringBuffer msgbuf = new StringBuffer("370211|");
        msgbuf.append(billInfo.getBilltype_code()).append("|");
        msgbuf.append(billInfo.getBill_no()).append("|");
        msgbuf.append(billInfo.getVerify_no()).append("|");
        msgbuf.append(billInfo.getPm_code()).append("|");
        msgbuf.append(billInfo.getIen_code()).append("|");
        msgbuf.append(billInfo.getIen_name()).append("|");
        msgbuf.append(billInfo.getConsign_ien_code()).append("|");
        msgbuf.append(billInfo.getConsign_ien_name()).append("|");
        msgbuf.append(billMoney).append("|");
        msgbuf.append(billInfo.getSet_year()).append("|");
        msgbuf.append(billInfo.getBank_user()).append("|");
        msgbuf.append(billInfo.getBank_no()).append("|");
        msgbuf.append(billInfo.getPayer()).append("|");
        msgbuf.append(billInfo.getPayerbank()).append("|");
        msgbuf.append(billInfo.getPayeraccount()).append("|");
        msgbuf.append(billInfo.getReceiver()).append("|");
        msgbuf.append(billInfo.getReceiverbank()).append("|");
        msgbuf.append(billInfo.getReceiveraccount()).append("|");
        msgbuf.append(billInfo.getIs_consign()).append("|");
        msgbuf.append(billInfo.getRemark()).append("|");
        msgbuf.append(items.size()).append("|");
        for (BillItem item : items) {
            msgbuf.append(item.getIn_bis_code()).append(",");
            msgbuf.append(item.getIn_bis_name()).append(",");
            msgbuf.append(item.getChargenum()).append(",");
            msgbuf.append(item.getChargemoney()).append("|");
        }
        tia.msgBody = msgbuf.toString().getBytes();
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            KarafLinkingSocketClient client = new KarafLinkingSocketClient();
            toa = client.onRequest(tia);
            toamsg = new String(toa.msgBody);
            logger.info("返回报文体：" + toamsg);
        } catch (Exception e) {
            logger.error("网络通信异常.", e);
            MessageUtil.addError("网络通信异常。");
        }
        if ("0000".equals(toa.rtnCode)) {
            MessageUtil.addInfo("缴款成功!");
            billInfo = new BillInfo();
            billInfo.setSet_year(new SimpleDateFormat("yyyy").format(new Date()));
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    // 撤销缴款
    public String onCancel() {
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        // TODO
        tia.txnCode = "1534040";
        tia.msgBody = (billInfo.getChr_id() + "|" + billInfo.getBilltype_code() + "|"
                + billInfo.getBill_no() + "|" + billInfo.getSet_year() + "|").getBytes();
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            KarafLinkingSocketClient client = new KarafLinkingSocketClient();
            toa = client.onRequest(tia);
            toamsg = new String(toa.msgBody);
            logger.info("返回报文体：" + toamsg);
        } catch (Exception e) {
            logger.error("网络通信异常.", e);
            MessageUtil.addError("网络通信异常。");
            return null;
        }
        if ("0000".equals(toa.rtnCode)) {
            MessageUtil.addInfo("撤销票据缴款成功");
            checkPassed = false;
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    // 撤销查询
    public String onQueryCancel() {

        LFixedLengthProtocol tia = newFixedLengthProtocol();
        // TODO
        tia.txnCode = "1534099";
        tia.msgBody = (billNo + "|").getBytes();
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            KarafLinkingSocketClient client = new KarafLinkingSocketClient();
            toa = client.onRequest(tia);
            toamsg = new String(toa.msgBody);
            logger.info("返回报文体：" + toamsg);
        } catch (Exception e) {
            logger.error("网络通信异常.", e);
            MessageUtil.addError("网络通信异常。");
            return null;
        }
        if ("0000".equals(toa.rtnCode)) {
            String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(toamsg, "|");
            billInfo.setChr_id(fieldArray[0]);
            billInfo.setBilltype_code(fieldArray[1]);
            billInfo.setBilltype_name(fieldArray[2]);
            billInfo.setBill_no(fieldArray[3]);
            billInfo.setMakedate(fieldArray[4]);
            billInfo.setSet_year(fieldArray[5]);
            billInfo.setBank_user(fieldArray[7]);
            payDate = fieldArray[8] + " " + fieldArray[9];
            billMoney = new BigDecimal(fieldArray[6]);
            checkPassed = true;
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    private LFixedLengthProtocol newFixedLengthProtocol() {
        LFixedLengthProtocol proto = new LFixedLengthProtocol();
        proto.appID = "FISKFQ";
        String date14 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        proto.txnTime = date14;
        proto.serialNo = "KFQFS" + date14.substring(8);
        proto.ueserID = PropertyManager.getProperty("linking.wsys.userid");
        OperatorManager om = ProtocolFactory.getOperatorManager();
        proto.branchID = om.getOperator().getDeptid();
        proto.tellerID = om.getOperatorId();
        return proto;
    }


    // ================================


    public String getBillTypeCode() {
        return billTypeCode;
    }

    public void setBillTypeCode(String billTypeCode) {
        this.billTypeCode = billTypeCode;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getVerifyNo() {
        return verifyNo;
    }

    public void setVerifyNo(String verifyNo) {
        this.verifyNo = verifyNo;
    }

    public BigDecimal getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(BigDecimal billMoney) {
        this.billMoney = billMoney;
    }

    public String getSetYear() {
        return setYear;
    }

    public void setSetYear(String setYear) {
        this.setYear = setYear;
    }

    public boolean isCheckPassed() {
        return checkPassed;
    }

    public void setCheckPassed(boolean checkPassed) {
        this.checkPassed = checkPassed;
    }

    public BillInfo getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(BillInfo billInfo) {
        this.billInfo = billInfo;
    }

    public List<SelectItem> getPayMethodOptions() {
        return payMethodOptions;
    }

    public void setPayMethodOptions(List<SelectItem> payMethodOptions) {
        this.payMethodOptions = payMethodOptions;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public List<SelectItem> getConsignOptions() {
        return consignOptions;
    }

    public void setConsignOptions(List<SelectItem> consignOptions) {
        this.consignOptions = consignOptions;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setItems(List<BillItem> items) {
        this.items = items;
    }

    public BillItem getItem1() {
        return item1;
    }

    public void setItem1(BillItem item1) {
        this.item1 = item1;
    }

    public BillItem getItem2() {
        return item2;
    }

    public void setItem2(BillItem item2) {
        this.item2 = item2;
    }

    public BillItem getItem3() {
        return item3;
    }

    public void setItem3(BillItem item3) {
        this.item3 = item3;
    }

    public BillItem getItem4() {
        return item4;
    }

    public void setItem4(BillItem item4) {
        this.item4 = item4;
    }

    public BillItem getItem5() {
        return item5;
    }

    public void setItem5(BillItem item5) {
        this.item5 = item5;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
}
