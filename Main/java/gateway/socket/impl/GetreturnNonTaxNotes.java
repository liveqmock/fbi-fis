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
public class GetreturnNonTaxNotes extends AbstractBizProcessor {

    private static Logger logger = LoggerFactory.getLogger(GetreturnNonTaxNotes.class);

    @Override
    public List<Map<String, String>> process(String bizCode, String postCode, List<String> paramList) throws Exception {

        List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
        init(bizCode, postCode, "bankservice", "getreturnNonTaxNotes", paramList);
        String rtnDataGaram = client.sendDataUntilRcv(dataGaram);
        logger.info("��************��ʼת�����յ��ı���*************��");
        /*
        ��Ϣͷ+4λ��Ӧ��+�˷���Ŀ��ϸ����8λ+��������
        ��һ������Ϊ�ɿ���ĸ����ֶΣ�BILLID, REFUNDAPPLYCODE, PAYNOTESCODE, PERFORMDEPT,
        CREATEUSER,RECUSERNAME,RECUSERBANK,RECUSERBANKACCOUNT,REFUNDREASON,AUDITOR,
        AUDITAMT,CREATETIME,TOTALAMT,CREATER,AGENTBANK,PRINTTAG
        */
        if (rtnDataGaram.substring(62, 66).equalsIgnoreCase("0000")) {
            StringTokenizer strTokenizer = new StringTokenizer(rtnDataGaram.substring(74), "\n");
            String item = null;
            while (strTokenizer.hasMoreTokens()) {
                HashMap<String, String> itemMap = new HashMap<String, String>();
                item = strTokenizer.nextToken().trim();
                String[] itemInfos = item.split(",");
                itemMap.put("BILLID", itemInfos[0]);
                itemMap.put("REFUNDAPPLYCODE", itemInfos[1]);
                itemMap.put("PAYNOTESCODE", itemInfos[2]);
                itemMap.put("PERFORMDEPT", itemInfos[3]);

                itemMap.put("CREATEUSER", itemInfos[4]);
                itemMap.put("RECUSERNAME", itemInfos[5]);
                itemMap.put("RECUSERBANK", itemInfos[6]);
                itemMap.put("RECUSERBANKACCOUNT", itemInfos[7]);
                itemMap.put("REFUNDREASON", itemInfos[8]);
                itemMap.put("AUDITOR", itemInfos[9]);
                /*
                AUDITAMT,CREATETIME,TOTALAMT,CREATER,AGENTBANK,PRINTTAG
                */
                itemMap.put("AUDITAMT", itemInfos[10]);
                itemMap.put("CREATETIME", itemInfos[11]);
                itemMap.put("TOTALAMT", itemInfos[12]);
                itemMap.put("CREATER", itemInfos[13]);
                itemMap.put("AGENTBANK", itemInfos[14]);
                // TODO ��һ���ֶ�  ���� ��Ҫ��������
                //itemMap.put("PRINTTAG", itemInfos[15]);
                dataMapList.add(itemMap);
            }
        } else {
            throw new RuntimeException("���������쳣�������룺" + rtnDataGaram.substring(62, 66));
        }
        return dataMapList;
    }

    public static void main(String[] args) {
        List<String> paramList = new ArrayList<String>();
        // ��������1+��+ִ�յ�λ����1
        paramList.add("000006");
        paramList.add("209001");
        try {
            List<Map<String, String>> dataList = new BizInterService().getBizDatas("FS", "266019", "getreturnNonTaxNotes", paramList);
            System.out.println("Total ���� : " + dataList.size());
            int i = 0;
            for (Map<String, String> dataMap : dataList) {
                System.out.println("BILLID:" + dataMap.get("BILLID"));
                System.out.println("REFUNDAPPLYCODE : " + dataMap.get("REFUNDAPPLYCODE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}