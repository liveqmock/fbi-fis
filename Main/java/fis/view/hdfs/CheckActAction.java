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
 * 黄岛非税对账
 */
@ManagedBean
@ViewScoped
public class CheckActAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(CheckActAction.class);
    private String startDate;
    private String endDate;

    private ChkactRtnInfo chkactRtnInfo = new ChkactRtnInfo();

    @PostConstruct
    public void init() {
        startDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        endDate = startDate;
    }

    // 对账
    public String onChk() {

        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1534000";
        tia.msgBody = (startDate + "|" + endDate + "|").getBytes();
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
            MessageUtil.addInfo("对账成功！[" + toa.rtnCode + "]" + new String(toa.msgBody));

        } else {
            MessageUtil.addError("对账不平，请查看明细！");
            chkactRtnInfo.assemble(toamsg);
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ChkactRtnInfo getChkactRtnInfo() {
        return chkactRtnInfo;
    }

    public void setChkactRtnInfo(ChkactRtnInfo chkactRtnInfo) {
        this.chkactRtnInfo = chkactRtnInfo;
    }
}
