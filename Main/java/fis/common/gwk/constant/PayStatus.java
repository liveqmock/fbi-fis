package fis.common.gwk.constant;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: ����8:54
 * To change this template use File | Settings | File Templates.
 */
public enum PayStatus {
    SPDB_INIT("01", "��ʼ"),
    SPDB_PAYSUC("00", "����ɹ�"),
    SPDB_ERR14("14", "���Ŵ���"),
    SPDB_ERR15("15", "֤�����벻ƥ��"),
    SPDB_ERR99("99", "����ԭ�����");
    private String code = null;
    private String title = null;
    public static Hashtable<String, PayStatus> aliasEnums;

    PayStatus(String code, String title) {
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

    public Hashtable<String, PayStatus> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, PayStatus> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
