package fis.service.pay;

import fis.repository.fs.dao.SysJoblogMapper;
import fis.repository.fs.model.SysJoblog;
import fis.repository.fs.model.SysJoblogExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: ÏÂÎç2:44
 * To change this template use File | Settings | File Templates.
 */
@Service
public class JobLogService {
    @Autowired
    private SysJoblogMapper fipJoblogMapper;


    public List<SysJoblog> selectJobLogsByOriginPkid(String tablename, String rowpkid) {
        SysJoblogExample example = new SysJoblogExample();
        example.createCriteria().andTablenameEqualTo(tablename).andRowpkidEqualTo(rowpkid);
        example.setOrderByClause("jobtime desc");
        return fipJoblogMapper.selectByExample(example);
    }

    public void insertNewLog(SysJoblog joblog) {
        fipJoblogMapper.insert(joblog);
    }
}
