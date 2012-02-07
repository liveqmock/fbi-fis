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
 * Time: œ¬ŒÁ2:54
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ActinfoQryAction implements Serializable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;
    @ManagedProperty(value = "#{actinfoService}")
    private ActinfoService actinfoService;
    private List<PayActinfo> actinfoList;
    private PayActinfo actinfo;
    private ActType actType = ActType.ACT_FINANCIAL;

    @PostConstruct
    public void init() {
        actinfo = new PayActinfo();
        query("", "", "");
    }

    public String onQuery(){
        try {
            query(actinfo.getActname(),actinfo.getActno(),actinfo.getActtype());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("’À∫≈≤È—Ø ß∞‹");
            MessageUtil.addError("’À∫≈≤È—Ø ß∞‹");
            return null;
        }
        return null;
    }

    private void query(String actname,String actno,String acttype) {
        actinfoList = actinfoService.selectedActinfoByCon(actname,actno,acttype);
    }

    private String onPrint() {
        return null;
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

    public PayActinfo getActinfo() {
        return actinfo;
    }

    public void setActinfo(PayActinfo actinfo) {
        this.actinfo = actinfo;
    }

    public ActType getActType() {
        return actType;
    }

    public void setActType(ActType actType) {
        this.actType = actType;
    }
}
