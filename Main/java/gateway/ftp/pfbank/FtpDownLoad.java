package gateway.ftp.pfbank;

/**
 * 把源文件拷贝到目标文件
 *
 * @param host 服务器地址
 * @param username 用户
 * @param password 密码
 * @param pathFile ftp服务器的源文件
 * @param sourceFile 目标文件
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
        //设置debug信息是否显示
        Logger.setLevel(Level.ALL);
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            //设置服务器的地址
            ftp.setRemoteHost(host);
            //设置可以访问中文路径
            ftp.setControlEncoding("GB2312");
            FTPMessageCollector listener = new FTPMessageCollector();
            ftp.setMessageListener(listener);
            //连接服务器并进行登陆
            ftp.connect();
            ftp.login(username, password);
            //设置ftp服务器上文件的传输模式
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            //获取文件第一参数是本地文件名，第二个位远程文件名
            System.out.println(fileList.size());
            for (int i = 0; i < fileList.size(); i++) {
                String curfilename = (String) fileList.get(i);
                File fileCustomer = new File(pathFile + curfilename);
                String ftpFilename = pathFile + curfilename;
                //判断文件是否存在
                if (ftp.exists(ftpFilename)) {
                    ftp.get(downFile + curfilename, ftpFilename);
                    System.out.println("downFile+curfilename " + pathFile + curfilename);
                }
            }
            String message = listener.getLog();
            System.out.println(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Tools.OutputLogFile(Tools.getDate("yyyy-MM-dd-HH-mm-ss") + " FTP下载文件异常： from FtpDownLoad " + e.getMessage() + "\n");
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

    //删除文件
    public boolean deleteFile(ArrayList fileList) {

        boolean flag = true;
        //设置debug信息是否显示
        Logger.setLevel(Level.ALL);
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            //设置服务器的地址
            ftp.setRemoteHost(host);
            //设置可以访问中文路径
            ftp.setControlEncoding("GB2312");
            FTPMessageCollector listener = new FTPMessageCollector();
            ftp.setMessageListener(listener);
            //连接服务器并进行登陆
            ftp.connect();
            ftp.login(username, password);
            //设置ftp服务器上文件的传输模式
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            //获取文件第一参数是本地文件名，第二个位远程文件名
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
            Tools.OutputLogFile(Tools.getDate("yyyy-MM-dd-HH-mm-ss") + " FTP删除文件异常： " + e.getMessage() + "\n");
        }
        return flag;
    }

    //得到要下载的文件列表
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
            Tools.OutputLogFile(Tools.getDate("yyyy-MM-dd-HH-mm-ss") + " FTP下载文件异常： " + e.getMessage() + "\n");
            e.printStackTrace();
        }
        return retuVal;
    }

    //上传文件
    public boolean putFile(String[] fileList) {

        boolean flag = true;
        // 设置debug信息是否显示
        Logger.setLevel(Level.ALL);
        FTPClient ftp = null;
        try {
            ftp = new FTPClient();
            // 设置服务器的地址
            ftp.setRemoteHost(host);
            // 设置可以访问中文路径
            ftp.setControlEncoding("GB2312");
            FTPMessageCollector listener = new FTPMessageCollector();
            ftp.setMessageListener(listener);
            // 连接服务器并进行登陆
            ftp.connect();
            ftp.login(username, password);
            // 设置ftp服务器上文件的传输模式
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            // 获取文件第一参数是本地文件名，第二个位远程FTP文件名
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
                    + " FTP上文件异常： from FtpDownLoad " + e.getMessage() + "\n");
        }
        return flag;
    }

}
