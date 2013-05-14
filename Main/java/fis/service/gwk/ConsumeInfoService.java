package fis.service.gwk;

import fis.common.gwk.constant.ConsumeInfoSts;
import fis.common.gwk.constant.RtnTagKey;
import fis.repository.fs.dao.SysJoblogMapper;
import fis.repository.fs.model.SysJoblog;
import fis.repository.gwk.dao.GwkConsumeinfoMapper;
import fis.repository.gwk.model.GwkConsumeinfo;
import fis.repository.gwk.model.GwkConsumeinfoExample;
import gateway.txn.GwkServiceFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.advance.utils.PropertyManager;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: ����10:02
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ConsumeInfoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private GwkConsumeinfoMapper gwkConsumeinfoMapper;
    @Resource
    private SysJoblogMapper sysJoblogMapper;
    @Resource
    private GwkServiceFactory factory;

    //��ȡ����������Ϣ
    public List<GwkConsumeinfo> selectConsumeSendNo(String bofcode) {
        String status = ConsumeInfoSts.SEND_SUC.getCode();
        GwkConsumeinfoExample example = new GwkConsumeinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andStatusNotEqualTo(status);
        return gwkConsumeinfoMapper.selectByExample(example);
    }

    //����
    public String sendConsumeinfo(GwkConsumeinfo[] gwkConsumeinfos, String bofcode) throws Exception {
        List consumeList = null;       //��������
        List rtnlist = new ArrayList();
        String applicationid = "";
        String branchbankcode = "";
        String finorgcode = "";
        String longtuVer = "";
        String admdivCode = "";
        try {
            //send
            SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            String nowYear = yearsdf.format(dt);
//            String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
//            String branchbankcode = PropertyManager.getProperty("gwk.sub_branchbankcode");
//            String finorgcode = PropertyManager.getProperty("gwk.finorgcode");
            //2012-12-06
            applicationid = PropertyManager.getProperty("gwk.application.id."+bofcode);
            branchbankcode = PropertyManager.getProperty("gwk.sub.branch.bank.code."+bofcode);
            finorgcode = PropertyManager.getProperty("gwk.fin.org.code."+bofcode);
            //��������longtuver����ͼ�ӿڰ汾�ţ���admdivCode�������������룩 2012-12-06
            longtuVer = PropertyManager.getProperty("gwk.longtu.version."+bofcode);
            admdivCode = PropertyManager.getProperty("gwk.admdiv.code."+bofcode);
            //��д�ӿ�
            gateway.txn.t266001.gwk.BankService service = factory.getBankServiceForArea(bofcode);
//            BankService service = FaspServiceAdapter.getBankService();
            //���ݽӿڰ汾�ţ����ò�ͬ�Ľӿ� 2012-12-06
            if("v1".equals(longtuVer)){
                consumeList = getSendList(gwkConsumeinfos);
                rtnlist = service.writeConsumeInfo(applicationid, branchbankcode, nowYear, finorgcode, consumeList);
            }else{
                consumeList = getSendListV2(gwkConsumeinfos);
                rtnlist = service.writeConsumeInfo(applicationid, branchbankcode, nowYear,admdivCode,finorgcode, consumeList);
            }

        } catch (Exception ex) {
            throw new RuntimeException("����������Ϣʧ��:" + ex);
        }

        //������Ϣ����
        if("v1".equals(longtuVer)){
            if (rtnlist != null && rtnlist.size() > 0) {
                Map m = (Map) rtnlist.get(0);
                String msg = m.get(RtnTagKey.MESSAGE).toString();
                try {
                    if (m.get(RtnTagKey.RESULT).toString().equalsIgnoreCase(RtnTagKey.RESULT_SUCCESS)) {
                        //���سɹ�  ���� sendflag��1
                        updateSendSuc(gwkConsumeinfos, bofcode);
                        return RtnTagKey.RESULT_SUCCESS;
                    } else {
                        //����ʧ�� �����ظ�id
                        if (m.size() > 2) {
                            updateSameData(rtnlist, bofcode);
                        }
                        return msg;
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("������Ϣ���ͳɹ�����±�������ʧ��:" + ex);
                }
            }
        }else{
            if (rtnlist != null && rtnlist.size() > 0) {
                for (int i = 0; i < rtnlist.size(); i++) {
                    Map m1 = (Map) rtnlist.get(i);
                    String result = (String) m1.get(RtnTagKey.RESULT);
                    if (result == null){
                        result = (String) m1.get("result");
                    }
                    // ͨ���ж�result��ֵ�ж��Ƿ��ͳɹ�����ȫ���ɹ��򷵻�һ��result==success��ֵ
                    if (RtnTagKey.RESULT_SUCCESS.equalsIgnoreCase(result)) {
                        try {
                            updateSendSuc(gwkConsumeinfos, bofcode);
                            return RtnTagKey.RESULT_SUCCESS.toString();
                        } catch (Exception e) {
                            logger.error("�������ݺ���±�������Ϊ�ѷ���״̬�����쳣����鿴ϵͳ��־��");
                            return RtnTagKey.RESULT_FAIL.toString();
                        }
                    } else if (RtnTagKey.RESULT_FAIL.equalsIgnoreCase(result)){
                        return RtnTagKey.RESULT_FAIL.toString();
                    } else {
                        // �ж��Ƿ����ظ��ʺź����֤�ţ����У�����ļ�¼״̬Ϊ�ѷ���
                        String sameid = (String) m1.get("sameidnumber");
                        String sameaccount = (String) m1.get("sameaccount");
                        if ((sameid != null && !"".equals(sameid.trim()))
                                && (sameaccount != null && !"".equals(sameaccount.trim()))) {
                            try {
                                updateSameData(rtnlist, bofcode);
                                return RtnTagKey.RESULT_SUCCESS.toString();
                            } catch (Exception e) {
                                logger.error("�������ݺ�����ظ����͵�����ʱ�����쳣����鿴ϵͳ��־��");
                                return RtnTagKey.RESULT_FAIL.toString();
                            }
                        }
                    }
                }
            }
        }
        return RtnTagKey.RESULT_SUCCESS.toString();
    }

    /*�������д�������������*/
    public String sendAllConsumeinfos(String bofcode) throws Exception {
        String status = ConsumeInfoSts.SEND_SUC.getCode();
        GwkConsumeinfoExample example = new GwkConsumeinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andStatusNotEqualTo(status);
        List<GwkConsumeinfo> gwkConsumeinfos = gwkConsumeinfoMapper.selectByExample(example);
        if (gwkConsumeinfos.size() < 1) {
            return RtnTagKey.RESULT_SUCCESS;
        }
        List consumeList = null;       //��������
        List rtnlist = new ArrayList();
        String applicationid = "";
        String branchbankcode = "";
        String finorgcode = "";
        String longtuVer = "";
        String admdivCode = "";
        try {
            //send
            SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            String nowYear = yearsdf.format(dt);
//            String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
//            String branchbankcode = PropertyManager.getProperty("gwk.sub_branchbankcode");
//            String finorgcode = PropertyManager.getProperty("gwk.finorgcode");
            //2012-12-06
            applicationid = PropertyManager.getProperty("gwk.application.id."+bofcode);
            branchbankcode = PropertyManager.getProperty("gwk.sub.branch.bank.code."+bofcode);
            finorgcode = PropertyManager.getProperty("gwk.fin.org.code."+bofcode);
            //��������longtuver����ͼ�ӿڰ汾�ţ���admdivCode�������������룩 2012-12-06
            longtuVer = PropertyManager.getProperty("gwk.longtu.version."+bofcode);
            admdivCode = PropertyManager.getProperty("gwk.admdiv.code."+bofcode);
            //��д�ӿ�
            gateway.txn.t266001.gwk.BankService service = factory.getBankServiceForArea(bofcode);
//            BankService service = FaspServiceAdapter.getBankService();
            //���ݽӿڰ汾�ţ����ò�ͬ�Ľӿ� 2012-12-06
            if("v1".equals(longtuVer)){
                consumeList = getSendList(gwkConsumeinfos);
                rtnlist = service.writeConsumeInfo(applicationid, branchbankcode, nowYear, finorgcode, consumeList);
            }else{
                consumeList = getSendListV2(gwkConsumeinfos);
                rtnlist = service.writeConsumeInfo(applicationid, branchbankcode, nowYear,admdivCode,finorgcode, consumeList);
            }
        } catch (Exception ex) {
            throw new RuntimeException("����������Ϣʧ��:" + ex);
        }
        //������Ϣ����
        if("v1".equals(longtuVer)){
            if (rtnlist != null && rtnlist.size() > 0) {
                Map m = (Map) rtnlist.get(0);
                String msg = m.get(RtnTagKey.MESSAGE).toString();
                try {
                    if (m.get(RtnTagKey.RESULT).toString().equalsIgnoreCase(RtnTagKey.RESULT_SUCCESS)) {
                        //���سɹ�  ���� sendflag��1
                        updateSendSuc(gwkConsumeinfos, bofcode);
                        return RtnTagKey.RESULT_SUCCESS;
                    } else {
                        //����ʧ�� �����ظ�id
                        if (m.size() > 2) {
                            updateSameData(rtnlist, bofcode);
                        }
                        return msg;
                    }
                } catch (Exception ex) {
                    throw new RuntimeException("������Ϣ���ͳɹ�����±�������ʧ��:" + ex);
                }
            }
        }else{
            if (rtnlist != null && rtnlist.size() > 0) {
                for (int i = 0; i < rtnlist.size(); i++) {
                    Map m1 = (Map) rtnlist.get(i);
                    String result = (String) m1.get(RtnTagKey.RESULT);
                    if (result == null){
                        result = (String) m1.get("result");
                    }
                    // ͨ���ж�result��ֵ�ж��Ƿ��ͳɹ�����ȫ���ɹ��򷵻�һ��result==success��ֵ
                    if (RtnTagKey.RESULT_SUCCESS.equalsIgnoreCase(result)) {
                        try {
                            updateSendSuc(gwkConsumeinfos, bofcode);
                            return RtnTagKey.RESULT_SUCCESS.toString();
                        } catch (Exception e) {
                            logger.error("�������ݺ���±�������Ϊ�ѷ���״̬�����쳣����鿴ϵͳ��־��");
                            return RtnTagKey.RESULT_FAIL.toString();
                        }
                    } else if (RtnTagKey.RESULT_FAIL.equalsIgnoreCase(result)){
                        return RtnTagKey.RESULT_FAIL.toString();
                    } else {
                        // �ж��Ƿ����ظ��ʺź����֤�ţ����У�����ļ�¼״̬Ϊ�ѷ���
                        String sameid = (String) m1.get("sameidnumber");
                        String sameaccount = (String) m1.get("sameaccount");
                        if ((sameid != null && !"".equals(sameid.trim()))
                                && (sameaccount != null && !"".equals(sameaccount.trim()))) {
                            try {
                                updateSameData(rtnlist, bofcode);
                                return RtnTagKey.RESULT_SUCCESS.toString();
                            } catch (Exception e) {
                                logger.error("�������ݺ�����ظ����͵�����ʱ�����쳣����鿴ϵͳ��־��");
                                return RtnTagKey.RESULT_FAIL.toString();
                            }
                        }
                    }
                }
            }
        }
        return RtnTagKey.RESULT_SUCCESS.toString();
    }

    private List getSendList(GwkConsumeinfo[] gwkConsumeinfos) throws ParseException {
        List consumeList = new ArrayList();       //��������
        SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf6 = new SimpleDateFormat("yyyyMM");
        for (GwkConsumeinfo record : gwkConsumeinfos) {
            Map m = new HashMap();
            String lsh = record.getLsh() == null ? "" : record.getLsh();
            String account = record.getAccount() == null ? "" : record.getAccount();
            String cardname = record.getCardname() == null ? "" : record.getCardname();
            String busidate = record.getBusidate() == null ? "" : record.getBusidate();
            Double busimoney = record.getBusimoney().doubleValue();
            String businame = record.getBusiname() == null ? "" : record.getBusiname();
            String limitdate = record.getLimitdate() == null ? "" : record.getLimitdate();      //�޸� ���Ľ������ڵ��¸���20��

            m.put("ID", lsh);
            m.put("ACCOUNT", account);
            m.put("CARDNAME", cardname);
            m.put("BUSIDATE", busidate);
            m.put("BUSIMONEY", busimoney);
            m.put("BUSINAME", businame);
            m.put("Limitdate", limitdate);

            consumeList.add(m);
            logger.info("����������ϸ:��ˮ��=" + lsh + ";����=" + account + ";�ֿ���=" + cardname + ";��������=" + busidate + ";���׽��=" + busimoney
                    + ";�̻�=" + businame + ";��ٻ���=" + limitdate);
        }
        return consumeList;
    }

    private List getSendListV2(GwkConsumeinfo[] gwkConsumeinfos) throws ParseException {
        List consumeList = new ArrayList();       //��������
        SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf6 = new SimpleDateFormat("yyyyMM");
        for (GwkConsumeinfo record : gwkConsumeinfos) {
            Map m = new HashMap();
            String lsh = record.getLsh() == null ? "" : record.getLsh();
            String account = record.getAccount() == null ? "" : record.getAccount();
            String cardname = record.getCardname() == null ? "" : record.getCardname();
            String busidate = record.getBusidate() == null ? "" : record.getBusidate();
            Double busimoney = record.getBusimoney().doubleValue();
            String businame = record.getBusiname() == null ? "" : record.getBusiname();
            String limitdate = record.getLimitdate() == null ? "" : record.getLimitdate();      //�޸� ���Ľ������ڵ��¸���20��

            m.put("id", lsh);
            m.put("account", account);
            m.put("cardname", cardname);
            m.put("busidate", busidate);
            m.put("busimoney", busimoney);
            m.put("businame", businame);
            m.put("limitdate", limitdate);
            consumeList.add(m);
            logger.info("����������ϸ:��ˮ��=" + lsh + ";����=" + account + ";�ֿ���=" + cardname + ";��������=" + busidate + ";���׽��=" + busimoney
                    + ";�̻�=" + businame + ";��ٻ���=" + limitdate);
        }
        return consumeList;
    }

    private List getSendList(List<GwkConsumeinfo> gwkConsumeinfos) throws ParseException {
        List consumeList = new ArrayList();       //��������
        SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf6 = new SimpleDateFormat("yyyyMM");
        for (GwkConsumeinfo record : gwkConsumeinfos) {
            Map m = new HashMap();
            String lsh = record.getLsh() == null ? "" : record.getLsh();
            String account = record.getAccount() == null ? "" : record.getAccount();
            String cardname = record.getCardname() == null ? "" : record.getCardname();
            String busidate = record.getBusidate() == null ? "" : record.getBusidate();
            Double busimoney = record.getBusimoney().doubleValue();
            String businame = record.getBusiname() == null ? "" : record.getBusiname();
            String limitdate = record.getLimitdate() == null ? "" : record.getLimitdate();      //�޸� ���Ľ������ڵ��¸���20��

            m.put("ID", lsh);
            m.put("ACCOUNT", account);
            m.put("CARDNAME", cardname);
            m.put("BUSIDATE", busidate);
            m.put("BUSIMONEY", busimoney);
            m.put("BUSINAME", businame);
            m.put("Limitdate", limitdate);

            consumeList.add(m);
            logger.info("����������ϸ:��ˮ��=" + lsh + ";����=" + account + ";�ֿ���=" + cardname + ";��������=" + busidate + ";���׽��=" + busimoney
                    + ";�̻�=" + businame + ";��ٻ���=" + limitdate);
        }
        return consumeList;
    }

    private List getSendListV2(List<GwkConsumeinfo> gwkConsumeinfos) throws ParseException {
        List consumeList = new ArrayList();       //��������
        SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf6 = new SimpleDateFormat("yyyyMM");
        for (GwkConsumeinfo record : gwkConsumeinfos) {
            Map m = new HashMap();
            String lsh = record.getLsh() == null ? "" : record.getLsh();
            String account = record.getAccount() == null ? "" : record.getAccount();
            String cardname = record.getCardname() == null ? "" : record.getCardname();
            String busidate = record.getBusidate() == null ? "" : record.getBusidate();
            Double busimoney = record.getBusimoney().doubleValue();
            String businame = record.getBusiname() == null ? "" : record.getBusiname();
            String limitdate = record.getLimitdate() == null ? "" : record.getLimitdate();      //�޸� ���Ľ������ڵ��¸���20��

            m.put("id", lsh);
            m.put("account", account);
            m.put("cardname", cardname);
            m.put("busidate", busidate);
            m.put("busimoney", busimoney);
            m.put("businame", businame);
            m.put("limitdate", limitdate);
            consumeList.add(m);
            logger.info("����������ϸ:��ˮ��=" + lsh + ";����=" + account + ";�ֿ���=" + cardname + ";��������=" + busidate + ";���׽��=" + busimoney
                    + ";�̻�=" + businame + ";��ٻ���=" + limitdate);
        }
        return consumeList;
    }


    @Transactional
    private void updateSendSuc(GwkConsumeinfo[] gwkConsumeinfos, String bofcode) {
        if (gwkConsumeinfos.length > 0) {
            Date dt = new Date();
            GwkConsumeinfo updata = new GwkConsumeinfo();
            updata.setStatus(ConsumeInfoSts.SEND_SUC.getCode());
            updata.setOperdate(new Date());
            for (GwkConsumeinfo record : gwkConsumeinfos) {
                GwkConsumeinfoExample example = new GwkConsumeinfoExample();
                example.clear();
                example.createCriteria().andAreacodeEqualTo(bofcode).andAccountEqualTo(record.getAccount()).andLshEqualTo(record.getLsh());
                gwkConsumeinfoMapper.updateByExampleSelective(updata, example);
                //��־�����
                SysJoblog sysJoblog = new SysJoblog();
                sysJoblog.setTablename("gwk_consumeinfo");
                sysJoblog.setRowpkid(record.getPkid());
                sysJoblog.setJobname("����");
                sysJoblog.setJobdesc("���ͺ����:����״̬=" + updata.getStatus());
                sysJoblog.setJobtime(dt);
                //������־
                sysJoblogMapper.insert(sysJoblog);
            }
        }
    }

    @Transactional
    private void updateSendSuc(List<GwkConsumeinfo> gwkConsumeinfos, String bofcode) {
        if (gwkConsumeinfos.size() > 0) {
            Date dt = new Date();
            GwkConsumeinfo updata = new GwkConsumeinfo();
            updata.setStatus(ConsumeInfoSts.SEND_SUC.getCode());
            updata.setOperdate(new Date());
            for (GwkConsumeinfo record : gwkConsumeinfos) {
                GwkConsumeinfoExample example = new GwkConsumeinfoExample();
                example.clear();
                example.createCriteria().andAreacodeEqualTo(bofcode).andAccountEqualTo(record.getAccount()).andLshEqualTo(record.getLsh());
                gwkConsumeinfoMapper.updateByExampleSelective(updata, example);
                //��־�����
                SysJoblog sysJoblog = new SysJoblog();
                sysJoblog.setTablename("gwk_consumeinfo");
                sysJoblog.setRowpkid(record.getPkid());
                sysJoblog.setJobname("����");
                sysJoblog.setJobdesc("���ͺ����:����״̬=" + updata.getStatus());
                sysJoblog.setJobtime(dt);
                //������־
                sysJoblogMapper.insert(sysJoblog);
            }
        }
    }

    @Transactional
    private void updateSameData(List<Map> rtnlist, String bofcode) {
        Date dt = new Date();
        GwkConsumeinfo updata = new GwkConsumeinfo();
        updata.setStatus(ConsumeInfoSts.SEND_SUC.getCode());
        updata.setOperdate(dt);
        for (Map record : rtnlist) {
            GwkConsumeinfoExample example = new GwkConsumeinfoExample();
            example.clear();
            example.createCriteria().andAreacodeEqualTo(bofcode).andAccountEqualTo(record.get(RtnTagKey.SAMEACCOUNT).toString()).andLshEqualTo(record.get(RtnTagKey.SAMEID).toString());
            gwkConsumeinfoMapper.updateByExampleSelective(updata, example);
            //��־�����
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_consumeinfo");
            sysJoblog.setRowpkid(record.get(RtnTagKey.SAMEACCOUNT).toString());
            sysJoblog.setJobname("�����ظ�����");
            sysJoblog.setJobdesc("���ͺ����:����״̬=" + updata.getStatus());
            sysJoblog.setJobtime(dt);
            //������־
            sysJoblogMapper.insert(sysJoblog);
        }
    }

    //��������ѯ������Ϣ
    public List<GwkConsumeinfo> selectConsumeForQry(String status, String account, String busistartdate, String busienddate, String bofcode) {
        GwkConsumeinfoExample example = new GwkConsumeinfoExample();
        GwkConsumeinfoExample.Criteria criteria = example.createCriteria();
        if (status != null && !StringUtils.isEmpty(status)) {
            criteria.andStatusEqualTo(status);
        }
        if (account != null && !StringUtils.isEmpty(account)) {
            criteria.andAccountEqualTo(account);
        }
        if (busistartdate != null && !StringUtils.isEmpty(busistartdate)) {
            criteria.andBusidateGreaterThanOrEqualTo(busistartdate);
        }
        if (busienddate != null && !StringUtils.isEmpty(busienddate)) {
            criteria.andBusidateLessThanOrEqualTo(busienddate);
        }
        criteria.andAreacodeEqualTo(bofcode);
        return gwkConsumeinfoMapper.selectByExample(example);
    }
}
