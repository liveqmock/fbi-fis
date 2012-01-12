package gateway.ftp.pfbank;
/**
 * 时间：2008年3月
 * 作者：
 * 功能：每隔设定的时间从局域网内下载银联数据，
 * 包括帐户信息，消费明细，换卡信息
 * 下载依据为文件名的最后两位
 * ZH为帐户信息文件
 * HK为换卡信息文件
 * XF为消费信息文件
 */

import fis.service.gwk.ImpExpService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;

@Service
public class IMPBankFileThread implements IMPBankFile {
    private static final Logger logger = LoggerFactory.getLogger(IMPBankFileThread.class);
    int[] CDOPNSIZE = {30, 30, 2, 19, 80, 20, 19, 19, 4, 8, 10, 20, 50, 4};//帐户文件格式
    int[] PURCHSIZE = {8, 6, 4, 4, 2, 2, 8, 11, 11, 11, 6, 21, 1, 12, 10, 4, 8, 15, 40, 3, 12, 6, 2, 1, 65, 50, 4};//消费明细
    int[] CDREPSIZE = {30, 30, 2, 19, 80, 20, 19, 19, 4, 8, 10, 20, 50, 4};//换卡信息
    int[] CHARGESRESPSIZE = {4, 8, 20, 12, 3, 8, 12, 19, 1, 24, 2, 50};//还款结果文件

    @Resource
    private ImpExpService impExpService;

    //导入银行数据
    public void impFile() {

        ArrayList list = new ArrayList();
        logger.info("开始导入总行文件......");
        try {
            FtpDownLoad fd = new FtpDownLoad();

            // 下载的文件列表
            // Ftp远程目录下的文件
            // ArrayList  list = fd.getDirectoryList();
            // 得到远程下载的文件
            Tools tools = new Tools();
            list = tools.getDownFiles();

            //下载文件
            fd.getFile(list);
        } catch (Exception exDL) {
            logger.error("下载文件失败:" + exDL.getMessage());
        }

        //本地文件目录，
        String localpath = Config.getString("downFilePath");
        ArrayList sucFilesList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            String filename = (String) list.get(i);
            impFileForsingle(localpath + filename);

        }     //把下载成功的文件删掉
        //if(sucFilesList!=null||sucFilesList.size()>0){
        //   fd.deleteFile(sucFilesList);
        //}
    }

    public void impFileForsingle(String filepath) {
        filepath = filepath.replace("\\","/");
        GetData getData = new GetData();
        int opCount = 0;
        String filename = filepath.split("/")[filepath.split("/").length-1];
        File afile = new File(filepath);
        if (afile.exists()) {
            logger.info("开始处理文件" + filename + "...");
            //1. 0310-PURCH开头的是  消费明细文件
            if (filename.startsWith("0310-PURCH")) {
                //开始存入数据库
                ArrayList dataList = getData.getDataList(filepath, PURCHSIZE);
                try {
                    opCount = impExpService.importConsumeinfo(dataList, filename);
                } catch (Exception e) {
                    logger.error("存入数据库错误:" + e.getMessage());
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                logger.info("成功导入文件:" + filename + ";消费信息插入条数=" + opCount);


            } //2. 0310-CDOPN开头的是  开卡信息文件
            else if (filename.startsWith("0310-CDOPN")) {
                ArrayList dataList = getData.getDataList(filepath, CDOPNSIZE);
                try {
                    opCount = impExpService.importCardOpen(dataList);
                } catch (Exception e) {
                    logger.error("存入数据库错误:" + e.getMessage());
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                logger.info("文件:" + filename + ";开卡信息插入条数=" + opCount);
            }//3. 0310-CDREP开头的是 换卡文件
            else if (filename.startsWith("0310-CDREP")) {
                ArrayList dataList = getData.getDataList(filepath, CDREPSIZE);
                try {
                    opCount = impExpService.importCardRep(dataList);
                } catch (Exception e) {
                    logger.error("存入数据库错误:" + e.getMessage());
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                logger.info("文件:" + filename + ";卡信息修改条数=" + opCount);

                //4. 0310-CHARGES 开头的是  公务卡还款结果文件 2008-08-20新增
            } else if (filename.startsWith("0310-CHARGESRESP")) {
                ArrayList dataList = getData.getDataList(filepath, CHARGESRESPSIZE);
                try {
                    opCount = impExpService.importPaybackresult(dataList, filename);
                } catch (Exception e) {
                    logger.error("存入数据库错误:" + e.getMessage());
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                logger.info("文件:" + filename + ";还款结果插入条数:" + opCount);
            }
        }
    }


    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("D:/svn-fbifis/src/Main/resources/applicationContext.xml");
//        ctx.
//        new IMPBankFileThread().impFile();
        IMPBankFileThread impBankFileThread = (IMPBankFileThread) ctx.getBean("IMPBankFileThread");
        impBankFileThread.impFile();

    }
}
