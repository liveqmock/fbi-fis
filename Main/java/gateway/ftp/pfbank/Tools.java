// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tools.java

package gateway.ftp.pfbank;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Tools {

    public Tools() {
    }

    public static int getPackLength(char len[]) {
        int length = 0;
        length = charToInt(len[3]) + charToInt(len[2]) * 16 + charToInt(len[1]) * 16 * 16 + charToInt(len[0]) * 16 * 16 * 16;
        return length;
    }

    private static int charToInt(char c) {
        int i = 0;
        switch (c) {
            case 65: // 'A'
            case 97: // 'a'
                i = 10;
                break;

            case 66: // 'B'
            case 98: // 'b'
                i = 11;
                break;

            case 67: // 'C'
            case 99: // 'c'
                i = 12;
                break;

            case 68: // 'D'
            case 100: // 'd'
                i = 13;
                break;

            case 69: // 'E'
            case 101: // 'e'
                i = 14;
                break;

            case 70: // 'F'
            case 102: // 'f'
                i = 15;
                break;

            case 71: // 'G'
            case 72: // 'H'
            case 73: // 'I'
            case 74: // 'J'
            case 75: // 'K'
            case 76: // 'L'
            case 77: // 'M'
            case 78: // 'N'
            case 79: // 'O'
            case 80: // 'P'
            case 81: // 'Q'
            case 82: // 'R'
            case 83: // 'S'
            case 84: // 'T'
            case 85: // 'U'
            case 86: // 'V'
            case 87: // 'W'
            case 88: // 'X'
            case 89: // 'Y'
            case 90: // 'Z'
            case 91: // '['
            case 92: // '\\'
            case 93: // ']'
            case 94: // '^'
            case 95: // '_'
            case 96: // '`'
            default:
                i = Integer.parseInt(String.valueOf(c));
                break;
        }
        return i;
    }

    public static String charToString(char c[]) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < c.length; i++)
            buffer.append(c[i]);

        return buffer.toString();
    }

    public static String intToHexString(int i) {
        StringBuffer hex = new StringBuffer();
        hex.append(Integer.toHexString(i + 4));
        for (int j = 0; j < 5 - hex.length(); j++)
            hex.insert(0, "0");

        return hex.toString().toUpperCase();
    }

    public static String replace(String originString, String oldString, String newString) {
        String getstr;
        for (getstr = originString; getstr.indexOf(oldString) > -1; getstr = getstr.substring(0, getstr.indexOf(oldString)) + newString + getstr.substring(getstr.indexOf(oldString) + oldString.length(), getstr.length()))
            ;
        return getstr;
    }

    public static String getDate(String dateformat) {
        Date myDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sDateformat = new SimpleDateFormat(dateformat);
        return sDateformat.format(myDate).toString();
    }

    public static void writeLog(String writestr) {
        StringBuffer strBufErr = new StringBuffer();
        strBufErr.append(getDate("yyyy/MM/dd/HH/mm/ss"));
        strBufErr.append("\r\n");
        strBufErr.append(writestr);
        strBufErr.append("\r\n");
        try {
            writeFile("syslog.txt", strBufErr.toString());
        } catch (IOException ioe) {
            System.out.print("\u5199\u65E5\u5FD7\u5F02\u5E38\uFF01");
            ioe.printStackTrace();
            return;
        }
    }

    public static boolean writeFile(String filename, String inputText)
            throws IOException {
        FileWriter fw = new FileWriter(filename, true);
        fw.write(inputText);
        fw.close();
        return true;

    }

    public static String OutputFileName(String message) {
        String fileName = null;
        try {
            String path = null;
            if (path == null)
                path = "c:" + File.separator + "log";
            File f = new File(path);
            if (!f.exists())
                f.mkdir();
            File file = new File(f.getAbsolutePath() + File.separator + getDate("yyyy-MM-dd-HH-mm-ss") + ".log");
            fileName = file.getName();
            writeFile(file.getAbsolutePath(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }


    public static String OutputLogFile(String message) {
        String fileName = null;
        try {
            String path = null;
            if (path == null)
                path = Config.getString("logfilepath");

            File f = new File(path);
            System.out.println(f.getAbsolutePath());
            if (!f.exists())
                f.mkdir();

            File file = new File(f.getAbsolutePath() + File.separator + "log" + getDate("yyyy-MM-dd") + ".log");
            fileName = file.getName();
            System.out.println("日志文件名:" + fileName);
            writeFile(file.getAbsolutePath(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static String OutputLogFile(String message, String filenameprefix) {
        String fileName = null;
        try {
            String path = null;
            if (path == null)
                path = Config.getString("logfilepath");
            File f = new File(path);

            if (!f.exists())
                f.mkdir();

            File file = new File(f.getAbsolutePath() + File.separator + filenameprefix + getDate("yyyy-MM-dd") + ".log");
            fileName = file.getName();
            //System.out.println("日志文件名:"+fileName);
            writeFile(file.getAbsolutePath(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }


    public static boolean writeFile(File fFile, String dataString) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fFile)));
            out.print(dataString);
            out.flush();
            out.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    //下载文件列表
    public ArrayList getDownFiles() {
        ArrayList<String> curlist = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        String text1 = sdf.format(c.getTime());
        curlist.add("0310-CDREP-" + Config.getString("Bank_Code") + "-" + text1);//换卡
        curlist.add("0310-PURCH-" + Config.getString("Bank_Code") + "-" + text1);//消费明细
        curlist.add("0310-CDOPN-" + Config.getString("Bank_Code") + "-" + text1);//帐户
        curlist.add("0310-CHARGESRESP-99" + Config.getString("Bank_Code") + "-" + text1);//还款结果
        return curlist;
    }
}
