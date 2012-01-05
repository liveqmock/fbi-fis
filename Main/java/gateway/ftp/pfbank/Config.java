package gateway.ftp.pfbank;


import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 从配置文件中读取数据的类
 *
 * @author TonyDong
 */

public class Config {
    private static Logger logger = Logger.getLogger(Config.class.getName());
    private static ResourceBundle resources = loadProperties("Configure");

    public static String getString(String key) {
        if (key == null)
            return null;
        try {
            return resources.getString(key).trim();
        } catch (Exception exception) {
            Tools.OutputLogFile("\n" + Tools.getDate("yyyy-MM-dd-HH-mm-ss") + "读取配置文件出错 from Config line28 信息： \n" + exception.getMessage());
            logger.error("failed to getString", exception);
            return null;
        }
    }

    private static ResourceBundle loadProperties(String file) {
        return ResourceBundle.getBundle(file, Locale.getDefault());
    }

    public static void main(String[] args) {
       // PropertyConfigurator.configure("conf/log4j.properties");
        System.out.println("CZ_serverip: " + Config.getString("CZ_serverip"));
    }
}
