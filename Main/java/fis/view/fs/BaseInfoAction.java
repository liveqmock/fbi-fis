package fis.view.fs;

import fis.repository.fs.model.*;
import fis.service.fs.BaseInfoService;
import org.primefaces.component.tabview.TabView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-12-21
 * Time: 上午9:23
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class BaseInfoAction {
    private static final Logger logger = LoggerFactory.getLogger(BaseInfoAction.class);

    @ManagedProperty(value = "#{baseInfoService}")
    private BaseInfoService baseInfoService;

    private List<FsBaseBank> fsBaseBankList;
    private FsBaseBank fsBaseBank;

    private List<FsBasePerformdept> fsBasePerformdeptList;
    private List<FsBaseBillinfo> fsBaseBillinfoList;
    private List<FsBasePrograminfo> fsBasePrograminfoList;
    private List<FsBaseMkvchmaninfo> fsBaseMkvchmaninfoList;
    private String parambofcode;

    @PostConstruct
    public void init() {
        try {
            Map parammap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            parambofcode = parammap.get("bofcode").toString();
            fsBaseBankList = baseInfoService.selectBankInfo(parambofcode);
            fsBasePerformdeptList = baseInfoService.selectDeptInfo(parambofcode);
            fsBaseBillinfoList = baseInfoService.selectBillInfo(parambofcode);
            fsBasePrograminfoList = baseInfoService.selectProgramInfo(parambofcode);
            fsBaseMkvchmaninfoList = baseInfoService.selectManInfo(parambofcode);
        } catch (Exception ex) {
            logger.error("获取代理银行信息失败." + ex.getMessage());
            MessageUtil.addError("获取代理银行信息失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
    }

    public String onBtnbankClick() {
        try {
            fsBaseBankList = baseInfoService.bankInfoAccpt(parambofcode);
            setActiveTab(0);
        } catch (Exception ex) {
            logger.error("获取代理银行信息失败." + ex.getMessage());
            MessageUtil.addError("获取代理银行信息失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            setActiveTab(0);
            return null;
        }
        return null;
    }

    /*执收单位获取*/
    public String onBtnexecunitClick() {
        try {
            fsBasePerformdeptList = baseInfoService.deptInfoAccpt(parambofcode);
            setActiveTab(1);
        } catch (Exception ex) {
            logger.error("获取执收单位信息失败." + ex.getMessage());
            MessageUtil.addError("获取执收单位信息失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            setActiveTab(1);
            return null;
        }
        return null;
    }

    /*票据种类获取*/
    public String onBtnbillClick() {
        try {
            fsBaseBillinfoList = baseInfoService.billInfoAccpt(parambofcode);
            setActiveTab(2);
        } catch (Exception ex) {
            logger.error("获取票据种类信息失败." + ex.getMessage());
            MessageUtil.addError("获取票据种类信息失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            setActiveTab(2);
            return null;
        }
        return null;
    }

    /*项目获取*/
    public String onBtnitemClick() {
        try {
            fsBasePrograminfoList = baseInfoService.programInfoAccpt(parambofcode);
            setActiveTab(3);
        } catch (Exception ex) {
            logger.error("获取项目信息失败." + ex.getMessage());
            MessageUtil.addError("获取项目信息失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            setActiveTab(3);
            return null;
        }
        return null;
    }

    /*制单人获取*/
    public String onBtnmkvchClick() {
        try {
            fsBaseMkvchmaninfoList = baseInfoService.manInfoAccpt(parambofcode);
            setActiveTab(4);
        } catch (Exception ex) {
            logger.error("获取制单人信息失败." + ex.getMessage());
            MessageUtil.addError("获取制单人信息失败." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            setActiveTab(4);
            return null;
        }
        return null;
    }

    private void setActiveTab(int tabindex) {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        org.primefaces.component.tabview.TabView tabView = (TabView) viewRoot.findComponent("baseTabview");
        tabView.setActiveIndex(tabindex);
    }

    public BaseInfoService getBaseInfoService() {
        return baseInfoService;
    }

    public void setBaseInfoService(BaseInfoService baseInfoService) {
        this.baseInfoService = baseInfoService;
    }

    public List<FsBaseBank> getFsBaseBankList() {
        return fsBaseBankList;
    }

    public void setFsBaseBankList(List<FsBaseBank> fsBaseBankList) {
        this.fsBaseBankList = fsBaseBankList;
    }

    public FsBaseBank getFsBaseBank() {
        return fsBaseBank;
    }

    public void setFsBaseBank(FsBaseBank fsBaseBank) {
        this.fsBaseBank = fsBaseBank;
    }

    public List<FsBasePerformdept> getFsBasePerformdeptList() {
        return fsBasePerformdeptList;
    }

    public void setFsBasePerformdeptList(List<FsBasePerformdept> fsBasePerformdeptList) {
        this.fsBasePerformdeptList = fsBasePerformdeptList;
    }

    public List<FsBaseBillinfo> getFsBaseBillinfoList() {
        return fsBaseBillinfoList;
    }

    public void setFsBaseBillinfoList(List<FsBaseBillinfo> fsBaseBillinfoList) {
        this.fsBaseBillinfoList = fsBaseBillinfoList;
    }

    public List<FsBasePrograminfo> getFsBasePrograminfoList() {
        return fsBasePrograminfoList;
    }

    public void setFsBasePrograminfoList(List<FsBasePrograminfo> fsBasePrograminfoList) {
        this.fsBasePrograminfoList = fsBasePrograminfoList;
    }

    public List<FsBaseMkvchmaninfo> getFsBaseMkvchmaninfoList() {
        return fsBaseMkvchmaninfoList;
    }

    public void setFsBaseMkvchmaninfoList(List<FsBaseMkvchmaninfo> fsBaseMkvchmaninfoList) {
        this.fsBaseMkvchmaninfoList = fsBaseMkvchmaninfoList;
    }
}
