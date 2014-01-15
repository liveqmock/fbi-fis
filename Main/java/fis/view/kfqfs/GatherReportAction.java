package fis.view.kfqfs;

import fnt.common.utils.JxlsManager;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报表
 */
@ManagedBean
@ViewScoped
public class GatherReportAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(GatherReportAction.class);

    private String startDate;
    private String endDate;

    private List<ReportItem> items = new ArrayList<ReportItem>();

    @PostConstruct
    public void init() {
        startDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public String onQry() {

        // 仅使用一个日期，日报
        endDate = startDate;
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1534080";
        tia.msgBody = ("1|370211|" + startDate + "|" + endDate + "|").getBytes();
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
            String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(toamsg, "|");
            int itemCnt = Integer.parseInt(fieldArray[0]); // 明细数
            items.clear();
            for (int i = 0; i < itemCnt; i++) {
                ReportItem item = new ReportItem();
                item.assemble(fieldArray[1 + i]);
                items.add(item);
            }
            if (items.size() == 0) {
                MessageUtil.addWarn("报表相关数据为空");
            }
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    public String onReport() {

        try {
            if (items.size() == 0) {
                MessageUtil.addWarn("请先查询报表数据。");
                return null;
            } else {
                String excelFilename = "开发区财政非税收入日报表" + startDate + ".xls";
                JxlsManager jxls = new JxlsManager();
                Map beansMap = new HashMap();
                beansMap.put("records", items);
                beansMap.put("startDate", startDate);
                jxls.exportDataToXls(excelFilename, "/report/KfqfsDayReport.xls", beansMap);
            }
        } catch (Exception e) {
            logger.error("生成收入日报表失败。", e);
            MessageUtil.addError("生成收入日报表失败。" + (e.getMessage() == null ? "" : e.getMessage()));
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

    public List<ReportItem> getItems() {
        return items;
    }

    public void setItems(List<ReportItem> items) {
        this.items = items;
    }
}
