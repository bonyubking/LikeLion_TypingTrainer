package com.typing.util;
import com.google.gson.Gson;

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
}
