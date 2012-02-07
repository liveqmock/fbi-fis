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
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: 下午3:05
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PayProcessAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ManagedProperty(value = "#{payProcessService}")
    private PayProcessService payProcessService;
    private PayPayvoucher payPayvoucher;
    private ProcessFlag processFlag = ProcessFlag.PROCESS_INIT;
    private VoucherType voucherType = VoucherType.VCHTYPE_NORMAL;
    private PayType payType = PayType.PAY_DIRECT;
    private String printid;

    @PostConstruct
    public void init() {
        payPayvoucher = new PayPayvoucher();
    }

    /*支付获取数据*/
    public String onBtnAcceptClick() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Map paramMap = context.getExternalContext().getRequestParameterMap();
            String paytype = paramMap.get("paytype").toString();
            payPayvoucher = payProcessService.selectedPayvchByPrintid(printid);
            if (payPayvoucher == null) {
                MessageUtil.addWarn("没有数据，请检查凭证号是否正确。");
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
                setButtonDisabled(true);
                String vchtypename = voucherType.getAliasEnums().get(payPayvoucher.getVouchertype()).getTitle();
                MessageUtil.addWarn("该凭证类型为" + vchtypename + "，无法进行支付。");
                return null;
            } else if (!payPayvoucher.getProcessflag().equals("0") && !payPayvoucher.getProcessflag().equals("5")
                    && !payPayvoucher.getProcessflag().equals("6")) {
                //设置button不可用
                setButtonDisabled(true);
                String processflagname = processFlag.getAliasEnums().get(payPayvoucher.getProcessflag()).getTitle();
                MessageUtil.addWarn("该凭证处理状态为" + processflagname + "，无法进行支付。");
                return null;
            }
            //设置button可用
            setButtonDisabled(false);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("获取数据失败：" + ex.getMessage());
            MessageUtil.addError("获取数据失败：" + ex.getMessage());
            return null;
        }

        return null;
    }

    /*支付*/
    public String btnPayClick() {
        if (payPayvoucher.getPrintid() == null || StringUtils.isEmpty(payPayvoucher.getPrintid().toString())) {
            MessageUtil.addWarn("请先输入凭证编号获取信息。");
            return null;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String paytype = paramMap.get("paytype").toString();
        //todo 通过接口发送处理成功信息
        //todo 修改账户授权额度
        //修改凭证表【处理状态】字段 1=支付回写成功
        // 插入凭证历史表数据
        try {
            payProcessService.paySucProcess(payPayvoucher, "1", paytype);
            setButtonDisabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("支付成功-插入数据错误：" + ex.getMessage());
            MessageUtil.addError("插入数据错误：" + ex.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        MessageUtil.addInfo("支付回写成功！");
        return null;
    }

    /*退票*/
    public String btnRefuseClick() {
        if (payPayvoucher.getPrintid() == null || StringUtils.isEmpty(payPayvoucher.getPrintid().toString().toString())) {
            MessageUtil.addWarn("请输入凭证编号获取信息。");
            return null;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String paytype = paramMap.get("paytype").toString();
        //todo 通过接口发送退票信息
        // 修改凭证表【凭证类型】 02-撤销支付；【处理状态】字段 3=回写成功
        // 插入凭证历史表数据
        try {
            payProcessService.refuseSucProcess(payPayvoucher, "2", paytype);
            setButtonDisabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("退票-插入数据错误：" + ex.getMessage());
            MessageUtil.addError("插入数据错误：" + ex.getCause().getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        MessageUtil.addInfo("退票回写成功！");
        return null;
    }

    /*设置button状态*/
    private void setButtonDisabled(boolean disabled) {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        HtmlCommandButton btnPay = (HtmlCommandButton) viewRoot.findComponent("editForm:btnPay");
        HtmlCommandButton btnRefuse = (HtmlCommandButton) viewRoot.findComponent("editForm:btnRefuse");
        btnPay.setDisabled(disabled);
        btnRefuse.setDisabled(disabled);
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

    public String getPrintid() {
        return printid;
    }

    public void setPrintid(String printid) {
        this.printid = printid;
    }

    public PayPayvoucher getPayPayvoucher() {
        return payPayvoucher;
    }

    public void setPayPayvoucher(PayPayvoucher payPayvoucher) {
        this.payPayvoucher = payPayvoucher;
    }

    public PayProcessService getPayProcessService() {
        return payProcessService;
    }

    public void setPayProcessService(PayProcessService payProcessService) {
        this.payProcessService = payProcessService;
    }

    public ProcessFlag getProcessFlag() {
        return processFlag;
    }

    public void setProcessFlag(ProcessFlag processFlag) {
        this.processFlag = processFlag;
    }
}
