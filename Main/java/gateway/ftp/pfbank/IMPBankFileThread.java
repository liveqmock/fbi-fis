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

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.util.ArrayList;


public class IMPBankFileThread implements Job {


    int[] CDOPNSIZE = {30, 30, 2, 19, 80, 20, 19, 19, 4, 8, 10, 20, 50, 4};//帐户文件格式
    int[] PURCHSIZE = {8, 6, 4, 4, 2, 2, 8, 11, 11, 11, 6, 21, 1, 12, 10, 4, 8, 15, 40, 3, 12, 6, 2, 1, 65, 50, 4};//消费明细
    int[] CDREPSIZE = {30, 30, 2, 19, 80, 20, 19, 19, 4, 8, 10, 20, 50, 4};//换卡信息
    int[] CHARGESRESPSIZE = {4, 8, 20, 12, 3, 8, 12, 19, 1, 24, 2, 50};//还款结果文件


    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println(Tools.getDate("yyyy-MM-dd-HH-mm-ss") + " 下载服务启动");

        impFile();
    }


    //导入银行数据
    private void impFile() {
        GetData getData = new GetData();
        FtpDownLoad fd = new FtpDownLoad();

        // 下载的文件列表
        // Ftp远程目录下的文件
        // ArrayList  list = fd.getDirectoryList();
        // 得到远程下载的文件
        Tools tools = new Tools();
        ArrayList list = tools.getDownFiles();

        //下载文件
        fd.getFile(list);
        //本地文件目录，
        String localpath = "./bankinpufile/";
        ArrayList sucFilesList = new ArrayList();

        for (int i = 0; i < list.size(); i++) {
            String filename = (String) list.get(i);
            File afile = new File(localpath + filename);
            if (afile.exists()) {
                //1. 0310-PURCH开头的是  消费明细文件
                if (filename.startsWith("0310-PURCH")) {
                    //开始存入数据库
                    ArrayList SQLlist = getData.getDataList(localpath + filename, PURCHSIZE);
                    //CreateSQL itd=new CreateSQL();
                    //boolean flag=itd.insertMX(SQLlist,filename);
                    //导入成功，则删除远程文件
//				if(flag){
//					sucFilesList.add(filename);
//				
//				}


                    //2. 0310-CDOPN开头的是  开卡信息文件
                } else if (filename.startsWith("0310-CDOPN")) {
                    ArrayList SQLlist = getData.getDataList(localpath + filename, CDOPNSIZE);
                    //CreateSQL itd=new CreateSQL();
                    //boolean flag=itd.insertZH(SQLlist,filename);
                    //if(flag){
                    //		sucFilesList.add(filename);
                    //	}

                }//3. 0310-CDREP开头的是 换卡文件
                else if (filename.startsWith("0310-CDREP")) {
                    ArrayList SQLlist = getData.getDataList(localpath + filename, CDREPSIZE);
                    //CreateSQL itd=new CreateSQL();
                    //boolean flag=itd.insertHK(SQLlist,filename);
//				if(flag){
//					sucFilesList.add(filename);
//				}
                    //4. 0310-CHARGES 开头的是  公务卡还款结果文件 2008-08-20新增
                } else if (filename.startsWith("0310-CHARGESRESP")) {

                    ArrayList SQLlist = getData.getDataList(localpath + filename, CHARGESRESPSIZE);
                    System.out.println(SQLlist);
                    //CreateSQL itd=new CreateSQL();
                    //boolean flag=itd.insertSK(SQLlist,filename);
                }
            }

        }     //把下载成功的文件删掉
        //if(sucFilesList!=null||sucFilesList.size()>0){
        //   fd.deleteFile(sucFilesList);
        //}
    }
}
