package fis.common.gwk.constant;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: 下午7:40
 * To change this template use File | Settings | File Templates.
 */
public enum CardSendFlg {
    SEND_NO("0","未发送"),
    SEND_YES("1","已发送");
    private String code = null;
    private String title = null;
    public static Hashtable<String, CardSendFlg> aliasEnums;

    CardSendFlg(String code, String title) {
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

    public Hashtable<String, CardSendFlg> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, CardSendFlg> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
