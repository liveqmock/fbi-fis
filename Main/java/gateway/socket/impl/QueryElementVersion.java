package gateway.socket.impl;

import gateway.AbstractBizProcessor;
import gateway.service.BizInterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-12-20
 * Time: ����3:00
 * To change this template use File | Settings | File Templates.
 */
public class QueryElementVersion extends AbstractBizProcessor {

    private static Logger logger = LoggerFactory.getLogger(QueryElementVersion.class);

    @Override
    public List<Map<String, String>> process(String bizCode, String postCode, List<String> paramList) throws Exception {

        List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
        init(bizCode, postCode, "elementservice", "queryElementVersion", paramList);

        String rtnDataGaram = client.sendDataUntilRcv(dataGaram, 12);
        logger.info("��************��ʼת�����յ��ı���*************�������룺" + rtnDataGaram.substring(62, 66));
        // ��Ϣͷ+4λ��Ӧ��+32�������ݱ���+,+�汾��
        // ���У��汾��Ϊ���㲿�֣��ڰ汾��ǰ��0�����磺100001���䱨��������Ϊ��000000100001��
        // ����ֵ˵��������汾�������д��ݰ汾����ͬ����˵���˻�������û�и���

        if (rtnDataGaram.substring(62, 66).equalsIgnoreCase("0000")) {
            HashMap<String, String> itemMap = new HashMap<String, String>();
            itemMap.put(rtnDataGaram.substring(66, 98).trim(), rtnDataGaram.substring(99));
            dataMapList.add(itemMap);
        } else {
            throw new RuntimeException("���������쳣��");
        }
        return dataMapList;
    }

    public static void main(String[] args) {
        List<String> strList = new ArrayList<String>();
        strList.add("BANK");
        try {
            List<Map<String, String>> dataList = new BizInterService().getBizDatas("FS", "266019", "queryElementVersion", strList);
            System.out.println("Total ���� : " + dataList.size());
            for (Map<String, String> dataMap : dataList) {
                System.out.println("�汾 : " + dataMap.get(strList.get(0)));
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
