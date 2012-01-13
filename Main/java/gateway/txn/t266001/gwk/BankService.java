package gateway.txn.t266001.gwk;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-1-5
 * Time: ÏÂÎç9:38
 * To change this template use File | Settings | File Templates.
 */
// /remoting/service/bankservice
public interface BankService {
    // List rtnlist = service.writeOfficeCard("BANK.CCB", "8015", "2010", "405", cardList);
    List writeOfficeCard(String applicationid, String bankcode, String year, String fanCode, List cardList);
    //                 List rtnlist = service.writeConsumeInfo("BANK.CCB", "8015", "2010", "405", cardList);
    List writeConsumeInfo(String applicationid, String bankcode, String year, String fanCode, List cardList);
    List getOfficeCardStatus(String applicationid, String bankcode, String year, String fanCode, String guid);
}
