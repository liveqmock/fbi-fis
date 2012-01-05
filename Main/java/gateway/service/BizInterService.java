package gateway.service;

import gateway.txn.AbstractTxnProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-12-20
 * Time: ÏÂÎç2:28
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BizInterService {

    // private static List<String> BIZZ_CODES = Arrays.asList(PropertyManager.getProperty("fbifis.business.codes").split(","));
    // private static List<String> POST_CODES = Arrays.asList(PropertyManager.getProperty("fbifis.endpoint.codes").split(","));

    public List<Map<String, String>> getBizDatas(String bizCode, String postCode, String bizName, List<String> paramList)
            throws Exception {

        bizName = "gateway.txn.t" + postCode + "." + bizCode.toLowerCase()
                + "." + bizName.substring(0, 1).toUpperCase() + bizName.substring(1, bizName.length());
        Class bizClass = Class.forName(bizName);
        AbstractTxnProcessor txnProcessor = (AbstractTxnProcessor) bizClass.newInstance();
        return txnProcessor.process(bizCode, postCode, paramList);
    }

}
