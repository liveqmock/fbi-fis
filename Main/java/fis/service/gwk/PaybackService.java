package fis.service.gwk;

import fis.common.gwk.constant.ConfirmPayFlg;
import fis.common.gwk.constant.PayStatus;
import fis.repository.fs.dao.SysJoblogMapper;
import fis.repository.fs.model.SysJoblog;
import fis.repository.gwk.dao.GwkCardbaseinfoMapper;
import fis.repository.gwk.dao.GwkPaybackinfoMapper;
import fis.repository.gwk.model.GwkCardbaseinfoExample;
import fis.repository.gwk.model.GwkPaybackinfo;
import fis.repository.gwk.model.GwkPaybackinfoExample;
import gateway.txn.GwkServiceFactory;
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
    @Resource
    private GwkServiceFactory factory;

    //��ȡ����
    public List<GwkPaybackinfo> getPaybackinfoByVch(String bofcode, String vchid) throws Exception {
        List<GwkPaybackinfo> dataList = new ArrayList<GwkPaybackinfo>();
        GwkPaybackinfoExample example = new GwkPaybackinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andVoucheridEqualTo(vchid);
        dataList = gwkPaybackinfoMapper.selectByExample(example);
        if (dataList == null || dataList.size() < 1) {
            List rtnlist = new ArrayList();
            String applicationid = "";
            String branchbankcode = "";
            String finorgcode = "";
            String longtuVer = "";
            String admdivCode = "";
            try {
                SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
                Date dt = new Date();
                String nowYear = yearsdf.format(dt);
//                String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
                //2012-12-06
                applicationid = PropertyManager.getProperty("gwk.application.id."+bofcode);
                branchbankcode = PropertyManager.getProperty("gwk.branch.bank.code."+bofcode);
                finorgcode = PropertyManager.getProperty("gwk.fin.org.code."+bofcode);
                //��������longtuver����ͼ�ӿڰ汾�ţ���admdivCode�������������룩 2012-12-06
                longtuVer = PropertyManager.getProperty("gwk.longtu.version."+bofcode);
                admdivCode = PropertyManager.getProperty("gwk.admdiv.code."+bofcode);
                //��ȡ
                //��д�ӿڲ���
                gateway.txn.t266001.gwk.CommonQueryService service = factory.getCommonQueryServiceForArea(bofcode);
                gateway.txn.t266001.gwk.BankService bankService = factory.getBankServiceForArea(bofcode);
//                CommonQueryService service = FaspServiceAdapter.getCommonQueryService();
                //���ݽӿڰ汾�ţ����ò�ͬ�Ľӿ� 2012-12-06
                if("v1".equals(longtuVer)){
                    Map m = new HashMap();
                    m.put("VOUCHERID", vchid);
                    rtnlist = service.getQueryListBySql(applicationid, "queryConsumeInfo", m, nowYear);
                }else{
                    rtnlist = bankService.queryVoucherByBillCode(applicationid,branchbankcode,nowYear,admdivCode,finorgcode,"5",vchid);
                    rtnlist = convertToPayBackInfo(vchid,rtnlist);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                if("v1".equals(longtuVer)){
                    insertVchinfo(rtnlist,bofcode);
                }else{
                    insertVchinfoV2(rtnlist,bofcode);
                }
                dataList = gwkPaybackinfoMapper.selectByExample(example);
            } catch (Exception ex) {
                throw new RuntimeException("��ȡ֧��ƾ֤��Ϣ�ɹ�,�����뱾�����ݿ�ʧ��:" + ex);
            }
        }
        return dataList;
    }

    //�����½ӿ��з��ر�����ԭ�е��г�ͻ����Ҫ���±���������Ϸ������й�̨ 2012-10-29
    private List convertToPayBackInfo(String voucherid,List oldList){
        List listBank = new ArrayList();
        List listDetails = new ArrayList();
        Map m1 = new HashMap();
        if (oldList.size()>1){
            m1 = (Map)oldList.get(1);
        }  else {
            m1 = (Map)oldList.get(0);
        }
        String result = (String) m1.get("result");
        if ("success".equalsIgnoreCase(result)){
            m1.clear();
            m1 = (Map)oldList.get(0);
            listDetails = (List)m1.get("details");
            for(int i=0;i<listDetails.size();i++){
                Map m2 = new HashMap();
                m2 = (Map)listDetails.get(i);
                m2.put("voucherid",voucherid);
                listBank.add(m2);
            }
        }
        return listBank;
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
            // ��д�ӿ� ���д
            Double dbAmt = Double.parseDouble(record.get("AMT").toString());
//            Double dbAmt = Double.parseDouble(record.get("Amt").toString());
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

    @Transactional
    private void insertVchinfoV2(List<Map> rtnlist,String bofcode) {
        Date dt = new Date();
        SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
        String strdt = sdf10.format(dt);
        String operid = SystemService.getOperatorManager().getOperatorId();
        for (Map record:rtnlist) {
            GwkPaybackinfo insertdata = new GwkPaybackinfo();
            GwkPaybackinfoExample example = new GwkPaybackinfoExample();
            example.clear();
//            insertdata.setVoucherid(record.get("VOUCHERID").toString());
//            insertdata.setAccount(record.get("ACCOUNT").toString());
//            insertdata.setCardname(record.get("CARDNAME").toString());
//            // ��д�ӿ� ���д
//            Double dbAmt = Double.parseDouble(record.get("AMT").toString());
            insertdata.setVoucherid(record.get("voucherid").toString());
            insertdata.setAccount(record.get("account").toString());
            insertdata.setCardname(record.get("cardname").toString());
            // ��д�ӿ� ���д
            Double dbAmt = Double.parseDouble(record.get("amt").toString());
//            Double dbAmt = Double.parseDouble(record.get("Amt").toString());
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
