package fis.view.gwk;

import fis.common.gwk.constant.ConfirmPayFlg;
import fis.common.gwk.constant.PayStatus;
import fis.repository.gwk.model.GwkPaybackinfo;
import fis.service.gwk.PaybackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;
import skyline.common.utils.MessageUtil;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: altiris-qdo
 * Date: 12-1-10
 * Time: 下午4:54
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PaybackQryAction {
    private static final Logger logger = LoggerFactory.getLogger(PaybackQryAction.class);
    @ManagedProperty(value = "#{paybackService}")
    private PaybackService paybackService;
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    private List<GwkPaybackinfo> gwkPaybackinfoList;
    private GwkPaybackinfo[] gwkPaybackinfos;
    private PayStatus payStatus = PayStatus.SPDB_INIT;
    private ConfirmPayFlg confirmPayFlg = ConfirmPayFlg.CONFIRMPAY_INIT;
    private List<SelectItem> payStsList;   //还款状态(不包含成功)
    private String vchid;
    private String acct;
    private String payStscode;
    private String parambofcode;
    private String strFinanceName;

    @PostConstruct
    public void init() {
        try {
            Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            parambofcode = parammap.get("bofcode").toString();
            strFinanceName = PropertyManager.getProperty("gwk.finance.name." + parambofcode);
            payStsList = toolsService.getEnuSelectItemList("PAYBACKSTS",true,false);
            query(vchid,acct,payStscode,parambofcode);
        } catch (Exception ex) {
            logger.error("查询"+strFinanceName+"还款错误数据失败." + ex.getMessage());
            MessageUtil.addError("查询还款错误数据失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
    }


    public String onBtnQueryClick() {
        try{
            query(vchid,acct,payStscode,parambofcode);
        } catch (Exception ex) {
            logger.error("查询"+strFinanceName+"还款错误数据失败." + ex.getMessage());
            MessageUtil.addError("查询还款错误数据失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        return null;
    }

    private void query(String vchid,String acct,String paySts,String bofcode) {
        gwkPaybackinfoList = paybackService.selectPaybackForQry(vchid, acct, paySts, bofcode);
    }

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
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

    public GwkPaybackinfo[] getGwkPaybackinfos() {
        return gwkPaybackinfos;
    }

    public void setGwkPaybackinfos(GwkPaybackinfo[] gwkPaybackinfos) {
        this.gwkPaybackinfos = gwkPaybackinfos;
    }

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public ConfirmPayFlg getConfirmPayFlg() {
        return confirmPayFlg;
    }

    public void setConfirmPayFlg(ConfirmPayFlg confirmPayFlg) {
        this.confirmPayFlg = confirmPayFlg;
    }

    public List<SelectItem> getPayStsList() {
        return payStsList;
    }

    public void setPayStsList(List<SelectItem> payStsList) {
        this.payStsList = payStsList;
    }

    public String getVchid() {
        return vchid;
    }

    public void setVchid(String vchid) {
        this.vchid = vchid;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }

    public String getPayStscode() {
        return payStscode;
    }

    public void setPayStscode(String payStscode) {
        this.payStscode = payStscode;
    }
}
