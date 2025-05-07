package com.typing.model.dto;


public class GameRequestDto {
    private String level;
    private String time;
    private String mode;

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
}