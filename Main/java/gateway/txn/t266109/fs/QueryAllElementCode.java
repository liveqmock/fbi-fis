package gateway.txn.t266109.fs;

import gateway.service.BizInterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-12-20
 * Time: ����3:00
 * ��˰--��ѯ��������
 */
public class QueryAllElementCode extends AbstractFSTxnBizProcessor {

    private static Logger logger = LoggerFactory.getLogger(QueryAllElementCode.class);

    @Override
    public List<Map<String, String>> process(String bizCode, String postCode, List<String> paramList) throws Exception {

        List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
        init(bizCode, postCode, "elementservice", "queryAllElementCode", paramList);
        String rtnDataGaram = client.sendDataUntilRcv(dataGaram, 12);
        logger.info("��************��ʼת�����յ��ı���*************��������:" + rtnDataGaram.substring(62, 66));
        /*
          64��Ϣͷ+4λ��Ӧ��+32�������ݱ���+12�汾��+8������������+��������
        */
        if (rtnDataGaram.substring(62, 66).equalsIgnoreCase("0000")) {
            String dataContent = rtnDataGaram.substring(106);
            logger.info("�������ģ�" + dataContent);

            String dataVersion = rtnDataGaram.substring(98, 110);
            StringTokenizer strTokenizer = new StringTokenizer(dataContent, "\n");
            while (strTokenizer.hasMoreTokens()) {
                HashMap<String, String> itemMap = new HashMap<String, String>();
                String item = strTokenizer.nextToken().trim();
                String[] itemInfos = item.split(",");
                itemMap.put("Code", itemInfos[0]);
                itemMap.put("Name", itemInfos[1]);
                itemMap.put("itemid", itemInfos[2]);
                itemMap.put("Version", dataVersion);
                dataMapList.add(itemMap);
            }
        }else {
            throw new RuntimeException("���ؿձ��Ļ������쳣��������:" + rtnDataGaram.substring(62, 66));
        }
        return dataMapList;
    }
    
    public static void main(String[] args) {
        List<String> paramList = new ArrayList<String>();
        paramList.add("USER");
        paramList.add("PROGRAM");
        paramList.add("USER");
        paramList.add("USER");
        paramList.add("USER");
        try {
            List<Map<String, String>> dataList = new BizInterService().getBizDatas("FS", "266109", "queryAllElementCode",paramList);
            System.out.println("Total ���� : " + dataList.size());
            for(Map<String, String> dataMap : dataList) {
                System.out.println("Code : " + dataMap.get("Code"));
                System.out.println("Name : " + dataMap.get("Name"));
                System.out.println("itemid : " + dataMap.get("itemid"));
                System.out.println("Version : " + dataMap.get("Version"));
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
