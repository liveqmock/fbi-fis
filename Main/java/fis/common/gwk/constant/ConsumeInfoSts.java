package fis.common.gwk.constant;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-1-8
 * Time: 下午10:05
 * To change this template use File | Settings | File Templates.
 */
public enum ConsumeInfoSts {
    SEND_INIT("10","初始状态"),
    SEND_TIMEOUT("11","发送超时"),
    SEND_FAIL("12","发送失败"),
    SEND_SUC("20","发送成功");
    private String code = null;
    private String title = null;
    public static Hashtable<String, ConsumeInfoSts> aliasEnums;

    ConsumeInfoSts(String code, String title) {
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

    public Hashtable<String, ConsumeInfoSts> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, ConsumeInfoSts> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
