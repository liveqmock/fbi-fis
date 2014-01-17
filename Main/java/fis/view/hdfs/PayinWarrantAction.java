package fis.view.hdfs;

import gateway.client.SyncSocketClient;
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
import java.util.Date;

/**
 * 黄岛非税查询、缴款
 */
@ManagedBean
@ViewScoped
public class PayinWarrantAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(PayinWarrantAction.class);

    private String billId;                               // 缴款通知书号
    private WarrantInfo warrantInfo = new WarrantInfo(); // 缴款通知书信息

    private String voucherType;                          // 票据类型
    private BigDecimal payAmt;                           // 金额
    private boolean payable = false;                     // 是否可缴款

    @PostConstruct
    public void init() {


    }

    public String onQuery() {

        LFixedLengthProtocol tia = newFixedLengthProtocol();
        // TODO 交易码
        tia.txnCode = "1000";
        tia.msgBody = (billId + "|").getBytes();   // 票号
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            SyncSocketClient client = new SyncSocketClient();
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
            payable = true;
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    public String onPay() {

        if (StringUtils.isEmpty(voucherType)) {
            MessageUtil.addError("必须输入票据类型");
            return null;
        }
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        // TODO 交易码
        tia.txnCode = "1010";
        tia.msgBody = (billId + "|" + voucherType + "|" + warrantInfo.getOverdueAmt() + "|").getBytes();
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            SyncSocketClient client = new SyncSocketClient();
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

    public String onReverse() {
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        // TODO 交易码
        tia.txnCode = "1090";
        tia.msgBody = (billId + "|" + voucherType + "|" + payAmt + "|").getBytes();
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            SyncSocketClient client = new SyncSocketClient();
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
}
