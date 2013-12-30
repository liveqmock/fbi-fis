package fnt.view;

import fnt.common.utils.JxlsManager;
import fnt.common.utils.ObjectFieldsCopier;
import fnt.repository.model.FsQdfSysCtl;
import fnt.repository.model.common.DayGatherData;
import fnt.service.CommonService;
import fnt.service.FsqdfSysCtlService;
import fnt.view.pojo.DayGatherRptPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 13-6-27
 * Time: ����4:40
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class DayGatherRptAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(PaymentAction.class);

    private String date8;
    @ManagedProperty(value = "#{fsqdfSysCtlService}")
    private FsqdfSysCtlService sysCtlService;
    @ManagedProperty(value = "#{commonService}")
    private CommonService commonService;

    private FsQdfSysCtl sysCtl;
    private String totalIncome = "0.00";                   // �ܼ�
    private String totalVchIncome = "0.00";                // ������������ϼ� [����Ʊ���ֹ�Ʊ�ɿ�]
    private String totalPendingIncome = "0.00";            // ���մ���Ʊת����ϼ� [���˳ɹ��Ĵ���Ʊת��Ʊ����]
    private String totalBudgetCityIncome = "0.00";            // Ԥ���ںϼ�
    private String totalFundCityIncome = "0.00";            // Ԥ����ϼ� [���˳ɹ��Ĳ�����תƱ��(ע�⣺�����������ִ���)]
    private String totalUnknownIncome = "0.00";            //  ���ղ�����ת����ϼ�

    private String vchBudgetCityIncome = "0.00";               // Ԥ���ڱ�����������
    private String pendingBudgetCityIncome = "0.00";           // Ԥ���ڱ�������Ʊ����
    private String unknownBudgetCityIncome = "0.00";           // Ԥ���ڱ�������������

    private String vchFundCityIncome = "0.00";               // Ԥ���Ȿ����������
    private String pendingFundCityIncome = "0.00";           // Ԥ���Ȿ������Ʊ����
    private String unknownFundCityIncome = "0.00";           // Ԥ���Ȿ������������

    private DecimalFormat df = new DecimalFormat("###,##0.00");
    private List<DayGatherData> dayGatherList = new ArrayList<DayGatherData>();
    private List<DayGatherRptPojo> dayGatherRptRecords = new ArrayList<DayGatherRptPojo>();

    @PostConstruct
    public void init() {
        sysCtl = sysCtlService.getFsQdfSysCtl("1");
        date8 = new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public String onQry() {
        try {
            if (date8.compareTo(sysCtl.getTxnDate()) >= 0) {
                MessageUtil.addWarn(date8 + "������δ���ˣ��޷����ɱ���");
                return null;
            }
            dayGatherList.clear();
            dayGatherList = commonService.qryDayGatherDataByDate(date8);
            dayGatherRptRecords.clear();
            if (dayGatherList.size() == 0) {
                MessageUtil.addWarn("û�в�ѯ�����ձ������ݡ�");
                return null;
            }
            BigDecimal totalBDIncome = new BigDecimal("0.00");
            BigDecimal totalVchBDIncome = new BigDecimal("0.00");
            BigDecimal totalPendingBDIncome = new BigDecimal("0.00");
            BigDecimal totalUnknownBDIncome = new BigDecimal("0.00");
            BigDecimal totalBudgetBDIncome = new BigDecimal("0.00");
            BigDecimal totalFundBDIncome = new BigDecimal("0.00");
            BigDecimal vchBudgetCityBDIncome = new BigDecimal("0.00");
            BigDecimal pendingBudgetCityBDIncome = new BigDecimal("0.00");
            BigDecimal unknownBudgetCityBDIncome = new BigDecimal("0.00");
            BigDecimal vchFundCityBDIncome = new BigDecimal("0.00");
            BigDecimal pendingFundCityBDIncome = new BigDecimal("0.00");
            BigDecimal unknownFundCityBDIncome = new BigDecimal("0.00");

            for (DayGatherData data : dayGatherList) {
                DayGatherRptPojo record = new DayGatherRptPojo();
                ObjectFieldsCopier.copyFields(data, record);

                if ("01".equals(data.getZjxzbm()) || "1".equals(data.getZjxzbm())) {                                     // 01-Ԥ����
                    record.setBudgetItemAmt(df.format(record.getZje()));
                    BigDecimal bjAmt = record.getZje();
                    if ("000000".equals(data.getHrxzqh())) {                             // �Ͻ�����
                        record.setBudgetCentreAmt(df.format(record.getFcje()));
                        bjAmt = bjAmt.subtract(record.getFcje());
                    }
                    if ("370000".equals(data.getHrxzqh())) {                      // �Ͻ�ʡ
                        record.setBudgetProvinceAmt(df.format(record.getFcje()));
                        bjAmt = bjAmt.subtract(record.getFcje());
                    }                                                              // ��������
                    record.setBudgetCityAmt(df.format(bjAmt));
                    totalBudgetBDIncome = totalBudgetBDIncome.add(bjAmt);          // ��������ϼ�
                    // ��������
                    if ("1".equals(data.getPendingflag())) {   // ��Ʊ
                        record.setIncomeProperty("����Ʊת����");
                        totalPendingBDIncome = totalPendingBDIncome.add(record.getZje());
                        pendingBudgetCityBDIncome = pendingBudgetCityBDIncome.add(bjAmt);
                    } else if ("3".equals(data.getPendingflag())) { // ������ֱ�Ӳ�Ʊ
                        record.setIncomeProperty("������ת����");
                        totalUnknownBDIncome = totalUnknownBDIncome.add(record.getZje());
                        unknownBudgetCityBDIncome = unknownBudgetCityBDIncome.add(bjAmt);
                    } else {
                        record.setIncomeProperty("��������");
                        totalVchBDIncome = totalVchBDIncome.add(record.getZje());
                        vchBudgetCityBDIncome = vchBudgetCityBDIncome.add(bjAmt);
                    }
                } else {                                                                  // 02-Ԥ����
                    record.setFundItemAmt(df.format(record.getZje()));
                    BigDecimal bjAmt = record.getZje();
                    if ("000000".equals(data.getHrxzqh())) {                             // �Ͻ�����
                        record.setFundCentreAmt(df.format(record.getFcje()));
                        bjAmt = bjAmt.subtract(record.getFcje());
                    }
                    if ("370000".equals(data.getHrxzqh())) {                      // �Ͻ�ʡ
                        record.setFundProvinceAmt(df.format(record.getFcje()));
                        bjAmt = bjAmt.subtract(record.getFcje());
                    }                                                               // ��������
                    record.setFundCityAmt(df.format(bjAmt));
                    totalFundBDIncome = totalFundBDIncome.add(bjAmt);               // ��������ϼ�
                    // ��������
                    if ("1".equals(data.getPendingflag())) {   // ��Ʊ
                        record.setIncomeProperty("����Ʊת����");
                        totalPendingBDIncome = totalPendingBDIncome.add(record.getZje());
                        pendingFundCityBDIncome = pendingFundCityBDIncome.add(bjAmt);
                    } else if ("3".equals(data.getPendingflag())) { // ������ֱ�Ӳ�Ʊ
                        record.setIncomeProperty("������ת����");
                        totalUnknownBDIncome = totalUnknownBDIncome.add(record.getZje());
                        unknownFundCityBDIncome = unknownFundCityBDIncome.add(bjAmt);
                    } else {
                        record.setIncomeProperty("��������");
                        totalVchBDIncome = totalVchBDIncome.add(record.getZje());
                        vchFundCityBDIncome = vchFundCityBDIncome.add(bjAmt);
                    }
                }
                dayGatherRptRecords.add(record);
            }
            totalBDIncome = totalBDIncome.add(totalPendingBDIncome).add(totalUnknownBDIncome).add(totalVchBDIncome);
            totalIncome = df.format(totalBDIncome);
            totalPendingIncome = df.format(totalPendingBDIncome);
            totalUnknownIncome = df.format(totalUnknownBDIncome);
            totalVchIncome = df.format(totalVchBDIncome);
            totalBudgetCityIncome = df.format(totalBudgetBDIncome);
            totalFundCityIncome = df.format(totalFundBDIncome);
            vchBudgetCityIncome = df.format(vchBudgetCityBDIncome);
            pendingBudgetCityIncome = df.format(pendingBudgetCityBDIncome);
            unknownBudgetCityIncome = df.format(unknownBudgetCityBDIncome);
            vchFundCityIncome = df.format(vchFundCityBDIncome);
            pendingFundCityIncome = df.format(pendingFundCityBDIncome);
            unknownFundCityIncome = df.format(unknownFundCityBDIncome);
        } catch (Exception e) {
            logger.error("��ѯ�ձ�����ʧ�ܡ�", e);
            MessageUtil.addError("��ѯ�ձ�����ʧ�ܡ�" + (e.getMessage() == null ? "" : e.getMessage()));
        }
        return null;
    }

    public String onGenRpt() {
        try {
            if (date8.compareTo(sysCtl.getTxnDate()) >= 0) {
                MessageUtil.addWarn(date8 + "������δ���ˣ��޷����ɱ���");
                return null;
            }
            if (dayGatherRptRecords.size() == 0) {
                MessageUtil.addWarn("���Ȳ�ѯ�������ݡ�");
                return null;
            } else {
                String excelFilename = "�ൺ�в�����˰�����ձ���-" + date8 + ".xls";
                JxlsManager jxls = new JxlsManager();
                Map beansMap = new HashMap();
                logger.info(totalIncome + "   " + totalVchIncome + " " + totalPendingIncome + " " + totalUnknownIncome);
                beansMap.put("records", dayGatherRptRecords);
                beansMap.put("actno", sysCtl.getCbsActno());
                beansMap.put("actnam", sysCtl.getCbsActnam());
                beansMap.put("rptdate", date8);
                beansMap.put("bankName", sysCtl.getBankName());
                beansMap.put("totalIncome", totalIncome);
                beansMap.put("totalVchIncome", totalVchIncome);
                beansMap.put("totalPendingIncome", totalPendingIncome);
                beansMap.put("totalUnknownIncome", totalUnknownIncome);
                beansMap.put("totalBudgetCityIncome", totalBudgetCityIncome);
                beansMap.put("totalFundCityIncome", totalFundCityIncome);
                beansMap.put("vchBudgetCityIncome", vchBudgetCityIncome);
                beansMap.put("pendingBudgetCityIncome", pendingBudgetCityIncome);
                beansMap.put("unknownBudgetCityIncome", unknownBudgetCityIncome);
                beansMap.put("vchFundCityIncome", vchFundCityIncome);
                beansMap.put("pendingFundCityIncome", pendingFundCityIncome);
                beansMap.put("unknownFundCityIncome", unknownFundCityIncome);
                jxls.exportDataToXls(excelFilename, "/report/FntTotalIncome.xls", beansMap);
            }
        } catch (Exception e) {
            logger.error("���������ձ���ʧ�ܡ�", e);
            MessageUtil.addError("���������ձ���ʧ�ܡ�" + (e.getMessage() == null ? "" : e.getMessage()));
        }
        return null;
    }

    // -------------------------------------

    public List<DayGatherData> getDayGatherList() {
        return dayGatherList;
    }

    public void setDayGatherList(List<DayGatherData> dayGatherList) {
        this.dayGatherList = dayGatherList;
    }

    public List<DayGatherRptPojo> getDayGatherRptRecords() {
        return dayGatherRptRecords;
    }

    public void setDayGatherRptRecords(List<DayGatherRptPojo> dayGatherRptRecords) {
        this.dayGatherRptRecords = dayGatherRptRecords;
    }

    public CommonService getCommonService() {
        return commonService;
    }

    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    public FsQdfSysCtl getSysCtl() {
        return sysCtl;
    }

    public void setSysCtl(FsQdfSysCtl sysCtl) {
        this.sysCtl = sysCtl;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalVchIncome() {
        return totalVchIncome;
    }

    public void setTotalVchIncome(String totalVchIncome) {
        this.totalVchIncome = totalVchIncome;
    }

    public String getTotalPendingIncome() {
        return totalPendingIncome;
    }

    public void setTotalPendingIncome(String totalPendingIncome) {
        this.totalPendingIncome = totalPendingIncome;
    }

    public String getTotalUnknownIncome() {
        return totalUnknownIncome;
    }

    public void setTotalUnknownIncome(String totalUnknownIncome) {
        this.totalUnknownIncome = totalUnknownIncome;
    }

    public DecimalFormat getDf() {
        return df;
    }

    public void setDf(DecimalFormat df) {
        this.df = df;
    }

    public String getDate8() {
        return date8;
    }

    public void setDate8(String date8) {
        this.date8 = date8;
    }

    public FsqdfSysCtlService getSysCtlService() {
        return sysCtlService;
    }

    public void setSysCtlService(FsqdfSysCtlService sysCtlService) {
        this.sysCtlService = sysCtlService;
    }
}
