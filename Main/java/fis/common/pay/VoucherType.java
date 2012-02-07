package fis.common.pay;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: 下午3:04
 * To change this template use File | Settings | File Templates.
 */
public enum VoucherType {
    VCHTYPE_NORMAL("01","正常支付"),
    VCHTYPE_REFUSE("02","已退票"),
    VCHTYPE_REFUND("03","已退款");

    private String code = null;
    private String title = null;
    public static Hashtable<String, VoucherType> aliasEnums;
    VoucherType(String code, String title) {
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

    public Hashtable<String, VoucherType> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, VoucherType> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
