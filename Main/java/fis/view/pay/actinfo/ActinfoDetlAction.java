package fis.view.pay.actinfo;

import fis.repository.fs.model.SysJoblog;
import fis.repository.pay.model.PayActinfo;
import fis.service.pay.ActinfoService;
import fis.service.pay.JobLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.service.SystemService;
import skyline.service.ToolsService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@RequestScoped
public class ActinfoDetlAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ActinfoDetlAction.class);

    private List<SysJoblog> logList;
    private PayActinfo selectedRecord;
    private String opertype = "";
    private String rtnmsg = "";

    @ManagedProperty(value = "#{actinfoService}")
    private ActinfoService actinfoService;
    @ManagedProperty(value = "#{jobLogService}")
    private JobLogService jobLogService;
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    private List<SelectItem> actTypeItemList;

    private String bizid;
    private String pkid;

    private Map<String, String> channelMap = new HashMap<String, String>();

    @PostConstruct
    public void init() {
        try {
            selectedRecord = new PayActinfo();
            actTypeItemList =toolsService.getEnuSelectItemList("ACTTYPE",false,false);
            Map paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            this.pkid = (String)paramMap.get("pkid");
            this.opertype = (String) paramMap.get("doType");
            if (pkid != null) {
                this.selectedRecord = actinfoService.selectActinfo(pkid);
                this.logList = jobLogService.selectJobLogsByOriginPkid("pay_actinfo",pkid);
            }
        } catch (Exception e) {
            logger.error("初始化时出现错误。");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "初始化时出现错误。", "检索数据库出现问题。"));
        }

    }
    //添加账户信息
    public String onBtnSaveAddClick() {
        try {
            String operid = SystemService.getOperatorManager().getOperatorId();
            selectedRecord.setCreatedBy(operid);
            selectedRecord.setCreatedDate(new Date());
            actinfoService.insertActinfo(selectedRecord);
        } catch (Exception ex) {
            ex.printStackTrace();
            String msg = ex.getCause().getMessage().replaceAll("\n","").replaceAll("\r","");
            rtnmsg = "<script language='javascript'>rtnScript('false','" + msg + "');</script>";
            return null;
        }
        rtnmsg = "<script language='javascript'>rtnScript('true','');</script>";
        return null;
    }

    //修改账户信息
    public String onBtnSaveEditClick() {
        try {
            String operid = SystemService.getOperatorManager().getOperatorId();
            selectedRecord.setPkid(this.pkid);
            selectedRecord.setLastUpdBy(operid);
            selectedRecord.setLastUpdDate(new Date());
            actinfoService.updateActinfo(selectedRecord);
        } catch (Exception ex) {
            ex.printStackTrace();
            String msg = ex.getCause().getMessage().replaceAll("\n","").replaceAll("\r","");
            rtnmsg = "<script language='javascript'>rtnScript('false','" + msg + "');</script>";
            return null;
        }
        rtnmsg = "<script language='javascript'>rtnScript('true','');</script>";
        return null;
    }


    //====================================================================================

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public List<SelectItem> getActTypeItemList() {
        return actTypeItemList;
    }

    public void setActTypeItemList(List<SelectItem> actTypeItemList) {
        this.actTypeItemList = actTypeItemList;
    }

    public String getRtnmsg() {
        return rtnmsg;
    }

    public void setRtnmsg(String rtnmsg) {
        this.rtnmsg = rtnmsg;
    }

    public String getOpertype() {
        return opertype;
    }

    public void setOpertype(String opertype) {
        this.opertype = opertype;
    }

    public List<SysJoblog> getLogList() {
        return logList;
    }

    public void setLogList(List<SysJoblog> logList) {
        this.logList = logList;
    }

    public PayActinfo getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(PayActinfo selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public ActinfoService getActinfoService() {
        return actinfoService;
    }

    public void setActinfoService(ActinfoService actinfoService) {
        this.actinfoService = actinfoService;
    }

    public JobLogService getJobLogService() {
        return jobLogService;
    }

    public void setJobLogService(JobLogService jobLogService) {
        this.jobLogService = jobLogService;
    }

    public String getBizid() {
        return bizid;
    }

    public void setBizid(String bizid) {
        this.bizid = bizid;
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public Map<String, String> getChannelMap() {
        return channelMap;
    }

    public void setChannelMap(Map<String, String> channelMap) {
        this.channelMap = channelMap;
    }
}
