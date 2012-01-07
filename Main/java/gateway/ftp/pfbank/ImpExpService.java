package gateway.ftp.pfbank;

import fis.repository.gwk.dao.GwkCardbaseinfoMapper;
import fis.repository.gwk.model.GwkCardbaseinfo;
import fis.repository.gwk.model.GwkCardbaseinfoExample;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-6
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public class ImpExpService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    @Resource
    private GwkCardbaseinfoMapper gwkCardbaseinfoMapper;

    /**
     * 换卡信息更新响应卡号*/
    @Transactional
    public void importCardRep(ArrayList<ArrayList> impVar) {
        if (impVar != null) {
            GwkCardbaseinfoExample gwkCardbaseinfoExample = new GwkCardbaseinfoExample();
            for (ArrayList record:impVar) {
                GwkCardbaseinfo gwkCardbaseinfo = new GwkCardbaseinfo();
                gwkCardbaseinfo.setAccount(record.get(7).toString());
                gwkCardbaseinfo.setNewaccount(record.get(7).toString());
                gwkCardbaseinfo.setCreatedate(record.get(9).toString());
                gwkCardbaseinfoExample.clear();
                gwkCardbaseinfoExample.createCriteria().andAccountEqualTo(record.get(6).toString());
                gwkCardbaseinfoMapper.updateByExampleSelective(gwkCardbaseinfo,gwkCardbaseinfoExample);
            }
        }
    }
}
