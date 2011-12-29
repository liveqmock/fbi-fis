package fis.common;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtil {


    private Class myClass = null;


    Object obj = null;

    private String className;


    public ReflectUtil() {
    }


    public ReflectUtil(String className) {
        try {
            this.myClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Object createObject() {
        try {
            obj = myClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }


    public Object createObject(Class[] paramTypes, Object[] paramValues) {
        //�����������Ϊ�ջ����Ϊ0,������޲ι��췽��
        if (paramTypes == null || paramTypes.length == 0) {
            obj = this.createObject();
        } else {
            Constructor myConstructor = null;
            try {
                myConstructor = this.myClass.getConstructor(paramTypes);
                obj = (Object) myConstructor.newInstance(paramValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }


    private Method getMethod(String methodname) {
        Method method = null;
        Method[] methods = myClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodname)) {
                method = methods[i];
                break;
            }
        }
        return method;
    }


    public Object methodRun(String methodName, Object[] args) {
        Object result = methodInvoke(getMethod(methodName), args);
        ;
        return result;
    }


    private Object methodInvoke(Method method, Object[] args) {
        Object result = null;
        try {
            // ����ײ㷽��(���÷��������invoke����)
            // invoke˵��������objʵ������ķ���(�൱��:obj.xXX();
            if (args == null || args.length == 0) {
                result = method.invoke(obj, new Object[]{null});
            } else {
                result = method.invoke(obj, args);
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }


    public List<String> getMethodNameList() {
        List<String> list = new ArrayList<String>();
        if (obj != null) {
            Class cls = obj.getClass();
            Method[] method = cls.getMethods();
            for (int i = 0; i < method.length; i++) {
                list.add(method[i].getName());
            }
        }
        return list;
    }

    public String[] getObjFieldsName() {
        Field[] fields = obj.getClass().getDeclaredFields();
        String[] names = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            names[i] = fields[i].getName();
        }
        return names;
    }


    public Object[] getObjAttValues(String[] attList) {
        // ��ȡ��������б�
        Object[] data = new Object[attList.length];

        for (int k = 0; k < attList.length; k++) {
            // ָ��λ������
            String fieldName = attList[k];
            // �������Լ���õ�get������
            String methodName = getMethodName("get", fieldName);
            try {
                // �����������
                Class[] types = new Class[]{};
                // ���ݷ����Ͳ�������,�õ�ָ����������
                Method method = obj.getClass().getMethod(methodName, types);
                // ִ�з��������Ӧ�ķ���(ע��ҲҪ�в���)
                Object result = method.invoke(obj, new Object[0]);
                data[k] = result;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }


    public Object[] getObjAttValues(int[] fieldOrder) {

        // ��ȡ��������б�
        Field[] fields = obj.getClass().getDeclaredFields();
        Object[] data = new Object[fieldOrder.length];

        for (int k = 0; k < fieldOrder.length; k++) {
            // ָ��λ������
            String fieldName = fields[fieldOrder[k]].getName();
            // �������Լ���õ�get������
            String methodName = getMethodName("get", fieldName);
            try {
                // �����������
                Class[] types = new Class[]{};
                // ���ݷ����Ͳ�������,�õ�ָ����������
                Method method = obj.getClass().getMethod(methodName, types);
                // ִ�з��������Ӧ�ķ���(ע��ҲҪ�в���)
                Object result = method.invoke(obj, new Object[0]);
                data[k] = result;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }


    public void copyValuesFromArray(Object[] values) {

        // ��ȡ��������б�
        Field[] fields = obj.getClass().getDeclaredFields();

        try {
            for (int i = 0; i < fields.length; i++) {
                // �������Լ���õ�set������
                String methodName = getMethodName("set", fields[i].getName());
                Method method = obj.getClass().getDeclaredMethod(methodName,
                        new Class[]{fields[i].getType()});
                String name = fields[i].getType().getName();

                if (values[i] != null) {
                    if (name.equals("java.lang.Integer")) {
                        Integer tmpInteger = null;
                        try {
                            tmpInteger = Integer.valueOf(values[i].toString());
                        } catch (Exception e) {
                            tmpInteger = null;
                        }
                        method.invoke(obj, new Object[]{tmpInteger});
                    } else if (name.equals("java.lang.Double")) {
                        method.invoke(obj, new Object[]{Double.valueOf(values[i].toString())});
                    } else if (name.equals("java.util.Date")) {
                        java.sql.Date dt = StringToDate(values[i].toString());
                        method.invoke(obj, new Object[]{dt});
                    } else if (name.equals("java.math.BigDecimal")) {
                        double val = Double.valueOf(values[i].toString());
                        method.invoke(obj, new Object[]{BigDecimal.valueOf(val)});
                    } else {
                        method.invoke(obj, new Object[]{values[i]});
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*��bean֮��Ŀ���*/
    public Object getPastBean(Object pasteBean,Object copyBean) throws InvocationTargetException, IllegalAccessException {
        Field[] fields = pasteBean.getClass().getDeclaredFields();
        String[] names = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            names[i] = fields[i].getName();
        }
        for (int i = 0; i < names.length;i++) {
            //��ȡ����bean��ֵ
            String strfld = names[i];
            String methodName = getMethodName("get",strfld);
            Class[] types = new Class[]{};
            Method method = null;
            try {
                method = copyBean.getClass().getMethod(methodName, types);
            } catch (NoSuchMethodException e) {
                continue;
            }
            Object result = method.invoke(copyBean, new Object[0]);
            //����ճ��bean��ֵ
            String setmethodName = getMethodName("set",strfld);
            Method setmethod = null;
            try {
                setmethod = pasteBean.getClass().getMethod(setmethodName, new Class[]{fields[i].getType()});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("bean����ʱû�л�ȡ��������", e);
            }
            setmethod.invoke(pasteBean,result);
        }
        return pasteBean;
    }


    public String objToString() {
        StringBuffer sb = new StringBuffer();

        // ��ȡ��������б�
        Field[] fields = obj.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            // �������Լ���õ�get������
            String methodName = getMethodName("get", fields[i].getName());
            try {
                // �����������
                Class[] types = new Class[]{};
                // ���ݷ����Ͳ�������,�õ�ָ����������
                Method method = obj.getClass().getMethod(methodName, types);
                // ִ�з��������Ӧ�ķ���(ע��ҲҪ�в���)
                Object result = method.invoke(obj, new Object[0]);

                sb.append(fields[i].getName() + "=" + result + "t");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    private static String getMethodName(String prefix, String fieldName) {
        return new StringBuffer(prefix).append(
                fieldName.substring(0, 1).toUpperCase()).append(
                fieldName.substring(1)).toString();
    }


    private static java.sql.Date StringToDate(String dtStr) {
        if (dtStr == null || dtStr.trim().length() == 0) {
            return null;
        } else {
            java.util.Date date = null;
            try {
                // ע���Ǵ�д��MM
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.parse(dtStr);
                return new java.sql.Date(date.getTime());
            } catch (ParseException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

}
