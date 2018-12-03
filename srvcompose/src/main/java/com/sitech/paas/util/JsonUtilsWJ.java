package com.sitech.paas.util;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * @类描述：自定义转换类
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.util
 * @类名称：JsonUtilsWJ
 * @创建人：wangjun_paas
 * @创建时间：2018年9月26日上午11:08:57
 * @修改人：wangjun_paas
 * @修改时间：2018年9月26日上午11:08:57
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
public class JsonUtilsWJ {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

   
    /**
     * 
     * @描述:将对象转化成json字符串
     * @方法名: objectToJson
     * @param data
     * @return
     * @返回类型 String
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午11:09:52
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午11:09:52
     * @修改备注
     * @since
     * @throws
     */
    public static String objectToJson(Object data) {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
  
    /**
     * 
     * @描述:将json结果集转化成对象
     * @方法名: jsonToPojo
     * @param jsonData
     * @param beanType
     * @return
     * @返回类型 T
     * @创建人 wangjun_paas
     * @创建时间 2018年9月26日上午11:10:52
     * @修改人 wangjun_paas
     * @修改时间 2018年9月26日上午11:10:52
     * @修改备注
     * @since
     * @throws
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    

    /**
     * 
     * @描述:将json数据转换成pojo对象list
     * @方法名: jsonToList
     * @param jsonData
     * @param beanType
     * @return
     * @返回类型 List<T>
     * @创建人 NeverGiveUp-WJ
     * @创建时间 2018年9月26日上午11:12:02
     * @修改人 NeverGiveUp-WJ
     * @修改时间 2018年9月26日上午11:12:02
     * @修改备注
     * @since
     * @throws
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
}
