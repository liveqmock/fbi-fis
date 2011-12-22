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
 * Time: 上午9:24
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
    /*代理银行*/
    public List<FsBaseBank> selectBankInfo() {
        return fsBaseBankMapper.selectByExample(new FsBaseBankExample());
    }

    public List<FsBaseBank> bankInfoAccpt() {
        //todo 获取接口信息 insert
        return fsBaseBankMapper.selectByExample(new FsBaseBankExample());
    }
    /*执收单位*/
    public List<FsBasePerformdept> selectDeptInfo() {
        return fsBasePerformdeptMapper.selectByExample(new FsBasePerformdeptExample());
    }

    public List<FsBasePerformdept> deptInfoAccpt() {
        //todo 获取接口信息 insert
        return fsBasePerformdeptMapper.selectByExample(new FsBasePerformdeptExample());
    }
    /*票据种类*/
    public List<FsBaseBillinfo> selectBillInfo() {
        return fsBaseBillinfoMapper.selectByExample(new FsBaseBillinfoExample());
    }

    public List<FsBaseBillinfo> billInfoAccpt() {
        //todo 获取接口信息 insert
        return fsBaseBillinfoMapper.selectByExample(new FsBaseBillinfoExample());
    }
    /*项目*/
    public List<FsBasePrograminfo> selectProgramInfo() {
        return fsBasePrograminfoMapper.selectByExample(new FsBasePrograminfoExample());
    }

    public List<FsBasePrograminfo> programInfoAccpt() {
        //todo 获取接口信息 insert
        return fsBasePrograminfoMapper.selectByExample(new FsBasePrograminfoExample());
    }
    /*制单人*/
    public List<FsBaseMkvchmaninfo> selectManInfo() {
        return fsBaseMkvchmaninfoMapper.selectByExample(new FsBaseMkvchmaninfoExample());
    }

    public List<FsBaseMkvchmaninfo> manInfoAccpt() {
        //todo 获取接口信息 insert
        return fsBaseMkvchmaninfoMapper.selectByExample(new FsBaseMkvchmaninfoExample());
    }
}
