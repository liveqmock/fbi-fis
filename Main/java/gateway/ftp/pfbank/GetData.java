package gateway.ftp.pfbank;


import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


/**
 * ������������ȡ���������ص��ļ�������TXT�ļ�����װSQL���
 * ÿ��ȡһ��Ϊһ����¼���ܶ�ȡ�����������ļ����
 * ��¼�������ļ�Ϊ����
 */
public class GetData {

    private static Logger logger = Logger.getLogger(GetData.class.getName());

    //��ȡ�ļ�������
    public ArrayList getDataList(String fileName, int[] len) {
        Tools log = new Tools();
        FileReader freader = null;
        BufferedReader reader = null;
        DisposeStr ds = new DisposeStr();
        ArrayList totalList = new ArrayList();
        try {
//			String filePathName = "GW980220080208HK.txt";
            File fileCustomer = new File(fileName);
            //�ж��ļ��Ƿ����
            if (!fileCustomer.exists()) {
                log.OutputLogFile(Tools.getDate("yyyy-MM-dd") + "�Ҳ���   " + fileName + "  �ļ�\n", "failureFile");
                return totalList;
            }
            freader = new FileReader(fileCustomer);
            reader = new BufferedReader(freader);
            String rline = reader.readLine();
            int t = 0;
            while (rline != null) {
                ArrayList dirList = new ArrayList();
                //if(rline.getBytes().length<25){

                System.out.println("�ļ���Ǽ�¼�� " + rline + "  ������¼����" + t);
                //����ļ���Ǽ�¼�����ڶ�����¼���������������ֱ�ӷ��ؿ�List
//	        		if(Integer.valueOf(rline)==t){
//	        			return totalList;        			
//	        		}else{
//	        			log.OutputLogFile(Tools.getDate("yyyy-MM-dd") + "�ļ���¼�����ȡ��¼������\n", "failureFile");	
//	        			break;
//	        		}     		
                //}
                //int[] CDOPNSIZE= {30,30,2,19,80,20,19,19,4,8,10,50,4};//�ʻ�
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

    //����������Ϣ
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
            //�ж��ļ��Ƿ����
            if (!fileCustomer.exists()) {
                log.OutputLogFile(Tools.getDate("yyyy-MM-dd") + "�Ҳ���   " + fileName + "  �ļ�\n", "failureFile");
                System.out.println("�Ҳ���" + fileName + "�ļ�");
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
     * �ɳ����ȡ�����ļ�ȡ����ֵ
     *
     * @param args
     */
    public static void main(String[] args) {
        GetData gd = new GetData();
        int[] CHARGESRESPSIZE = {4, 8, 20, 12, 3, 8, 12, 19, 1, 24, 2, 50};

        int[] CDOPNSIZE = {30, 30, 2, 19, 80, 20, 19, 19, 4, 8, 10, 20, 50, 4};//�ʻ��ļ���ʽ
        int[] PURCHSIZE = {8, 6, 4, 4, 2, 2, 8, 11, 11, 11, 6, 21, 1, 12, 10, 4, 8, 15, 40, 3, 12, 6, 2, 1, 65, 50, 4};//
        ArrayList list = gd.getDataList(Config.getString("downFilePath") + "0310-CDOPN-1000-20080831", CDOPNSIZE);
        //CreateSQL itd=new CreateSQL();
        //itd.insertZH(list,"0310-CDOPN-1000-20080831");

    }

}

