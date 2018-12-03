package com.sitech.paas.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by 72707 on 2018/9/6.
 */
public class JsonUtils {


    /**
     * json字符串数组转换为JsonArray对象
     * @param json
     * @return
     */
    public static JsonArray changeStr2JsonArray(String json){
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(json).getAsJsonArray();
        return array;
    }
    
    public static JsonObject changeStr2JsonObj(String json){
        JsonParser parser = new JsonParser();
        JsonObject JsonObject = parser.parse(json).getAsJsonObject();
        return JsonObject;
    }


}
