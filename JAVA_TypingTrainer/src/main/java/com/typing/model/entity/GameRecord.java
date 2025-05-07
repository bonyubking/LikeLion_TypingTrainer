package com.typing.model.entity;

public class GameRecord {
	 private String level;
	    private String time;
	    private String mode;

	    public GameRecord(String level, String time, String mode) {
	        this.level = level;
	        this.time = time;
	        this.mode = mode;
	    }

	    public String getLevel() { return level; }
	    public String getTime() { return time; }
	    public String getMode() { return mode; }
}
