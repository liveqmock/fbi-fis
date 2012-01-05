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
public class SyncElementCode extends AbstractFSBizProcessor {

    private static Logger logger = LoggerFactory.getLogger(SyncElementCode.class);

    @Override
    public List<Map<String, String>> process(String bizCode, String postCode, List<String> paramList) throws Exception {

        List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
        init(bizCode, postCode, "elementservice", "syncElementCode", paramList);
        String rtnDataGaram = client.sendDataUntilRcv(dataGaram, 12);
        logger.info("��************��ʼת�����յ��ı���*************�������룺" + rtnDataGaram.substring(62, 66));
        /*
        ��Ϣͷ+4λ��Ӧ��+32�������ݱ���+12�汾��+8������������+��������
        ��һ������Ԫ�ر䶯����+��+��һ������Ԫ�ر���+��+��һ������Ԫ������+��+��һ������Ԫ��itemid
        Ԫ�ر䶯���ͣ�0-���� 1-�޸� 2-ɾ��
        */
        if (rtnDataGaram.substring(62, 66).equalsIgnoreCase("0000")) {
            StringTokenizer strTokenizer = new StringTokenizer(rtnDataGaram.substring(118), "\n");
            String item = null;
            while (strTokenizer.hasMoreTokens()) {
                HashMap<String, String> itemMap = new HashMap<String, String>();
                item = strTokenizer.nextToken().trim();
                String[] itemInfos = item.split(",");
                itemMap.put("updateFlag", itemInfos[0]);
                itemMap.put("Code", itemInfos[1]);
                itemMap.put("Name", itemInfos[2]);
                itemMap.put("itemid", itemInfos[3]);
                dataMapList.add(itemMap);
            }
        }else {
            throw new RuntimeException("���������쳣��");
        }
        return dataMapList;
    }
    
    public static void main(String[] args) {
        List<String> paramList = new ArrayList<String>();
        paramList.add("BANK");
        try {
            List<Map<String, String>> dataList = new BizInterService().getBizDatas("FS", "266019", "syncElementCode",paramList);
            System.out.println("Total ���� : " + dataList.size());
            for(Map<String, String> dataMap : dataList) {
                System.out.println("UpdateFlag : " + dataMap.get("updateFlag"));
                System.out.println("Code : " + dataMap.get("Code"));
                System.out.println("Name : " + dataMap.get("Name"));
                System.out.println("itemid : " + dataMap.get("itemid"));
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
