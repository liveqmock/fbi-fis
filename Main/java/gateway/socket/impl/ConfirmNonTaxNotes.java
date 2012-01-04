package gateway.socket.impl;

import gateway.AbstractBizProcessor;
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
public class ConfirmNonTaxNotes extends AbstractBizProcessor {

    private static Logger logger = LoggerFactory.getLogger(ConfirmNonTaxNotes.class);

    @Override
    public List<Map<String, String>> process(String bizCode, String postCode, List<String> paramList) throws Exception {

        List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
        init(bizCode, postCode, "bankservice", "confirmNonTaxNotes", paramList);
        String rtnDataGaram = client.sendDataUntilRcv(dataGaram, 12);
        logger.info("��************��ʼת�����յ��ı���*************��");
        /*
        ��Ϣͷ+4λ��Ӧ��+��������
        ��������Ϊ���ɿ���1��+�����+�ɿ���2��+��..
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
            throw new RuntimeException("���������쳣��");
        }
        return dataMapList;
    }

    public static void main(String[] args) {
        List<String> paramList = new ArrayList<String>();
        // PAYNOTESCODE,NOTESCODE,BANKACCTDATE,RECFEEFLAG
        paramList.add("000000061");
        paramList.add("150000000006");
        paramList.add("20111229");
        paramList.add("1");
        try {
            List<Map<String, String>> dataList = new BizInterService().getBizDatas("FS", "266019", "confirmNonTaxNotes", paramList);
            System.out.println("Total ���� : " + dataList.size());
            int i = 0;
            for (Map<String, String> dataMap : dataList) {
                System.out.println("NonTaxNote : " + dataMap.get("NonTaxNote" + i++));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}