package fis.view.hdfs;

import fis.view.kfqfs.BillItem;
import gateway.client.KarafLinkingSocketClient;
import gateway.domain.LFixedLengthProtocol;
import gateway.domain.ProtocolFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.security.OperatorManager;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 黄岛非税查询、缴款
 */
@ManagedBean
@ViewScoped
public class PayinWarrantAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(PayinWarrantAction.class);

    private String billType;                             // 通知书类型
    private String billId;                               // 缴款通知书号
    private String instCode;                             // 单位代码
    private WarrantInfo warrantInfo = new WarrantInfo(); // 缴款通知书信息

    private String voucherType;                          // 票据类型
    private BigDecimal payAmt;                           // 金额
    private String fisBizId;
    private String instName;
    private boolean payable = false;                     // 是否可缴款
    private boolean confirmed = false;                   // 手工票是否已验证

    private WarrantItem item1 = new WarrantItem();
    private WarrantItem item2 = new WarrantItem();
    private WarrantItem item3 = new WarrantItem();
    private WarrantItem item4 = new WarrantItem();
    private WarrantItem item5 = new WarrantItem();

    private Voucher vch1 = new Voucher();
    private Voucher vch2 = new Voucher();
    private Voucher vch3 = new Voucher();
    private Voucher vch4 = new Voucher();
    private Voucher vch5 = new Voucher();

    private List<Voucher> vchs = new ArrayList<Voucher>();

    @PostConstruct
    public void init() {
        String date8 = new SimpleDateFormat("yyyyMMdd").format(new Date());
        warrantInfo.setNotifyDate(date8);
        warrantInfo.setLatestDate(date8);

    }

    // 缴款单查询
    public String onQuery() {

        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1531000";
        tia.msgBody = (billId + "|" + voucherType + "|").getBytes();   // 票号
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
            warrantInfo.assemble(toamsg);
            payAmt = new BigDecimal("0.00");
            warrantInfo.assemble(toamsg);
            for (WarrantItem item : warrantInfo.getItems()) {
                payAmt = payAmt.add(new BigDecimal(item.getTxnAmt()));
            }
            payable = true;
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    // 机打票缴款
    public String onPay() {

       /* if (StringUtils.isEmpty(voucherType)) {
            MessageUtil.addError("必须输入票据类型");
            return null;
        }*/
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1531010";
        tia.msgBody = (billId + "|" + voucherType + "|" + payAmt + "|").getBytes();
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
            MessageUtil.addInfo("缴款成功");
            billId = null;
            voucherType = null;
            payable = false;
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    // 机打票冲正
    public String onReverse() {
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1531090";
        tia.msgBody = (billId + "|" + voucherType + "|" + payAmt + "|").getBytes();
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
            MessageUtil.addInfo("缴款冲正成功");
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    // 手工票缴款验证
    public String onHandWritenPayConfirm() {

        warrantInfo.getItems().clear();
        if (!StringUtils.isEmpty(item1.getPrjCode())) {
            warrantInfo.getItems().add(item1);
//            vch1.setVchAmt(item1.getTxnAmt());
//            vchs.add(vch1);
        }
        if (!StringUtils.isEmpty(item2.getPrjCode())) {
            warrantInfo.getItems().add(item2);
//            vch2.setVchAmt(item2.getTxnAmt());
//            vchs.add(vch2);
        }
        if (!StringUtils.isEmpty(item3.getPrjCode())) {
            warrantInfo.getItems().add(item3);
//            vch3.setVchAmt(item3.getTxnAmt());
//            vchs.add(vch3);
        }
        if (!StringUtils.isEmpty(item4.getPrjCode())) {
            warrantInfo.getItems().add(item4);
//            vch4.setVchAmt(item4.getTxnAmt());
//            vchs.add(vch4);
        }
        if (!StringUtils.isEmpty(item5.getPrjCode())) {
            warrantInfo.getItems().add(item5);
//            vch5.setVchAmt(item5.getTxnAmt());
//            vchs.add(vch5);
        }
        warrantInfo.setItemNum(warrantInfo.getItems().size());

        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1532000";
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(warrantInfo.getBillId()).append("|");
        strBuilder.append(warrantInfo.getInstCode()).append("|");
        strBuilder.append(warrantInfo.getPayerName()).append("|");
        strBuilder.append(warrantInfo.getNotifyDate()).append("|");
        strBuilder.append(warrantInfo.getLatestDate()).append("|");
        strBuilder.append(warrantInfo.getVerifyNo()).append("|");
        strBuilder.append(warrantInfo.getRemark()).append("|");
        strBuilder.append(warrantInfo.getVoucherType()).append("|");
//        strBuilder.append(warrantInfo.getBillType()).append("|");
        strBuilder.append("").append("|");
//        strBuilder.append(warrantInfo.getContractNo()).append("|");
        strBuilder.append("").append("|");
        strBuilder.append(warrantInfo.getItemNum()).append("|");

        for (WarrantItem item : warrantInfo.getItems()) {
            strBuilder.append(item.getPrjCode()).append(",");
            strBuilder.append(item.getMeasure()).append(",");
            strBuilder.append(item.getHandleNum()).append(",");
            strBuilder.append(item.getTxnAmt()).append("|");
        }

        tia.msgBody = strBuilder.toString().getBytes();
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
            MessageUtil.addInfo("验证通过，可缴款");
            confirmed = true;
            String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(toamsg, "|");
            fisBizId = fieldArray[0];
            instName = fieldArray[1];

        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    // 手工票验证后缴款
    public String onHandWritenPay() {
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1532010";
        StringBuilder strBuilder = new StringBuilder();
//        strBuilder.append(fisBizId).append("|").append(instName).append("|").append(vchs.size()).append("|");
        strBuilder.append(fisBizId).append("|").append(instName).append("|").append("0|");
        /*for (Voucher vch : vchs) {
            strBuilder.append(vch.getVchType()).append(",");
            strBuilder.append(vch.getVchNum()).append(",");
            strBuilder.append(vch.getVchAmt()).append(",");
            strBuilder.append(vch.getVchSts()).append("|");
        }*/
        tia.msgBody = strBuilder.toString().getBytes();
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
            MessageUtil.addInfo("缴款成功");
            confirmed = false;
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    // 手工票冲正
    public String onWrtnReverse() {
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1532090";
        tia.msgBody = (billId + "|" + instCode + "|" + payAmt + "|").getBytes();
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
            MessageUtil.addInfo("缴款冲正成功");
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }


    private LFixedLengthProtocol newFixedLengthProtocol() {
        LFixedLengthProtocol proto = new LFixedLengthProtocol();
        proto.appID = "FISHD";
        String date14 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        proto.txnTime = date14;
        proto.serialNo = "HDFS" + date14.substring(8);
        proto.ueserID = PropertyManager.getProperty("linking.wsys.userid");
        OperatorManager om = ProtocolFactory.getOperatorManager();
        proto.branchID = om.getOperator().getDeptid();
        proto.tellerID = om.getOperatorId();
        return proto;
    }


    // ================================


    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public boolean isPayable() {
        return payable;
    }

    public void setPayable(boolean payable) {
        this.payable = payable;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public WarrantInfo getWarrantInfo() {
        return warrantInfo;
    }

    public void setWarrantInfo(WarrantInfo warrantInfo) {
        this.warrantInfo = warrantInfo;
    }

    public WarrantItem getItem1() {
        return item1;
    }

    public void setItem1(WarrantItem item1) {
        this.item1 = item1;
    }

    public WarrantItem getItem2() {
        return item2;
    }

    public void setItem2(WarrantItem item2) {
        this.item2 = item2;
    }

    public WarrantItem getItem3() {
        return item3;
    }

    public void setItem3(WarrantItem item3) {
        this.item3 = item3;
    }

    public WarrantItem getItem4() {
        return item4;
    }

    public void setItem4(WarrantItem item4) {
        this.item4 = item4;
    }

    public WarrantItem getItem5() {
        return item5;
    }

    public void setItem5(WarrantItem item5) {
        this.item5 = item5;
    }

    public Voucher getVch1() {
        return vch1;
    }

    public void setVch1(Voucher vch1) {
        this.vch1 = vch1;
    }

    public Voucher getVch2() {
        return vch2;
    }

    public void setVch2(Voucher vch2) {
        this.vch2 = vch2;
    }

    public Voucher getVch3() {
        return vch3;
    }

    public void setVch3(Voucher vch3) {
        this.vch3 = vch3;
    }

    public Voucher getVch4() {
        return vch4;
    }

    public void setVch4(Voucher vch4) {
        this.vch4 = vch4;
    }

    public Voucher getVch5() {
        return vch5;
    }

    public void setVch5(Voucher vch5) {
        this.vch5 = vch5;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getFisBizId() {
        return fisBizId;
    }

    public void setFisBizId(String fisBizId) {
        this.fisBizId = fisBizId;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public List<Voucher> getVchs() {
        return vchs;
    }

    public void setVchs(List<Voucher> vchs) {
        this.vchs = vchs;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }
}
