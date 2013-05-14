package fis.service.gwk;

import fis.common.BeanCopy;
import fis.repository.gwk.dao.GwkBaseBdgagencyMapper;
import fis.repository.gwk.model.GwkBaseBdgagency;
import fis.repository.gwk.model.GwkBaseBdgagencyExample;
import gateway.txn.GwkServiceFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.advance.utils.PropertyManager;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: 下午5:33
 * To change this template use File | Settings | File Templates.
 */
@Service
public class GwkBaseInfoService {
//    protected static String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
    @Resource
    private GwkBaseBdgagencyMapper gwkBaseBdgagencyMapper;
    @Resource
    private GwkServiceFactory factory;

    /*本地查询信息*/
    public List<GwkBaseBdgagency> selectBdgagency(String bofcode) {
        GwkBaseBdgagencyExample example = new GwkBaseBdgagencyExample();
        example.createCriteria().andAreacodeEqualTo(bofcode);
        return gwkBaseBdgagencyMapper.selectByExample(example);
    }

    /*接口获取*/
    @Transactional
    public void getAllInfoBdgagency(String bofcode) throws Exception {
        SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
        Date dt = new Date();
        int nowYear = Integer.parseInt(yearsdf.format(dt));
        List rtnlist = new ArrayList<Map>();
        try {
            //2012-12-06
            String applicationid = PropertyManager.getProperty("gwk.application.id."+bofcode);
            // 自写接口
            gateway.txn.t266001.gwk.ElementService service = factory.getElementServiceForArea(bofcode);
//            ElementService service = FaspServiceAdapter.getElementService();
            rtnlist = service.queryAllElementCode(applicationid,"BDGAGENCY",nowYear);
            System.out.println(String.valueOf(rtnlist.size()));
        } catch (Exception e) {
            rtnlist.size();
            throw new RuntimeException("返回数据异常:" + e.getMessage());
        }
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = maplast.get("version").toString();
        //获取数据 先删除所有数据
        GwkBaseBdgagencyExample example = new GwkBaseBdgagencyExample();
        gwkBaseBdgagencyMapper.deleteByExample(example);
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            GwkBaseBdgagency obj = (GwkBaseBdgagency) BeanCopy.copyObject("fis.repository.gwk.model.GwkBaseBdgagency", m1);
            obj.setVersion(Integer.parseInt(version));
            obj.setOperdate(dt);
            obj.setAreacode(bofcode);
            gwkBaseBdgagencyMapper.insertSelective(obj);
        }
    }
}
