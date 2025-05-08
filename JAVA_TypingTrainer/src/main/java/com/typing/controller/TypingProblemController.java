package com.typing.controller;

import com.typing.dao.Typing.TypingProblemDaoImpl;
import com.typing.model.entity.TypingProblem;
import com.typing.util.HttpUtil;
import com.typing.util.JsonUtil;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class TypingProblemController {
    private final TypingProblemDaoImpl problemDao = new TypingProblemDaoImpl();

    public void handle(String query, OutputStream out) {
        try {
            Map<String, String> params = parseQuery(query);
            String language = params.get("language");
            String difficulty = params.get("difficulty");
            String type = params.get("type");

            TypingProblem problem = problemDao.findRandomProblem(language, difficulty, type);

            if (problem != null) {
                HttpUtil.sendJsonResponse(out, JsonUtil.toJson(problem), HttpURLConnection.HTTP_OK);
            } else {
                HttpUtil.sendJsonResponse(out, JsonUtil.toJson(Map.of("error", "문제를 찾을 수 없습니다.")), HttpURLConnection.HTTP_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            HttpUtil.sendJsonResponse(out, JsonUtil.toJson(Map.of("error", "서버 오류 발생")), HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }

    private Map<String, String> parseQuery(String query) throws Exception {
        Map<String, String> map = new HashMap<>();
        if (query == null) return map;
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length == 2) {
                map.put(URLDecoder.decode(pair[0], "UTF-8"), URLDecoder.decode(pair[1], "UTF-8"));
            }
        }
        return map;
    }
}
