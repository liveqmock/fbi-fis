package fis.view;

import fis.repository.model.*;
import fis.service.BaseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skyline.common.utils.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-12-21
 * Time: ����9:23
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
            logger.error("��ȡ����������Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ȡ����������Ϣʧ��." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
        }
    }

    public String onBtnbankClick() {
        try {
            fsBaseBankList = baseInfoService.bankInfoAccpt(parambofcode);
        } catch (Exception ex) {
            logger.error("��ȡ����������Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ȡ����������Ϣʧ��." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        return null;
    }

    /*ִ�յ�λ��ȡ*/
    public String onBtnexecunitClick() {
        try {
            fsBasePerformdeptList = baseInfoService.deptInfoAccpt(parambofcode);
//            javax.faces.vie
        } catch (Exception ex) {
            logger.error("��ȡִ�յ�λ��Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ȡִ�յ�λ��Ϣʧ��." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        return null;
    }

    /*Ʊ�������ȡ*/
    public String onBtnbillClick() {
        try {
            fsBaseBillinfoList = baseInfoService.billInfoAccpt(parambofcode);

        } catch (Exception ex) {
            logger.error("��ȡƱ��������Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ȡƱ��������Ϣʧ��." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        return null;
    }

    /*��Ŀ��ȡ*/
    public String onBtnitemClick() {
        try {
            fsBasePrograminfoList = baseInfoService.programInfoAccpt(parambofcode);
        } catch (Exception ex) {
            logger.error("��ȡ��Ŀ��Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ȡ��Ŀ��Ϣʧ��." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        return null;
    }

    /*�Ƶ��˻�ȡ*/
    public String onBtnmkvchClick() {
        try {
            fsBaseMkvchmaninfoList = baseInfoService.manInfoAccpt(parambofcode);
        } catch (Exception ex) {
            logger.error("��ȡ�Ƶ�����Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ȡ�Ƶ�����Ϣʧ��." + ex.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
            return null;
        }
        return null;
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
