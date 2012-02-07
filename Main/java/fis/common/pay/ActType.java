package fis.common.pay;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: 下午2:53
 * To change this template use File | Settings | File Templates.
 */
public enum ActType {
    ACT_FINANCIAL("1","财政零余额账户"),
    ACT_BDGAGENCY("2","预算单位零余额账户");
    private String code = null;
    private String title = null;
    public static Hashtable<String, ActType> aliasEnums;

    ActType(String code,String title) {
        this.init(code,title);
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

    public Hashtable<String, ActType> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, ActType> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
