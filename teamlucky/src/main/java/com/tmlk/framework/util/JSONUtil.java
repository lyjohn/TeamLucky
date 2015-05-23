package com.tmlk.framework.util;



import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.text.SimpleDateFormat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.xml.XMLSerializer;

/**
 * @ProjectName：srp
 * @ClassName：JSONUtil
 * @ClassRemark：
 * @Author：T08388
 * @Date：Dec 26, 2011 9:10:36 AM
 * @UpdateUser：T08388
 * @UpdateDate：Dec 26, 2011 9:10:36 AM
 * @UpdateRemark：
 * @version:
 */
public class JSONUtil {

    /**
     * object2JSONString  Bean List Map等对象转JsonString
     *
     * @param Object obj(JavaBean List Map Array ...)
     * @return JsonString
     */
    public static String object2JSONString(Object obj) {
        StringBuilder json = new StringBuilder();
        if (obj == null) {
            json.append("\"\"");
        } else if (obj instanceof String || obj instanceof Integer || obj instanceof Float || obj instanceof Boolean
                || obj instanceof Short || obj instanceof Double || obj instanceof Long || obj instanceof BigDecimal
                || obj instanceof BigInteger || obj instanceof Byte) {
            json.append("\"").append(string2JSONString(obj.toString())).append("\"");
        } else if (obj instanceof Object[]) {
            json.append(array2JSONString((Object[]) obj));
        } else if (obj instanceof List) {
            json.append(list2JSONString((List<?>) obj));
        } else if (obj instanceof Map) {
            json.append(map2JSONString((Map<?, ?>) obj));
        } else if (obj instanceof Set) {
            json.append(set2JSONString((Set<?>) obj));
        } else {
            json.append(bean2JSONString(obj));
        }
        return json.toString();
    }

