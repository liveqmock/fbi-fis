package fis.view.gwk;

import fis.common.gwk.constant.ConfirmPayFlg;
import fis.common.gwk.constant.PayStatus;
import fis.repository.gwk.model.GwkPaybackinfo;
import fis.service.gwk.PaybackService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: 下午11:13
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PaybackAction {
    private static final Logger logger = LoggerFactory.getLogger(PaybackAction.class);
    @ManagedProperty(value = "#{paybackService}")
    private PaybackService paybackService;
    private List<GwkPaybackinfo> gwkPaybackinfoList;
    private PayStatus payStatus = PayStatus.SPDB_INIT;
    private ConfirmPayFlg confirmPayFlg = ConfirmPayFlg.CONFIRMPAY_INIT;
    private String vchid;
    private String parambofcode;

    @PostConstruct
    public void init() {
        try {
            Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            parambofcode = parammap.get("bofcode").toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //根据支付凭证获取数据
    public String onBtnAcceptClick() {
        try {
            gwkPaybackinfoList = paybackService.getPaybackinfoByVch(parambofcode,vchid);
            if (gwkPaybackinfoList == null || gwkPaybackinfoList.size() < 1) {
                MessageUtil.addWarn("没有数据，请检查凭证号是否正确。");
                return null;
            }
            GwkPaybackinfo record = gwkPaybackinfoList.get(0);
            if (record.getConfirmpayflag().equals(ConfirmPayFlg.CONFIRMPAY_VALID.getCode())) {
                String confirmtitle = ConfirmPayFlg.CONFIRMPAY_VALID.getTitle();
                MessageUtil.addInfo("该凭证状态为" + confirmtitle);
                setButtonDisabled(true);
                return null;
            }
            setButtonDisabled(false);
        } catch (Exception ex) {
            logger.error("获取支付凭证信息失败:凭证号=" + vchid + ";" + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("获取支付凭证信息失败:" + msg);
            return null;
        }
        return null;
    }

    //收款确认
    public String onBtnConfirmClick() {
        try {
            paybackService.updatePaybackinfoByVch(parambofcode,vchid);
            setButtonDisabled(true);
        } catch (Exception ex) {
            logger.error("更新确认收款失败:凭证号=" + vchid + ";" + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("更新确认收款失败:" + msg);
            return null;
        }
        try {
            gwkPaybackinfoList = paybackService.getPaybackinfoByVch(parambofcode,vchid);
        } catch (Exception ex) {
            logger.error("确认成功后查询失败。");
            return null;
        }
        MessageUtil.addInfo("收款确认成功。");
        return null;
    }

    /*设置button状态*/
    private void setButtonDisabled(boolean disabled) {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        HtmlCommandButton btnPay = (HtmlCommandButton) viewRoot.findComponent("editForm:btnConfirm");
        btnPay.setDisabled(disabled);
    }

    public PaybackService getPaybackService() {
        return paybackService;
    }

    public void setPaybackService(PaybackService paybackService) {
        this.paybackService = paybackService;
    }

    public List<GwkPaybackinfo> getGwkPaybackinfoList() {
        return gwkPaybackinfoList;
    }

    public void setGwkPaybackinfoList(List<GwkPaybackinfo> gwkPaybackinfoList) {
        this.gwkPaybackinfoList = gwkPaybackinfoList;
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public String getVchid() {
        return vchid;
    }

    public void setVchid(String vchid) {
        this.vchid = vchid;
    }

    public ConfirmPayFlg getConfirmPayFlg() {
        return confirmPayFlg;
    }

    public void setConfirmPayFlg(ConfirmPayFlg confirmPayFlg) {
        this.confirmPayFlg = confirmPayFlg;
    }
}
