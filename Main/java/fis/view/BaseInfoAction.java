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
import java.util.List;

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

    @PostConstruct
    public void init() {
        try {
            fsBaseBankList = baseInfoService.selectBankInfo();
            fsBasePerformdeptList = baseInfoService.selectDeptInfo();
            fsBaseBillinfoList = baseInfoService.selectBillInfo();
            fsBasePrograminfoList = baseInfoService.selectProgramInfo();
            fsBaseMkvchmaninfoList = baseInfoService.selectManInfo();
        } catch (Exception ex) {
            logger.error("��ȡ����������Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ȡ����������Ϣʧ��." + ex.getMessage());
        }
    }

    public String onBtnbankClick() {
        try {
            fsBaseBankList = baseInfoService.bankInfoAccpt();
        } catch (Exception ex) {
            logger.error("��ȡ����������Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ȡ����������Ϣʧ��." + ex.getMessage());
            return null;
        }
        return null;
    }
    /*ִ�յ�λ��ȡ*/
    public String onBtnexecunitClick() {
        try {
            fsBasePerformdeptList = baseInfoService.deptInfoAccpt();
        } catch (Exception ex) {
            logger.error("��ȡ����������Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ȡ����������Ϣʧ��." + ex.getMessage());
            return null;
        }
        return null;
    }
    /*Ʊ�������ȡ*/
    public String onBtnbillClick() {
        try {
            fsBaseBillinfoList = baseInfoService.billInfoAccpt();
        } catch (Exception ex) {
            logger.error("��ȡ����������Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ȡ����������Ϣʧ��." + ex.getMessage());
            return null;
        }
        return null;
    }
    /*��Ŀ��ȡ*/
    public String onBtnitemClick() {
        try {
            fsBasePrograminfoList = baseInfoService.programInfoAccpt();
        } catch (Exception ex) {
            logger.error("��ȡ����������Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ȡ����������Ϣʧ��." + ex.getMessage());
            return null;
        }
        return null;
    }
    /*�Ƶ��˻�ȡ*/
    public String onBtnmkvchClick() {
        try {
            fsBaseMkvchmaninfoList = baseInfoService.manInfoAccpt();
        } catch (Exception ex) {
            logger.error("��ȡ����������Ϣʧ��." + ex.getMessage());
            MessageUtil.addError("��ȡ����������Ϣʧ��." + ex.getMessage());
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
