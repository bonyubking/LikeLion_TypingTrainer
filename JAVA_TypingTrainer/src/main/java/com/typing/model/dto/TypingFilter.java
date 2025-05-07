package com.typing.model.dto;

public class TypingFilter {
	
	private Integer userId;
	private String contentType;
	private String difficulty;
	private String language;
	
	public TypingFilter() {
		
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public TypingFilter(Integer userId, String contentType, String difficulty, String language) {

		this.userId = userId;
		this.contentType = contentType;
		this.difficulty = difficulty;
		this.language = language;
	}
	
	

}
