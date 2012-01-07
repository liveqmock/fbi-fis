package gateway.ftp.pfbank;
/**
 * ʱ�䣺2008��3��
 * ���ߣ�
 * ���ܣ�ÿ���趨��ʱ��Ӿ������������������ݣ�
 * �����ʻ���Ϣ��������ϸ��������Ϣ
 * ��������Ϊ�ļ����������λ
 * ZHΪ�ʻ���Ϣ�ļ�
 * HKΪ������Ϣ�ļ�
 * XFΪ������Ϣ�ļ�
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
import java.util.ArrayList;

@Service
public class IMPBankFileThread {
    private static final Logger logger = LoggerFactory.getLogger(IMPBankFileThread.class);
    int[] CDOPNSIZE = {30, 30, 2, 19, 80, 20, 19, 19, 4, 8, 10, 20, 50, 4};//�ʻ��ļ���ʽ
    int[] PURCHSIZE = {8, 6, 4, 4, 2, 2, 8, 11, 11, 11, 6, 21, 1, 12, 10, 4, 8, 15, 40, 3, 12, 6, 2, 1, 65, 50, 4};//������ϸ
    int[] CDREPSIZE = {30, 30, 2, 19, 80, 20, 19, 19, 4, 8, 10, 20, 50, 4};//������Ϣ
    int[] CHARGESRESPSIZE = {4, 8, 20, 12, 3, 8, 12, 19, 1, 24, 2, 50};//�������ļ�

    @Resource
    private ImpExpService impExpService;
    //������������
    public void impFile() {
        GetData getData = new GetData();
        FtpDownLoad fd = new FtpDownLoad();

        // ���ص��ļ��б�
        // FtpԶ��Ŀ¼�µ��ļ�
        // ArrayList  list = fd.getDirectoryList();
        // �õ�Զ�����ص��ļ�
        Tools tools = new Tools();
        ArrayList list = tools.getDownFiles();

        //todo �����ļ�
//        fd.getFile(list);
        //�����ļ�Ŀ¼��
        String localpath = Config.getString("downFilePath");
        ArrayList sucFilesList = new ArrayList();
//        ImpExpService impExpService = new ImpExpService();
        int opCount = 0;
        for (int i = 0; i < list.size(); i++) {
            String filename = (String) list.get(i);
            File afile = new File(localpath + filename);
            if (afile.exists()) {
                //1. 0310-PURCH��ͷ����  ������ϸ�ļ�
                if (filename.startsWith("0310-PURCH")) {
                    //��ʼ�������ݿ�
                    ArrayList dataList = getData.getDataList(localpath + filename, PURCHSIZE);
                    opCount = impExpService.importConsumeinfo(dataList,filename);
                    logger.info( "�ļ�:" + filename + ";������Ϣ��������=" + opCount);


                } //2. 0310-CDOPN��ͷ����  ������Ϣ�ļ�
                else if (filename.startsWith("0310-CDOPN")) {
                    ArrayList dataList = getData.getDataList(localpath + filename, CDOPNSIZE);
                    opCount = impExpService.importCardOpen(dataList);
                    logger.info( "�ļ�:" + filename + ";������Ϣ��������=" + opCount);
                }//3. 0310-CDREP��ͷ���� �����ļ�
                else if (filename.startsWith("0310-CDREP")) {
                    ArrayList dataList = getData.getDataList(localpath + filename, CDREPSIZE);
                    opCount = impExpService.importCardRep(dataList);
                    logger.info( "�ļ�:" + filename + ";����Ϣ�޸�����=" + opCount);

                    //4. 0310-CHARGES ��ͷ����  ���񿨻������ļ� 2008-08-20����
                } else if (filename.startsWith("0310-CHARGESRESP")) {
                    ArrayList dataList = getData.getDataList(localpath + filename, CHARGESRESPSIZE);
                    opCount = impExpService.importPaybackresult(dataList,filename);
                    logger.info("�ļ�:" + filename + ";��������������:" + opCount);
                }
            }

        }     //�����سɹ����ļ�ɾ��
        //if(sucFilesList!=null||sucFilesList.size()>0){
        //   fd.deleteFile(sucFilesList);
        //}
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("D:/svn-fbifis/src/Main/resources/applicationContext.xml");
//        ctx.
//        new IMPBankFileThread().impFile();
        IMPBankFileThread impBankFileThread = (IMPBankFileThread)ctx.getBean("IMPBankFileThread");
        impBankFileThread.impFile();
    }
}
