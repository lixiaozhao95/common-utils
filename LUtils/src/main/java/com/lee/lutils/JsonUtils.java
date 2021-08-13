package com.lee.lutils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * @ClassName JsonUtils
 * @Description Json 工具类
 * @Author lixiaozhao
 * @Date 2021/8/13 15:44
 * @Version 1.0
 */
public class JsonUtils {
    private static Gson mGson = new Gson();

    /**
     * 将对象准换为json字符串
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String toJson(T object) {
        return mGson.toJson(object);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json对象转换为实体对象
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T fromJson(JsonObject json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type type) throws JsonSyntaxException {
        return mGson.fromJson(json, type);
    }

    public static String getJsonFieldString(JSONObject jsonObject, String type) {
        try {
            if (jsonObject == null) return null;
            return (!jsonObject.has(type) ? "" : setNullString(jsonObject.getString(type)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String setNullString(String string) {
        if ("{}".equals(string)) {
            return "";
        }
        return string;
    }
}
