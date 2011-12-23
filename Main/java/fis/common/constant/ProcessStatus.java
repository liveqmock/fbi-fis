package fis.common.constant;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-12-22
 * Time: 上午11:04
 * To change this template use File | Settings | File Templates.
 */
public enum ProcessStatus {
    PROCESS_INIT("0", "初始状态"),
    PROCESS_CONFIRMSUC("1", "收款已确认"),
    PROCESS_TOACTSUC("2", "到账已确认"),
    PROCESS_CONFIRMFAIL("3", "收款确认失败"),
    PROCESS_TOACTFAIL("4", "到账确认发送失败");
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
