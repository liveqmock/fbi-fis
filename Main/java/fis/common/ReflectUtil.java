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
        //如果参数类型为空或个数为0,则调用无参构造方法
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
            // 反射底层方法(调用方法对象的invoke方法)
            // invoke说明：调用obj实例对象的方法(相当于:obj.xXX();
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
        // 获取类的属性列表
        Object[] data = new Object[attList.length];

        for (int k = 0; k < attList.length; k++) {
            // 指定位置属性
            String fieldName = attList[k];
            // 根据属性计算得到get方法名
            String methodName = getMethodName("get", fieldName);
            try {
                // 定义参数对象
                Class[] types = new Class[]{};
                // 根据方法和参数对象,得到指定方法对象
                Method method = obj.getClass().getMethod(methodName, types);
                // 执行方法对象对应的方法(注意也要有参数)
                Object result = method.invoke(obj, new Object[0]);
                data[k] = result;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }


    public Object[] getObjAttValues(int[] fieldOrder) {

        // 获取类的属性列表
        Field[] fields = obj.getClass().getDeclaredFields();
        Object[] data = new Object[fieldOrder.length];

        for (int k = 0; k < fieldOrder.length; k++) {
            // 指定位置属性
            String fieldName = fields[fieldOrder[k]].getName();
            // 根据属性计算得到get方法名
            String methodName = getMethodName("get", fieldName);
            try {
                // 定义参数对象
                Class[] types = new Class[]{};
                // 根据方法和参数对象,得到指定方法对象
                Method method = obj.getClass().getMethod(methodName, types);
                // 执行方法对象对应的方法(注意也要有参数)
                Object result = method.invoke(obj, new Object[0]);
                data[k] = result;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }


    public void copyValuesFromArray(Object[] values) {

        // 获取类的属性列表
        Field[] fields = obj.getClass().getDeclaredFields();

        try {
            for (int i = 0; i < fields.length; i++) {
                // 根据属性计算得到set方法名
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

    /*两bean之间的拷贝*/
    public Object getPastBean(Object pasteBean,Object copyBean) throws InvocationTargetException, IllegalAccessException {
        Field[] fields = pasteBean.getClass().getDeclaredFields();
        String[] names = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            names[i] = fields[i].getName();
        }
        for (int i = 0; i < names.length;i++) {
            //获取拷贝bean的值
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
            //设置粘贴bean的值
            String setmethodName = getMethodName("set",strfld);
            Method setmethod = null;
            try {
                setmethod = pasteBean.getClass().getMethod(setmethodName, new Class[]{fields[i].getType()});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("bean拷贝时没有获取到方法。", e);
            }
            setmethod.invoke(pasteBean,result);
        }
        return pasteBean;
    }


    public String objToString() {
        StringBuffer sb = new StringBuffer();

        // 获取类的属性列表
        Field[] fields = obj.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            // 根据属性计算得到get方法名
            String methodName = getMethodName("get", fields[i].getName());
            try {
                // 定义参数对象
                Class[] types = new Class[]{};
                // 根据方法和参数对象,得到指定方法对象
                Method method = obj.getClass().getMethod(methodName, types);
                // 执行方法对象对应的方法(注意也要有参数)
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
                // 注意是大写的MM
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
