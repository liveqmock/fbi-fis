package gateway.ftp.pfbank;
/**
 * ʱ�䣺2008��3��
 * ���ߣ�
 * ���ܣ������࣬�����Ͳ����ַ���
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

public class DisposeStr {
    //��"|"�ָ��ַ���
    public ArrayList disposeStr(String str) {
        if (str == null || "".equals(str)) {
            return null;
        } else {

            StringTokenizer tokens = new StringTokenizer(str.substring(4), "|");
            ArrayList blickStr = new ArrayList();
            int idx = 0;
            while (tokens.hasMoreTokens()) {
                blickStr.add(idx, tokens.nextToken());
                idx++;
            }
            return blickStr;
        }
    }

    //���ո�slenΪ�ַ����ܳ���
    public String addSpace(String inStr, int slen) {
        String SPACE = " ";
        String retStr = inStr;
        if (retStr == null) {
            retStr = " ";

        }
        retStr = retStr.trim();
        int j = slen - retStr.getBytes().length;

        for (int i = 0; i < j; i++) {
            retStr = retStr + SPACE;
        }
        return retStr;
        //System.out.println(str1);

    }

    //�������inStr��ָ�����ȷָ�
    public ArrayList PaseStr(String inStr1, int[] StrLen) {
        ArrayList retArrStr = new ArrayList();
        int arrlen[] = StrLen;
        byte StrByte[] = inStr1.getBytes();
        int len = 0;
        for (int i = 0; i < StrLen.length; i++) {
            int j = arrlen[i];
            len = len + j;
            byte curStr[] = new byte[j];
            for (int k = 0; k < j; k++) {
                curStr[k] = StrByte[len - j + k];
            }
            retArrStr.add(new String(curStr));
        }
        return retArrStr;
    }

    //�������inStrǰ��ָ��������
    public String addZero(String inStr, int slen) {
        String ZERO = "0";
        String retStr = inStr;

        if (inStr == null || "".equals(inStr)) {
            inStr = "0";
        }

        int j = slen - retStr.getBytes().length;
        for (int i = 0; i < j; i++) {
            retStr = ZERO + retStr;
        }
        return retStr;
        //System.out.println(str1);

    }

    //��"|"�ָ��ַ���
    public ArrayList disposeAllStr(String str) {
        ArrayList blickStr = new ArrayList();
        String subStr = str;
        if (str == null) {
            return null;
        } else {

            // subStr = subStr.substring(1);
            int idx = 0;
            while (subStr.getBytes().length != 0) {
                String str1 = "";
                int i = subStr.indexOf("|");
                if (i == 0) {
                    str1 = " ";
                } else {
                    if (subStr.getBytes().length != 0)
                        str1 = subStr.substring(0, i);
                }
                System.out.println(idx + "w  " + str1);
                blickStr.add(str1);
                subStr = subStr.substring(i + 1);
            }

            return blickStr;
        }
    }

    //��"|"�ָ��ַ���
    public ArrayList decodeStr(String str) {
        ArrayList blickStr = new ArrayList();
        String subStr = str;
        if (str == null) {
            return null;
        } else {

            // subStr = subStr.substring(1);
            int idx = 0;
            while (subStr.getBytes().length != 0) {
                String str1 = "";
                int i = subStr.indexOf("|");
                if (i == -1) {
                    str1 = subStr;
                    blickStr.add(str1);
                    break;
                } else {
                    if (subStr.getBytes().length != 0) {
                        str1 = subStr.substring(0, i);
                        blickStr.add(str1);
                        subStr = subStr.substring(i + 1);
                    }
                }

            }

            return blickStr;
        }
    }

    public static void main(String[] args) {
//		DisposeStr ds = new DisposeStr();
//		ArrayList al=ds.disposeAllStr("|9843011000179852|9843011000200039|870107000180076|20030407|");
//		for(int i=0;i<al.size();i++){
//			System.out.println(al.get(i));
//		}
//		
        int[] lens = {20};
        DisposeStr ds1 = new DisposeStr();
        String str = "R112008-0209V-00044 ";
        //String  str = "00331466666622";
        System.out.println(ds1.addSpace(str, 20));

//		ArrayList al2 = ds1.PaseStr(str,lens);
//		System.out.println("len: "+str.getBytes().length);
//		
//		for(int i=0;i<al2.size();i++){
//			System.out.println(i+":"+al2.get(i));
//		}

    }


}
