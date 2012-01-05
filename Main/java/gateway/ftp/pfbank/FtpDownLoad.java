package gateway.ftp.pfbank;

/**
 * ��Դ�ļ�������Ŀ���ļ�
 *
 * @param host ��������ַ
 * @param username �û�
 * @param password ����
 * @param pathFile ftp��������Դ�ļ�
 * @param sourceFile Ŀ���ļ�
 * @return true or false
 */

import com.enterprisedt.net.ftp.*;
import com.enterprisedt.util.debug.Level;
import com.enterprisedt.util.debug.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FtpDownLoad {

    String host = Config.getString("ftphost");
    String username = Config.getString("ftpusername");
    String password = Config.getString("ftppassword");
    String pathFile = Config.getString("ftppathFile");
    String downFile = "./bankinpufile/";

    public boolean getFile(ArrayList fileList) {

        boolean flag = true;
        //����debug��Ϣ�Ƿ���ʾ
        Logger.setLevel(Level.ALL);
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            //���÷������ĵ�ַ
            ftp.setRemoteHost(host);
            //���ÿ��Է�������·��
            ftp.setControlEncoding("GB2312");
            FTPMessageCollector listener = new FTPMessageCollector();
            ftp.setMessageListener(listener);
            //���ӷ����������е�½
            ftp.connect();
            ftp.login(username, password);
            //����ftp���������ļ��Ĵ���ģʽ
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            //��ȡ�ļ���һ�����Ǳ����ļ������ڶ���λԶ���ļ���
            System.out.println(fileList.size());
            for (int i = 0; i < fileList.size(); i++) {
                String curfilename = (String) fileList.get(i);
                File fileCustomer = new File(pathFile + curfilename);
                String ftpFilename = pathFile + curfilename;
                //�ж��ļ��Ƿ����
                if (ftp.exists(ftpFilename)) {
                    ftp.get(downFile + curfilename, ftpFilename);
                    System.out.println("downFile+curfilename " + pathFile + curfilename);
                }
            }
            String message = listener.getLog();
            System.out.println(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Tools.OutputLogFile(Tools.getDate("yyyy-MM-dd-HH-mm-ss") + " FTP�����ļ��쳣�� from FtpDownLoad " + e.getMessage() + "\n");
        }
        try {
            ftp.quit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        }
        return flag;
    }

    //ɾ���ļ�
    public boolean deleteFile(ArrayList fileList) {

        boolean flag = true;
        //����debug��Ϣ�Ƿ���ʾ
        Logger.setLevel(Level.ALL);
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            //���÷������ĵ�ַ
            ftp.setRemoteHost(host);
            //���ÿ��Է�������·��
            ftp.setControlEncoding("GB2312");
            FTPMessageCollector listener = new FTPMessageCollector();
            ftp.setMessageListener(listener);
            //���ӷ����������е�½
            ftp.connect();
            ftp.login(username, password);
            //����ftp���������ļ��Ĵ���ģʽ
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            //��ȡ�ļ���һ�����Ǳ����ļ������ڶ���λԶ���ļ���
            for (int i = 0; i < fileList.size(); i++) {
                String curfilename = (String) fileList.get(i);
            // ftp.get(downFile+curfilename,pathFile+curfilename);
                ftp.delete(pathFile + curfilename);
            }
            ftp.quit();
            String message = listener.getLog();
            System.out.println(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Tools.OutputLogFile(Tools.getDate("yyyy-MM-dd-HH-mm-ss") + " FTPɾ���ļ��쳣�� " + e.getMessage() + "\n");
        }
        return flag;
    }

    //�õ�Ҫ���ص��ļ��б�
    public ArrayList getDirectoryList() {
        ArrayList retuVal = new ArrayList();
        Logger.setLevel(Level.INFO);
        FileTransferClient ftp = null;
        try {
            ftp = new FileTransferClient();
            ftp.setRemoteHost(host);
            ftp.setUserName(username);
            ftp.setPassword(password);

            // connect to the server
            ftp.connect();
            FTPFile[] files = ftp.directoryList(pathFile);
            System.out.println("files2  " + files.length);
            for (int i = 0; i < files.length; i++) {
                if (!files[i].isDir())
                    retuVal.add(files[i].getName());
                System.out.println("files3  " + files[i].getName());
            }

            // Shut down client
            ftp.disconnect();
        } catch (Exception e) {
            Tools.OutputLogFile(Tools.getDate("yyyy-MM-dd-HH-mm-ss") + " FTP�����ļ��쳣�� " + e.getMessage() + "\n");
            e.printStackTrace();
        }
        return retuVal;
    }

    //�ϴ��ļ�
    public boolean putFile(String[] fileList) {

        boolean flag = true;
        // ����debug��Ϣ�Ƿ���ʾ
        Logger.setLevel(Level.ALL);
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            // ���÷������ĵ�ַ
            ftp.setRemoteHost(host);
            // ���ÿ��Է�������·��
            ftp.setControlEncoding("GB2312");
            FTPMessageCollector listener = new FTPMessageCollector();
            ftp.setMessageListener(listener);
            // ���ӷ����������е�½
            ftp.connect();
            ftp.login(username, password);
            // ����ftp���������ļ��Ĵ���ģʽ
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            // ��ȡ�ļ���һ�����Ǳ����ļ������ڶ���λԶ��FTP�ļ���
            for (int i = 0; i < fileList.length; i++) {
                String curfilename = fileList[i];
                ftp.put("./PLHK/" + curfilename, pathFile + curfilename);
                // ftp.delete(pathFile+curfilename);
            }
            ftp.quit();
            String message = listener.getLog();
            System.out.println(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Tools.OutputLogFile(Tools.getDate("yyyy-MM-dd-HH-mm-ss")
                    + " FTP���ļ��쳣�� from FtpDownLoad " + e.getMessage() + "\n");
        }
        return flag;
    }

}
