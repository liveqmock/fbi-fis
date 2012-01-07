package gateway.ftp.pfbank;

import fis.service.fs.PaymentService;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-7
 * Time: ÏÂÎç9:25
 * To change this template use File | Settings | File Templates.
 */
public class testmain {
    @Resource
    private testmain testmain;
    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("D:/svn-fbifis/src/Main/resources/applicationContext.xml");
//        new IMPBankFileThread().impFile();
//        paymentService.selectPayinfoToact("266109");
//        gateway.ftp.pfbank.testmain.main();
    }

}
