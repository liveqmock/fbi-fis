package fis.service.gwk;

import fis.common.gwk.constant.ConfirmPayFlg;
import fis.common.gwk.constant.PayStatus;
import fis.repository.fs.dao.SysJoblogMapper;
import fis.repository.fs.model.SysJoblog;
import fis.repository.gwk.dao.GwkCardbaseinfoMapper;
import fis.repository.gwk.dao.GwkPaybackinfoMapper;
import fis.repository.gwk.model.GwkCardbaseinfo;
import fis.repository.gwk.model.GwkCardbaseinfoExample;
import fis.repository.gwk.model.GwkPaybackinfo;
import fis.repository.gwk.model.GwkPaybackinfoExample;
import gov.mof.fasp.service.CommonQueryService;
import gov.mof.fasp.service.adapter.client.FaspServiceAdapter;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.advance.utils.PropertyManager;
import skyline.service.SystemService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: ����11:12
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PaybackService {
    @Resource
    private GwkPaybackinfoMapper gwkPaybackinfoMapper;
    @Resource
    private GwkCardbaseinfoMapper gwkCardbaseinfoMapper;
    @Resource
    private SysJoblogMapper sysJoblogMapper;

    //��ȡ����
    public List<GwkPaybackinfo> getPaybackinfoByVch(String bofcode, String vchid) throws Exception {
        List<GwkPaybackinfo> dataList = new ArrayList<GwkPaybackinfo>();
        GwkPaybackinfoExample example = new GwkPaybackinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andVoucheridEqualTo(vchid);
        dataList = gwkPaybackinfoMapper.selectByExample(example);
        if (dataList == null || dataList.size() < 1) {
            List rtnlist = new ArrayList();
            try {
                SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
                Date dt = new Date();
                String nowYear = yearsdf.format(dt);
                String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
                Map m = new HashMap();
                m.put("VOUCHERID", vchid);
                //��ȡ
                CommonQueryService service = FaspServiceAdapter.getCommonQueryService();
                rtnlist = service.getQueryListBySql(applicationid, "queryConsumeInfo", m, nowYear);
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
            try {
                insertVchinfo(rtnlist,bofcode);
                dataList = gwkPaybackinfoMapper.selectByExample(example);
            } catch (Exception ex) {
                throw new RuntimeException("��ȡ֧��ƾ֤��Ϣ�ɹ�,�����뱾�����ݿ�ʧ��:" + ex.getMessage());
            }
        }
        return dataList;
    }

    //����ȷ��֧����־
    @Transactional
    public void updatePaybackinfoByVch(String bofcode,String vchid) throws Exception {
        GwkPaybackinfoExample example = new GwkPaybackinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andVoucheridEqualTo(vchid);
        GwkPaybackinfo record = new GwkPaybackinfo();
        record.setConfirmpayflag(ConfirmPayFlg.CONFIRMPAY_VALID.getCode());
        gwkPaybackinfoMapper.updateByExampleSelective(record,example);
        //��־�����
        SysJoblog sysJoblog = new SysJoblog();
        sysJoblog.setTablename("gwk_paybackinfo");
        sysJoblog.setRowpkid(vchid);
        sysJoblog.setJobname("����");
        sysJoblog.setJobdesc("����֧����ȷ��֧����־vchid=" + vchid);
        sysJoblog.setJobtime(new Date());
        //������־
        sysJoblogMapper.insertSelective(sysJoblog);
    }

    @Transactional
    private void insertVchinfo(List<Map> rtnlist,String bofcode) {
        Date dt = new Date();
        SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
        String strdt = sdf10.format(dt);
        String operid = SystemService.getOperatorManager().getOperatorId();
        for (Map record:rtnlist) {
            GwkPaybackinfo insertdata = new GwkPaybackinfo();
            GwkPaybackinfoExample example = new GwkPaybackinfoExample();
            example.clear();
            insertdata.setVoucherid(record.get("VOUCHERID").toString());
            insertdata.setAccount(record.get("ACCOUNT").toString());
            insertdata.setCardname(record.get("CARDNAME").toString());
            Double dbAmt = Double.parseDouble(record.get("Amt").toString());
            BigDecimal amt = BigDecimal.valueOf(dbAmt);
            insertdata.setAmt(amt);
            insertdata.setQuerydate(strdt);
            insertdata.setOperid(operid);
            insertdata.setOperdate(dt);
            insertdata.setStatus(PayStatus.SPDB_INIT.getCode());
            insertdata.setYear(strdt.substring(0,4));
            insertdata.setAreacode(bofcode);
            gwkPaybackinfoMapper.insertSelective(insertdata);
            //��־�����
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_paybackinfo");
//            sysJoblog.setRowpkid(record.);
            sysJoblog.setJobname("����");
            sysJoblog.setJobdesc("����֧����");
            sysJoblog.setJobtime(dt);
            //������־
            sysJoblogMapper.insertSelective(sysJoblog);

        }
    }

    //����������ѯ(status!=����ɹ� and confirmflag=1(��ȷ�ϻ���) and filesendflag=0)
    public List<GwkPaybackinfo> selectPaybackForBatch(String vchid,String acct,String paySts,String bofcode) {
        GwkPaybackinfoExample example = new GwkPaybackinfoExample();
        GwkPaybackinfoExample.Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo(PayStatus.SPDB_PAYSUC.getCode());       //����ɹ�����
        criteria.andConfirmpayflagEqualTo(ConfirmPayFlg.CONFIRMPAY_VALID.getCode()); //��ȷ�ϻ����
        criteria.andFilesendflagEqualTo("0");                                         //todo �������ļ������ݳ��� ��ȷ��
        if (vchid != null && !StringUtils.isEmpty(vchid)) {
            criteria.andVoucheridEqualTo(vchid);
        }
        if (acct != null && !StringUtils.isEmpty(acct)) {
            criteria.andAccountEqualTo(acct);
        }
        if (paySts != null && !StringUtils.isEmpty(paySts)) {
            criteria.andStatusEqualTo(paySts);
        }
        criteria.andAreacodeEqualTo(bofcode);
        return gwkPaybackinfoMapper.selectByExample(example);
    }

    //�������»�������(���� status=01��ʼ filesendflag=0��ʼ;)
    @Transactional
    public void updatePaybackBatch(GwkPaybackinfo[] gwkPaybackinfos) {
        Date dt = new Date();
        for (GwkPaybackinfo record:gwkPaybackinfos) {
            GwkPaybackinfo updata = new GwkPaybackinfo();
            updata.setAccount(record.getAccount());
            updata.setFilesendflag("0");          //�����ļ����ɱ�־0 �����������ļ�
            updata.setStatus(PayStatus.SPDB_INIT.getCode());
            GwkPaybackinfoExample example = new GwkPaybackinfoExample();
            example.clear();
            example.createCriteria().andPkidEqualTo(record.getPkid());
            gwkPaybackinfoMapper.updateByExampleSelective(updata,example);
            //��־�����
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_paybackinfo");
            sysJoblog.setRowpkid(record.getPkid());
            sysJoblog.setJobname("����");
            sysJoblog.setJobdesc("������������֧����");
            sysJoblog.setJobtime(dt);
            //������־
            sysJoblogMapper.insertSelective(sysJoblog);
        }
    }
    
    //��ѯ�����Ƿ����
    public boolean queryCardExists(String acct) {
        GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
        example.createCriteria().andAccountEqualTo(acct);
        List rcdlist = gwkCardbaseinfoMapper.selectByExample(example);
        if (rcdlist.size() < 1) {
            return false;
        } else {
            return true;
        }
    }

    //��������ѯ������Ϣ
    public List<GwkPaybackinfo> selectPaybackForQry(String vchid,String acct,String paySts,String bofcode) {
        GwkPaybackinfoExample example = new GwkPaybackinfoExample();
        GwkPaybackinfoExample.Criteria criteria = example.createCriteria();
        if (vchid != null && !StringUtils.isEmpty(vchid)) {
            criteria.andVoucheridEqualTo(vchid);
        }
        if (acct != null && !StringUtils.isEmpty(acct)) {
            criteria.andAccountEqualTo(acct);
        }
        if (paySts != null && !StringUtils.isEmpty(paySts)) {
            criteria.andStatusEqualTo(paySts);
        }
        criteria.andAreacodeEqualTo(bofcode);
        return gwkPaybackinfoMapper.selectByExample(example);
    }
}
