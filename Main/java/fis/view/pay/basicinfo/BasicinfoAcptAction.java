package fis.view.pay.basicinfo;

import fis.repository.pay.model.*;
import fis.service.pay.BasicinfoQryService;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: 下午2:55
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class BasicinfoAcptAction implements Serializable {
    private List<PayBasicPaytype> basicPaytypeList;
    private List<PayBasicBdgagency> basicBdgagencyList;
    private List<PayBasicBdgmanagedivision> basicBdgmanagedivisionList;
    private List<PayBasicExpeco> basicExpecoList;

    private List<PayBasicExpfunc> basicExpfuncList;
    private List<PayBasicFunc> basicFuncList;
    private List<PayBasicFundtype> basicFundtypeList;
    private List<PayBasicIncomesort> basicIncomesortList;
    private List<PayBasicIndsource> basicIndsourceList;
    private List<PayBasicPaykind> basicPaykindList;
    private List<PayBasicProfund> basicProfundList;
    private List<PayBasicProgram> basicProgramList;
    private List<PayBasicSettlemode> basicSettlemodeList;

    private String basicflag = "";
    @ManagedProperty(value = "#{basicinfoQryService}")
    private BasicinfoQryService basicinfoQryService;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        basicflag = map.get("basicflag").toString();
        qryBasicinfo();
    }

    /*获取基础数据*/
    public String getBasicinfo() {
        try {
            if (basicflag.equals("ALL")) {
                basicinfoQryService.getAllInfoBdgagency();
                basicinfoQryService.getAllInfoBdgmanagedivision();
                basicinfoQryService.getAllInfoPaytype();
                basicinfoQryService.getAllInfoExpeco();
                basicinfoQryService.getAllInfoExpfunc();
                basicinfoQryService.getAllInfoFunc();
                basicinfoQryService.getAllInfoFundtype();
                basicinfoQryService.getAllInfoIncomesort();
                basicinfoQryService.getAllInfoIndsource();
                basicinfoQryService.getAllInfoPaykind();
                basicinfoQryService.getAllInfoProfund();
                basicinfoQryService.getAllInfoProgram();
                basicinfoQryService.getAllInfoSettlemode();
            } else if (basicflag.equals("BDGAGENCY")) {
                basicinfoQryService.getAllInfoBdgagency();
            } else if (basicflag.equals("BDGMANAGEDIVISION")) {
                basicinfoQryService.getAllInfoBdgmanagedivision();
            } else if (basicflag.equals("PAYTYPE")) {
                basicinfoQryService.getAllInfoPaytype();
            } else if (basicflag.equals("EXPECO")) {
                basicinfoQryService.getAllInfoExpeco();
            } else if (basicflag.equals("EXPFUNC")) {
                basicinfoQryService.getAllInfoExpfunc();
            } else if (basicflag.equals("FUNC")) {
                basicinfoQryService.getAllInfoFunc();
            } else if (basicflag.equals("FUNDTYPE")) {
                basicinfoQryService.getAllInfoFundtype();
            } else if (basicflag.equals("INCOMESORT")) {
                basicinfoQryService.getAllInfoIncomesort();
            } else if (basicflag.equals("INDSOURCE")) {
                basicinfoQryService.getAllInfoIndsource();
            } else if (basicflag.equals("PAYKIND")) {
                basicinfoQryService.getAllInfoPaykind();
            } else if (basicflag.equals("PROFUND")) {
                basicinfoQryService.getAllInfoProfund();
            } else if (basicflag.equals("PROGRAM")) {
                basicinfoQryService.getAllInfoProgram();
            } else if (basicflag.equals("SETTLEMODE")) {
                basicinfoQryService.getAllInfoSettlemode();
            }
            qryBasicinfo();     //查询
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            MessageUtil.addError("插入失败。");
            return null;
        }
        MessageUtil.addInfo("插入成功。");
        return null;
    }
    //数据查询
    public String qryBasicinfo() {
        try {
            if (basicflag.equals("BDGAGENCY")) {
                this.basicBdgagencyList = basicinfoQryService.qryBdgagencyInfo();
            } else if (basicflag.equals("BDGMANAGEDIVISION")) {
                basicBdgmanagedivisionList = basicinfoQryService.qryBdgmanagedivInfo();
            } else if (basicflag.equals("PAYTYPE")) {
                this.basicPaytypeList = basicinfoQryService.qryPaytypeInfo();
            } else if (basicflag.equals("EXPECO")) {
                this.basicExpecoList = basicinfoQryService.qryExpecoInfo();
            } else if (basicflag.equals("EXPFUNC")) {
                this.basicExpfuncList = basicinfoQryService.qryExpfuncInfo();
            } else if (basicflag.equals("FUNC")) {
                this.basicFuncList = basicinfoQryService.qryFuncInfo();
            } else if (basicflag.equals("FUNDTYPE")) {
                this.basicFundtypeList = basicinfoQryService.qryFundtyleInfo();
            } else if (basicflag.equals("INCOMESORT")) {
                this.basicIncomesortList = basicinfoQryService.qryIncomesortInfo();
            } else if (basicflag.equals("INDSOURCE")) {
                this.basicIndsourceList = basicinfoQryService.qryIndsourceInfo();
            } else if (basicflag.equals("PAYKIND")) {
                this.basicPaykindList = basicinfoQryService.qryPaykindInfo();
            } else if (basicflag.equals("PROFUND")) {
                this.basicProfundList = basicinfoQryService.qryProfundInfo();
            } else if (basicflag.equals("PROGRAM")) {
                this.basicProgramList = basicinfoQryService.qryProgramInfo();
            } else if (basicflag.equals("SETTLEMODE")) {
                this.basicSettlemodeList = basicinfoQryService.qrySettlemodeInfo();
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public BasicinfoQryService getBasicinfoQryService() {
        return basicinfoQryService;
    }

    public void setBasicinfoQryService(BasicinfoQryService basicinfoQryService) {
        this.basicinfoQryService = basicinfoQryService;
    }

    public List<PayBasicPaytype> getBasicPaytypeList() {
        return basicPaytypeList;
    }

    public void setBasicPaytypeList(List<PayBasicPaytype> basicPaytypeList) {
        this.basicPaytypeList = basicPaytypeList;
    }

    public List<PayBasicBdgagency> getBasicBdgagencyList() {
        return basicBdgagencyList;
    }

    public void setBasicBdgagencyList(List<PayBasicBdgagency> basicBdgagencyList) {
        this.basicBdgagencyList = basicBdgagencyList;
    }

    public List<PayBasicBdgmanagedivision> getBasicBdgmanagedivisionList() {
        return basicBdgmanagedivisionList;
    }

    public void setBasicBdgmanagedivisionList(List<PayBasicBdgmanagedivision> basicBdgmanagedivisionList) {
        this.basicBdgmanagedivisionList = basicBdgmanagedivisionList;
    }

    public List<PayBasicExpeco> getBasicExpecoList() {
        return basicExpecoList;
    }

    public void setBasicExpecoList(List<PayBasicExpeco> basicExpecoList) {
        this.basicExpecoList = basicExpecoList;
    }

    public List<PayBasicExpfunc> getBasicExpfuncList() {
        return basicExpfuncList;
    }

    public void setBasicExpfuncList(List<PayBasicExpfunc> basicExpfuncList) {
        this.basicExpfuncList = basicExpfuncList;
    }

    public List<PayBasicFunc> getBasicFuncList() {
        return basicFuncList;
    }

    public void setBasicFuncList(List<PayBasicFunc> basicFuncList) {
        this.basicFuncList = basicFuncList;
    }

    public List<PayBasicFundtype> getBasicFundtypeList() {
        return basicFundtypeList;
    }

    public void setBasicFundtypeList(List<PayBasicFundtype> basicFundtypeList) {
        this.basicFundtypeList = basicFundtypeList;
    }

    public List<PayBasicIncomesort> getBasicIncomesortList() {
        return basicIncomesortList;
    }

    public void setBasicIncomesortList(List<PayBasicIncomesort> basicIncomesortList) {
        this.basicIncomesortList = basicIncomesortList;
    }

    public List<PayBasicIndsource> getBasicIndsourceList() {
        return basicIndsourceList;
    }

    public void setBasicIndsourceList(List<PayBasicIndsource> basicIndsourceList) {
        this.basicIndsourceList = basicIndsourceList;
    }

    public List<PayBasicPaykind> getBasicPaykindList() {
        return basicPaykindList;
    }

    public void setBasicPaykindList(List<PayBasicPaykind> basicPaykindList) {
        this.basicPaykindList = basicPaykindList;
    }

    public List<PayBasicProfund> getBasicProfundList() {
        return basicProfundList;
    }

    public void setBasicProfundList(List<PayBasicProfund> basicProfundList) {
        this.basicProfundList = basicProfundList;
    }

    public List<PayBasicProgram> getBasicProgramList() {
        return basicProgramList;
    }

    public void setBasicProgramList(List<PayBasicProgram> basicProgramList) {
        this.basicProgramList = basicProgramList;
    }

    public List<PayBasicSettlemode> getBasicSettlemodeList() {
        return basicSettlemodeList;
    }

    public void setBasicSettlemodeList(List<PayBasicSettlemode> basicSettlemodeList) {
        this.basicSettlemodeList = basicSettlemodeList;
    }
}
