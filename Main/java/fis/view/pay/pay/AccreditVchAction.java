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
 * Time: 下午3:00
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

    /*按条件查询数据*/
    public String onQueryClick() {
        try {
            this.payAccreditvoucherList = accreditVchService.selectedAccreditsByCon(payAccreditvoucherQry.getBdgagencyname(), payAccreditvoucherQry.getPaybankaccount(),
                    payAccreditvoucherQry.getBillcode(), payAccreditvoucherQry.getMonth());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("授权额度查询失败：" + ex.getMessage());
            MessageUtil.addError("授权额度查询失败：" + ex.getMessage());
            return null;
        }
        return null;
    }

    public String onPrintQryClick() {
        try {
            this.payAccreditvoucherListForPrt = accreditVchService.selectedAccreditForPrint(payAccreditvoucherQry.getBdgagencyname());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("打印授权额度查询失败：" + ex.getMessage());
            MessageUtil.addError("打印授权额度查询失败：" + ex.getMessage());
            return null;
        }
        return null;

    }

    /*打印授权额度通知单*/
    public String printAccreditvch() {
        return null;
    }

    /*导出xls表格*/
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
