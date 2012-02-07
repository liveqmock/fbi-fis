package fis.service.pay;

import fis.repository.fs.dao.SysJoblogMapper;
import fis.repository.fs.model.SysJoblog;
import fis.repository.pay.dao.PayActinfoMapper;
import fis.repository.pay.model.PayActinfo;
import fis.repository.pay.model.PayActinfoExample;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.security.OperatorManager;
import skyline.service.SystemService;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: ����2:05
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ActinfoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PayActinfoMapper payActinfoMapper;
    @Autowired
    private SysJoblogMapper sysJoblogMapper;

    public List<PayActinfo> selectActinfoList() {
        PayActinfoExample example = new PayActinfoExample();
        example.createCriteria().andDeletedFlagEqualTo("0");
        return payActinfoMapper.selectByExample(example);
    }

    /*��������ѯ*/
    public List<PayActinfo> selectedActinfoByCon(String actname, String actno, String acttype) {
        PayActinfoExample example = new PayActinfoExample();
        PayActinfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedFlagEqualTo("0");
        if (!StringUtils.isEmpty(actname.trim())) {
            criteria.andActnameLike("%" + actname + "%");
        }
        if (!StringUtils.isEmpty(actno.trim())) {
            criteria.andActnoEqualTo(actno);
        }
        if (!StringUtils.isEmpty(acttype.trim())) {
            criteria.andActtypeEqualTo(acttype);
        }
        return payActinfoMapper.selectByExample(example);
    }

    /*ѡ��ɾ������*/
    public List<PayActinfo> selectDelActinfoList() {
        PayActinfoExample example = new PayActinfoExample();
        example.createCriteria().andDeletedFlagEqualTo("1");
        return payActinfoMapper.selectByExample(example);
    }
    public PayActinfo selectActinfo(String pkid) {
        return payActinfoMapper.selectByPrimaryKey(pkid);
    }

    @Transactional
    public void insertActinfo(PayActinfo payActinfo) {
        SysJoblog sysJoblog = new SysJoblog();
        sysJoblog.setTablename("pay_actinfo");
        sysJoblog.setRowpkid(payActinfo.getPkid());
        sysJoblog.setJobname("�½�");
        sysJoblog.setJobdesc("�����˻�");
        sysJoblog.setJobtime(new Date());
        OperatorManager operatorManager = SystemService.getOperatorManager();
        sysJoblog.setJobuserid(operatorManager.getOperatorId());
        sysJoblog.setJobusername(operatorManager.getOperatorName());
        payActinfoMapper.insertSelective(payActinfo);
        sysJoblogMapper.insertSelective(sysJoblog);
    }

    @Transactional
    public void updateActinfo(PayActinfo payActinfo) {
        SysJoblog sysJoblog = new SysJoblog();
        sysJoblog.setTablename("pay_actinfo");
        sysJoblog.setRowpkid(payActinfo.getPkid());
        sysJoblog.setJobname("�޸�");
        sysJoblog.setJobdesc("�޸��˻�");
        sysJoblog.setJobtime(new Date());
        OperatorManager operatorManager = SystemService.getOperatorManager();
        sysJoblog.setJobuserid(operatorManager.getOperatorId());
        sysJoblog.setJobusername(operatorManager.getOperatorName());
        PayActinfoExample example = new PayActinfoExample();
        example.createCriteria().andPkidEqualTo(payActinfo.getPkid());
        payActinfoMapper.updateByExampleSelective(payActinfo, example);
        sysJoblogMapper.insertSelective(sysJoblog);
    }

    /*ɾ���˻���Ϣ*/
    @Transactional
    public void deleteActinfo(PayActinfo[] payActinfos) {
        PayActinfoExample example = new PayActinfoExample();
        for (PayActinfo record : payActinfos) {
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("pay_actinfo");
            sysJoblog.setRowpkid(record.getPkid());
            sysJoblog.setJobname("�߼�ɾ��");
            sysJoblog.setJobdesc("�߼�ɾ���˻�");
            sysJoblog.setJobtime(new Date());
            OperatorManager operatorManager = SystemService.getOperatorManager();
            sysJoblog.setJobuserid(operatorManager.getOperatorId());
            sysJoblog.setJobusername(operatorManager.getOperatorName());
            PayActinfo rcdtmp = new PayActinfo();
            rcdtmp.setDeletedFlag("1");
            example.clear();
            example.createCriteria().andPkidEqualTo(record.getPkid());
            payActinfoMapper.updateByExampleSelective(rcdtmp, example);
            sysJoblogMapper.insertSelective(sysJoblog);
        }
    }
}
