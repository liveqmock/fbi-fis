package fis.common.gwk.constant;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: ����10:05
 * To change this template use File | Settings | File Templates.
 */
public enum ConsumeInfoSts {
    SEND_INIT("10","��ʼ״̬"),
    SEND_TIMEOUT("11","���ͳ�ʱ"),
    SEND_FAIL("12","����ʧ��"),
    SEND_SUC("20","���ͳɹ�");
    private String code = null;
    private String title = null;
    public static Hashtable<String, ConsumeInfoSts> aliasEnums;

    ConsumeInfoSts(String code, String title) {
        this.init(code, title);
    }

    @SuppressWarnings("unchecked")
    private void init(String code, String title) {
        this.code = code;
        this.title = title;
        synchronized (this.getClass()) {
            if (aliasEnums == null) {
                aliasEnums = new Hashtable();
            }
        }
        aliasEnums.put(code, this);
        aliasEnums.put(title, this);
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public Hashtable<String, ConsumeInfoSts> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, ConsumeInfoSts> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
