package fis.view.gwk;

import antlr.debug.MessageAdapter;
import fis.common.gwk.constant.ConfirmPayFlg;
import fis.common.gwk.constant.PayStatus;
import fis.repository.gwk.model.GwkPaybackinfo;
import fis.service.gwk.PaybackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: altiris-qdo
 * Date: 12-1-10
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class PaybackBatchAction {
    private static final Logger logger = LoggerFactory.getLogger(PaybackBatchAction.class);
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

    @PostConstruct
    public void init() {
        try {
            Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            parambofcode = parammap.get("bofcode").toString();
            payStsList = toolsService.getEnuSelectItemList("PAYBACKSTSFAIL",true,false);
            query(vchid,acct,payStscode,parambofcode);
        } catch (Exception ex) {
            logger.error("查询还款错误数据失败." + ex.getMessage());
            MessageUtil.addError("查询还款错误数据失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
    }
    
    
    public String onBtnQueryClick() {
        try{
            query(vchid,acct,payStscode,parambofcode);
        } catch (Exception ex) {
            logger.error("查询还款错误数据失败." + ex.getMessage());
            MessageUtil.addError("查询还款错误数据失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        return null;
    }
    
    public String onBtnSaveClick() {
        if (gwkPaybackinfos.length < 1) {
            MessageUtil.addInfo("请选择至少一条数据");
            return null;
        }
        try{
            paybackService.updatePaybackBatch(gwkPaybackinfos);
        } catch (Exception ex) {
            logger.error("批量修改还款数据失败:" + ex.getMessage());
            String msg = ex.getMessage() == null ? "" : ex.getMessage().replaceAll("\n", "").replaceAll("\r", "");
            MessageUtil.addError("批量修改还款数据失败:" + msg);
            return null;
        }
        try{
            query(vchid,acct,payStscode,parambofcode);
        } catch (Exception ex) {
            logger.error("查询还款错误数据失败." + ex.getMessage());
            MessageUtil.addError("查询还款错误数据失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        MessageUtil.addInfo("修改成功");
        return null;
    }
    
    private void query(String vchid,String acct,String paySts,String bofcode) {
        gwkPaybackinfoList = paybackService.selectPaybackForBatch(vchid,acct,paySts,bofcode);
    }
    
    public void cardisExist(ActionEvent actionEvent) {
        HtmlInputText txtAcctObj = (HtmlInputText) actionEvent.getComponent();
        String stracct = txtAcctObj.getValue().toString();
        try{
            boolean exists = paybackService.queryCardExists(stracct);
            if (!exists) {
                MessageUtil.addError("该卡号不存在,请重新输入.");
                txtAcctObj.setValue("");
            }
        } catch (Exception ex) {
            MessageUtil.addError("查询卡号失败.");
            ex.printStackTrace();
        }
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
