package fis.common.pay;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: 下午3:04
 * To change this template use File | Settings | File Templates.
 */
public enum PayType {
    PAY_DIRECT("1","直接支付"),
    PAY_ACCREDIT("2","授权支付");

    private String code = null;
    private String title = null;
    public static Hashtable<String, PayType> aliasEnums;
    PayType(String code, String title) {
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

    public Hashtable<String, PayType> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, PayType> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