    /**
     * bean2JSONString  Bean转换JsonString
     *
     * @param Object bean
     * @return JsonString
     */
    public static String bean2JSONString(Object bean) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        PropertyDescriptor[] props = null;
        try {
            props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
        } catch (IntrospectionException e) {
        }
        if (props != null) {
            for (int i = 0; i < props.length; i++) {
                try {
                    String name = object2JSONString(props[i].getName());
                    String value = object2JSONString(props[i].getReadMethod().invoke(bean));
                    json.append(name);
                    json.append(":");
                    json.append(value);
                    json.append(",");
                } catch (Exception e) {
                }
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    /**
     * list2JSONString List转换JsonString
     *
     * @param List list
     * @return JsonString
     */
    public static String list2JSONString(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(object2JSONString(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * array2JSONString    Array转换JsonString
     *
     * @param Object[] array
     * @return JsonString
     */
    public static String array2JSONString(Object[] array) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (array != null && array.length > 0) {
            for (Object obj : array) {
                json.append(object2JSONString(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * map2JSONString Map转换JsonString
     *
     * @param Map map
     * @return JsonString
     */
    public static String map2JSONString(Map<?, ?> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                json.append(object2JSONString(key));
                json.append(":");
                json.append(object2JSONString(map.get(key)));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    /**
     * set2JSONString Set转换JsonString
     *
     * @param Set set
     * @return JsonString
     */
    public static String set2JSONString(Set<?> set) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (set != null && set.size() > 0) {
            for (Object obj : set) {
                json.append(object2JSONString(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * string2JSONString   String转换JsonString
     *
     * @param String s
     * @return JsonString
     * @Exception（异常对象）
     */
    public static String string2JSONString(String s) {
        if (s == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if (ch >= '\u0000' && ch <= '\u001F') {
                        String ss = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); k++) {
                            sb.append('0');
                        }
                        sb.append(ss.toUpperCase());
                    } else {
                        sb.append(ch);
                    }
            }
        }
        return sb.toString();
    }

    /**
     * jsonString2Bean 从json串转换成实体对象
     *
     * @param jsonObjStr e.g. {'name':'get','dateAttr':'2009-11-12'}
     * @param clazz      Person.class
     * @return
     */
    public static Object jsonString2Bean(String jsonStr, Class clazz) {
        return JSONObject.toBean(JSONObject.fromObject(jsonStr), clazz);
    }

    /**
     * jsonString2Bean 从json串转换成实体对象，并且实体集合属性存有另外实体Bean
     *
     * @param jsonStr  e.g. {'data':[{'name':'get'},{'name':'set'}]}
     * @param clazz    e.g. MyBean.class
     * @param classMap e.g. classMap.put("data", Person.class)
     * @return Object
     */
    public static Object jsonString2Bean(String jsonStr, Class clazz, Map classMap) {
        return JSONObject.toBean(JSONObject.fromObject(jsonStr), clazz, classMap);
    }

    /**
     * jsonString2Array 把一个json数组串转换成普通数组
     *
     * @param jsonArrStr e.g. ['get',1,true,null]
     * @return Object[]
     */
    public static Object[] jsonString2Array(String jsonArrStr) {
        return JSONArray.fromObject(jsonArrStr).toArray();
    }

    /**
     * jsonString2List 把一个json数组串转换成实体数组
     *
     * @param jsonArrStr e.g. [{'name':'get'},{'name':'set'}]
     * @param clazz      e.g. Person.class
     * @return Object[]
     */
    public static Object[] jsonString2List(String jsonArrStr, Class clazz) {
        JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
        Object[] objArr = new Object[jsonArr.size()];
        for (int i = 0; i < jsonArr.size(); i++) {
            objArr[i] = JSONObject.toBean(jsonArr.getJSONObject(i), clazz);
        }
        return objArr;
    }

    /**
     * jsonString2List 把一个json数组串转换成实体数组，且数组元素的属性含有另外实例Bean
     *
     * @param jsonArrStr e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
     * @param clazz      e.g. MyBean.class
     * @param classMap   e.g. classMap.put("data", Person.class)
     * @return Object[]
     */
    public static Object[] jsonString2List(String jsonArrStr, Class clazz, Map classMap) {
        JSONArray array = JSONArray.fromObject(jsonArrStr);
        Object[] obj = new Object[array.size()];
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            obj[i] = JSONObject.toBean(jsonObject, clazz, classMap);
        }
        return obj;
    }

    /**
     * jsonString2List 把一个json数组串转换成存放普通类型元素的集合
     *
     * @param jsonArrStr e.g. ['get',1,true,null]
     * @return List
     */
    public static List jsonString2List(String jsonArrStr) {
        JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
        List list = new ArrayList();
        for (int i = 0; i < jsonArr.size(); i++) {
            list.add(jsonArr.get(i));
        }
        return list;
    }

    /**
     * jsonString2ListBean 把一个json数组串转换成集合，且集合里存放的为实例Bean
     *
     * @param jsonArrStr e.g. [{'name':'get'},{'name':'set'}]
     * @param clazz
     * @return List
     */
    public static List jsonString2ListBean(String jsonArrStr, Class clazz) {
        JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
        List list = new ArrayList();
        for (int i = 0; i < jsonArr.size(); i++) {
            list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz));
        }
        return list;
    }

    /**
     * jsonString2ListBean 把一个json数组串转换成集合，且集合里的对象的属性含有另外实例Bean
     *
     * @param jsonArrStr e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
     * @param clazz      e.g. MyBean.class
     * @param classMap   e.g. classMap.put("data", Person.class)
     * @return List
     */
    public static List jsonString2ListBean(String jsonArrStr, Class clazz, Map classMap) {
        JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
        List list = new ArrayList();
        for (int i = 0; i < jsonArr.size(); i++) {
            list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz, classMap));
        }
        return list;
    }

    /**
     * jsonString2Map 把json对象串转换成map对象
     *
     * @param jsonStr e.g. {'name':'get','int':1,'double',1.1,'null':null}
     * @return Map
     */
    public static Map jsonString2Map(String jsonStr) {
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Map map = new HashMap();
        for (Iterator iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = (String) iter.next();
            map.put(key, jsonObject.get(key));
        }
        return map;
    }

    /**
     * jsonString2MapBean 把json对象串转换成map对象，且map对象里存放的为其他实体Bean
     *
     * @param jsonStr e.g. {'data1':{'name':'get'},'data2':{'name':'set'}}
     * @param clazz   e.g. Person.class
     * @return Map
     */
    public static Map jsonString2MapBean(String jsonStr, Class clazz) {
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Map map = new HashMap();
        for (Iterator iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = (String) iter.next();
            map.put(key, JSONObject.toBean(jsonObject.getJSONObject(key), clazz));
        }
        return map;
    }

    /**
     * jsonString2MapBean 把json对象串转换成map对象，且map对象里存放的其他实体Bean还含有另外实体Bean
     *
     * @param jsonStr  e.g. {'mybean':{'data':[{'name':'get'}]}}
     * @param clazz    e.g. MyBean.class
     * @param classMap e.g. classMap.put("data", Person.class)
     * @return Map
     */
    public static Map jsonString2MapBean(String jsonStr, Class clazz, Map classMap) {
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Map map = new HashMap();
        for (Iterator iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = (String) iter.next();
            map.put(key, JSONObject.toBean(jsonObject.getJSONObject(key), clazz, classMap));
        }
        return map;
    }

    /**
     * object2JsonString 把实体Bean、Map对象、数组、列表集合转换成Json串
     *
     * @param obj
     * @return
     * @throws Exception String
     */
    public static String object2JsonString(Object obj) {
        String jsonStr = null;
        //Json配置
        JsonConfig jsonCfg = new JsonConfig();
        //注册日期处理器
        jsonCfg.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor("yyyyMMdd_HHmmss"));
        if (obj == null) {
            return "{}";
        }
        if (obj instanceof Collection || obj instanceof Object[]) {
            jsonStr = JSONArray.fromObject(obj, jsonCfg).toString();
        } else {
            jsonStr = JSONObject.fromObject(obj, jsonCfg).toString();
        }
        return jsonStr;
    }

    /**
     * object2XML 把json串、数组、集合(collection map)、实体Bean转换成XML
     * XMLSerializer API：
     * http://json-lib.sourceforge.net/apidocs/net/sf/json/xml/XMLSerializer.html
     * 具体实例请参考：
     * http://json-lib.sourceforge.net/xref-test/net/sf/json/xml/TestXMLSerializer_writes.html
     * http://json-lib.sourceforge.net/xref-test/net/sf/json/xml/TestXMLSerializer_writes.html
     *
     * @param obj
     * @return
     * @throws Exception String
     */
    public static String object2XML(Object obj) {
        XMLSerializer xmlSerial = new XMLSerializer();
        //Json配置
        JsonConfig jsonCfg = new JsonConfig();
        //注册日期处理器
        jsonCfg.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor("yyyyMMdd_HHmmss"));
        if ((String.class.isInstance(obj) && String.valueOf(obj).startsWith("[")) || obj.getClass().isArray()
                || Collection.class.isInstance(obj)) {
            JSONArray jsonArr = JSONArray.fromObject(obj, jsonCfg);
            return xmlSerial.write(jsonArr);
        } else {
            JSONObject jsonObj = JSONObject.fromObject(obj, jsonCfg);
            return xmlSerial.write(jsonObj);
        }
    }

    /**
     * xml2JsonString 从XML转json串
     *
     * @param xml
     * @return String
     */
    public static String xml2JsonString(String xml) {
        XMLSerializer xmlSerial = new XMLSerializer();
        return String.valueOf(xmlSerial.read(xml));
    }
}

/**
 * @ProjectName：srp
 * @ClassName：JsonDateValueProcessor Json日期处理器
 * @ClassRemark：
 * @Author：T08388
 * @Date：Dec 26, 2011 10:01:17 AM
 * @UpdateUser：T08388
 * @UpdateDate：Dec 26, 2011 10:01:17 AM
 * @UpdateRemark：
 * @version: 1
 */
class JsonDateValueProcessor implements JsonValueProcessor {
    private String format = "yyyyMMdd_HHmmss";

    public JsonDateValueProcessor() {
    }

    public JsonDateValueProcessor(String format) {
        this.format = format;
    }

    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value, jsonConfig);
    }

    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        return process(value, jsonConfig);
    }

    private Object process(Object value, JsonConfig jsonConfig) {
        if (value instanceof Date) {
            String str = new SimpleDateFormat(format).format((Date) value);
            return str;
        }
        return value == null ? null : value.toString();
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}