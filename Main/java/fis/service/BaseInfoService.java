package fis.service;

import fis.repository.dao.*;
import fis.repository.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-12-21
 * Time: ����9:24
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BaseInfoService {
    @Resource
    private FsBaseBankMapper fsBaseBankMapper;
    @Resource
    private FsBasePerformdeptMapper fsBasePerformdeptMapper;
    @Resource
    private FsBaseBillinfoMapper fsBaseBillinfoMapper;
    @Resource
    private FsBaseMkvchmaninfoMapper fsBaseMkvchmaninfoMapper;
    @Resource
    private FsBasePrograminfoMapper fsBasePrograminfoMapper;
    /*��������*/
    public List<FsBaseBank> selectBankInfo() {
        return fsBaseBankMapper.selectByExample(new FsBaseBankExample());
    }

    public List<FsBaseBank> bankInfoAccpt() {
        //todo ��ȡ�ӿ���Ϣ insert
        return fsBaseBankMapper.selectByExample(new FsBaseBankExample());
    }
    /*ִ�յ�λ*/
    public List<FsBasePerformdept> selectDeptInfo() {
        return fsBasePerformdeptMapper.selectByExample(new FsBasePerformdeptExample());
    }

    public List<FsBasePerformdept> deptInfoAccpt() {
        //todo ��ȡ�ӿ���Ϣ insert
        return fsBasePerformdeptMapper.selectByExample(new FsBasePerformdeptExample());
    }
    /*Ʊ������*/
    public List<FsBaseBillinfo> selectBillInfo() {
        return fsBaseBillinfoMapper.selectByExample(new FsBaseBillinfoExample());
    }

    public List<FsBaseBillinfo> billInfoAccpt() {
        //todo ��ȡ�ӿ���Ϣ insert
        return fsBaseBillinfoMapper.selectByExample(new FsBaseBillinfoExample());
    }
    /*��Ŀ*/
    public List<FsBasePrograminfo> selectProgramInfo() {
        return fsBasePrograminfoMapper.selectByExample(new FsBasePrograminfoExample());
    }

    public List<FsBasePrograminfo> programInfoAccpt() {
        //todo ��ȡ�ӿ���Ϣ insert
        return fsBasePrograminfoMapper.selectByExample(new FsBasePrograminfoExample());
    }
    /*�Ƶ���*/
    public List<FsBaseMkvchmaninfo> selectManInfo() {
        return fsBaseMkvchmaninfoMapper.selectByExample(new FsBaseMkvchmaninfoExample());
    }

    public List<FsBaseMkvchmaninfo> manInfoAccpt() {
        //todo ��ȡ�ӿ���Ϣ insert
        return fsBaseMkvchmaninfoMapper.selectByExample(new FsBaseMkvchmaninfoExample());
    }
}
