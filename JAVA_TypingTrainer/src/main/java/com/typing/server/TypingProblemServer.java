package com.typing.server;

import com.google.gson.Gson;
import com.typing.dao.Typing.TypingProblemDao;
import com.typing.dao.Typing.TypingProblemDaoImpl;
import com.typing.model.entity.TypingProblem;

import java.util.Optional;

public class TypingProblemServer {

    private final TypingProblemDao dao = new TypingProblemDaoImpl();
    public String getProblemJson(String language, String difficulty, String type) {

        TypingProblem problem = dao.findRandomProblem(language, difficulty, type);

        if (problem != null) {
            return new Gson().toJson(problem);
        } else {
            return "{\"error\": \"문제를 찾을 수 없습니다.\"}";
        }
    }
}
