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

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.util.ArrayList;


public class IMPBankFileThread implements Job {


    int[] CDOPNSIZE = {30, 30, 2, 19, 80, 20, 19, 19, 4, 8, 10, 20, 50, 4};//�ʻ��ļ���ʽ
    int[] PURCHSIZE = {8, 6, 4, 4, 2, 2, 8, 11, 11, 11, 6, 21, 1, 12, 10, 4, 8, 15, 40, 3, 12, 6, 2, 1, 65, 50, 4};//������ϸ
    int[] CDREPSIZE = {30, 30, 2, 19, 80, 20, 19, 19, 4, 8, 10, 20, 50, 4};//������Ϣ
    int[] CHARGESRESPSIZE = {4, 8, 20, 12, 3, 8, 12, 19, 1, 24, 2, 50};//�������ļ�


    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println(Tools.getDate("yyyy-MM-dd-HH-mm-ss") + " ���ط�������");

        impFile();
    }


    //������������
    private void impFile() {
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

        for (int i = 0; i < list.size(); i++) {
            String filename = (String) list.get(i);
            File afile = new File(localpath + filename);
            if (afile.exists()) {
                //1. 0310-PURCH��ͷ����  ������ϸ�ļ�
                if (filename.startsWith("0310-PURCH")) {
                    //��ʼ�������ݿ�
                    ArrayList SQLlist = getData.getDataList(localpath + filename, PURCHSIZE);
                    String aa = "22";
//                    CreateSQL itd=new CreateSQL();
                    //boolean flag=itd.insertMX(SQLlist,filename);
                    //����ɹ�����ɾ��Զ���ļ�
//				if(flag){
//					sucFilesList.add(filename);
//				
//				}


                    //2. 0310-CDOPN��ͷ����  ������Ϣ�ļ�
                } else if (filename.startsWith("0310-CDOPN")) {
                    ArrayList SQLlist = getData.getDataList(localpath + filename, CDOPNSIZE);
                    String aa = "22";
                    //CreateSQL itd=new CreateSQL();
                    //boolean flag=itd.insertZH(SQLlist,filename);
                    //if(flag){
                    //		sucFilesList.add(filename);
                    //	}

                }//3. 0310-CDREP��ͷ���� �����ļ�
                else if (filename.startsWith("0310-CDREP")) {
                    ArrayList SQLlist = getData.getDataList(localpath + filename, CDREPSIZE);
                    String aa = "22";
                    //CreateSQL itd=new CreateSQL();
                    //boolean flag=itd.insertHK(SQLlist,filename);
//				if(flag){
//					sucFilesList.add(filename);
//				}
                    //4. 0310-CHARGES ��ͷ����  ���񿨻������ļ� 2008-08-20����
                } else if (filename.startsWith("0310-CHARGESRESP")) {

                    ArrayList SQLlist = getData.getDataList(localpath + filename, CHARGESRESPSIZE);
                    System.out.println(SQLlist);
                    //CreateSQL itd=new CreateSQL();
                    //boolean flag=itd.insertSK(SQLlist,filename);
                }
            }

        }     //�����سɹ����ļ�ɾ��
        //if(sucFilesList!=null||sucFilesList.size()>0){
        //   fd.deleteFile(sucFilesList);
        //}
    }

    public static void main(String[] args) {
        new IMPBankFileThread().impFile();
    }
}
