package gateway.txn.t266001.gwk;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-1-5
 * Time: 下午9:38
 * To change this template use File | Settings | File Templates.
 */
// /remoting/service/bankservice
public interface BankService {
    // List rtnlist = service.writeOfficeCard("BANK.CCB", "8015", "2010", "405", cardList);
    List writeOfficeCard(String applicationid, String bankcode, String year, String fanCode, List cardList);
    //                 List rtnlist = service.writeConsumeInfo("BANK.CCB", "8015", "2010", "405", cardList);
    //新的接口发送卡信息，增加行政区划编码（admdiv） 2012-12-06
    List writeOfficeCard(String applicationid, String bankcode, String year,String admdiv, String fanCode, List cardList);
    List writeConsumeInfo(String applicationid, String bankcode, String year, String fanCode, List cardList);
    //新的接口发送消费信息，增加行政区划编码（admdiv） 2012-12-06
    List writeConsumeInfo(String applicationid, String bankcode, String year, String admdiv, String fanCode, List cardList);
    List getOfficeCardStatus(String applicationid, String bankcode, String year, String fanCode, String guid);
    //新的接口，根据支付令查询消费信息， 2012-12-06
    List queryVoucherByBillCode(String applicationid, String bankcode, String year, String admdiv, String fanCode,
                                String billtype,String billcode );
}
