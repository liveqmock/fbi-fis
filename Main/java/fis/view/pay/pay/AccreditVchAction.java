package fis.view.pay.pay;

import fis.repository.pay.model.PayAccreditvoucher;
import fis.service.pay.AccreditVchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;
import skyline.service.PlatformService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: ����3:00
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class AccreditVchAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;

    @ManagedProperty(value = "#{accreditVchService}")
    private AccreditVchService accreditVchService;

    private PayAccreditvoucher payAccreditvoucherQry;
    private List<PayAccreditvoucher> payAccreditvoucherList;
    private PayAccreditvoucher payAccreditvoucher;
    private PayAccreditvoucher[] payAccreditvouchers;

    private List<PayAccreditvoucher> payAccreditvoucherListForPrt;
    private PayAccreditvoucher payAccreditvoucherForPrt;

    @PostConstruct
    public void init() {
        payAccreditvoucherQry = new PayAccreditvoucher();
    }

    /*��������ѯ����*/
    public String onQueryClick() {
        try {
            this.payAccreditvoucherList = accreditVchService.selectedAccreditsByCon(payAccreditvoucherQry.getBdgagencyname(), payAccreditvoucherQry.getPaybankaccount(),
                    payAccreditvoucherQry.getBillcode(), payAccreditvoucherQry.getMonth());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("��Ȩ��Ȳ�ѯʧ�ܣ�" + ex.getMessage());
            MessageUtil.addError("��Ȩ��Ȳ�ѯʧ�ܣ�" + ex.getMessage());
            return null;
        }
        return null;
    }

    public String onPrintQryClick() {
        try {
            this.payAccreditvoucherListForPrt = accreditVchService.selectedAccreditForPrint(payAccreditvoucherQry.getBdgagencyname());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("��ӡ��Ȩ��Ȳ�ѯʧ�ܣ�" + ex.getMessage());
            MessageUtil.addError("��ӡ��Ȩ��Ȳ�ѯʧ�ܣ�" + ex.getMessage());
            return null;
        }
        return null;

    }

    /*��ӡ��Ȩ���֪ͨ��*/
    public String printAccreditvch() {
        return null;
    }

    /*����xls���*/
    public String exportxls() {
        return null;
    }

    public List<PayAccreditvoucher> getPayAccreditvoucherListForPrt() {
        return payAccreditvoucherListForPrt;
    }

    public void setPayAccreditvoucherListForPrt(List<PayAccreditvoucher> payAccreditvoucherListForPrt) {
        this.payAccreditvoucherListForPrt = payAccreditvoucherListForPrt;
    }

    public PayAccreditvoucher getPayAccreditvoucherForPrt() {
        return payAccreditvoucherForPrt;
    }

    public void setPayAccreditvoucherForPrt(PayAccreditvoucher payAccreditvoucherForPrt) {
        this.payAccreditvoucherForPrt = payAccreditvoucherForPrt;
    }

    public PayAccreditvoucher getPayAccreditvoucherQry() {
        return payAccreditvoucherQry;
    }

    public void setPayAccreditvoucherQry(PayAccreditvoucher payAccreditvoucherQry) {
        this.payAccreditvoucherQry = payAccreditvoucherQry;
    }

    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public AccreditVchService getAccreditVchService() {
        return accreditVchService;
    }

    public void setAccreditVchService(AccreditVchService accreditVchService) {
        this.accreditVchService = accreditVchService;
    }

    public List<PayAccreditvoucher> getPayAccreditvoucherList() {
        return payAccreditvoucherList;
    }

    public void setPayAccreditvoucherList(List<PayAccreditvoucher> payAccreditvoucherList) {
        this.payAccreditvoucherList = payAccreditvoucherList;
    }

    public PayAccreditvoucher getPayAccreditvoucher() {
        return payAccreditvoucher;
    }

    public void setPayAccreditvoucher(PayAccreditvoucher payAccreditvoucher) {
        this.payAccreditvoucher = payAccreditvoucher;
    }

    public PayAccreditvoucher[] getPayAccreditvouchers() {
        return payAccreditvouchers;
    }

    public void setPayAccreditvouchers(PayAccreditvoucher[] payAccreditvouchers) {
        this.payAccreditvouchers = payAccreditvouchers;
    }
}
