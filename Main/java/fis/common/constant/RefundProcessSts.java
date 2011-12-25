package fis.common.constant;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-12-25
 * Time: ����7:55
 * To change this template use File | Settings | File Templates.
 */
public enum RefundProcessSts {
    PROCESS_INIT("0","��ʼ״̬"),
    PROCESS_CONFIRMSUC("1", "�˸���ȷ��"),
    PROCESS_CONFIRMFAIL("2", "�˸�ȷ��ʧ��");
    private String code = null;
    private String title = null;
    public static Hashtable<String, RefundProcessSts> aliasEnums;

    RefundProcessSts(String code, String title) {
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

    public Hashtable<String, RefundProcessSts> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, RefundProcessSts> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
