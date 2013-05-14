package fis.view.gwk;

import fis.common.gwk.constant.ConsumeInfoSts;
import fis.repository.gwk.model.GwkConsumeinfo;
import fis.service.gwk.ConsumeInfoService;
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
 * Time: 下午5:47
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ConsumeInfoQryAction {
    private static final Logger logger = LoggerFactory.getLogger(ConsumeInfoQryAction.class);
    @ManagedProperty(value = "#{consumeInfoService}")
    private ConsumeInfoService consumeInfoService;
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    private List<GwkConsumeinfo> gwkConsumeinfoList;
    private ConsumeInfoSts consumeInfoSts = ConsumeInfoSts.SEND_INIT;
    private String busistartdate;
    private String busienddate;
    private String acct;
    private String qryConsumeSts;
    private List<SelectItem> consumeStsList;
    private String parambofcode;
    private String strFinanceName;

    @PostConstruct
    public void init() {
        try {
            Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            parambofcode = parammap.get("bofcode").toString();
            strFinanceName = PropertyManager.getProperty("gwk.finance.name." + parambofcode);
            query(qryConsumeSts, acct, busistartdate, busienddate, parambofcode);
            consumeStsList = toolsService.getEnuSelectItemList("COUSUMESTS",true,false);
        } catch (Exception ex) {
            logger.error("查询"+strFinanceName+"消费信息失败." + ex.getMessage());
            MessageUtil.addError("查询消费信息失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
    }

    public String onBtnQueryClick() {
        try {
            query(qryConsumeSts, acct, busistartdate, busienddate, parambofcode);
        } catch (Exception ex) {
            logger.error("查询"+strFinanceName+"消费信息失败." + ex.getMessage());
            MessageUtil.addError("查询消费信息失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
        return null;
    }
    
    private void query(String status,String account,String busistartdate,String busienddate,String bofcode) {
        gwkConsumeinfoList = consumeInfoService.selectConsumeForQry(status,account,busistartdate,busienddate,bofcode);
    }

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public String getQryConsumeSts() {
        return qryConsumeSts;
    }

    public void setQryConsumeSts(String qryConsumeSts) {
        this.qryConsumeSts = qryConsumeSts;
    }

    public List<SelectItem> getConsumeStsList() {
        return consumeStsList;
    }

    public void setConsumeStsList(List<SelectItem> consumeStsList) {
        this.consumeStsList = consumeStsList;
    }

    public ConsumeInfoService getConsumeInfoService() {
        return consumeInfoService;
    }

    public void setConsumeInfoService(ConsumeInfoService consumeInfoService) {
        this.consumeInfoService = consumeInfoService;
    }

    public List<GwkConsumeinfo> getGwkConsumeinfoList() {
        return gwkConsumeinfoList;
    }

    public void setGwkConsumeinfoList(List<GwkConsumeinfo> gwkConsumeinfoList) {
        this.gwkConsumeinfoList = gwkConsumeinfoList;
    }

    public ConsumeInfoSts getConsumeInfoSts() {
        return consumeInfoSts;
    }

    public void setConsumeInfoSts(ConsumeInfoSts consumeInfoSts) {
        this.consumeInfoSts = consumeInfoSts;
    }

    public String getBusistartdate() {
        return busistartdate;
    }

    public void setBusistartdate(String busistartdate) {
        this.busistartdate = busistartdate;
    }

    public String getBusienddate() {
        return busienddate;
    }

    public void setBusienddate(String busienddate) {
        this.busienddate = busienddate;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }

    
}
