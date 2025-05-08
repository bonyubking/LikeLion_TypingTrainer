package com.typing.model.entity;

public class TypingProblem {
    private int problemId;
    private String content;
    private String language;
    private String difficulty;
    private String type;

    public TypingProblem(int problemId, String content, String language, String difficulty, String type) {
        this.problemId = problemId;
        this.content = content;
        this.language = language;
        this.difficulty = difficulty;
        this.type = type;
    }

    public TypingProblem() {}

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
} 