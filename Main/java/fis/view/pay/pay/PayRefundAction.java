package fis.view.pay.pay;

import fis.common.pay.PayType;
import fis.common.pay.ProcessFlag;
import fis.common.pay.VoucherType;
import fis.repository.pay.model.PayPayvoucher;
import fis.service.pay.PayProcessService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlCommandButton;
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
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PayRefundAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ManagedProperty(value = "#{payProcessService}")
    private PayProcessService payProcessService;

    private List<PayPayvoucher> payPayvoucherList;
    private String printid;
    private PayPayvoucher payPayvoucher;
    private ProcessFlag processFlag = ProcessFlag.PROCESS_INIT;
    private VoucherType voucherType = VoucherType.VCHTYPE_NORMAL;
    private PayType payType = PayType.PAY_DIRECT;

    @PostConstruct
    public void init() {
        try {
            payPayvoucher = new PayPayvoucher();
            qryRefundsuc();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("获取当日退款记录数据错误:" + ex.getMessage());
            MessageUtil.addError("获取当日退款记录数据错误:" + ex.getMessage());
        }

    }

    private void qryRefundsuc() throws ParseException {
        FacesContext context = FacesContext.getCurrentInstance();
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String paytype = paramMap.get("paytype").toString();
        List<String> procflgRefundsuc = new ArrayList<String>();
        procflgRefundsuc.add("3");        //退款回写成功
        payPayvoucherList = payProcessService.selectedPayvchForProcflag(procflgRefundsuc, new Date(), paytype);
    }

    public String onBtnAcceptClick() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Map paramMap = context.getExternalContext().getRequestParameterMap();
            String paytype = paramMap.get("paytype").toString();
            payPayvoucher = payProcessService.selectedPayvchByPrintid(this.printid);
            if (payPayvoucher == null) {
                MessageUtil.addWarn("没有数据，请检查凭证编号是否正确。");
                payPayvoucher = new PayPayvoucher();
                return null;
            } else if (!payPayvoucher.getPaytype().equals(paytype)) {
                //todo 支付方式判断  临时处理
                String paptypename = payType.getAliasEnums().get(payPayvoucher.getPaytype()).getTitle();
                MessageUtil.addWarn("该凭证支付方式为" + paptypename + "，请到" + paptypename + "页面操作。");
                payPayvoucher = new PayPayvoucher();
                return null;
            } else if (!payPayvoucher.getVouchertype().equals("01")) {
                //设置button不可用
                UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
                HtmlCommandButton btnRefund = (HtmlCommandButton) viewRoot.findComponent("tabView1:editForm:btnRefund");
                btnRefund.setDisabled(true);
                String vchtypename = voucherType.getAliasEnums().get(payPayvoucher.getVouchertype()).getTitle();
                MessageUtil.addWarn("该凭证类型为" + vchtypename + "，无法退款。");
                return null;
            } else if (!payPayvoucher.getProcessflag().equals("1") && !payPayvoucher.getProcessflag().equals("4")) {
                //设置button不可用
                UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
                HtmlCommandButton btnRefund = (HtmlCommandButton) viewRoot.findComponent("tabView1:editForm:btnRefund");
                btnRefund.setDisabled(true);
                String vchtypename = voucherType.getAliasEnums().get(payPayvoucher.getVouchertype()).getTitle();
                MessageUtil.addWarn("该凭证当前的处理状态无法退款。");
                return null;
            }
            //设置button可用
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            HtmlCommandButton btnRefund = (HtmlCommandButton) viewRoot.findComponent("tabView1:editForm:btnRefund");
            btnRefund.setDisabled(false);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("退款-凭证数据=" + this.printid + " 获取错误:" + ex.getMessage());
            MessageUtil.addError("退款-凭证数据=" + this.printid + " 获取错误:" + ex.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }


        return null;
    }

    public String btnRefundClick() {
        if (payPayvoucher.getPrintid() == null || StringUtils.isEmpty(payPayvoucher.getPrintid().toString().toString())) {
            MessageUtil.addWarn("请先获取凭证编号获取信息。");
            return null;
        } else if (!payPayvoucher.getVouchertype().equals("01")) {
            //设置button不可用
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            HtmlCommandButton btnRefund = (HtmlCommandButton) viewRoot.findComponent("tabView1:editForm:btnRefund");
            btnRefund.setDisabled(true);
            String vchtypename = voucherType.getAliasEnums().get(payPayvoucher.getVouchertype()).getTitle();
            MessageUtil.addWarn("该凭证类型为 " + vchtypename + "，无法退款。");
            return null;
        } else if (!payPayvoucher.getProcessflag().equals("1") && !payPayvoucher.getProcessflag().equals("4")) {
            //设置button不可用
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            HtmlCommandButton btnRefund = (HtmlCommandButton) viewRoot.findComponent("tabView1:editForm:btnRefund");
            btnRefund.setDisabled(true);
            String vchtypename = voucherType.getAliasEnums().get(payPayvoucher.getVouchertype()).getTitle();
            MessageUtil.addWarn("该凭证当前处理状态无法退款。");
            return null;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String paytype = paramMap.get("paytype").toString(); //根据支付方式判断下面的流程
        //todo 发送至支付中心退款凭证数据
        //todo (如果是授权支付)更新授权额度
        //todo 更新凭证类型为已退款 处理状态字段 3=退款回写成功
        //todo 插入历史数据
        try {
            payProcessService.refundSucProcess(payPayvoucher, "3", paytype);
            UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
            HtmlCommandButton btnRefund = (HtmlCommandButton) viewRoot.findComponent("tabView1:editForm:btnRefund");
            btnRefund.setDisabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("退款-插入数据错误：" + ex.getMessage());
            MessageUtil.addError("插入数据错误：" + ex.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        MessageUtil.addInfo("退款回写成功。");
        try {
            qryRefundsuc();
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("退款回写-数据查询错误：" + e.getMessage());
            MessageUtil.addError("退款回写-数据查询错误：" + e.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        return null;
    }

    public PayProcessService getPayProcessService() {
        return payProcessService;
    }

    public void setPayProcessService(PayProcessService payProcessService) {
        this.payProcessService = payProcessService;
    }

    public String getPrintid() {
        return printid;
    }

    public void setPrintid(String printid) {
        this.printid = printid;
    }

    public List<PayPayvoucher> getPayPayvoucherList() {
        return payPayvoucherList;
    }

    public void setPayPayvoucherList(List<PayPayvoucher> payPayvoucherList) {
        this.payPayvoucherList = payPayvoucherList;
    }

    public PayPayvoucher getPayPayvoucher() {
        return payPayvoucher;
    }

    public void setPayPayvoucher(PayPayvoucher payPayvoucher) {
        this.payPayvoucher = payPayvoucher;
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

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }
}
