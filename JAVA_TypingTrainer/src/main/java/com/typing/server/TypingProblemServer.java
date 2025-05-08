package com.typing.server;

import com.google.gson.Gson;
import com.typing.dao.Typing.TypingProblemDao;
import com.typing.dao.Typing.TypingProblemDaoImpl;
import com.typing.model.entity.TypingProblem;

import java.util.Optional;

public class TypingProblemServer {

    private final TypingProblemDao dao = new TypingProblemDaoImpl();
    public String getProblemJson(String language, String difficulty, String type) {
        System.out.println("🟡 요청 도착 → lang=" + language + ", diff=" + difficulty + ", type=" + type);

        TypingProblem problem = dao.findRandomProblem(language, difficulty, type);

        if (problem != null) {
            System.out.println("🟢 문제 찾음 → " + problem.getContent());
            return new Gson().toJson(problem);
        } else {
            System.out.println("🔴 문제 없음 → null 반환");
            return "{\"error\": \"문제를 찾을 수 없습니다.\"}";
        }
    }
}
