package gateway.ftp.pfbank;

import fis.service.gwk.ImpExpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: ����9:48
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ExportHkmx {
    private static final Logger logger = LoggerFactory.getLogger(ExportHkmx.class);
    @Resource
    private ImpExpService impExpService;
    public ArrayList getResults() {
        int cardStrArrlen[] = { 4, 4, 8, 20, 12, 3, 8, 19, 5, 2, 18, 1, 1, 1,
				2, 30, 24 ,50};// ���صı��ĳ��ȵĶ���
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String date=sdf.format(new Date());
        //����״̬status=01��ʼ״̬��������ȡ����
        ArrayList<ArrayList> pay_sumList4 = impExpService.selectPaybackinfos();
        ArrayList  resultList=new ArrayList();
		for (int k = 0; k < pay_sumList4.size(); k++) {
			String carderStr1 = "";
			ArrayList pay_sumList23 = (ArrayList) pay_sumList4.get(k);
			//�����Ҫ��������Ҫ�󲹳�12λ����Ϊ��λ
			Double hkje = Double.parseDouble((String)pay_sumList23.get(4)) * 100;
			System.out.println("hkje:" + hkje);
			 java.text.DecimalFormat   myformat=new   java.text.DecimalFormat("#0");
			System.out.println("hkje(formatted):" + myformat.format(hkje));

			/**********MODIFIED BY ZHANGKQ 20100211****************/
			//String[] t_hkje = hkje.toString().split("\\.");
			//String str_hkje = t_hkje[0];
			String str_hkje = myformat.format(hkje);

			/**********MODIFIED BY ZHANGKQ 20100211 END****************/
			System.out.println("str_hkje:" + str_hkje);
			DisposeStr tmpds = new DisposeStr();
			str_hkje = tmpds.addZero(str_hkje, 12);
			System.out.println("new str_hkje:" + str_hkje);
			pay_sumList23.set(4, str_hkje);
			System.out.println("formatted hkje:" + pay_sumList23.get(4));
			//��ÿ�м�¼��ָ����ʽ���Ȳ��ո�
			carderStr1=weaveResult(pay_sumList23,cardStrArrlen);
			resultList.add(carderStr1);
		}
        logger.info("�������������¼����:" + String.valueOf(resultList.size()));
        return resultList;
    }

    //��ָ����ʽ��֯�����
	public String weaveResult(ArrayList list,int[] len){
		String result="";
		DisposeStr ds = new DisposeStr();
		for(int i=0;i<len.length;i++){
			result+=ds.addSpace(list.get(i).toString().trim(), len[i]);
		}
		return result;
	}

    public String writePLHK(ArrayList messageList) {
		String fileName = null;
		try {
			String path = null;
			if (path == null)
				path = Config.getString("pilianghuankuan");
			File file = new File(path + File.separator
					+ "0310-CHARGES-99"+Config.getString("Bank_Code")+"-" + getDate("yyyyMMdd"));
			fileName = file.getName();
			String message="";
			for (int i = 0; i < messageList.size(); i++) {
				message+=(String)messageList.get(i)+"\n";
			}

			writeFile(file.getAbsolutePath(), message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}
   //������д�ļ���������FTP�ϴ�
	public boolean writeFile(String filename, String inputText)
			throws IOException {
		FileWriter fw = new FileWriter(filename, true);
		fw.write(inputText);
		fw.close();

		FtpDownLoad ftpDownLoad=new FtpDownLoad();
		String path = null;
		if (path == null)
			path = Config.getString("pilianghuankuan");
		File f = new File(path);
		File afile=new File(f.getAbsolutePath() + File.separator
				+ "0310-CHARGES-99"+Config.getString("Bank_Code")+"-" + getDate("yyyyMMdd"));
		String[]   listfilenames = new String[1];
		listfilenames[0]=afile.getName();
		System.out.println("charges file=" + listfilenames.toString());
        //�ļ�ֱ��д��FTPĿ¼����
		// ftpDownLoad.putFile(listfilenames);
		return true;
	}

	public static String getDate(String dateformat) {
		Date myDate = new Date(System.currentTimeMillis());
		SimpleDateFormat sDateformat = new SimpleDateFormat(dateformat);
		return sDateformat.format(myDate).toString();
	}

    //�ļ����ɺ� ����paybackinfo.filesendflag=��ǰ����
    public void updatePaybackinfo() {
        impExpService.updatePaybackinfos();
    }


	public static void main(String[] args){
        ApplicationContext ctx = new FileSystemXmlApplicationContext("D:/svn-fbifis/src2/Main/resources/applicationContext.xml");
        ExportHkmx exportHkmx = (ExportHkmx) ctx.getBean("exportHkmx");
        try {
            ArrayList sa=exportHkmx.getResults();
            exportHkmx.writePLHK(sa);
            exportHkmx.updatePaybackinfo();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

}
