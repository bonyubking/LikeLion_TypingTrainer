package com.typing.util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/*
 * 서버, 클라이언트간 데이터 전송 포맷을 설정 
 * 예시
 * JSON -> String : String json = JsonUtil.toJson(history);
 * */
public class JsonUtil {
	private static final Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
    
    // String 값 추출
    public static String getStringValueByKey(String json, String key) {
    	JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        if (!jsonObject.has(key)) throw new RuntimeException("Key '" + key + "' 가 존재하지 않습니다.");
        return jsonObject.get(key).getAsString();
    }

    // int 값 추출
    public static Integer getIntValueByKey(String json, String key) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        if (!jsonObject.has(key)) throw new RuntimeException("Key '" + key + "' 가 존재하지 않습니다.");
        return jsonObject.get(key).getAsInt();
    }

    // long 값 추출
    public static Long getLongValueByKey(String json, String key) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        if (!jsonObject.has(key)) throw new RuntimeException("Key '" + key + "' 가 존재하지 않습니다.");
        return jsonObject.get(key).getAsLong();
    }

    // boolean 값 추출
    public static Boolean getBooleanValueByKey(String json, String key) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        if (!jsonObject.has(key)) throw new RuntimeException("Key '" + key + "' 가 존재하지 않습니다.");
        return jsonObject.get(key).getAsBoolean();
    }
}
