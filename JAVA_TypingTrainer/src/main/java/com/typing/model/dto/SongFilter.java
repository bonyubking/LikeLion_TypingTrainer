package com.typing.model.dto;

public class SongFilter {
	
	private Integer userId;
	private String genre;
	private Integer hintTime;
	
	public SongFilter(Integer userId, String genre, Integer hintTime) {

		this.userId = userId;
		this.genre = genre;
		this.hintTime = hintTime;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Integer getHintTime() {
		return hintTime;
	}

	public void setHintTime(Integer hintTime) {
		this.hintTime = hintTime;
	}

	public SongFilter() {
		
	}

	
	

}
