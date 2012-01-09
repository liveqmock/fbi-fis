package gateway.txn.t266001.gwk;

import gov.mof.fasp.service.BankService;
import gov.mof.fasp.service.CommonQueryService;
import gov.mof.fasp.service.ElementService;
import gov.mof.fasp.service.adapter.client.FaspServiceAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTest {

    private static final Log logger = LogFactory.getLog(MyTest.class);

    public static void main(String argv[]) {

        MyTest test = new MyTest();
        try {
            test.getAllElementInfo();
            //test.queryElementVersion();
            //test.syncElementCode();
            //test.writeOfficeCardInfo();
            // test.writeConsumeInfo();
            //test.testGetOfficeCardStatus();
            // test.testAdapter_queryservice();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public void getAllElementInfo() throws Exception {
        ElementService service = FaspServiceAdapter.getElementService();
        List rtnlist = service.queryAllElementCode("BANK.SPDB", "BDGAGENCY", 2011);
        for (int i = 0; i < rtnlist.size(); i++) {
            Map m1 = (Map) rtnlist.get(i);
            if (i == rtnlist.size() - 1) {
                String version = (String) m1.get("version");
                System.out.println(" ======================= ");
                System.out.println(" version=" + version);
            } else {
                String code = (String) m1.get("code");
                String name = (String) m1.get("name");
                String guid = (String) m1.get("guid");
                String levelno = (String) m1.get("levelno");
                String supercode = (String) m1.get("supercode");
                String isleaf = (String) m1.get("isleaf");
                System.out.println("code=" + code + name);

            }
        }
    }

    public void queryElementVersion() throws Exception {
        ElementService service = FaspServiceAdapter.getElementService();
        List elementCodeList = new ArrayList();
        elementCodeList.add("BDGAGENCY");
        Map rtnMap = service.queryElementVersion("BANK.SPDB", 2011, elementCodeList);
        System.out.println("BDGAGENCY : " + rtnMap.get("BDGAGENCY"));
    }

    public void syncElementCode() {
        ElementService service = FaspServiceAdapter.getElementService();
        try {
            System.out.println("================返回最新版本数据===============");
            HashMap versionMap = new HashMap();
            int version = 72;
            versionMap.put("BDGAGENCY", version);
            List rtnlist = service.syncElementCode("BANK.SPDB", 2011, versionMap);
            Map rtnMap = (Map) rtnlist.get(0);//datas
            System.out.println(" version: " + rtnMap.get("version"));
            List list = (List) rtnMap.get("datas");
            rtnMap = (Map) list.get(0);
            System.out.println((String) rtnMap.get("action") + rtnMap.get("code") + rtnMap.get("name") + rtnMap.get("guid"));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void writeOfficeCardInfo() {
        BankService service = FaspServiceAdapter.getBankService();
        // 4-公务卡信息接入
        System.out.println("==================公务卡信息接入==============");
        List cardList = new ArrayList();
        Map m = new HashMap();
        m.put("ACCOUNT", "6282680005361121");
        m.put("CARDNAME", "沈函泉");
        m.put("BDGAGENCY", "001001");
        m.put("GATHERINGBANKACCTNAME", "张三");
        m.put("GATHERINGBANKNAME", "浦发银行福州路支行");
        m.put("GATHERINGBANKACCTCODE", "0701010400171033");
        m.put("IDNUMBER", "370206198012067641");
        m.put("DIGEST", "公务消费");
        m.put("BANK", "004001"); //?9	开户银行	BANK	NUMBER(16)	财政银行编码
        m.put("CREATEDATE", "2012-01-01"); //
        m.put("STARTDATE", "2012-01-01");
        m.put("ENDDATE", "2019-12-01");
        m.put("ACTION", "0");  //13	数据操作类型	ACTION	VARCHAR2(32)	0-新增 1-修改 2-删除
        cardList.add(m);
        List rtnlist = service.writeOfficeCard("BANK.SPDB", "004", "2012", "405", cardList);
        Map rtnMap = (Map) rtnlist.get(0);
        System.out.println((String)rtnMap.get("sameidnumber") +"---" + rtnMap.get("sameaccount")
                +"--- "+ rtnMap.get("message") + "---" + rtnMap.get("result"));
    }

    public void writeConsumeInfo() {
        BankService service = FaspServiceAdapter.getBankService();

        List cardList = new ArrayList();
        Map m = new HashMap();
        m.put("ID", "201105270000001");
        m.put("ACCOUNT", "6283660015627785");
        m.put("CARDNAME", "沈函泉");
        m.put("BUSIDATE", "20110527");
        m.put("BUSIMONEY", "17.00");
        m.put("BUSINAME", "青岛乐天超市崂山分店");
        m.put("Limitdate", "20110616");
        cardList.add(m);

        List rtnlist = service.writeConsumeInfo("BANK.SPDB", "004001", "2011", "405", cardList);
        for (int i = 0; i < rtnlist.size(); i++) {
            Map m1 = (Map) rtnlist.get(i);
            String result = (String) m1.get("result");
            System.out.println("result : " + result);
            if ("SUCCESS".equalsIgnoreCase(result)) {
                System.out.println(result);
            }

        }
    }

    public void testGetOfficeCardStatus() {
        // 6-公务卡状态更改 004 分行 004001
        BankService service = FaspServiceAdapter.getBankService();
        List rtnlist = service.getOfficeCardStatus("BANK.SPDB", "004", "2011", "405", "");
        System.out.println("===================公务卡状态更改=======================");
        for (int i = 0; i < rtnlist.size(); i++) {
            Map m1 = (Map) rtnlist.get(i);
            String account = (String) m1.get("ACCOUNT");
            System.out.println("ACCOUNT : " + account);
        }
        Map rtnMap = rtnlist.size() > 0 ? (Map) rtnlist.get(0) : null;
        System.out.println((String) rtnMap.get("ACCOUNT") + "--" + rtnMap.get("CARDNAME") + "--" + rtnMap.get("STATUS"));
    }

    /*自财政局获取还款信息 */

    public void testAdapter_queryservice() {
        CommonQueryService service = FaspServiceAdapter.getCommonQueryService();
        Map m = new HashMap();
        m.put("VOUCHERID", "10-016001-000001");
        try {
            List rtnlist = service.getQueryListBySql("BANK.SPDB", "queryConsumeInfo", m, "2011");
            System.out.println(rtnlist.size());
            Map rtnMap = (Map) rtnlist.get(0);
            System.out.println((String) rtnMap.get("VOUCHERID") + rtnMap.get("ACCOUNT") + rtnMap.get("CARDNAME") + rtnMap.get("Amt"));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
