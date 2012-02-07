package fis.service.pay;

import fis.common.BeanCopy;
import fis.repository.fs.dao.SysJoblogMapper;
import fis.repository.fs.model.SysJoblog;
import fis.repository.pay.dao.PayPayvoucherMapper;
import fis.repository.pay.dao.PayPayvoucherhisMapper;
import fis.repository.pay.model.PayPayvoucher;
import fis.repository.pay.model.PayPayvoucherExample;
import fis.repository.pay.model.PayPayvoucherhis;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.platform.security.OperatorManager;
import skyline.service.SystemService;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: ����2:45
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PayProcessService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String applicationid = "BANK.CCB";
    private String bankcode = "222002";  //���б���
    private String finorg = "266100";    //�����ֱ���
    @Autowired
    private PayPayvoucherMapper payPayvoucherMapper;
    @Autowired
    private PayPayvoucherhisMapper payPayvoucherhisMapper;
    @Autowired
    private SysJoblogMapper sysJoblogMapper;

    /*֧��ҵ���ѯҳ�� ��������ѯ*/
    public List<PayPayvoucher> selectedPayvchByCondition(String printid, String bdgagency,
                                                         String paytype, String vouchertype, String processflag,
                                                         Date updatedt) throws ParseException {
        PayPayvoucherExample example = new PayPayvoucherExample();
        PayPayvoucherExample.Criteria criteria = example.createCriteria();
        if (printid != null && !StringUtils.isEmpty(printid.trim())) {
            criteria.andPrintidEqualTo(printid);
        }
        if (bdgagency != null && !StringUtils.isEmpty(bdgagency.trim())) {
            criteria.andBdgagencyEqualTo(bdgagency);
        }
        if (paytype != null && !StringUtils.isEmpty(paytype.trim())) {
            criteria.andPaytypeEqualTo(paytype);
        }
        if (vouchertype != null && !StringUtils.isEmpty(vouchertype.trim())) {
            criteria.andVouchertypeEqualTo(vouchertype);
        }
        if (processflag != null && !StringUtils.isEmpty(processflag.trim())) {
            criteria.andProcessflagEqualTo(processflag);
        }
        if (updatedt != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String begindt = df.format(updatedt) + " 00:00:00";
            String enddt = df.format(updatedt) + " 23:59:59";
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            criteria.andLastUpdDateBetween(df2.parse(begindt), df2.parse(enddt));
        }
        return payPayvoucherMapper.selectByExample(example);
    }

    /*����guid��ѯ����*/
    public PayPayvoucher selectedPayvchByguid(String guid) {
        PayPayvoucherExample example = new PayPayvoucherExample();
        example.createCriteria().andGuidEqualTo(guid);
        List<PayPayvoucher> payPayvouchers = payPayvoucherMapper.selectByExample(example);
        return payPayvouchers.get(0);
    }

    /*֧��-����֧����Ż�ȡ����*/
    public PayPayvoucher selectedPayvchByPrintid(String printid) {
        PayPayvoucherExample example = new PayPayvoucherExample();
        example.createCriteria().andPrintidEqualTo(printid);
        List<PayPayvoucher> payPayvouchers = payPayvoucherMapper.selectByExample(example);
        if (payPayvouchers.size() < 1) {
            return null;
            //todo ͨ���ӿڻ�ȡ����
            /*BankService service = FaspServiceAdapter.getBankService();
            //����֧����Ż�ȡ
            List rtnlist = service.queryVoucherByPrintId(this.applicationid, this.bankcode, "2011", finorg, "1", printid);
            if (rtnlist.size() > 0) {
                int rntlistCount = rtnlist.size() - 1;
                ListOrderedMap maplast = (ListOrderedMap) rtnlist.get(rntlistCount);
                String result = maplast.get("result").toString();
                logger.info("��ȡƾ֤�����Ϣ��"+result);
                if (result.equalsIgnoreCase("success")) {
                    ListOrderedMap m1 = (ListOrderedMap) rtnlist.get(0);
                    PayPayvoucher av = (PayPayvoucher) BeanCopy.copyObject("tsas.repository.model.PayPayvoucher",m1);
                    payPayvoucherMapper.insertSelective(av);
                    return av;
                } else {
                    logger.error("��ȡƾ֤�����Ϣʧ�ܣ�" + result);
                    return null;
                }
            }*/
        }
        return payPayvouchers.get(0);
    }

    /*������״̬,��������,֧�����ͣ���ѯ����*/
    public List<PayPayvoucher> selectedPayvchForProcflag(List<String> procflagary, Date updatedt, String paytype) throws ParseException {
        PayPayvoucherExample example = new PayPayvoucherExample();
        if (updatedt == null) {
            example.createCriteria().andProcessflagIn(procflagary).andPaytypeEqualTo(paytype);
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            String begindt = df.format(updatedt) + " 00:00:00";
            String enddt = df.format(updatedt) + " 23:59:59";
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            example.createCriteria().andProcessflagIn(procflagary).andLastUpdDateBetween(df2.parse(begindt), df2.parse(enddt)).andPaytypeEqualTo(paytype);
        }
        return payPayvoucherMapper.selectByExample(example);
    }

    /*֧���ɹ���д����*/
    @Transactional
    public void paySucProcess(PayPayvoucher record, String processflag, String paytype) throws InvocationTargetException, IllegalAccessException {
        //todo ��д���� ���ʻ�д
//        BankService service = FaspServiceAdapter.getBankService();
//        List<Map> paySucList = new ArrayList<Map>();
//        Map paySucMap = new HashMap();
//        paySucMap.put("actiontype","02");
//        paySucMap.put("result","");
//        paySucMap.put("reason","");
//        paySucMap.put("amt",record.getAmt());
//        paySucMap.put("guid",record.getGuid());
//        paySucMap.put("operator",record.getLastUpdBy());
//        paySucMap.put("operatetime","");
//        service.confirmDirectVou()
        if (1 == 1) {
            //��д�ɹ�
            updatePrintinfo(record, processflag, "01");
        }
    }

    /*��Ʊ��д����*/
    @Transactional
    public void refuseSucProcess(PayPayvoucher record, String processflag, String paytype) throws InvocationTargetException, IllegalAccessException {
        //todo ��д����
        if (1 == 1) {
            //��д�ɹ���
            updatePrintinfo(record, processflag, "02");
        }
    }

    /*�˿��д����*/
    @Transactional
    public void refundSucProcess(PayPayvoucher record, String processflag, String paytype) throws InvocationTargetException, IllegalAccessException {
        //todo ��д����
        if (1 == 1) {
            //��д�ɹ���
            updatePrintinfo(record, processflag, "03");
        }
    }

    /*�����д����*/
    @Transactional
    public void clearanceSucProcess(PayPayvoucher record, String processflag) throws InvocationTargetException, IllegalAccessException {
        //todo ��д����
        if (1 == 1) {
            //��д�ɹ�
            updatePrintinfo(record, processflag, "01");
        }
    }

    /*��д�ɹ��󱾵ظ���֧��������ʷ�� [ƾ֤����]��[����״̬]�����ݻ�д����(֧��;��Ʊ;�˿�;����)��*/
    public void updatePrintinfo(PayPayvoucher record, String processflag, String vouchertype) throws InvocationTargetException, IllegalAccessException {
        //����ƾ֤�� processflag vouchertype amt
        PayPayvoucher tmprcd = new PayPayvoucher();
        tmprcd.setAmt(record.getAmt());    //����ֶ��޸�
        tmprcd.setPayeebankaccount(record.getPayeebankaccount()); //�տ����˺�
        tmprcd.setPayeebank(record.getPayeebank());               //�տ��˿�����
        tmprcd.setPayee(record.getPayee());                       //�տ���ȫ��
        tmprcd.setProcessflag(processflag);
        tmprcd.setVouchertype(vouchertype);
        String operid = SystemService.getOperatorManager().getOperatorId();
        tmprcd.setLastUpdBy(operid);
        tmprcd.setLastUpdDate(new Date());
        PayPayvoucherExample example = new PayPayvoucherExample();
        example.createCriteria().andPrintidEqualTo(record.getPrintid());
        //��ʷ�����
        PayPayvoucherhis payvoucherhis = new PayPayvoucherhis();
        payvoucherhis = (PayPayvoucherhis) BeanCopy.copayObject(payvoucherhis, record);
        payvoucherhis.setCreatedBy(operid);
        payvoucherhis.setCreatedDt(new Date());
        //��־�����
        SysJoblog sysJoblog = new SysJoblog();
        sysJoblog.setTablename("pay_payvoucher");
        sysJoblog.setRowpkid(record.getPrintid());
        sysJoblog.setJobname("����");
        sysJoblog.setJobdesc("��д�ɹ������:����״̬=" + processflag);
        sysJoblog.setJobtime(new Date());
        OperatorManager operatorManager = SystemService.getOperatorManager();
        sysJoblog.setJobuserid(operatorManager.getOperatorId());
        sysJoblog.setJobusername(operatorManager.getOperatorName());
        payPayvoucherMapper.updateByExampleSelective(tmprcd, example);
        //������ʷ��¼
        payPayvoucherhisMapper.insertSelective(payvoucherhis);
        //������־��
        sysJoblogMapper.insertSelective(sysJoblog);
    }
}
