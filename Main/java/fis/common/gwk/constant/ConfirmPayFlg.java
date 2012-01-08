package fis.common.gwk.constant;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: 下午7:09
 * To change this template use File | Settings | File Templates.
 */
public enum ConfirmPayFlg {
    CONFIRMPAY_INIT("0","初始"),
    CONFIRMPAY_VALID("1","确认还款");
    private String code = null;
    private String title = null;
    public static Hashtable<String, ConfirmPayFlg> aliasEnums;

    ConfirmPayFlg(String code, String title) {
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

    public Hashtable<String, ConfirmPayFlg> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, ConfirmPayFlg> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
