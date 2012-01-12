package fis.service.gwk;

import fis.common.gwk.constant.RtnTagKey;
import fis.common.gwk.constant.CardSendFlg;
import fis.repository.fs.dao.SysJoblogMapper;
import fis.repository.fs.model.SysJoblog;
import fis.repository.gwk.dao.GwkCardbaseinfoMapper;
import fis.repository.gwk.model.GwkCardbaseinfo;
import fis.repository.gwk.model.GwkCardbaseinfoExample;
import gateway.ftp.pfbank.Config;
import gov.mof.fasp.service.BankService;
import gov.mof.fasp.service.adapter.client.FaspServiceAdapter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.advance.utils.PropertyManager;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: ����7:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CardInfoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private GwkCardbaseinfoMapper gwkCardbaseinfoMapper;
    @Resource
    private SysJoblogMapper sysJoblogMapper;

    //�����Ϳ���Ϣ��ѯ
    public List<GwkCardbaseinfo> selectCardinfos(String bofcode, String sendflag) {
        GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andSentflagEqualTo(sendflag).andStatusEqualTo("1");
        return gwkCardbaseinfoMapper.selectByExample(example);
    }

    //���Ϳ���Ϣ
    public String sendCardinfos(GwkCardbaseinfo[] gwkCardbaseinfos, String bofcode) throws Exception {
        List cardList = getSendList(gwkCardbaseinfos);
        List rtnlist = new ArrayList();
        try {
            SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            String nowYear = yearsdf.format(dt);
            String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
            String branchbankcode = PropertyManager.getProperty("gwk.branchbankcode");
            String finorgcode = PropertyManager.getProperty("gwk.finorgcode");
            BankService service = FaspServiceAdapter.getBankService();
            rtnlist = service.writeOfficeCard(applicationid, branchbankcode, nowYear, finorgcode, cardList);
        } catch (Exception ex) {
            throw new RuntimeException("���Ϳ���Ϣʧ��:" + ex.getMessage());
        }
        //������Ϣ����
        if (rtnlist != null && rtnlist.size() > 0) {
            Map m = (Map) rtnlist.get(0);
            String msg = m.get(RtnTagKey.MESSAGE).toString();
            try {
                if (m.get(RtnTagKey.RESULT).toString().equalsIgnoreCase(RtnTagKey.RESULT_SUCCESS)) {
                    //���سɹ�  ���� sendflag��1
                    updateSendSuc(gwkCardbaseinfos, bofcode);
                    return RtnTagKey.RESULT_SUCCESS.toString();
                } else {
                    //����ʧ�� �����ظ�id
                    if (m.size() > 2) {
                        updateSameData(rtnlist, bofcode);
                    }
                    return msg;
                }
            } catch (Exception ex) {
                throw new RuntimeException("����Ϣ���ͳɹ�����±�������ʧ��:" + ex.getMessage());
            }
        }
        return RtnTagKey.RESULT_SUCCESS.toString();
    }

    /*�������д����Ϳ���Ϣ*/
    public String sendAllCardinfos(String bofcode) throws Exception {
        GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
        example.createCriteria().andAreacodeEqualTo(bofcode).andSentflagEqualTo(CardSendFlg.SEND_NO.getCode()).andStatusEqualTo("1");
        List<GwkCardbaseinfo> gwkCardbaseinfos = gwkCardbaseinfoMapper.selectByExample(example);
        if (gwkCardbaseinfos.size() < 1) {
            return RtnTagKey.RESULT_SUCCESS;
        }
        List cardList = getSendList(gwkCardbaseinfos);
        List rtnlist = new ArrayList();
        try {
            SimpleDateFormat yearsdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            String nowYear = yearsdf.format(dt);
            String applicationid = PropertyManager.getProperty("fbifis.sys.bank.code");
            String branchbankcode = PropertyManager.getProperty("gwk.branchbankcode");
            String finorgcode = PropertyManager.getProperty("gwk.finorgcode");
            BankService service = FaspServiceAdapter.getBankService();
            //todo ����
            rtnlist = service.writeOfficeCard(applicationid, branchbankcode, nowYear, finorgcode, cardList);
        } catch (Exception ex) {
            throw new RuntimeException("���Ϳ���Ϣʧ��:" + ex.getMessage());
        }
        //������Ϣ����
        if (rtnlist != null && rtnlist.size() > 0) {
            Map m = (Map) rtnlist.get(0);
            String msg = m.get(RtnTagKey.MESSAGE).toString();
            try {
                if (m.get(RtnTagKey.RESULT).toString().equalsIgnoreCase(RtnTagKey.RESULT_SUCCESS)) {
                    //���سɹ�  ���� sendflag��1
                    updateSendSuc(gwkCardbaseinfos, bofcode);
                    return RtnTagKey.RESULT_SUCCESS.toString();
                } else {
                    //����ʧ�� �����ظ�id
                    if (m.size() > 2) {
                        updateSameData(rtnlist, bofcode);
                    }
                    return msg;
                }
            } catch (Exception ex) {
                throw new RuntimeException("����Ϣ���ͳɹ�����±�������ʧ��:" + ex.getMessage());
            }
        }
        return RtnTagKey.RESULT_SUCCESS.toString();
    }

    private List getSendList(GwkCardbaseinfo[] gwkCardbaseinfos) {
        List cardList = new ArrayList();       //��������
        for (GwkCardbaseinfo record : gwkCardbaseinfos) {
            Map m = new HashMap();
            String account = (record.getAccount() == null ? "" : record.getAccount().toString());
            String cardname = record.getCardname() == null ? "" : record.getCardname().toString();
            String bdgagency = record.getBdgagency() == null ? "" : record.getBdgagency().toString();
            String GATHERINGBANKACCTNAME = (record.getGatheringbankacctname() == null ? "" : record.getGatheringbankacctname().toString());
            String GATHERINGBANKNAME = (record.getGatheringbankname() == null ? "" : record.getGatheringbankname().toString());
            String GATHERINGBANKACCTCODE = (record.getGatheringbankacctcode() == null ? "" : record.getGatheringbankacctcode().toString());
            String IDNUMBER = (record.getIdnumber() == null ? "" : record.getIdnumber().toString());
            String DIGEST = (record.getDigest() == null ? "" : record.getDigest().toString());
            String BANK = (record.getBank() == null ? "" : record.getBank().toString());
            String CREATEDATE = record.getCreatedate() == null ? "" : record.getCreatedate().toString();
            String Startdate = record.getStartdate() == null ? "" : record.getStartdate().toString();           // ��Ч��ֹ��
            String enddate = record.getEnddate() == null ? "" : record.getEnddate().toString();                 // ת����ʽ yyyy-mm-dd
            String action = record.getAction() == null ? "" : record.getAction().toString();
            m.put("ACCOUNT", account);
            m.put("CARDNAME", cardname);
            m.put("BDGAGENCY", bdgagency);
            m.put("GATHERINGBANKACCTNAME", GATHERINGBANKACCTNAME);
            m.put("GATHERINGBANKNAME", GATHERINGBANKNAME);
            m.put("GATHERINGBANKACCTCODE", GATHERINGBANKACCTCODE);
            m.put("IDNUMBER", IDNUMBER);
            m.put("DIGEST", DIGEST);
            m.put("BANK", BANK);
            m.put("CREATEDATE", CREATEDATE);
            m.put("STARTDATE", Startdate);
            m.put("ENDDATE", enddate);
            m.put("ACTION", action);
            cardList.add(m);
            logger.info("���Ϳ���Ϣ:" + "ACCOUNT=" + account + ";CARDNAME=" + cardname + ";Ԥ�㵥λ=" + bdgagency
                    + ";�����ʻ���=" + GATHERINGBANKACCTNAME + ";�����˻�������=" + GATHERINGBANKNAME + ";�����ʺ�=" + GATHERINGBANKACCTCODE
                    + ";֤����=" + IDNUMBER + ";��;=" + DIGEST + ";�������б���=" + BANK + ";��������=" + CREATEDATE
                    + ";��Ч��ʼ=" + Startdate + ";��Ч��ֹ=" + enddate + ";��״̬=" + action);
        }
        return cardList;
    }

    private List getSendList(List<GwkCardbaseinfo> gwkCardbaseinfos) {
        List cardList = new ArrayList();       //��������
        for (GwkCardbaseinfo record : gwkCardbaseinfos) {
            Map m = new HashMap();
            String account = (record.getAccount() == null ? "" : record.getAccount().toString());
            String cardname = record.getCardname() == null ? "" : record.getCardname().toString();
            String bdgagency = record.getBdgagency() == null ? "" : record.getBdgagency().toString();
            String GATHERINGBANKACCTNAME = (record.getGatheringbankacctname() == null ? "" : record.getGatheringbankacctname().toString());
            String GATHERINGBANKNAME = (record.getGatheringbankname() == null ? "" : record.getGatheringbankname().toString());
            String GATHERINGBANKACCTCODE = (record.getGatheringbankacctcode() == null ? "" : record.getGatheringbankacctcode().toString());
            String IDNUMBER = (record.getIdnumber() == null ? "" : record.getIdnumber().toString());
            String DIGEST = (record.getDigest() == null ? "" : record.getDigest().toString());
            String BANK = (record.getBank() == null ? "" : record.getBank().toString());
            String CREATEDATE = record.getCreatedate() == null ? "" : record.getCreatedate().toString();
            String Startdate = record.getStartdate() == null ? "" : record.getStartdate().toString();           // ��Ч��ֹ��
            String enddate = record.getEnddate() == null ? "" : record.getEnddate().toString();                 // ת����ʽ yyyy-mm-dd
            String action = record.getAction() == null ? "" : record.getAction().toString();
            m.put("ACCOUNT", account);
            m.put("CARDNAME", cardname);
            m.put("BDGAGENCY", bdgagency);
            m.put("GATHERINGBANKACCTNAME", GATHERINGBANKACCTNAME);
            m.put("GATHERINGBANKNAME", GATHERINGBANKNAME);
            m.put("GATHERINGBANKACCTCODE", GATHERINGBANKACCTCODE);
            m.put("IDNUMBER", IDNUMBER);
            m.put("DIGEST", DIGEST);
            m.put("BANK", BANK);
            m.put("CREATEDATE", CREATEDATE);
            m.put("STARTDATE", Startdate);
            m.put("ENDDATE", enddate);
            m.put("ACTION", action);
            cardList.add(m);
            logger.info("���Ϳ���Ϣ:" + "ACCOUNT=" + account + ";CARDNAME=" + cardname + ";Ԥ�㵥λ=" + bdgagency
                    + ";�����ʻ���=" + GATHERINGBANKACCTNAME + ";�����˻�������=" + GATHERINGBANKNAME + ";�����ʺ�=" + GATHERINGBANKACCTCODE
                    + ";֤����=" + IDNUMBER + ";��;=" + DIGEST + ";�������б���=" + BANK + ";��������=" + CREATEDATE
                    + ";��Ч��ʼ=" + Startdate + ";��Ч��ֹ=" + enddate + ";��״̬=" + action);
        }
        return cardList;
    }

    @Transactional
    private void updateSendSuc(GwkCardbaseinfo[] gwkCardbaseinfos, String bofcode) {
        if (gwkCardbaseinfos.length > 0) {
            Date dt = new Date();
            GwkCardbaseinfo updata = new GwkCardbaseinfo();
            updata.setSentflag(CardSendFlg.SEND_YES.getCode());
            updata.setOperdate(new Date());
            for (GwkCardbaseinfo record : gwkCardbaseinfos) {
                GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
                example.clear();
                example.createCriteria().andAccountEqualTo(record.getAccount()).andIdnumberEqualTo(record.getIdnumber())
                        .andAreacodeEqualTo(bofcode);
                gwkCardbaseinfoMapper.updateByExampleSelective(updata, example);
                //��־�����
                SysJoblog sysJoblog = new SysJoblog();
                sysJoblog.setTablename("gwk_cardbaseinfo");
                sysJoblog.setRowpkid(record.getPkid());
                sysJoblog.setJobname("����");
                sysJoblog.setJobdesc("���ͺ����:����״̬=" + updata.getSentflag());
                sysJoblog.setJobtime(dt);
                //������־
                sysJoblogMapper.insert(sysJoblog);
            }
        }
    }

    @Transactional
    private void updateSendSuc(List<GwkCardbaseinfo> gwkCardbaseinfos, String bofcode) {
        if (gwkCardbaseinfos.size() > 0) {
            Date dt = new Date();
            GwkCardbaseinfo updata = new GwkCardbaseinfo();
            updata.setSentflag(CardSendFlg.SEND_YES.getCode());
            updata.setOperdate(new Date());
            for (GwkCardbaseinfo record : gwkCardbaseinfos) {
                GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
                example.clear();
                example.createCriteria().andAccountEqualTo(record.getAccount()).andIdnumberEqualTo(record.getIdnumber())
                        .andAreacodeEqualTo(bofcode);
                gwkCardbaseinfoMapper.updateByExampleSelective(updata, example);
                //��־�����
                SysJoblog sysJoblog = new SysJoblog();
                sysJoblog.setTablename("gwk_cardbaseinfo");
                sysJoblog.setRowpkid(record.getPkid());
                sysJoblog.setJobname("����");
                sysJoblog.setJobdesc("���ͺ����:����״̬=" + updata.getSentflag());
                sysJoblog.setJobtime(dt);
                //������־
                sysJoblogMapper.insert(sysJoblog);
            }
        }
    }

    @Transactional
    private void updateSameData(List<Map> rtnlist, String bofcode) {
        Date dt = new Date();
        GwkCardbaseinfo updata = new GwkCardbaseinfo();
        updata.setSentflag(CardSendFlg.SEND_YES.getCode());
        updata.setOperdate(dt);
        for (Map record : rtnlist) {
            GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
            example.clear();
            example.createCriteria().andAccountEqualTo(record.get(RtnTagKey.SAMEACCOUNT).toString())
                    .andIdnumberEqualTo(record.get(RtnTagKey.SAMEIDNUM).toString()).andAreacodeEqualTo(bofcode);
            gwkCardbaseinfoMapper.updateByExampleSelective(updata, example);
            //��־�����
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_cardbaseinfo");
            sysJoblog.setRowpkid(record.get(RtnTagKey.SAMEACCOUNT).toString());
            sysJoblog.setJobname("�����ظ�����");
            sysJoblog.setJobdesc("���ͺ����:����״̬=" + updata.getSentflag());
            sysJoblog.setJobtime(dt);
            //������־
            sysJoblogMapper.insert(sysJoblog);
        }
    }

    //��������ѯ����Ϣ
    public List<GwkCardbaseinfo> selectCardinfoForQry(String bdgagencyname, String account, String bofcode) {
        GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
        GwkCardbaseinfoExample.Criteria criteria = example.createCriteria();
        if (bdgagencyname != null && !StringUtils.isEmpty(bdgagencyname)) {
            criteria.andBdgagencynameLike("%" + bdgagencyname + "%");
        }
        if (account != null && !StringUtils.isEmpty(account)) {
            criteria.andAccountEqualTo(account);
        }
        criteria.andAreacodeEqualTo(bofcode);
        return gwkCardbaseinfoMapper.selectByExample(example);
    }
}
