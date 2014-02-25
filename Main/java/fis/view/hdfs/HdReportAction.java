package fis.view.hdfs;

import fis.view.kfqfs.ReportItem;
import fnt.common.utils.JxlsManager;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ����
 */
@ManagedBean
@ViewScoped
public class HdReportAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(HdReportAction.class);

    private String startDate;
    private String endDate;
    private String totalMsg;

    private List<HdReportItem> items = new ArrayList<HdReportItem>();

    @PostConstruct
    public void init() {
        startDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        endDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public String onQry() {

        // ��ʹ��һ�����ڣ��ձ�
        endDate = startDate;
        LFixedLengthProtocol tia = newFixedLengthProtocol();
        tia.txnCode = "1536000";
        tia.msgBody = (startDate + "|" + endDate + "|").getBytes();
        LFixedLengthProtocol toa = null;
        String toamsg = null;
        try {
            KarafLinkingSocketClient client = new KarafLinkingSocketClient();
            toa = client.onRequest(tia);
            toamsg = new String(toa.msgBody);
            logger.info("���ر����壺" + toamsg);
        } catch (Exception e) {
            logger.error("����ͨ���쳣.", e);
            MessageUtil.addError("����ͨ���쳣��");
            return null;
        }
        if ("0000".equals(toa.rtnCode)) {
            String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(toamsg, "|");
            totalMsg = fieldArray[1];
            int itemCnt = Integer.parseInt(fieldArray[2]); // ��ϸ��
            items.clear();
            for (int i = 0; i < itemCnt; i++) {
                HdReportItem item = new HdReportItem();
                item.setSeqId(String.valueOf(i + 1));
                item.assemble(fieldArray[3 + i]);
                items.add(item);
            }
            if (items.size() == 0) {
                MessageUtil.addWarn("�����������Ϊ��");
            } else {
                MessageUtil.addInfo(totalMsg);
            }
        } else {
            MessageUtil.addError("[" + toa.rtnCode + "]" + new String(toa.msgBody));
        }
        return null;
    }

    public String onReport() {

        try {
            if (items.size() == 0) {
                MessageUtil.addWarn("���Ȳ�ѯ�������ݡ�");
                return null;
            } else {
                String excelFilename = "�Ƶ���˰�����ձ���" + startDate + ".xls";
                JxlsManager jxls = new JxlsManager();
                Map beansMap = new HashMap();
                beansMap.put("records", items);
                beansMap.put("startDate", startDate);
                beansMap.put("totalMsg", totalMsg);
                jxls.exportDataToXls(excelFilename, "/report/HdfsDayReport.xls", beansMap);
            }
        } catch (Exception e) {
            logger.error("���������ձ���ʧ�ܡ�", e);
            MessageUtil.addError("���������ձ���ʧ�ܡ�" + (e.getMessage() == null ? "" : e.getMessage()));
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

    public List<HdReportItem> getItems() {
        return items;
    }

    public void setItems(List<HdReportItem> items) {
        this.items = items;
    }

    public String getTotalMsg() {
        return totalMsg;
    }

    public void setTotalMsg(String totalMsg) {
        this.totalMsg = totalMsg;
    }
}
