package fis.common.constant;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-12-22
 * Time: ����11:04
 * To change this template use File | Settings | File Templates.
 */
public enum ProcessStatus {
    PROCESS_INIT("0", "��ʼ״̬"),
    PROCESS_CONFIRMSUC("1", "�տ���ȷ��"),
    PROCESS_TOACTSUC("2", "������ȷ��"),
    PROCESS_CONFIRMFAIL("3", "�տ�ȷ��ʧ��"),
    PROCESS_TOACTFAIL("4", "����ȷ�Ϸ���ʧ��");
    private String code = null;
    private String title = null;
    public static Hashtable<String, ProcessStatus> aliasEnums;

    ProcessStatus(String code, String title) {
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

    public Hashtable<String, ProcessStatus> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, ProcessStatus> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
