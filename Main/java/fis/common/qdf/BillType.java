package fis.common.qdf;

import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 13-5-21
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public enum BillType {
    PB_STANDARD("01","121标准《山东省非税收入缴款书》"),
    PB_QUOTA("02","139类定额《山东省非税收入缴款书》"),
    PB_PENALTY("03","154《山东省非税收入缴款书（罚没专用）》"),
    PB_DONATE("04","125《山东省非税收入缴款书（接受捐赠专用）》"),
    PB_COURT("05","103《山东省非税收入缴款书（法院专用）》"),
    PB_SEWAGE("06","107《山东省非税收入缴款书（排污费专用）》"),
    ACT_MATERIAL("07","109《山东省非税收入缴款书（新型墙体材料基金专用）》");
    private String code = null;
    private String title = null;
    public static Hashtable<String, BillType> aliasEnums;

    BillType(String code,String title) {
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

    public Hashtable<String, BillType> getAliasEnums() {
        return aliasEnums;
    }

    public void setAliasEnums(Hashtable<String, BillType> aliasEnums) {
        this.aliasEnums = aliasEnums;
    }
}
