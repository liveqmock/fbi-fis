package fis.service.gwk;

import fis.repository.fs.dao.SysJoblogMapper;
import fis.repository.fs.model.SysJoblog;
import fis.repository.gwk.dao.GwkCardbaseinfoMapper;
import fis.repository.gwk.dao.GwkConsumeinfoMapper;
import fis.repository.gwk.dao.GwkPaybackinfoMapper;
import fis.repository.gwk.dao.GwkPaybackresultMapper;
import fis.repository.gwk.model.*;
import org.apache.ecs.html.Big;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.security.OperatorManager;
import skyline.service.SystemService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-6
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ImpExpService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private static DecimalFormat df = new DecimalFormat("#.##");
    @Resource
    private GwkCardbaseinfoMapper gwkCardbaseinfoMapper;
    @Resource
    private GwkConsumeinfoMapper gwkConsumeinfoMapper;
    @Resource
    private GwkPaybackresultMapper gwkPaybackresultMapper;
    @Resource
    private GwkPaybackinfoMapper gwkPaybackinfoMapper;
    @Resource
    private SysJoblogMapper sysJoblogMapper;

    /**
     * 扣款结果导入
     */
    @Transactional
    public int importPaybackresult(ArrayList<ArrayList> impVar,String filename) {
        int count = 0;
        if (impVar != null && impVar.size() > 0) {
            Date dt = new Date();
            //删除同文件数据
            GwkPaybackresultExample example = new GwkPaybackresultExample();
            example.createCriteria().andFilenameEqualTo(filename);
            gwkPaybackresultMapper.deleteByExample(example);
            for (ArrayList record:impVar) {
                GwkPaybackresult gwkPaybackresult = new GwkPaybackresult();
                gwkPaybackresult.setBankcode(record.get(0).toString());
                gwkPaybackresult.setRecoroccurdate(record.get(1).toString());
                gwkPaybackresult.setGatheringbankacctcode(record.get(2).toString());
                double amt = Double.parseDouble(record.get(3).toString());
                BigDecimal dcmAmt = BigDecimal.valueOf(Double.parseDouble(df.format(amt)));
                gwkPaybackresult.setAmt(dcmAmt);
                gwkPaybackresult.setCurcde(record.get(4).toString());
                gwkPaybackresult.setInacDate(record.get(5).toString());
                double inamt =Double.parseDouble(record.get(6).toString());
                BigDecimal dcmInamt = BigDecimal.valueOf(Double.parseDouble(df.format(inamt)));
                gwkPaybackresult.setInacAmt(dcmInamt);
                gwkPaybackresult.setAccount(record.get(7).toString());
                gwkPaybackresult.setDirectiontype(record.get(8).toString());
                gwkPaybackresult.setDklsh(record.get(9).toString());
                gwkPaybackresult.setResponsecode(record.get(10).toString());
                gwkPaybackresult.setExpand(record.get(11).toString());
                gwkPaybackresult.setFilename(filename);
                gwkPaybackresultMapper.insertSelective(gwkPaybackresult);
                count++;
            }
        }
        return count;
    }

    /**
     * 消费信息导入
     */
    @Transactional
    public int importConsumeinfo(ArrayList<ArrayList> impVar,String filename) {
        int count = 0;
        if (impVar != null && impVar.size() > 0) {
            Date dt = new Date();
            GwkConsumeinfoExample example = new GwkConsumeinfoExample();
            example.createCriteria().andFilenameEqualTo(filename);
            gwkConsumeinfoMapper.deleteByExample(example);
            for (ArrayList record : impVar) {
                GwkConsumeinfo gwkConsumeinfo = new GwkConsumeinfo();
                gwkConsumeinfo.setBusidate(record.get(0).toString());
                gwkConsumeinfo.setCorelsh(record.get(1).toString());
                gwkConsumeinfo.setTxType(record.get(2).toString());
                gwkConsumeinfo.setMsginfosign(record.get(3).toString());
                gwkConsumeinfo.setTxCd(record.get(4).toString());
                gwkConsumeinfo.setServertermcode(record.get(5).toString());
                gwkConsumeinfo.setCleardate(record.get(6).toString());
                gwkConsumeinfo.setMngdeptcode(record.get(7).toString());
                gwkConsumeinfo.setTransmitdeptcode(record.get(8).toString());
                gwkConsumeinfo.setReceivedeptcode(record.get(9).toString());
                gwkConsumeinfo.setLsh(record.get(10).toString());
                gwkConsumeinfo.setAccount(record.get(11).toString());
                double money = Double.parseDouble(record.get(12).toString() + record.get(13).toString());
                BigDecimal busimoney = BigDecimal.valueOf(Double.parseDouble(df.format(money)));
                gwkConsumeinfo.setBusimoney(busimoney);                    //消费金额
                gwkConsumeinfo.setUniontxdate(record.get(14).toString());
                gwkConsumeinfo.setBusitype(record.get(15).toString());
                gwkConsumeinfo.setTerminalsign(record.get(16).toString());
                gwkConsumeinfo.setBusicode(record.get(17).toString());
                gwkConsumeinfo.setBusiname(record.get(18).toString());
                gwkConsumeinfo.setCurcde(record.get(19).toString());
                gwkConsumeinfo.setSearchrefercode(record.get(20).toString());
                gwkConsumeinfo.setAuthorizecode(record.get(21).toString());
                gwkConsumeinfo.setBackcode(record.get(22).toString());
                gwkConsumeinfo.setDaycanceltype(record.get(23).toString());
                gwkConsumeinfo.setRemark(record.get(24).toString());
                gwkConsumeinfo.setExpand(record.get(25).toString());
                gwkConsumeinfo.setBranchcode(record.get(26).toString());
                gwkConsumeinfo.setOperdate(dt);
                gwkConsumeinfo.setFilename(filename);
                gwkConsumeinfoMapper.insertSelective(gwkConsumeinfo);
                count++;
            }
            //日志表插入
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_consumeinfo");
            sysJoblog.setRowpkid("0");
            sysJoblog.setJobname("插入");
            sysJoblog.setJobdesc("插入条数" + String.valueOf(count));
            sysJoblog.setJobtime(dt);
            sysJoblogMapper.insert(sysJoblog);
        }
        return count;
    }

    /**
     * 开卡信息导入
     */
    @Transactional
    public int importCardOpen(ArrayList<ArrayList> impVar) {
        int count = 0;
        if (impVar != null && impVar.size() > 0) {
            Date dt = new Date();
            GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
            for (ArrayList record : impVar) {
                GwkCardbaseinfo gwkCardbaseinfo = new GwkCardbaseinfo();
                gwkCardbaseinfo.setCardname(record.get(0).toString());
                gwkCardbaseinfo.setCardengname(record.get(1).toString());
                gwkCardbaseinfo.setIdtype(record.get(2).toString());
                gwkCardbaseinfo.setIdnumber(record.get(3).toString());
                gwkCardbaseinfo.setBdgagencyname(record.get(4).toString());
                gwkCardbaseinfo.setBdgagency(record.get(5).toString());
                gwkCardbaseinfo.setAccount(record.get(6).toString());
                gwkCardbaseinfo.setNewaccount(record.get(7).toString());
                gwkCardbaseinfo.setStartdate("");
                gwkCardbaseinfo.setEnddate(record.get(8).toString());
                gwkCardbaseinfo.setCreatedate(record.get(9).toString());
                double acdtAmt = Double.parseDouble(df.format(Double.parseDouble(record.get(10).toString())));
                gwkCardbaseinfo.setAccreditamt(BigDecimal.valueOf(acdtAmt));
                gwkCardbaseinfo.setTitle(record.get(11).toString());
                gwkCardbaseinfo.setExpand(record.get(12).toString());
                gwkCardbaseinfo.setAction("0");                 //新增
                gwkCardbaseinfo.setOperdate(dt);
                //删除存在数据
                example.clear();
                example.createCriteria().andAccountEqualTo(record.get(6).toString());
                gwkCardbaseinfoMapper.deleteByExample(example);
                gwkCardbaseinfoMapper.insertSelective(gwkCardbaseinfo);
                count++;
            }
            //日志表插入
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_cardbaseinfo");
            sysJoblog.setRowpkid("0");
            sysJoblog.setJobname("插入");
            sysJoblog.setJobdesc("插入条数" + String.valueOf(count));
            sysJoblog.setJobtime(dt);
            sysJoblogMapper.insert(sysJoblog);
        }
        return count;
    }

    /**
     * 换卡信息更新响应卡号
     */
    @Transactional
    public int importCardRep(ArrayList<ArrayList> impVar) {
        int count = 0;
        if (impVar != null && impVar.size() > 0) {
            Date dt = new Date();
            GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
            for (ArrayList record : impVar) {
                GwkCardbaseinfo gwkCardbaseinfo = new GwkCardbaseinfo();
                gwkCardbaseinfo.setAccount(record.get(7).toString());
                gwkCardbaseinfo.setNewaccount(record.get(7).toString());
                gwkCardbaseinfo.setCreatedate(record.get(9).toString());
                example.clear();
                example.createCriteria().andAccountEqualTo(record.get(6).toString());
                gwkCardbaseinfoMapper.updateByExampleSelective(gwkCardbaseinfo, example);
                count++;
            }
            //日志表插入
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_cardbaseinfo");
            sysJoblog.setRowpkid("0");
            sysJoblog.setJobname("更新");
            sysJoblog.setJobdesc("更新条数" + String.valueOf(count));
            sysJoblog.setJobtime(dt);
            sysJoblogMapper.insert(sysJoblog);
        }
        return count;
    }
}
