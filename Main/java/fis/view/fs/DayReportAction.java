package fis.view.fs;

import fis.repository.fs.model.FsPaymentinfo;
import fis.service.fs.PaymentService;
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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ����
 */
@ManagedBean
@ViewScoped
public class DayReportAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(DayReportAction.class);

    private String qrydate;

    private List<FsPaymentinfo> items = new ArrayList<FsPaymentinfo>();
    private String areaCode = "";
    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;

    @PostConstruct
    public void init() {
        qrydate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        areaCode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bofcode");
    }

    public String onQry() {


        try {
            items = paymentService.qryPaymentsByDate(qrydate, areaCode);
            if (items == null || items.isEmpty()) {
                MessageUtil.addWarn("û�в�ѯ�����ݣ�");
            }

        } catch (Exception e) {
            logger.error("�ձ����ݲ�ѯ�쳣.", e);
            MessageUtil.addError("�ձ����ݲ�ѯ�쳣��");
        }
        return null;

    }

    public String onReport() {

        try {
            if (items.size() == 0) {
                MessageUtil.addWarn("���Ȳ�ѯ�������ݡ�");
                return null;
            } else {
                // �����
                int i = 1;
                for (FsPaymentinfo item : items) {
                    item.setPkid(String.valueOf(i++));
                    item.setPayfeemethod("�ֽ�");
                }
                String excelFilename = "����������˰�����ձ���" + qrydate + ".xls";
                JxlsManager jxls = new JxlsManager();
                Map beansMap = new HashMap();
                beansMap.put("records", items);
                beansMap.put("qrydate", qrydate);

                jxls.exportDataToXls(excelFilename, "/report/cyfsDayReport.xls", beansMap);
            }
        } catch (Exception e) {
            logger.error("���������ձ���ʧ�ܡ�", e);
            MessageUtil.addError("���������ձ���ʧ�ܡ�" + (e.getMessage() == null ? "" : e.getMessage()));
        }
        return null;
    }


    public String getQrydate() {
        return qrydate;
    }

    public void setQrydate(String qrydate) {
        this.qrydate = qrydate;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public List<FsPaymentinfo> getItems() {
        return items;
    }

    public void setItems(List<FsPaymentinfo> items) {
        this.items = items;
    }
}
