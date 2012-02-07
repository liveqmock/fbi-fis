package fis.common.pay;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 12-2-6
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
public enum ProcessFlag {
    PROCESS_INIT("0","初始状态"),
    PROCESS_PAYSUC("1","支付回写成功"),
    PROCESS_REFUSESUC("2","退票回写成功"),
    PROCESS_REFUNDSUC("3","退款回写成功"),
    PROCESS_CLEARANCESUC("4","清算回写成功"),
    PROCESS_PAYFAIL("5","支付回写失败"),
    PROCESS_REFUSEFAIL("6","退票回写失败"),
    PROCESS_REFUNDFAIL("7","退款回写失败"),
    PROCESS_CLEARANCEFAIL("8","清算回写失败");

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
