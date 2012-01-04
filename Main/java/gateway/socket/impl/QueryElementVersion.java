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
 * Time: 下午3:00
 * To change this template use File | Settings | File Templates.
 */
public class QueryElementVersion extends AbstractBizProcessor {

    private static Logger logger = LoggerFactory.getLogger(QueryElementVersion.class);

    @Override
    public List<Map<String, String>> process(String bizCode, String postCode, List<String> paramList) throws Exception {

        List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
        init(bizCode, postCode, "elementservice", "queryElementVersion", paramList);

        String rtnDataGaram = client.sendDataUntilRcv(dataGaram, 12);
        logger.info("【************开始转换接收到的报文*************】返回码：" + rtnDataGaram.substring(62, 66));
        // 消息头+4位响应码+32基本数据编码+,+版本号
        // 其中，版本号为不足部分，在版本号前补0，比如：100001，其报文中内容为：000000100001。
        // 返回值说明：如果版本号与银行传递版本号相同，则说明此基础数据没有更新

        if (rtnDataGaram.substring(62, 66).equalsIgnoreCase("0000")) {
            HashMap<String, String> itemMap = new HashMap<String, String>();
            itemMap.put(rtnDataGaram.substring(66, 98).trim(), rtnDataGaram.substring(99));
            dataMapList.add(itemMap);
        } else {
            throw new RuntimeException("返回数据异常！");
        }
        return dataMapList;
    }

    public static void main(String[] args) {
        List<String> strList = new ArrayList<String>();
        strList.add("BANK");
        try {
            List<Map<String, String>> dataList = new BizInterService().getBizDatas("FS", "266019", "queryElementVersion", strList);
            System.out.println("Total 笔数 : " + dataList.size());
            for (Map<String, String> dataMap : dataList) {
                System.out.println("版本 : " + dataMap.get(strList.get(0)));
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
