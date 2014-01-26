package fis.view.hdfs;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ���˷�����Ϣ
 */
public class ChkactRtnInfo {

    private String rtnCode;
    private String rtnMsg;


    private int itemNum;              // ��ϸ��
    private List<ChkactRtnItem> items = new ArrayList<ChkactRtnItem>();

    public void assemble(String infoMsg) {
        // ����������
        String[] fieldArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(infoMsg, "|");
        rtnCode = fieldArray[0];
        rtnMsg = fieldArray[1];

        itemNum = Integer.parseInt(fieldArray[2]); // ��ϸ��
        items.clear();
        for (int i = 0; i < itemNum; i++) {
            ChkactRtnItem item = new ChkactRtnItem();
            item.assemble(fieldArray[3 + i]);
            items.add(item);
        }
    }

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public List<ChkactRtnItem> getItems() {
        return items;
    }

    public void setItems(List<ChkactRtnItem> items) {
        this.items = items;
    }
}
