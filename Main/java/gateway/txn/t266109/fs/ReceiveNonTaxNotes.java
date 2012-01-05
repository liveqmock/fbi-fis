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
public class ReceiveNonTaxNotes extends AbstractFSBizProcessor {

    private static Logger logger = LoggerFactory.getLogger(ReceiveNonTaxNotes.class);

    @Override
    public List<Map<String, String>> process(String bizCode, String postCode, List<String> paramList) throws Exception {

        List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
        init(bizCode, postCode, "bankservice", "receiveNonTaxNotes", paramList);
        String rtnDataGaram = client.sendDataUntilRcv(dataGaram, 12);
        logger.info("【************开始转换接收到的报文*************】");
        /*
        消息头+4位响应码+报文正文
        报文正文为：缴款书1号+ 间隔符+缴款书2号+…..
        */
        if (rtnDataGaram.substring(62, 66).equalsIgnoreCase("0000")) {
            StringTokenizer strTokenizer = new StringTokenizer(rtnDataGaram.substring(66), "\n");
            String item = null;
            int index = 0;
            while (strTokenizer.hasMoreTokens()) {
                HashMap<String, String> itemMap = new HashMap<String, String>();
                item = strTokenizer.nextToken().trim();
                itemMap.put("NonTaxNote" + index++, item);
                dataMapList.add(itemMap);
            }
        } else {
            throw new RuntimeException("返回数据异常！返回码：" + rtnDataGaram.substring(62, 66));
        }
        return dataMapList;
    }

    public static void main(String[] args) {
        List<String> paramList = new ArrayList<String>();
        // PAYNOTESCODE，AGENTBANK，RECMETHOD，NOTESCODE，BANKRECDATE，NOTESKIND
        paramList.add("000000043");
        paramList.add("8129");
        paramList.add("99");
        paramList.add("150000000005");
        paramList.add("20111228");
        paramList.add("1");
        try {
            List<Map<String, String>> dataList = new BizInterService().getBizDatas("FS", "266019", "receiveNonTaxNotes", paramList);
            System.out.println("Total 笔数 : " + dataList.size());
            int i = 0;
            for (Map<String, String> dataMap : dataList) {
                System.out.println("NonTaxNote : " + dataMap.get("NonTaxNote" + i++));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}