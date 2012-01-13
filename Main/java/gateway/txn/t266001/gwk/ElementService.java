package gateway.txn.t266001.gwk;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 12-1-5
 * Time: обнГ9:26
 * To change this template use File | Settings | File Templates.
 */
/*
 serverUrl : /remoting/service/elementservice
 */
public interface ElementService {
    List queryAllElementCode(String applicationid,String elementCode,int year);
    Map queryElementVersion(String applicationid,int year,List elements);
    List syncElementCode(String applicationid,int year, Map element);
}
