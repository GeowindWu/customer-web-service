package com.gxecard.customerservice.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 常用的转换
 *
 * @author zhengchuan
 */
public class Utilitys {

    private final static String DateStrFormat = "yyyy-MM-dd HH:mm:ss";
    private final static SimpleDateFormat DateFormat = new SimpleDateFormat(
            DateStrFormat);

    public static String timestamp2StandardStr(Timestamp timestamp) {
        if (timestamp == null)
            return "";
        
        String standTime = "";
        try {
            standTime = DateFormat.format(new Date(timestamp.getTime()));
        } catch (Exception e) {
        }
        return standTime;
    }

    public static Long returnLong(Object obj) {
        if (obj == null)
            return null;
        try {
            return new Long(obj + "");
        } catch (Exception e) {
        }
        return null;
    }

    public static Double returnDobule(Object obj) {
        if (obj == null)
            return null;
        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception e) {
        }
        return null;
    }

    public static String getString(Object object) {
        if (object == null)
            return "";
        return object.toString();
    }

    public static Long getLong(Object object) {
        Long result = null;
        try {
            result = Long.parseLong(object.toString());
        } catch (Exception e) {
        }
        return result;
    }


    public static boolean isInArray(String str, Field[] fields) {
        String key = str.substring(3).toLowerCase();
        for (Field tmp : fields) {
            String temp = tmp.getName().toLowerCase();
            if (key.equalsIgnoreCase(temp))
                return true;
        }
        return false;
    }

    // 对象深度拷贝
    public static Object copyObject(Object obj) {
        if (obj == null)
            return null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(obj);// 写对象，序列化
            ByteArrayInputStream byteIn = new ByteArrayInputStream(
                    byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            Object newObj = in.readObject(); // 读对象，反序列化
            return newObj;
        } catch (Exception e) {
        }
        return null;
    }

    // 对象序列化
    public static byte[] objectToBytes(Object obj) {
        if (obj == null)
            return null;
        byte[] bytes = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(obj);// 写对象，序列化
            bytes = byteOut.toByteArray();
            out.close();
        } catch (Exception e) {
        }
        return bytes;
    }

    // 序列化对象转对象
    public static Object bytesToObject(byte[] bytes) {
        if (bytes == null)
            return null;
        Object obj = null;
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(byteIn);
            obj = in.readObject(); // 读对象，反序列化
            in.close();
        } catch (Exception e) {
        }
        return obj;
    }

    // 对象转Map
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map objectToMap(Object thisObj) {
        Map map = new HashMap();
        Class c;
        try {
            c = Class.forName(thisObj.getClass().getName());
            // 方法
            Method[] m = c.getMethods();
            // 属性
            Field[] fields = c.getDeclaredFields();
            for (int i = 0; i < m.length; i++) {
                String method = m[i].getName();

                method = method.toLowerCase();
                if (method.startsWith("get")) {

                    if (isInArray(method, fields)) {
                        try {
                            Object value = m[i].invoke(thisObj);
                            if (value != null) {
                                String key = method.substring(3);
                                map.put(key, value);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return map;
    }

    // 对象转Map
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map objectToMapByAll(Object thisObj) {
        Map map = new HashMap();
        Class c;
        try {
            c = Class.forName(thisObj.getClass().getName());
            // 方法
            Method[] m = c.getMethods();
            // 属性
            // Field[] fields = c.getDeclaredFields();
            for (int i = 0; i < m.length; i++) {
                String method = m[i].getName();
                method = method.toLowerCase();
                if (method.startsWith("get")) {
                    // if (isInArray(method, fields)) {
                    try {
                        Object value = m[i].invoke(thisObj);
                        if (value != null) {
                            String key = method.substring(3);
                            map.put(key, value);
                        }
                    } catch (Exception e) {
                    }
                    // }
                }
            }
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * 这里只转换一级属性；可以解决懒加载和对象转换的错误
     *
     * @param thisObj
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map objectToMapByDepthOnce(Object thisObj) {
        Map map = new HashMap();
        if (thisObj == null)
            return map;

        Class c;
        try {
            c = Class.forName(thisObj.getClass().getName());
            // 方法
            Method[] m = c.getMethods();
            for (int i = 0; i < m.length; i++) {
                String method = m[i].getName();
                // method = method.toLowerCase();
                // 其他类型
                if (method.startsWith("get") && method.startsWith("getClass") == false) {
                    try {
                        Object value = m[i].invoke(thisObj);
                        if (value != null) {
                            String key = method.substring(3);
                            // 保证风格一样
                            if (key.length() == 0) {
                                key = key.toLowerCase();
                            } else {
                                key = key.substring(0, 1).toLowerCase()
                                        + key.substring(1);
                            }
                            map.put(key, value.toString());
                        }
                    } catch (Exception e) {
                    }
                }
                // boolean 类型
                if (method.startsWith("is")) {
                    try {
                        Object value = m[i].invoke(thisObj);
                        if (value != null) {
                            String key = method.substring(2);
                            // 保证风格一样
                            if (key.length() == 0) {
                                key = key.toLowerCase();
                            } else {
                                key = key.substring(0, 1).toLowerCase()
                                        + key.substring(1);
                            }
                            map.put(key, value.toString());
                        }
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
        }
        return map;
    }

    /**
     * @param thisMap
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map map2MapStrand(Map thisMap) {
        Map map = new HashMap();
        if (thisMap == null)
            return map;

        try {// 将对象转为字符串
            Object[] keySet = thisMap.keySet().toArray();
            for (Object key : keySet) {
                Object obj = thisMap.get(key);
                if (obj == null)
                    map.put(key, "");
                else
                    map.put(key, obj.toString());
            }
        } catch (Exception e) {
        }
        return map;
    }

    @SuppressWarnings("rawtypes")
    public static List listMap2MapStrand(List list) {
        List<Map> result = new ArrayList<Map>();
        if (list == null)
            return result;
        try {
            for (Object mapObj : list) {
                Map map = (Map) mapObj;
                // 标准化一下
                map = map2MapStrand(map);
                result.add(map);
            }
        } catch (Exception e) {
        }
        return result;
    }


    /**
     * 检查指定日期是不是今天
     *
     * @param startTime
     * @param dateformat
     * @return
     */
    public static boolean checkIsCurrentDate(String startTime, String dateformat) {
        boolean trage = false;
        try {
            // 比较结算时期是不是当天的 :返回前台是否更新
            SimpleDateFormat sd = new SimpleDateFormat(dateformat);
            String now_date = sd.format(new Date());
            if (now_date.equalsIgnoreCase(startTime.trim()))
                trage = true;
        } catch (Exception e) {
        }
        return trage;
    }

    /**
     * 计算改天后的多少天时间
     *
     * @param
     * @param nextTime
     * @return
     */
    public static Date getAfterDateByDay(Date tmpDate, int nextTime) {
        Date endDate = new Date();
        long expTime = nextTime;
        // 分开相乘 防止溢出
        expTime *= 24; // 24小时；
        expTime *= 3600; // 秒；
        expTime *= 1000; // 毫秒；
        // 计算最后一天
        expTime += tmpDate.getTime();
        endDate = new Date(expTime);
        return endDate;
    }


    /**
     * 获取当前的时间
     *
     * @return
     */
    public static String getCurTime() {
        try {
            return DateFormat.format(new Date());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取当前的时间
     *
     * @return
     */
    public static String getCurTimeByFromt(String format) {
        try {
            SimpleDateFormat DateFormat = new SimpleDateFormat(format);

            return DateFormat.format(new Date());
        } catch (Exception e) {
            SimpleDateFormat DateFormat = new SimpleDateFormat(
                    DateStrFormat);
            return DateFormat.format(new Date());
        }
    }

    public static SimpleDateFormat getDateformat() {
        return DateFormat;
    }


    public static Object[] arrayCopy(Object[] arrayfrom, Object[] arrayTo) {
        if (arrayfrom == null || arrayTo == null)
            return arrayTo;
        for (int i = 0; i < arrayfrom.length && i < arrayTo.length; ++i) {
            arrayTo[i] = arrayfrom[i];
        }
        return arrayTo;
    }


    /************************************************************/
    public static String getMapAttrValue(Map<String,String> map,String attrName) throws RuntimeException{
        if(map == null)
            throw new RuntimeException("map对象为空！");
        if(attrName == null)
            throw new RuntimeException("查询属性名称为空！");
        if(map.containsKey(attrName) )
            return map.get(attrName);

        return null;
    }

    /************************************************************/
    public static boolean isBlank(String strVal) {
        if(strVal == null)
            return true;
        if(strVal.trim().length()==0)
            return true;
        return false;
    }
}
