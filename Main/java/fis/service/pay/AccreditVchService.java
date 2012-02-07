package fis.service.pay;

import fis.common.BeanCopy;
import fis.repository.pay.dao.PayAccreditvoucherMapper;
import fis.repository.pay.model.PayAccreditvoucher;
import fis.repository.pay.model.PayAccreditvoucherExample;
import gov.mof.fasp.service.BankService;
import gov.mof.fasp.service.adapter.client.FaspServiceAdapter;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skyline.service.SystemService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: ����2:01
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AccreditVchService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String applicationid = "BANK.CCB";
    private String bankcode = "222002";  //���б���
    private String finorg = "266100";    //�����ֱ���

    @Autowired
    private PayAccreditvoucherMapper payAccreditvoucherMapper;

    /*��ȡ��Ȩ���֪ͨ������*/
    @Transactional
    public void getAutAccreditVoucher() {
        List rtnlist = new ArrayList();
        try {
            BankService service = FaspServiceAdapter.getBankService();
            rtnlist = service.queryAutAccreditVoucher(this.applicationid, this.bankcode, "2011", finorg, "0");
        } catch (Exception ex0) {
            logger.error("��ȡ��Ȩ���֪ͨ���ӿ�����ʧ�ܡ�");
            throw new RuntimeException("��ȡ��Ȩ���֪ͨ���ӿ�����ʧ�ܡ�", ex0);
        }
        if (rtnlist.size() > 0) {
            int rtnlistCount = rtnlist.size() - 1;
            ListOrderedMap maplast = (ListOrderedMap) rtnlist.get(rtnlistCount);
            String result = maplast.get("result").toString();
            String msg = maplast.get("message").toString();
            System.out.println("result=" + result + "��message=" + msg);
            if (result.equalsIgnoreCase("SUCCESS")) {
                logger.info("��ȡ�ɹ�������=" + rtnlistCount);
                try {
                    String operid = SystemService.getOperatorManager().getOperatorId();
                    for (int i = 0; i < rtnlistCount; i++) {
                        ListOrderedMap m1 = (ListOrderedMap) rtnlist.get(i);
                        PayAccreditvoucher av = (PayAccreditvoucher) BeanCopy.copyObject("fis.repository.pay.model.PayAccreditvoucher", m1);
                        av.setCreatedBy(operid);
                        av.setCreatedDt(new Date());
                        PayAccreditvoucherExample example = new PayAccreditvoucherExample();
                        example.clear();
                        example.createCriteria().andGuidEqualTo(m1.get("guid").toString());
                        payAccreditvoucherMapper.deleteByExample(example);
                        payAccreditvoucherMapper.insertSelective(av);
                    }
                } catch (Exception ex) {
                    logger.error("���ݲ���ʧ�ܡ�");
                    ex.printStackTrace();
                }
            }
        }

    }

    /*����ֹ�·ݲ�ѯ��ȡ�����֪ͨ��*/
    public List<PayAccreditvoucher> selectedAccreditList(short beginmonth, short endmonth) {
        PayAccreditvoucherExample example = new PayAccreditvoucherExample();
        example.clear();
        example.createCriteria().andMonthBetween(beginmonth, endmonth);
        return payAccreditvoucherMapper.selectByExample(example);
    }

    /*��Ȩ��ȵ�������ѯ*/
    public List<PayAccreditvoucher> selectedAccreditsByCon(String bdgagencyname, String paybankactno, String billcode, short month) {
        PayAccreditvoucherExample example = new PayAccreditvoucherExample();
        example.clear();
        PayAccreditvoucherExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(bdgagencyname.trim()) && bdgagencyname != null) {
            criteria.andBdgagencynameLike("%" + bdgagencyname + "%");
        }
        if (!StringUtils.isEmpty(paybankactno.trim()) && paybankactno != null) {
            criteria.andPaybankaccountEqualTo(paybankactno);
        }
        if (!StringUtils.isEmpty(billcode.trim()) && billcode != null) {
            criteria.andBillcodeEqualTo(billcode);
        }
        if (month != 0) {
            criteria.andMonthEqualTo(month);
        }
        return payAccreditvoucherMapper.selectByExample(example);

    }

    /*��Ԥ�㵥λ��ȡԤ�㵥λ�˻���Ϣ*/
    public List<PayAccreditvoucher> selectedAccreditForPrint(String bdgagencyname) {
        bdgagencyname = "%" + bdgagencyname + "%";
        return payAccreditvoucherMapper.selectedAccreditByDist(bdgagencyname);
    }


}
