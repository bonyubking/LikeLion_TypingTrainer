package com.typing.dao.Typing;

import com.typing.model.entity.TypingProblem;

public interface TypingProblemDao {
    TypingProblem findRandomProblem(String language, String difficulty, String type);
}
