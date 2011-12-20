package skyline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skyline.repository.model.Ptenudetail;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-4-22
 * Time: ����2:32
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ToolsService {

    @Autowired
    PlatformService platformService;


    /**
     * ����ö�ٱ�������������˵�
     *
     * @param enuName     ö������
     * @param isSelectAll �Ƿ����ȫ����ѡ��
     * @param isExpandID  true:�����б�������ID�� false���б��а���ID
     * @return �����˵�
     */
    public List<SelectItem> getEnuSelectItemList(String enuName, boolean isSelectAll, boolean isExpandID) {
        List<Ptenudetail> records = platformService.selectEnuDetail(enuName);
        List<SelectItem> items = new ArrayList<SelectItem>();
        SelectItem item;
        if (isSelectAll) {
            item = new SelectItem("", "ȫ��");
            items.add(item);
        }
        for (Ptenudetail record : records) {
            if (isExpandID) {
                item = new SelectItem(record.getEnuitemvalue(), record.getEnuitemvalue() + " " + record.getEnuitemlabel());
            } else {
                item = new SelectItem(record.getEnuitemvalue(), record.getEnuitemlabel());
            }
            items.add(item);
        }
        return items;
    }

    /**
     * SelectItems
     * @param enuName
     * @param isExpandID
     * @return
     */
     public Map<String, String> getEnuMaps(String enuName, boolean isExpandID) {
        List<Ptenudetail> records = platformService.selectEnuDetail(enuName);
        Map<String, String> enuMaps = new HashMap<String, String>();
        for (Ptenudetail record : records) {
            if (isExpandID) {
                enuMaps.put(record.getEnuitemlabel(), record.getEnuitemvalue() + " " + record.getEnuitemlabel());
            } else {
                enuMaps.put(record.getEnuitemlabel(), record.getEnuitemvalue());
            }
        }
        return enuMaps;
    }

    /**
     * �б���ʾ��ֵ��
     * @param enuName
     * @param isExpandID
     * @return
     */
    public Map<String, String> getEnuKVMaps(String enuName, boolean isExpandID) {
        List<Ptenudetail> records = platformService.selectEnuDetail(enuName);
        Map<String, String> enuMaps = new HashMap<String, String>();
        for (Ptenudetail record : records) {
            if (isExpandID) {
                enuMaps.put(record.getEnuitemvalue(), record.getEnuitemvalue() + " " + record.getEnuitemlabel());
            } else {
                enuMaps.put(record.getEnuitemvalue(), record.getEnuitemlabel());
            }
        }
        return enuMaps;
    }
}
