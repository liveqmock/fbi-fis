package gateway.txn;

import com.caucho.burlap.client.BurlapProxyFactory;
import gateway.txn.t266001.gwk.BankService;
import gateway.txn.t266001.gwk.CommonQueryService;
import gateway.txn.t266001.gwk.ElementService;
import org.springframework.stereotype.Component;
import pub.platform.advance.utils.PropertyManager;

import java.net.MalformedURLException;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-13
 * Time: 下午4:42
 * To change this template use File | Settings | File Templates.
 */
@Component
public class GwkServiceFactory {
    /*为方便以后添加其它财政局公务卡操作，特修改。2012-12-06  linyong*/
    private static GwkServiceFactory instance = new GwkServiceFactory();
    private static BurlapProxyFactory burlapProxyFactory = new BurlapProxyFactory();

    public static GwkServiceFactory getInstance() {
        if (instance == null) {
            instance = new GwkServiceFactory();
        }
        if (burlapProxyFactory == null) {
            burlapProxyFactory = new BurlapProxyFactory();
        }
        return instance;
    }

    private GwkServiceFactory() {
    }

    public ElementService getElementServiceForArea(String areaCode) throws MalformedURLException {
        String url = PropertyManager.getProperty("fbifis.endpoint.url.gwk." + areaCode + ".elementservice");
        ElementService elementService = (ElementService) burlapProxyFactory.create(ElementService.class, url);
        return elementService;
    }

    public BankService getBankServiceForArea(String areaCode) throws MalformedURLException {
        String url = PropertyManager.getProperty("fbifis.endpoint.url.gwk." + areaCode + ".bankservice");
        BankService bankservice = (BankService) burlapProxyFactory.create(BankService.class, url);
        return bankservice;
    }

    public CommonQueryService getCommonQueryServiceForArea(String areaCode) throws MalformedURLException {
        String url = PropertyManager.getProperty("fbifis.endpoint.url.gwk." + areaCode + ".commonqueryservice");
        CommonQueryService commonQueryService = (CommonQueryService) burlapProxyFactory.create(CommonQueryService.class, url);
        return commonQueryService;
    }
    /*
    @Resource(name = "gwk266001elementService")
    private ElementService elementService266001;
    @Resource(name = "gwk266061elementService")
    private ElementService elementService266061;
    @Resource(name = "gwk266001bankService")
    private BankService bankService;
    @Resource(name = "gwk266001commonQueryService")
    private CommonQueryService commonQueryService;

    public ElementService getElementServiceForArea(String bofcode) {
        if (bofcode.equals("266001")) {
            return elementService266001;
        } else if (bofcode.equals("266061")) {
            return elementService266061;
        }
        throw new IllegalArgumentException("Invalid file type");
    }
    
    public BankService getBankServiceForArea(String bofcode) {
        if (bofcode.equals("266001")) {
            return bankService;
        }
        throw new IllegalArgumentException("Invalid file type");
    }
    
    public CommonQueryService getCommonQueryServiceForArea(String bofcode) {
        if (bofcode.equals("266001")) {
            return commonQueryService;
        }
        throw new IllegalArgumentException("Invalid file type");
    }
    */

}
