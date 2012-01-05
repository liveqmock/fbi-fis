package gateway.txn.t266109.fs;

import fis.common.BeanCopy;
import fis.repository.model.FsPaymentinfo;
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
public class QueryNonTaxNotes extends AbstractFSBizProcessor {

    private static Logger logger = LoggerFactory.getLogger(QueryNonTaxNotes.class);

    @Override
    public List<Map<String, String>> process(String bizCode, String postCode, List<String> paramList) throws Exception {

        List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
        init(bizCode, postCode, "bankservice", "queryNonTaxNotes", paramList);
        String rtnDataGaram = client.sendDataUntilRcv(dataGaram, 12);
        logger.info("【************开始转换接收到的报文*************】返回码：" + rtnDataGaram.substring(62, 66));
        /*
        消息头+4位响应码+缴款书项目数8位+报文正文
        */
        if (rtnDataGaram.substring(62, 66).equalsIgnoreCase("0000")) {
            StringTokenizer strTokenizer = new StringTokenizer(rtnDataGaram.substring(74), "\n");
            String item = null;
            /*
            缴款书的各个字段，BILLID，PAYNOTESCODE，TOTALAMT，PRINTDATE，
            PAYER，AGENTBANK，PAYERDEPT，CHECKCODE，RECMETHOD，REMARK，
            CREATETIME，CREATER，PAYERBANK，PAYERBANKACCT，PERFORMDEPT，
            PAYMETHOD，NOTESCODE，BANKRECDATE，BANKACCTDATE，NOTESKIND，
            ISPREAUDIT，RECFEEFLAG，PAYFEEMETHOD，PROGRAM，UNITS，AMOUNT，
            STANDARD，AMT
             */
            while (strTokenizer.hasMoreTokens()) {
                HashMap<String, String> itemMap = new HashMap<String, String>();
                item = strTokenizer.nextToken().trim();
                String[] itemInfos = item.split(",");
                itemMap.put("BILLID", itemInfos[0]);
                itemMap.put("PAYNOTESCODE", itemInfos[1]);
                itemMap.put("TOTALAMT", itemInfos[2]);
                itemMap.put("PRINTDATE", itemInfos[3]);

                itemMap.put("PAYER", itemInfos[4]);
                itemMap.put("AGENTBANK", itemInfos[5]);
                itemMap.put("PAYERDEPT", itemInfos[6]);
                itemMap.put("CHECKCODE", itemInfos[7]);
                itemMap.put("RECMETHOD", itemInfos[8]);
                itemMap.put("REMARK", itemInfos[9]);

                itemMap.put("CREATETIME", itemInfos[10]);
                itemMap.put("CREATER", itemInfos[11]);
                itemMap.put("PAYERBANK", itemInfos[12]);
                itemMap.put("PAYERBANKACCT", itemInfos[13]);
                itemMap.put("PERFORMDEPT", itemInfos[14]);

                itemMap.put("PAYMETHOD", itemInfos[15]);
                itemMap.put("NOTESCODE", itemInfos[16]);
                itemMap.put("BANKRECDATE", itemInfos[17]);
                itemMap.put("BANKACCTDATE", itemInfos[18]);
                itemMap.put("NOTESKIND", itemInfos[19]);

                itemMap.put("ISPREAUDIT", itemInfos[20]);
                itemMap.put("RECFEEFLAG", itemInfos[21]);

                itemMap.put("PAYFEEMETHOD", itemInfos[22]);
                itemMap.put("PROGRAM", itemInfos[23]);
                itemMap.put("UNITS", itemInfos[24]);
                itemMap.put("AMOUNT", itemInfos[25]);
                itemMap.put("STANDARD", itemInfos[26]);
                itemMap.put("AMT", itemInfos[27]);
                dataMapList.add(itemMap);
            }
        } else {
            throw new RuntimeException("返回数据异常！");
        }
        return dataMapList;
    }

    public static void main(String[] args) {
        List<String> paramList = new ArrayList<String>();
        paramList.add("150000000005");
        paramList.add("DZXL5R");
        try {
            List<Map<String, String>> dataList = new BizInterService().getBizDatas("FS", "266019", "queryNonTaxNotes", paramList);
            System.out.println("Total 笔数 : " + dataList.size());
            for (Map<String, String> dataMap : dataList) {
                System.out.println("BILLID : " + dataMap.get("BILLID"));
                System.out.println("PAYNOTESCODE : " + dataMap.get("PAYNOTESCODE"));
                //        // PAYNOTESCODE，AGENTBANK，RECMETHOD，NOTESCODE，BANKRECDATE，NOTESKIND
                System.out.println("PAYNOTESCODE : " + dataMap.get("PAYNOTESCODE"));
                System.out.println("AGENTBANK : " + dataMap.get("AGENTBANK"));
                System.out.println("RECMETHOD : " + dataMap.get("RECMETHOD"));
                System.out.println("BANKRECDATE : " + dataMap.get("BANKRECDATE"));
                System.out.println("NOTESKIND : " + dataMap.get("NOTESKIND"));
                System.out.println("NOTESCODE : " + dataMap.get("NOTESCODE"));
                FsPaymentinfo fsPaymentinfo = (FsPaymentinfo) BeanCopy.copyObject("fis.repository.model.FsPaymentinfo", dataMap);
                String sss = "sd";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}