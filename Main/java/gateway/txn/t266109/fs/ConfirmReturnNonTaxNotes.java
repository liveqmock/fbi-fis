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
 * 非税-退付确认
 */
public class ConfirmReturnNonTaxNotes extends AbstractFSBizProcessor {

    private static Logger logger = LoggerFactory.getLogger(ConfirmReturnNonTaxNotes.class);

    @Override
    public List<Map<String, String>> process(String bizCode, String postCode, List<String> paramList) throws Exception {

        List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
        init(bizCode, postCode, "bankservice", "confirmReturnNonTaxNotes", paramList);
        String rtnDataGaram = client.sendDataUntilRcv(dataGaram, 12);
        logger.info("【************开始转换接收到的报文*************】返回码：" + rtnDataGaram.substring(62, 66));
         /*
            消息头+4位响应码+报文正文
            报文正文为：申请书1号+，+原缴款书编号1+间隔符 +申请书2号+，+原缴款书编号2+…..
             */
        if (rtnDataGaram.substring(62, 66).equalsIgnoreCase("0000")) {
            StringTokenizer strTokenizer = new StringTokenizer(rtnDataGaram.substring(66), "\n");
            String item = null;
            while (strTokenizer.hasMoreTokens()) {
                HashMap<String, String> itemMap = new HashMap<String, String>();
                item = strTokenizer.nextToken().trim();
                String[] itemInfos = item.split(",");
                itemMap.put("REFUNDAPPLYCODE", itemInfos[0]);
                itemMap.put("PAYNOTESCODE", itemInfos[1]);
                dataMapList.add(itemMap);
            }
        } else {
            throw new RuntimeException("返回数据异常！返回码：" + rtnDataGaram.substring(62, 66));
        }
        return dataMapList;
    }

    public static void main(String[] args) {
        List<String> paramList = new ArrayList<String>();
        // REFUNDAPPLYCODE, PAYNOTESCODE  000006,209001
        paramList.add("0000007");
        paramList.add("209001");
        try {
            List<Map<String, String>> dataList = new BizInterService().getBizDatas("FS", "266109", "confirmReturnNonTaxNotes", paramList);
            System.out.println("Total 笔数 : " + dataList.size());
            int i = 0;
            for (Map<String, String> dataMap : dataList) {
                System.out.println("REFUNDAPPLYCODE : " + dataMap.get("REFUNDAPPLYCODE"));
                System.out.println("PAYNOTESCODE : " + dataMap.get("PAYNOTESCODE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}