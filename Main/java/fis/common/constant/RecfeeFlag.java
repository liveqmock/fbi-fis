package fis.common.constant;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-12-23
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
public enum RecfeeFlag {
    RECFEE_NOTOACT("0", "未到账"),
    RECFEE_TOACT("1", "已到账");
    private String code = null;
    private String title = null;
    public static Hashtable<String, RecfeeFlag> aliasEnums;

    RecfeeFlag(String code, String title) {
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

    public Hashtable<String, RecfeeFlag> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, RecfeeFlag> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
