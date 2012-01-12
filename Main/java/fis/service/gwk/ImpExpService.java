package fis.service.gwk;

import fis.common.gwk.constant.ConfirmPayFlg;
import fis.common.gwk.constant.PayStatus;
import fis.repository.fs.dao.SysJoblogMapper;
import fis.repository.fs.model.SysJoblog;
import fis.repository.gwk.dao.GwkCardbaseinfoMapper;
import fis.repository.gwk.dao.GwkConsumeinfoMapper;
import fis.repository.gwk.dao.GwkPaybackinfoMapper;
import fis.repository.gwk.dao.GwkPaybackresultMapper;
import fis.repository.gwk.model.*;
import gateway.ftp.pfbank.Config;
import org.apache.ecs.html.Big;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.advance.utils.PropertyManager;
import pub.platform.security.OperatorManager;
import skyline.service.SystemService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-6
 * Time: ����3:02
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ImpExpService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
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
     * ��ѯ�������� status=01��ʼ;filesendflag=0��ʼ;confirmpayflag=1ȷ�ϻ���
     */
    public ArrayList<ArrayList> selectPaybackinfos() throws Exception {
        Date dt = new Date();
        ArrayList<ArrayList> dataList = new ArrayList<ArrayList>();
        List<GwkPaybackinfo> paybackinfoList = gwkPaybackinfoMapper.selectPaybackinfo4File();
        for (GwkPaybackinfo record : paybackinfoList) {
            ArrayList<String> subDataList = new ArrayList<String>();
            subDataList.add("0310");    //���д���
            subDataList.add(Config.getString("Bank_Code"));  //���д���
            subDataList.add(sdf.format(dt));                 //��¼��������
            subDataList.add("");                             //�����ʻ�����
            String stramt = record.getAmt().toString();
            subDataList.add(stramt);
            subDataList.add("156");                          //����
            subDataList.add("");                             //�������� ��
            subDataList.add(record.getAccount().toString()); //�˺�
            subDataList.add("00000");                        //ǿ��У���־
            subDataList.add(record.getIdtype() == null ? "" : record.getIdtype());
            subDataList.add(record.getIdnumber() == null ? "" : record.getIdnumber());
            subDataList.add("1");                            //�ۿ���ߴ���־
            subDataList.add("0");                            //ʹ����ɿ����͸֧��ȱ�־
            subDataList.add("0");                            //����ʱ��ȫ��ۿ��־
            subDataList.add("20");                           //�ۿ�/�������
            subDataList.add("������������");                //��������
            subDataList.add(record.getVoucherid());          //��ˮ��(֧�����)
            subDataList.add("����");                             //����
            dataList.add(subDataList);
        }
        return dataList;
    }

    /*�����ļ������filesendflag=yyyyMMdd*/
    @Transactional
    public void updatePaybackinfos() {
        Date dt = new Date();
        GwkPaybackinfoExample example = new GwkPaybackinfoExample();
        example.createCriteria().andStatusEqualTo(PayStatus.SPDB_INIT.getCode()).andFilesendflagEqualTo("0")
                .andConfirmpayflagEqualTo(ConfirmPayFlg.CONFIRMPAY_VALID.getCode());
        GwkPaybackinfo record = new GwkPaybackinfo();
        record.setFilesendflag(sdf.format(dt));
        gwkPaybackinfoMapper.updateByExampleSelective(record, example);
    }

    /**
     * �ۿ�������
     */
    @Transactional
    public int importPaybackresult(ArrayList<ArrayList> impVar, String filename) throws Exception {
        int count = 0;
        if (impVar != null && impVar.size() > 0) {
            Date dt = new Date();
            //ɾ��ͬ�ļ�����
            GwkPaybackresultExample example = new GwkPaybackresultExample();
            example.createCriteria().andFilenameEqualTo(filename);
            gwkPaybackresultMapper.deleteByExample(example);
            for (ArrayList record : impVar) {
                GwkPaybackresult gwkPaybackresult = new GwkPaybackresult();
                gwkPaybackresult.setBankcode(record.get(0).toString());
                gwkPaybackresult.setRecoroccurdate(record.get(1).toString());
                gwkPaybackresult.setGatheringbankacctcode(record.get(2).toString());
                //��� ��λ��->Ԫ
                double amt = Double.parseDouble(record.get(3).toString()) / 100;
                BigDecimal dcmAmt = BigDecimal.valueOf(Double.parseDouble(df.format(amt)));
                gwkPaybackresult.setAmt(dcmAmt);
                gwkPaybackresult.setCurcde(record.get(4).toString());
                gwkPaybackresult.setInacDate(record.get(5).toString());
                //��� ��λ��->Ԫ
                double inamt = Double.parseDouble(record.get(6).toString()) / 100;
                BigDecimal dcmInamt = BigDecimal.valueOf(Double.parseDouble(df.format(inamt)));
                gwkPaybackresult.setInacAmt(dcmInamt);
                gwkPaybackresult.setAccount(record.get(7).toString());
                gwkPaybackresult.setDirectiontype(record.get(8).toString());
                gwkPaybackresult.setDklsh(record.get(9).toString());           //֧�����
                gwkPaybackresult.setResponsecode(record.get(10).toString());   //������״̬
                gwkPaybackresult.setExpand(record.get(11).toString());
                gwkPaybackresult.setFilename(filename);
                gwkPaybackresult.setAreacode(PropertyManager.getProperty("gwk.areacode"));    //������
                gwkPaybackresultMapper.insertSelective(gwkPaybackresult);
                //���»���״̬ ��������
                GwkPaybackinfo gwkPaybackinfo = new GwkPaybackinfo();
                gwkPaybackinfo.setStatus(record.get(10).toString());
                gwkPaybackinfo.setPaybackdate(record.get(5).toString());
                //�������ʧ�� ����filesendflag=0 ���·���
                if (!record.get(10).toString().equals(PayStatus.SPDB_PAYSUC.getCode())) {
                    gwkPaybackinfo.setFilesendflag("0");
                }
                gwkPaybackinfo.setOperdate(dt);
                GwkPaybackinfoExample paybackinfoExample = new GwkPaybackinfoExample();
                paybackinfoExample.clear();
                // ������� ���� ��λ
                paybackinfoExample.createCriteria().andVoucheridEqualTo(record.get(9).toString())
                        .andAccountEqualTo(record.get(7).toString())
                        .andAmtEqualTo(dcmAmt);
                gwkPaybackinfoMapper.updateByExampleSelective(gwkPaybackinfo, paybackinfoExample);
                count++;
            }
            //��־�����
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_paybackresult");
            sysJoblog.setRowpkid("0");
            sysJoblog.setJobname("����");
            sysJoblog.setJobdesc("��������" + String.valueOf(count));
            sysJoblog.setJobtime(dt);
            sysJoblogMapper.insert(sysJoblog);
        }
        return count;
    }

    /**
     * ������Ϣ����
     */
    @Transactional
    public int importConsumeinfo(ArrayList<ArrayList> impVar, String filename) throws Exception {
        int count = 0;
        if (impVar != null && impVar.size() > 0) {
            SimpleDateFormat sdf6 = new SimpleDateFormat("yyyyMM");
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
                gwkConsumeinfo.setAccount(record.get(11).toString().substring(2));      //ɾ��ǰ��λ
                gwkConsumeinfo.setCardname(queryCardname(record.get(11).toString().substring(2)));//���뿨��
                //��� ��ת����ԪΪ��λ
                double money = Double.parseDouble(record.get(12).toString() + record.get(13).toString()) / 100;
                BigDecimal busimoney = BigDecimal.valueOf(Double.parseDouble(df.format(money)));
                gwkConsumeinfo.setBusimoney(busimoney);                    //���ѽ��
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
                gwkConsumeinfo.setAreacode(PropertyManager.getProperty("gwk.areacode"));              //����
                //������ٻ������� busidate������20��
                Calendar c = Calendar.getInstance();
                c.clear();
                c.setTime(sdf.parse(record.get(0).toString()));
                c.add(Calendar.MONTH, 1);
                Date endTime = c.getTime();
                String limitdate = sdf6.format(endTime) + "20";
                gwkConsumeinfo.setLimitdate(limitdate);
                gwkConsumeinfoMapper.insertSelective(gwkConsumeinfo);
                count++;
            }
            //��־�����
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_consumeinfo");
            sysJoblog.setRowpkid("0");
            sysJoblog.setJobname("����");
            sysJoblog.setJobdesc("��������" + String.valueOf(count));
            sysJoblog.setJobtime(dt);
            sysJoblogMapper.insert(sysJoblog);
        }
        return count;
    }

    //���ݿ��Ų��ҳֿ��� ����������Ϣʱ��
    private String queryCardname(String account) {
        GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
        example.clear();
        example.createCriteria().andAccountEqualTo(account);
        List<GwkCardbaseinfo> gwkCardbaseinfoList = gwkCardbaseinfoMapper.selectByExample(example);
        if (gwkCardbaseinfoList.size() < 1) {
            return "";
        } else {
            return gwkCardbaseinfoList.get(0).getCardname();
        }
    }

    /**
     * ������Ϣ����
     */
    @Transactional
    public int importCardOpen(ArrayList<ArrayList> impVar) throws Exception {
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
                gwkCardbaseinfo.setStartdate(sdf10.format(dt));                            //��������       yyyy-mm-dd
                String enddate10 = getformatdt10(record.get(8).toString());
                gwkCardbaseinfo.setEnddate(enddate10);        //yymm --> yyyy-mm-dd
                String createdate8 = record.get(9).toString();
                String createdate10 = createdate8.substring(0, 4) + "-" + createdate8.substring(4, 6) + "-" + createdate8.substring(6, 8);
                gwkCardbaseinfo.setCreatedate(createdate10);     //yyyymmdd ת�� yyyy-mm-dd
                double acdtAmt = Double.parseDouble(df.format(Double.parseDouble(record.get(10).toString())));
                gwkCardbaseinfo.setAccreditamt(BigDecimal.valueOf(acdtAmt));
                gwkCardbaseinfo.setTitle(record.get(11).toString());
                gwkCardbaseinfo.setExpand(record.get(12).toString());
                gwkCardbaseinfo.setBranchbankcode(record.get(13).toString());
                gwkCardbaseinfo.setAction("0");                 //����
                gwkCardbaseinfo.setOperdate(dt);
                gwkCardbaseinfo.setAreacode(PropertyManager.getProperty("gwk.areacode"));          //�ַ�����
                gwkCardbaseinfo.setBank(Config.getString("CARDBANK"));
                gwkCardbaseinfo.setGatheringbankacctname(Config.getString("GATHERINGBANKACCTNAME"));
                gwkCardbaseinfo.setGatheringbankacctcode(Config.getString("GATHERINGBANKACCTCODE"));
                gwkCardbaseinfo.setGatheringbankname(Config.getString("GATHERINGBANKNAME"));
                //ɾ����������
                example.clear();
                example.createCriteria().andAccountEqualTo(record.get(6).toString());
                gwkCardbaseinfoMapper.deleteByExample(example);
                gwkCardbaseinfoMapper.insertSelective(gwkCardbaseinfo);
                count++;
            }
            //��־�����
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_cardbaseinfo");
            sysJoblog.setRowpkid("0");
            sysJoblog.setJobname("����");
            sysJoblog.setJobdesc("��������" + String.valueOf(count));
            sysJoblog.setJobtime(dt);
            sysJoblogMapper.insert(sysJoblog);
        }
        return count;
    }

    private String getformatdt10(String yymm) {
        SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");
        String yyyymmdd = "20" + yymm + "01";
        Date tempdt = null;
        try {
            tempdt = sdf.parse(yyyymmdd);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(tempdt);
        c.set(Calendar.DATE, 1);
        c.roll(Calendar.DATE, -1);
        Date endTime = c.getTime();
        return sdf10.format(endTime);
    }

    /**
     * ������Ϣ������Ӧ����
     */
    @Transactional
    public int importCardRep(ArrayList<ArrayList> impVar) throws Exception {
        int count = 0;
        if (impVar != null && impVar.size() > 0) {
            Date dt = new Date();
            GwkCardbaseinfoExample example = new GwkCardbaseinfoExample();
            for (ArrayList record : impVar) {
                GwkCardbaseinfo gwkCardbaseinfo = new GwkCardbaseinfo();
                gwkCardbaseinfo.setAccount(record.get(7).toString());
                gwkCardbaseinfo.setNewaccount(record.get(7).toString());
                gwkCardbaseinfo.setCreatedate(record.get(9).toString());
                gwkCardbaseinfo.setAction("1");
                gwkCardbaseinfo.setSentflag("0"); //Ϊ����
                example.clear();
                example.createCriteria().andAccountEqualTo(record.get(6).toString());
                gwkCardbaseinfoMapper.updateByExampleSelective(gwkCardbaseinfo, example);
                count++;
            }
            //��־�����
            SysJoblog sysJoblog = new SysJoblog();
            sysJoblog.setTablename("gwk_cardbaseinfo");
            sysJoblog.setRowpkid("0");
            sysJoblog.setJobname("����");
            sysJoblog.setJobdesc("��������" + String.valueOf(count));
            sysJoblog.setJobtime(dt);
            sysJoblogMapper.insert(sysJoblog);
        }
        return count;
    }
}
