package fis.view.pay.pay;

import fis.common.pay.PayType;
import fis.common.pay.ProcessFlag;
import fis.common.pay.VoucherType;
import fis.repository.pay.model.PayPayvoucher;
import fis.service.pay.PayProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ClearanceProcessAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ManagedProperty(value = "#{payProcessService}")
    private PayProcessService payProcessService;

    private List<PayPayvoucher> payvoucherListFortab1;
    private List<PayPayvoucher> payvoucherListFortab2;
    private PayPayvoucher[] payPayvouchers;
    private ProcessFlag processFlag = ProcessFlag.PROCESS_INIT;
    private VoucherType voucherType = VoucherType.VCHTYPE_NORMAL;
    private PayType payType = PayType.PAY_DIRECT;

    @PostConstruct
    public void init() {
        try {
            queryPayvch();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("查询可清算记录失败。");
            MessageUtil.addError("查询可清算记录失败。");
        }
    }

    /*查询*/
    private void queryPayvch() throws ParseException {
        FacesContext context = FacesContext.getCurrentInstance();
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String paytype = paramMap.get("paytype").toString();
        Date todaydt = new Date();
        List<String> procflgPaysuc = new ArrayList<String>();
        procflgPaysuc.add("1");        //支付回写成功
        this.payvoucherListFortab1 = payProcessService.selectedPayvchForProcflag(procflgPaysuc, null, paytype);
        List<String> procflgClearance = new ArrayList<String>();
        procflgClearance.add("4");        //清算回写成功当日数据
        this.payvoucherListFortab2 = payProcessService.selectedPayvchForProcflag(procflgClearance, todaydt, paytype);
    }

    /*多笔清算成功记录发送处理*/
    public String onMultipleReback() {
        if (this.payPayvouchers.length < 1) {
            MessageUtil.addWarn("请选择至少一条数据。");
            return null;
        }
        //todo 发送至支付中心
        //todo 修改 凭证表【处理状态】4=清算回写成功 否则=8清算回写失败
        // 插入凭证历史表
        for (PayPayvoucher record : this.payPayvouchers) {
            try {
                payProcessService.clearanceSucProcess(record, "4");
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("清算回写-插入数据错误：" + ex.getMessage());
                MessageUtil.addError("凭证编号=" + record.getPrintid() + " 插入数据错误：" + ex.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
                return null;
            }
        }
        try {
            queryPayvch();
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("清算回写-数据查询错误：" + e.getMessage());
            MessageUtil.addError("清算回写-数据查询错误：" + e.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        MessageUtil.addInfo("发送成功。");
        return null;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public PayPayvoucher[] getPayPayvouchers() {
        return payPayvouchers;
    }

    public void setPayPayvouchers(PayPayvoucher[] payPayvouchers) {
        this.payPayvouchers = payPayvouchers;
    }

    public PayProcessService getPayProcessService() {
        return payProcessService;
    }

    public void setPayProcessService(PayProcessService payProcessService) {
        this.payProcessService = payProcessService;
    }

    public List<PayPayvoucher> getPayvoucherListFortab1() {
        return payvoucherListFortab1;
    }

    public void setPayvoucherListFortab1(List<PayPayvoucher> payvoucherListFortab1) {
        this.payvoucherListFortab1 = payvoucherListFortab1;
    }

    public List<PayPayvoucher> getPayvoucherListFortab2() {
        return payvoucherListFortab2;
    }

    public void setPayvoucherListFortab2(List<PayPayvoucher> payvoucherListFortab2) {
        this.payvoucherListFortab2 = payvoucherListFortab2;
    }

    public ProcessFlag getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(ProcessFlag processFlag) {
        this.processFlag = processFlag;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }
}
