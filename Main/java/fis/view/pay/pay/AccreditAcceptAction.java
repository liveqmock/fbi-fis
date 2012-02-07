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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: 下午2:59
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class AccreditAcceptAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;

    @ManagedProperty(value = "#{accreditVchService}")
    private AccreditVchService accreditVchService;

    private List<PayAccreditvoucher> payAccreditvoucherList;
    private PayAccreditvoucher payAccreditvoucher;

    @PostConstruct
    public void init() {
        getAutAccreditData();
    }

    private void queryAccreditList(short beginmonth, short endmonth) {
        payAccreditvoucherList = accreditVchService.selectedAccreditList(beginmonth, endmonth);
    }

    /*获取数据*/
    public String getAutAccreditData() {
        try {
            //todo 获取支付中心授权额度数据
//            accreditVchService.getAutAccreditVoucher();
            Date dt = new Date();
            SimpleDateFormat df = new SimpleDateFormat("dd");
            short shortday = Short.parseShort(df.format(dt));
            queryAccreditList(shortday, shortday);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("获取授权额度通知单失败。");
            MessageUtil.addError("获取授权额度通知单失败");
            return null;
        }
        MessageUtil.addInfo("获取授权额度数据成功。");
        return null;
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
}
