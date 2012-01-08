package fis.service.gwk;

import fis.common.BeanCopy;
import fis.repository.gwk.dao.GwkBaseBdgagencyMapper;
import fis.repository.gwk.model.GwkBaseBdgagency;
import fis.repository.gwk.model.GwkBaseBdgagencyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    @Resource
    private GwkBaseBdgagencyMapper gwkBaseBdgagencyMapper;

    /*本地查询信息*/
    public List<GwkBaseBdgagency> selectBdgagency(String bofcode) {
        GwkBaseBdgagencyExample example = new GwkBaseBdgagencyExample();
        example.createCriteria().andAreacodeEqualTo(bofcode);
        return gwkBaseBdgagencyMapper.selectByExample(example);
    }

    /*接口获取*/
    @Transactional
    public void getAllInfoBdgagency(String bofcode) throws Exception {
        Date dt = new Date();
        //todo 接口获取
   /*     try {
        // List rtnlist = getAllElementInfo("BDGAGENCY");
        } catch (Exception e) {
            throw new RuntimeException("返回数据异常:" + e.getMessage());
        }*/
        List rtnlist = new ArrayList<Map>();
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            GwkBaseBdgagency obj = (GwkBaseBdgagency) BeanCopy.copyObject("fis.repository.gwk.model.GwkBaseBdgagency", m1);
            obj.setVersion(Integer.parseInt(version));
            obj.setOperdate(dt);
            obj.setAreacode(bofcode);
            GwkBaseBdgagencyExample example = new GwkBaseBdgagencyExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            gwkBaseBdgagencyMapper.deleteByExample(example);
            gwkBaseBdgagencyMapper.insertSelective(obj);
        }
    }
}
