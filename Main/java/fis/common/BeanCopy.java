package fis.common;

import org.apache.commons.collections.map.ListOrderedMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-11-3
 * Time: ����2:56
 * To change this template use File | Settings | File Templates.
 */
public class BeanCopy {
    //bean����copy
    public static Object copyObject(String className, Map map) {
        ReflectUtil reflectUtil = new ReflectUtil(className);
        reflectUtil.createObject();
        String[] fieldsName = reflectUtil.getObjFieldsName();
        Object[] objvals = new Object[fieldsName.length];
        int i = 0;
        for (String fdname : fieldsName) {
            //��д�ӿ� �޸�
//            fdname = fdname.toUpperCase();
            fdname = fdname.toLowerCase();
            objvals[i] = map.get(fdname);
            i++;
        }
        reflectUtil.copyValuesFromArray(objvals);
        Object obj = reflectUtil.getObj();
        return obj;
    }
    //bean����copy
    public static Object copyObject(String className, ListOrderedMap map) {
        ReflectUtil reflectUtil = new ReflectUtil(className);
        reflectUtil.createObject();
        String[] fieldsName = reflectUtil.getObjFieldsName();
        Object[] objvals = new Object[fieldsName.length];
        int i = 0;
        for (String fdname : fieldsName) {

            objvals[i] = map.get(fdname);
            i++;
        }
        reflectUtil.copyValuesFromArray(objvals);
        Object obj = reflectUtil.getObj();
        return obj;
    }

    //��bean���copy
    public static Object copayObject(Object pasteBean,Object copyBean) throws InvocationTargetException, IllegalAccessException {
        ReflectUtil reflectUtil = new ReflectUtil();
        return reflectUtil.getPastBean(pasteBean,copyBean);
    }
}

