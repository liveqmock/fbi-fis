package fis.service.pay;

import fis.common.BeanCopy;
import fis.repository.pay.dao.*;
import fis.repository.pay.model.*;
import gov.mof.fasp.service.ElementService;
import gov.mof.fasp.service.adapter.client.FaspServiceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skyline.service.SystemService;

import javax.faces.model.SelectItem;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: ����2:29
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BasicinfoQryService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String operdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    public String applicationid = "BANK.CCB";
    @Autowired
    private PayBasicBdgagencyMapper basicBdgagencyMapper;
    @Autowired
    private PayBasicBdgmanagedivisionMapper basicBdgmanagedivisionMapper;
    @Autowired
    private PayBasicExpecoMapper basicExpecoMapper;
    @Autowired
    private PayBasicExpfuncMapper basicExpfuncMapper;
    @Autowired
    private PayBasicFuncMapper basicFuncMapper;
    @Autowired
    private PayBasicFundtypeMapper basicFundtypeMapper;
    @Autowired
    private PayBasicIncomesortMapper basicIncomesortMapper;
    @Autowired
    private PayBasicIndsourceMapper basicIndsourceMapper;
    @Autowired
    private PayBasicPaykindMapper basicPaykindMapper;
    @Autowired
    private PayBasicPaytypeMapper basicPaytypeMapper;
    @Autowired
    private PayBasicProfundMapper basicProfundMapper;
    @Autowired
    private PayBasicProgramMapper basicProgramMapper;
    @Autowired
    private PayBasicSettlemodeMapper basicSettlemodeMapper;

    /*��ѯ��λ��Ϣ*/
    public List qryBdgagencyInfo() {
        return basicBdgagencyMapper.selectByExample(new PayBasicBdgagencyExample());
    }

    public List<SelectItem> getBdgagencyItems(boolean isSelectAll) {
        List<PayBasicBdgagency> basicBdgagencyList = basicBdgagencyMapper.selectByExample(new PayBasicBdgagencyExample());
        List<SelectItem> items = new ArrayList<SelectItem>();
        SelectItem item;
        if (isSelectAll) {
            items.add(new SelectItem("",""));
        }
        for (PayBasicBdgagency record:basicBdgagencyList) {
            item = new SelectItem(record.getCode(),record.getName());
            items.add(item);
        }
        return items;
    }

    /*��ѯ ��ڴ���*/
    public List qryBdgmanagedivInfo() {
        return basicBdgmanagedivisionMapper.selectByExample(new PayBasicBdgmanagedivisionExample());
    }

    /*��ѯ֧����ʽ����*/

    public List qryPaytypeInfo() {
        return basicPaytypeMapper.selectByExample(new PayBasicPaytypeExample());
    }

    /*��ѯ ֧�����÷�����Ϣ ��*/
    public List qryExpecoInfo() {
        return basicExpecoMapper.selectByExample(new PayBasicExpecoExample());
    }

    /*��ѯ ֧�����ܷ�����Ϣ ��*/
    public List qryExpfuncInfo() {
        return basicExpfuncMapper.selectByExample(new PayBasicExpfuncExample());
    }

    /*��ѯ ���ܿ�Ŀ ��*/
    public List qryFuncInfo() {
        return basicFuncMapper.selectByExample(new PayBasicFuncExample());
    }

    /*��ѯ �ʽ����� ��*/
    public List qryFundtyleInfo() {
        return basicFundtypeMapper.selectByExample(new PayBasicFundtypeExample());
    }

    /*��ѯ ��������Ŀ ��*/
    public List qryIncomesortInfo() {
        return basicIncomesortMapper.selectByExample(new PayBasicIncomesortExample());
    }

    /*��ѯ Ԥ����Դ ��*/
    public List qryIndsourceInfo() {
        return basicIndsourceMapper.selectByExample(new PayBasicIndsourceExample());
    }

    /*��ѯ ֧������ ��*/
    public List qryPaykindInfo() {
        return basicPaykindMapper.selectByExample(new PayBasicPaykindExample());
    }

    /*��ѯ ��Ŀ���� ��*/
    public List qryProfundInfo() {
        return basicProfundMapper.selectByExample(new PayBasicProfundExample());
    }

    /*��ѯ ��Ŀ ��*/
    public List qryProgramInfo() {
        return basicProgramMapper.selectByExample(new PayBasicProgramExample());
    }

    /*��ѯ ���ʽ ��*/
    public List qrySettlemodeInfo() {
        return basicSettlemodeMapper.selectByExample(new PayBasicSettlemodeExample());
    }

    /*���� ��λ��Ϣ ��*/
    @Transactional
    public void getAllInfoBdgagency() throws Exception {
        List rtnlist = getAllElementInfo("BDGAGENCY");
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            PayBasicBdgagency obj = (PayBasicBdgagency) BeanCopy.copyObject("fis.repository.pay.model.PayBasicBdgagency", m1);
            PayBasicBdgagencyExample example = new PayBasicBdgagencyExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            basicBdgagencyMapper.deleteByExample(example);
            basicBdgagencyMapper.insertSelective(obj);
        }
    }

    /*���� ��ڴ��� ��*/
    @Transactional
    public void getAllInfoBdgmanagedivision() throws Exception {
        List rtnlist = getAllElementInfo("BDGMANAGEDIVISION");
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            PayBasicBdgmanagedivision obj = (PayBasicBdgmanagedivision) BeanCopy.copyObject("fis.repository.pay.model.PayBasicBdgmanagedivision", m1);
            PayBasicBdgmanagedivisionExample example = new PayBasicBdgmanagedivisionExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            basicBdgmanagedivisionMapper.deleteByExample(example);
            basicBdgmanagedivisionMapper.insertSelective(obj);
        }
    }

    /*���� ֧�����÷�����Ϣ ��*/
    @Transactional
    public void getAllInfoExpeco() throws Exception {
        //todo �汾�űȶ�
       List rtnlist = getAllElementInfo("EXPECO");
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            PayBasicExpeco obj = (PayBasicExpeco) BeanCopy.copyObject("fis.repository.pay.model.PayBasicExpeco", m1);
            PayBasicExpecoExample example = new PayBasicExpecoExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            basicExpecoMapper.deleteByExample(example);
            basicExpecoMapper.insertSelective(obj);
        }
    }

    /*���� ֧�����ܷ�����Ϣ ��*/
    @Transactional
    public void getAllInfoExpfunc() throws Exception {
        //todo �汾�űȶ�
        List rtnlist = getAllElementInfo("EXPFUNC");
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            PayBasicExpfunc obj = (PayBasicExpfunc) BeanCopy.copyObject("fis.repository.pay.model.PayBasicExpfunc", m1);
            PayBasicExpfuncExample example = new PayBasicExpfuncExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            basicExpfuncMapper.deleteByExample(example);
            basicExpfuncMapper.insertSelective(obj);
        }
    }

    /*���� ���ܿ�Ŀ ��*/
    @Transactional
    public void getAllInfoFunc() throws Exception {
        //todo �汾�űȶ�
        List rtnlist = getAllElementInfo("FUNC");
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            PayBasicFunc obj = (PayBasicFunc) BeanCopy.copyObject("fis.repository.pay.model.PayBasicFunc", m1);
            PayBasicFuncExample example = new PayBasicFuncExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            basicFuncMapper.deleteByExample(example);
            basicFuncMapper.insertSelective(obj);
        }
    }

    /*���� �ʽ����� ��*/
    @Transactional
    public void getAllInfoFundtype() throws Exception {
        //todo �汾�űȶ�
        List rtnlist = getAllElementInfo("FUNDTYPE");
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            PayBasicFundtype obj = (PayBasicFundtype) BeanCopy.copyObject("fis.repository.pay.model.PayBasicFundtype", m1);
            PayBasicFundtypeExample example = new PayBasicFundtypeExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            basicFundtypeMapper.deleteByExample(example);
            basicFundtypeMapper.insertSelective(obj);
        }
    }

    /*���� ��������Ŀ ��*/
    @Transactional
    public void getAllInfoIncomesort() throws Exception {
        //todo �汾�űȶ�
        List rtnlist = getAllElementInfo("INCOMESORT");
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            PayBasicIncomesort obj = (PayBasicIncomesort) BeanCopy.copyObject("fis.repository.pay.model.PayBasicIncomesort", m1);
            PayBasicIncomesortExample example = new PayBasicIncomesortExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            basicIncomesortMapper.deleteByExample(example);
            basicIncomesortMapper.insertSelective(obj);
        }
    }

    /*���� Ԥ����Դ ��*/
    @Transactional
    public void getAllInfoIndsource() throws Exception {
        //todo �汾�űȶ�
        List rtnlist = getAllElementInfo("INDSOURCE");
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            PayBasicIndsource obj = (PayBasicIndsource) BeanCopy.copyObject("fis.repository.pay.model.PayBasicIndsource", m1);
            PayBasicIndsourceExample example = new PayBasicIndsourceExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            basicIndsourceMapper.deleteByExample(example);
            basicIndsourceMapper.insertSelective(obj);
        }
    }

    /*���� ֧������ ��*/
    @Transactional
    public void getAllInfoPaykind() throws Exception {
        //todo �汾�űȶ�
        List rtnlist = getAllElementInfo("PAYKIND");
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            PayBasicPaykind obj = (PayBasicPaykind) BeanCopy.copyObject("fis.repository.pay.model.PayBasicPaykind", m1);
            PayBasicPaykindExample example = new PayBasicPaykindExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            basicPaykindMapper.deleteByExample(example);
            basicPaykindMapper.insertSelective(obj);
        }
    }

    /*���� ��Ŀ���� ��*/
    @Transactional
    public void getAllInfoProfund() throws Exception {
        //todo �汾�űȶ�
        List rtnlist = getAllElementInfo("PROFUND");
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            PayBasicProfund obj = (PayBasicProfund) BeanCopy.copyObject("fis.repository.pay.model.PayBasicProfund", m1);
            PayBasicProfundExample example = new PayBasicProfundExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            basicProfundMapper.deleteByExample(example);
            basicProfundMapper.insertSelective(obj);
        }
    }

    /*���� ��Ŀ ��*/
    @Transactional
    public void getAllInfoProgram() throws Exception {
        //todo �汾�űȶ�
        List rtnlist = getAllElementInfo("PROGRAM");
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            PayBasicProgram obj = (PayBasicProgram) BeanCopy.copyObject("fis.repository.pay.model.PayBasicProgram", m1);
            PayBasicProgramExample example = new PayBasicProgramExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            basicProgramMapper.deleteByExample(example);
            basicProgramMapper.insertSelective(obj);
        }
    }

    /*���� ���ʽ ��*/
    @Transactional
    public void getAllInfoSettlemode() throws Exception {
        //todo �汾�űȶ�
        List rtnlist = getAllElementInfo("SETTLEMODE");
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            PayBasicSettlemode obj = (PayBasicSettlemode) BeanCopy.copyObject("fis.repository.pay.model.PayBasicSettlemode", m1);
            PayBasicSettlemodeExample example = new PayBasicSettlemodeExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(m1.get("guid").toString());
            basicSettlemodeMapper.deleteByExample(example);
            basicSettlemodeMapper.insertSelective(obj);
        }
    }

    /*���� ֧����ʽ ��*/
    @Transactional
    public void getAllInfoPaytype() throws Exception {
        String elementcode = "PAYTYPE";
        //todo �ȶ԰汾��
        //��ȡƽ̨�汾��
//        String[] elementcodeArray = new String[1];
//        elementcodeArray[0] = elementcode;
//        Map mapVersion = queryElementVersion(elementcodeArray);
//        String zfversion = mapVersion.get(elementcode).toString();
        //��ȡ�������ݰ汾
//        String lcversion = qryBasicdtVersionMapper.selectPaytypeVersion(elementcode);
        //todo ���汾�Ų�һ�� ��ȡ��������
        List rtnlist = getAllElementInfo(elementcode);
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        //todo ���뱾�����ݿ�
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            String code = m1.get("guid").toString();
            PayBasicPaytypeExample example = new PayBasicPaytypeExample();
            example.clear();
            example.createCriteria().andCodeEqualTo(code);
            basicPaytypeMapper.deleteByExample(example);   //ɾ������
            String tempcode = String.valueOf(Integer.parseInt(code) + 1);
            m1.remove("code");
            m1.put("code", tempcode);
            PayBasicPaytype obj = (PayBasicPaytype) BeanCopy.copyObject("fis.repository.pay.model.PayBasicPaytype", m1);
            basicPaytypeMapper.insertSelective(obj);
        }
        System.out.println(" ======================= ");
        System.out.println(" version=" + version);
    }

    /*
   * ��ȡ����������Ϣ*/
    private List getAllElementInfo(String elementcode) throws Exception {

//        ElementService service = FaspServiceAdapter.getElementService();
//        List rtnlist = service.queryAllElementCode(applicationid, elementcode, 2011);

        String operid = SystemService.getOperatorManager().getOperatorId();
        //���ز���
        List rtnlist = new ArrayList();
        Map map1 = new HashMap();
        map1.put("code", "101");
        map1.put("name", "Сţ");
        map1.put("supercode", "200");
        map1.put("isleaf", "1");
        map1.put("levelno", "1");
        map1.put("guid", "20020020012002002001200200200120020020012002002001");
        rtnlist.add(map1);
        Map map2 = new HashMap();
        map2.put("version", "1");
        rtnlist.add(map2);
        int rtnlistCount = rtnlist.size() - 1;
        Map maplast = (Map) rtnlist.get(rtnlistCount);
        String version = "";
        version = (String) maplast.get("version");
        for (int i = 0; i < rtnlistCount; i++) {
            Map m1 = (Map) rtnlist.get(i);
            m1.put("version", version);
            m1.put("operid", operid);
            m1.put("operdate", operdate);
        }
        return rtnlist;
    }

    //��ѯ�汾��
    public Map queryElementVersion(String[] elementcodeArray) {
        ElementService service = FaspServiceAdapter.getElementService();
        List elementCodeList = new ArrayList();
        for (String elementcode : elementcodeArray) {
            elementCodeList.add(elementcode);
        }
        Map elversionmap = null;
        try {
            elversionmap = service.queryElementVersion(applicationid, 2011, elementCodeList);
            if (!elversionmap.isEmpty()) {
                for (String elementcode : elementcodeArray) {
                    logger.info("��ȡ�汾��:");
                    logger.info("�汾��: " + elversionmap.get(elementcode).toString());
                    System.out.println("�汾��: " + elversionmap.get(elementcode).toString());
                }
            }
        } catch (Exception e) {
            logger.error("��ȡ�汾��ʧ��.");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return elversionmap;
    }

    public PayBasicPaytypeMapper getBasicPaytypeMapper() {
        return basicPaytypeMapper;
    }

    public void setBasicPaytypeMapper(PayBasicPaytypeMapper basicPaytypeMapper) {
        this.basicPaytypeMapper = basicPaytypeMapper;
    }

    public PayBasicBdgagencyMapper getBasicBdgagencyMapper() {
        return basicBdgagencyMapper;
    }

    public void setBasicBdgagencyMapper(PayBasicBdgagencyMapper basicBdgagencyMapper) {
        this.basicBdgagencyMapper = basicBdgagencyMapper;
    }

    public PayBasicBdgmanagedivisionMapper getBasicBdgmanagedivisionMapper() {
        return basicBdgmanagedivisionMapper;
    }

    public void setBasicBdgmanagedivisionMapper(PayBasicBdgmanagedivisionMapper basicBdgmanagedivisionMapper) {
        this.basicBdgmanagedivisionMapper = basicBdgmanagedivisionMapper;
    }

    public PayBasicExpecoMapper getBasicExpecoMapper() {
        return basicExpecoMapper;
    }

    public void setBasicExpecoMapper(PayBasicExpecoMapper basicExpecoMapper) {
        this.basicExpecoMapper = basicExpecoMapper;
    }

    public PayBasicExpfuncMapper getBasicExpfuncMapper() {
        return basicExpfuncMapper;
    }

    public void setBasicExpfuncMapper(PayBasicExpfuncMapper basicExpfuncMapper) {
        this.basicExpfuncMapper = basicExpfuncMapper;
    }

    public PayBasicFuncMapper getBasicFuncMapper() {
        return basicFuncMapper;
    }

    public void setBasicFuncMapper(PayBasicFuncMapper basicFuncMapper) {
        this.basicFuncMapper = basicFuncMapper;
    }

    public PayBasicFundtypeMapper getBasicFundtypeMapper() {
        return basicFundtypeMapper;
    }

    public void setBasicFundtypeMapper(PayBasicFundtypeMapper basicFundtypeMapper) {
        this.basicFundtypeMapper = basicFundtypeMapper;
    }

    public PayBasicIncomesortMapper getBasicIncomesortMapper() {
        return basicIncomesortMapper;
    }

    public void setBasicIncomesortMapper(PayBasicIncomesortMapper basicIncomesortMapper) {
        this.basicIncomesortMapper = basicIncomesortMapper;
    }

    public PayBasicIndsourceMapper getBasicIndsourceMapper() {
        return basicIndsourceMapper;
    }

    public void setBasicIndsourceMapper(PayBasicIndsourceMapper basicIndsourceMapper) {
        this.basicIndsourceMapper = basicIndsourceMapper;
    }

    public PayBasicPaykindMapper getBasicPaykindMapper() {
        return basicPaykindMapper;
    }

    public void setBasicPaykindMapper(PayBasicPaykindMapper basicPaykindMapper) {
        this.basicPaykindMapper = basicPaykindMapper;
    }

    public PayBasicProfundMapper getBasicProfundMapper() {
        return basicProfundMapper;
    }

    public void setBasicProfundMapper(PayBasicProfundMapper basicProfundMapper) {
        this.basicProfundMapper = basicProfundMapper;
    }

    public PayBasicProgramMapper getBasicProgramMapper() {
        return basicProgramMapper;
    }

    public void setBasicProgramMapper(PayBasicProgramMapper basicProgramMapper) {
        this.basicProgramMapper = basicProgramMapper;
    }

    public PayBasicSettlemodeMapper getBasicSettlemodeMapper() {
        return basicSettlemodeMapper;
    }

    public void setBasicSettlemodeMapper(PayBasicSettlemodeMapper basicSettlemodeMapper) {
        this.basicSettlemodeMapper = basicSettlemodeMapper;
    }
}
