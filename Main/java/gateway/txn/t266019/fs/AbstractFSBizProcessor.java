package gateway.txn.t266019.fs;

import gateway.AbstractBizProcessor;
import gateway.socket.client.impl.XSocketBlockClient;
import pub.platform.advance.utils.PropertyManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-12-20
 * Time: 下午2:50
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFSBizProcessor extends AbstractBizProcessor {
    protected static String BANK_CODE = PropertyManager.getProperty("fbifis.sys.bank.code");
    protected static long TIME_OUT = PropertyManager.getLongProperty("fbifis.xsocket.timeout");

    protected String ENDPOINT_IP;
    protected int ENDPOINT_PORT;
    protected String dataGaram;           // 报文
    protected XSocketBlockClient client;

    // 初始化参数
    protected void init(String bizCode, String postCode, String serviceName, String methodName, List<String> paramList) throws Exception {
        ENDPOINT_IP = PropertyManager.getProperty("fbifis.endpoint.ip." + bizCode.toLowerCase() + "." + postCode);
        ENDPOINT_PORT = PropertyManager.getIntProperty("fbifis.endpoint.port." + bizCode.toLowerCase() + "." + postCode);
        client = new XSocketBlockClient(ENDPOINT_IP, ENDPOINT_PORT, TIME_OUT);
        StringBuilder dataBuilder = new StringBuilder(BANK_CODE);
        dataBuilder.append("@").append(new SimpleDateFormat("yyyy").format(new Date())).append("@");
        for (String param : paramList) {
            dataBuilder.append(param).append(",");
        }
        dataBuilder.deleteCharAt(dataBuilder.length() - 1);
        int totalLength = 64 + dataBuilder.length();
        // 报文初始化
        dataGaram = new StringBuilder().append(append0BeforeStr(12, String.valueOf(totalLength)))
                .append(appendSpaceAfterStr(32, serviceName))
                .append(appendSpaceAfterStr(32, methodName))
                .append(dataBuilder).toString();
    }

    private String append0BeforeStr(int totalLength, String str) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < totalLength - str.length(); i++) {
            strBuilder.append("0");
        }
        strBuilder.append(str);
        return strBuilder.toString();
    }

    private String appendSpaceAfterStr(int totalLength, String str) {
        StringBuilder strBuilder = new StringBuilder(str);
        for (int i = 0; i < totalLength - str.length(); i++) {
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }
}
