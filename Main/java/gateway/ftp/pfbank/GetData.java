package gateway.ftp.pfbank;


import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


/**
 * 本类是用来读取从银行下载的文件，解析TXT文件，组装SQL语句
 * 每读取一行为一条记录，总读取行数若等于文件标记
 * 记录数，则文件为正常
 */
public class GetData {

    private static Logger logger = Logger.getLogger(GetData.class.getName());

    //读取文件并解析
    public ArrayList getDataList(String fileName, int[] len) {
        Tools log = new Tools();
        FileReader freader = null;
        BufferedReader reader = null;
        DisposeStr ds = new DisposeStr();
        ArrayList totalList = new ArrayList();
        try {
//			String filePathName = "GW980220080208HK.txt";
            File fileCustomer = new File(fileName);
            //判断文件是否存在
            if (!fileCustomer.exists()) {
                log.OutputLogFile(Tools.getDate("yyyy-MM-dd") + "找不到   " + fileName + "  文件\n", "failureFile");
                return totalList;
            }
            freader = new FileReader(fileCustomer);
            reader = new BufferedReader(freader);
            String rline = reader.readLine();
            int t = 0;
            while (rline != null) {
                ArrayList dirList = new ArrayList();
                //if(rline.getBytes().length<25){

                System.out.println("文件标记记录数 " + rline + "  读到记录数：" + t);
                //如果文件标记记录数等于读到记录数，则继续，否则直接返回空List
//	        		if(Integer.valueOf(rline)==t){
//	        			return totalList;        			
//	        		}else{
//	        			log.OutputLogFile(Tools.getDate("yyyy-MM-dd") + "文件记录数与读取记录数不符\n", "failureFile");	
//	        			break;
//	        		}     		
                //}
                //int[] CDOPNSIZE= {30,30,2,19,80,20,19,19,4,8,10,50,4};//帐户
                //int[] CDOPNSIZE= {8,6,4,2,2,8,11,11,11,6,21,12,10,4,8,15,40,3,12,6,2,1,50};
                dirList = ds.PaseStr(rline, len);
                totalList.add(dirList);
                rline = reader.readLine();
                t++;
            }
        } catch (Exception ex) {
//			/tranlog.println(ex, ex.getMessage());
            logger.error(ex);
        }
        return totalList;
    }

    //公务卡上账信息
    public ArrayList getCacheDataList(String fileName) {

        System.out.println("fileName" + fileName);
        Tools log = new Tools();
        FileReader freader = null;
        BufferedReader reader = null;
        DisposeStr ds = new DisposeStr();
        ArrayList totalList = new ArrayList();
        try {
//			String filePathName = "DFAIL.TXT";
            File fileCustomer = new File(fileName);
            //判断文件是否存在
            if (!fileCustomer.exists()) {
                log.OutputLogFile(Tools.getDate("yyyy-MM-dd") + "找不到   " + fileName + "  文件\n", "failureFile");
                System.out.println("找不到" + fileName + "文件");
                return null;
            }
            freader = new FileReader(fileCustomer);
            reader = new BufferedReader(freader);
            String rline = reader.readLine();


            ArrayList dirList = new ArrayList();
            while (rline != null) {

                if ("---------------------------".equals(rline)) {
                    rline = reader.readLine();
                    break;
                } else {
                    dirList.add(rline.substring(7));
                }
                rline = reader.readLine();

            }
//	        System.out.println(dirList);
            while (rline != null) {
                ArrayList allList = new ArrayList();
                for (int i = 0; i < dirList.size(); i++) {
                    allList.add(dirList.get(i));
//	        		 System.out.println(msList.get(i));
                }
                ArrayList msList = ds.disposeAllStr(rline);
//	        	 System.out.println(msList);
                for (int i = 0; i < msList.size(); i++) {
                    allList.add(msList.get(i));
//	        		 System.out.println(msList.get(i));
                }
                System.out.println("allList: " + allList);
                totalList.add(allList);
                rline = reader.readLine();
            }


        } catch (Exception ex) {
//			/tranlog.println(ex, ex.getMessage());
            logger.error(ex);
        }
        return totalList;
    }

    /**
     * 由程序读取配置文件取得其值
     *
     * @param args
     */
    public static void main(String[] args) {
        GetData gd = new GetData();
        int[] CHARGESRESPSIZE = {4, 8, 20, 12, 3, 8, 12, 19, 1, 24, 2, 50};

        int[] CDOPNSIZE = {30, 30, 2, 19, 80, 20, 19, 19, 4, 8, 10, 20, 50, 4};//帐户文件格式
        int[] PURCHSIZE = {8, 6, 4, 4, 2, 2, 8, 11, 11, 11, 6, 21, 1, 12, 10, 4, 8, 15, 40, 3, 12, 6, 2, 1, 65, 50, 4};//
        ArrayList list = gd.getDataList(Config.getString("downFilePath") + "0310-CDOPN-1000-20080831", CDOPNSIZE);
        //CreateSQL itd=new CreateSQL();
        //itd.insertZH(list,"0310-CDOPN-1000-20080831");

    }

}

