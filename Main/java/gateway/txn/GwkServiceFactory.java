package gateway.txn;

import gateway.txn.t266001.gwk.BankService;
import gateway.txn.t266001.gwk.CommonQueryService;
import gateway.txn.t266001.gwk.ElementService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-13
 * Time: ÏÂÎç4:42
 * To change this template use File | Settings | File Templates.
 */
@Component
public class GwkServiceFactory {
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
}
