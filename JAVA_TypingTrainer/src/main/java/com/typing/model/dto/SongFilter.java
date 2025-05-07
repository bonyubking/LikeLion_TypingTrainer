package com.typing.model.dto;

public class SongFilter {
	
	private Integer userId;
	private String genre;
	private Integer hintTime;
	private Integer duration;
	
	public SongFilter(Integer userId, String genre, Integer hintTime, Integer duration) {

		this.userId = userId;
		this.genre = genre;
		this.hintTime = hintTime;
		this.duration = duration;
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

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public void setHintTime(Integer hintTime) {
		this.hintTime = hintTime;
	}

	public SongFilter() {
		
	}

	
	

}
