package gateway.ftp.pfbank;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: altiris-qdo
 * Date: 12-1-11
 * Time: обнГ3:37
 * To change this template use File | Settings | File Templates.
 */
public class DoTimeAutoIMPHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "IMPBankFileThread")
    private IMPBankFile impBankFile;

    public void execute() {
        impBankFile.impFile();
    }
}
