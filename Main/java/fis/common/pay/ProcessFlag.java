package fis.common.pay;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: ����3:03
 * To change this template use File | Settings | File Templates.
 */
public enum ProcessFlag {
    PROCESS_INIT("0","��ʼ״̬"),
    PROCESS_PAYSUC("1","֧����д�ɹ�"),
    PROCESS_REFUSESUC("2","��Ʊ��д�ɹ�"),
    PROCESS_REFUNDSUC("3","�˿��д�ɹ�"),
    PROCESS_CLEARANCESUC("4","�����д�ɹ�"),
    PROCESS_PAYFAIL("5","֧����дʧ��"),
    PROCESS_REFUSEFAIL("6","��Ʊ��дʧ��"),
    PROCESS_REFUNDFAIL("7","�˿��дʧ��"),
    PROCESS_CLEARANCEFAIL("8","�����дʧ��");

    private String code = null;
    private String title = null;
    public static Hashtable<String, ProcessFlag> aliasEnums;
    ProcessFlag(String code, String title) {
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

    public Hashtable<String, ProcessFlag> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, ProcessFlag> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
