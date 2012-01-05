package gateway.txn.t266109.fs;

import gateway.service.BizInterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-12-20
 * Time: 下午3:00
 * 非税--查询基础数据
 */
public class QueryAllElementCode extends AbstractFSTxnBizProcessor {

    private static Logger logger = LoggerFactory.getLogger(QueryAllElementCode.class);

    @Override
    public List<Map<String, String>> process(String bizCode, String postCode, List<String> paramList) throws Exception {

        List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
        init(bizCode, postCode, "elementservice", "queryAllElementCode", paramList);
        String rtnDataGaram = client.sendDataUntilRcv(dataGaram, 12);
        logger.info("【************开始转换接收到的报文*************】返回码:" + rtnDataGaram.substring(62, 66));
        /*
          64消息头+4位响应码+32基本数据编码+12版本号+8基础数据条数+报文正文
        */
        if (rtnDataGaram.substring(62, 66).equalsIgnoreCase("0000")) {
            String dataContent = rtnDataGaram.substring(106);
            logger.info("报文正文：" + dataContent);

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
            throw new RuntimeException("返回空报文或数据异常！返回码:" + rtnDataGaram.substring(62, 66));
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
            System.out.println("Total 笔数 : " + dataList.size());
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
