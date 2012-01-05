package fis.service.fs;

import fis.repository.fs.dao.*;
import fis.repository.fs.model.*;
import gateway.service.BizInterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.security.OperatorManager;
import skyline.service.SystemService;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    private BizInterService bizInterService;

    /*代理银行*/
    public List<FsBaseBank> selectBankInfo(String bofcode) {
        FsBaseBankExample example = new FsBaseBankExample();
        example.createCriteria().andAreacodeEqualTo(bofcode);
        return fsBaseBankMapper.selectByExample(example);
    }

    @Transactional
    public List<FsBaseBank> bankInfoAccpt(String bofcode) throws Exception {
        FsBaseBank fsBaseBank = new FsBaseBank();
        OperatorManager om = SystemService.getOperatorManager();
        fsBaseBank.setAreacode(bofcode);
        fsBaseBank.setOperdate(new Date());
        fsBaseBank.setOperid(om.getOperatorId());
        //获取接口信息 insert
        List<String> paramList = new ArrayList<String>();
        paramList.add("BANK");
        List<Map<String, String>> mapList = bizInterService.getBizDatas("FS", bofcode, "queryAllElementCode", paramList);
        //插入前先删除本地数据
        fsBaseBankMapper.deleteByExample(new FsBaseBankExample());
        for (Map<String, String> m : mapList) {
            String code = m.get("Code");
            String name = m.get("Name");
            String itemid = m.get("itemid");
            int version = Integer.parseInt(m.get("Version"));
            fsBaseBank.setBankcode(code);
            fsBaseBank.setBankname(name);
            fsBaseBank.setItemid(itemid);
            fsBaseBank.setVersion(version);
            fsBaseBankMapper.insert(fsBaseBank);
        }
        //查询
        FsBaseBankExample example = new FsBaseBankExample();
        example.createCriteria().andAreacodeEqualTo(bofcode);
        return fsBaseBankMapper.selectByExample(example);
    }

    /*执收单位*/
    public List<FsBasePerformdept> selectDeptInfo(String bofcode) {
        FsBasePerformdeptExample example = new FsBasePerformdeptExample();
        example.createCriteria().andAreacodeEqualTo(bofcode);
        return fsBasePerformdeptMapper.selectByExample(example);
    }

    @Transactional
    public List<FsBasePerformdept> deptInfoAccpt(String bofcode) throws Exception {
        //获取接口信息 insert
        FsBasePerformdept fsBasePerformdept = new FsBasePerformdept();
        fsBasePerformdept.setAreacode(bofcode);
        fsBasePerformdept.setOperdate(new Date());
        fsBasePerformdept.setOperid(SystemService.getOperatorManager().getOperatorId());
        List<String> paramList = new ArrayList<String>();
        paramList.add("FSAGENCY");
//        List<Map<String, String>> mapList = bizInterService.getBizDatas("FS", bofcode, "queryAllElementCode", paramList);
        //插入前先删除本地数据
        fsBasePerformdeptMapper.deleteByExample(new FsBasePerformdeptExample());
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        Map<String,String> map = new HashMap<String,String>();
        map.put("Code","1111");
        map.put("Name","2222");
        map.put("itemid","110");
        map.put("ersion","12345");
        mapList.add(map);
        for (Map<String, String> m : mapList) {
            String code = m.get("Code");
            String name = m.get("Name");
            String itemid = m.get("itemid");
            int version = Integer.parseInt(m.get("Version"));
            fsBasePerformdept.setDeptcode(code);
            fsBasePerformdept.setDeptname(name);
            fsBasePerformdept.setItemid(itemid);
            fsBasePerformdept.setVersion(version);
            fsBasePerformdeptMapper.insert(fsBasePerformdept);
        }
        FsBasePerformdeptExample example = new FsBasePerformdeptExample();
        example.createCriteria().andAreacodeEqualTo(bofcode);
        return fsBasePerformdeptMapper.selectByExample(example);
    }

    /*票据种类*/
    public List<FsBaseBillinfo> selectBillInfo(String bofcode) {
        FsBaseBillinfoExample example = new FsBaseBillinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode);
        return fsBaseBillinfoMapper.selectByExample(example);
    }

    @Transactional
    public List<FsBaseBillinfo> billInfoAccpt(String bofcode) throws Exception {
        //获取接口信息 insert
        FsBaseBillinfo fsBaseBillinfo = new FsBaseBillinfo();
        fsBaseBillinfo.setAreacode(bofcode);
        fsBaseBillinfo.setOperdate(new Date());
        fsBaseBillinfo.setOperid(SystemService.getOperatorManager().getOperatorId());
        List<String> paramList = new ArrayList<String>();
        paramList.add("STRORAGEID");
        List<Map<String, String>> mapList = bizInterService.getBizDatas("FS", bofcode, "queryAllElementCode", paramList);
        //插入前先删除本地数据
        fsBaseBillinfoMapper.deleteByExample(new FsBaseBillinfoExample());
        for (Map<String, String> m : mapList) {
            String code = m.get("Code");
            String name = m.get("Name");
            String itemid = m.get("itemid");
            int version = Integer.parseInt(m.get("Version"));
            fsBaseBillinfo.setBillcode(code);
            fsBaseBillinfo.setBillname(name);
            fsBaseBillinfo.setItemid(itemid);
            fsBaseBillinfo.setVersion(version);
            fsBaseBillinfoMapper.insert(fsBaseBillinfo);
        }
        FsBaseBillinfoExample example = new FsBaseBillinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode);
        return fsBaseBillinfoMapper.selectByExample(example);
    }

    /*项目*/
    public List<FsBasePrograminfo> selectProgramInfo(String bofcode) {
        FsBasePrograminfoExample example = new FsBasePrograminfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode);
        return fsBasePrograminfoMapper.selectByExample(example);
    }

    @Transactional
    public List<FsBasePrograminfo> programInfoAccpt(String bofcode) throws Exception {
        //获取接口信息 insert
        FsBasePrograminfo fsBasePrograminfo = new FsBasePrograminfo();
        fsBasePrograminfo.setAreacode(bofcode);
        fsBasePrograminfo.setOperdate(new Date());
        fsBasePrograminfo.setOperid(SystemService.getOperatorManager().getOperatorId());
        List<String> paramList = new ArrayList<String>();
        paramList.add("PROGRAM");
        List<Map<String, String>> mapList = bizInterService.getBizDatas("FS", bofcode, "queryAllElementCode", paramList);
        //插入前先删除本地数据
        fsBasePrograminfoMapper.deleteByExample(new FsBasePrograminfoExample());
        for (Map<String, String> m : mapList) {
            String code = m.get("Code");
            String name = m.get("Name");
            String itemid = m.get("itemid");
            int version = Integer.parseInt(m.get("Version"));
            fsBasePrograminfo.setProgramcode(code);
            fsBasePrograminfo.setProgramname(name);
            fsBasePrograminfo.setItemid(itemid);
            fsBasePrograminfo.setVersion(version);
            fsBasePrograminfoMapper.insert(fsBasePrograminfo);
        }
        FsBasePrograminfoExample example = new FsBasePrograminfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode);
        return fsBasePrograminfoMapper.selectByExample(example);
    }

    /*制单人*/
    public List<FsBaseMkvchmaninfo> selectManInfo(String bofcode) {
        FsBaseMkvchmaninfoExample example = new FsBaseMkvchmaninfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode);
        return fsBaseMkvchmaninfoMapper.selectByExample(example);
    }

    @Transactional
    public List<FsBaseMkvchmaninfo> manInfoAccpt(String bofcode) throws Exception {
        //获取接口信息 insert
        FsBaseMkvchmaninfo fsBaseMkvchmaninfo = new FsBaseMkvchmaninfo();
        fsBaseMkvchmaninfo.setAreacode(bofcode);
        fsBaseMkvchmaninfo.setOperdate(new Date());
        fsBaseMkvchmaninfo.setOperid(SystemService.getOperatorManager().getOperatorId());
        List<String> paramList = new ArrayList<String>();
        paramList.add("USER");
        List<Map<String, String>> mapList = bizInterService.getBizDatas("FS", bofcode, "queryAllElementCode", paramList);
        //插入前先删除本地数据
        fsBaseMkvchmaninfoMapper.deleteByExample(new FsBaseMkvchmaninfoExample());
        for (Map<String, String> m : mapList) {
            String code = m.get("Code");
            String name = m.get("Name");
            String itemid = m.get("itemid");
            int version = Integer.parseInt(m.get("Version"));
            fsBaseMkvchmaninfo.setMancode(code);
            fsBaseMkvchmaninfo.setManname(name);
            fsBaseMkvchmaninfo.setItemid(itemid);
            fsBaseMkvchmaninfo.setVersion(version);
            fsBaseMkvchmaninfoMapper.insert(fsBaseMkvchmaninfo);
        }

        FsBaseMkvchmaninfoExample example = new FsBaseMkvchmaninfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode);
        return fsBaseMkvchmaninfoMapper.selectByExample(example);
    }
}
