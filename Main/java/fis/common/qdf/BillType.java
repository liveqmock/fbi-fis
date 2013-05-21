package fis.common.qdf;

import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 13-5-21
 * Time: ����11:29
 * To change this template use File | Settings | File Templates.
 */
public enum BillType {
    PB_STANDARD("01","121��׼��ɽ��ʡ��˰����ɿ��顷"),
    PB_QUOTA("02","139�ඨ�ɽ��ʡ��˰����ɿ��顷"),
    PB_PENALTY("03","154��ɽ��ʡ��˰����ɿ��飨��ûר�ã���"),
    PB_DONATE("04","125��ɽ��ʡ��˰����ɿ��飨���ܾ���ר�ã���"),
    PB_COURT("05","103��ɽ��ʡ��˰����ɿ��飨��Ժר�ã���"),
    PB_SEWAGE("06","107��ɽ��ʡ��˰����ɿ��飨���۷�ר�ã���"),
    ACT_MATERIAL("07","109��ɽ��ʡ��˰����ɿ��飨����ǽ����ϻ���ר�ã���");
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
