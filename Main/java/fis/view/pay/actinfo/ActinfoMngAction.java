package fis.view.pay.actinfo;

import fis.common.pay.ActType;
import fis.repository.pay.model.PayActinfo;
import fis.service.pay.ActinfoService;
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
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ActinfoMngAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{actinfoService}")
    private ActinfoService actinfoService;

    private List<PayActinfo> actinfoList;
    private List<PayActinfo> deletedActinfoList;
    private PayActinfo actinfo;
    private PayActinfo[] selectedRecords;
    private PayActinfo selectedRecord;
    private ActType actType = ActType.ACT_FINANCIAL;

    @PostConstruct
    public void init() {
        onQuery();
    }

    public String onDeleteMulti() {
        try {
            if (selectedRecords.length > 0) {
                actinfoService.deleteActinfo(selectedRecords);
                onQuery();
                MessageUtil.addInfo("删除成功。");
            } else {
                MessageUtil.addError("请至少选择一条数据。");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("删除未成功:" + ex.getMessage());
            MessageUtil.addError("删除未成功:" + ex.getMessage());
        }
        return null;
    }

    public String onQuery() {
        actinfoList = actinfoService.selectActinfoList();
        deletedActinfoList = actinfoService.selectDelActinfoList();
        return null;
    }

    public String onPrintDeletedRecord() {
        return null;
    }

    //====================================================

    public ActType getActType() {
        return actType;
    }

    public void setActType(ActType actType) {
        this.actType = actType;
    }

    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    public ActinfoService getActinfoService() {
        return actinfoService;
    }

    public void setActinfoService(ActinfoService actinfoService) {
        this.actinfoService = actinfoService;
    }

    public List<PayActinfo> getActinfoList() {
        return actinfoList;
    }

    public void setActinfoList(List<PayActinfo> actinfoList) {
        this.actinfoList = actinfoList;
    }

    public List<PayActinfo> getDeletedActinfoList() {
        return deletedActinfoList;
    }

    public void setDeletedActinfoList(List<PayActinfo> deletedActinfoList) {
        this.deletedActinfoList = deletedActinfoList;
    }

    public PayActinfo getActinfo() {
        return actinfo;
    }

    public void setActinfo(PayActinfo actinfo) {
        this.actinfo = actinfo;
    }

    public PayActinfo[] getSelectedRecords() {
        return selectedRecords;
    }

    public void setSelectedRecords(PayActinfo[] selectedRecords) {
        this.selectedRecords = selectedRecords;
    }

    public PayActinfo getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(PayActinfo selectedRecord) {
        this.selectedRecord = selectedRecord;
    }
}