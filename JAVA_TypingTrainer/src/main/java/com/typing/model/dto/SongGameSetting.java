package com.typing.model.dto;

import java.util.ArrayList;
import java.util.List;

public class SongGameSetting {
	private int userId;
	private int playtime;
	private int hintTime;
	private List<String> genre = new ArrayList<>();
	private List<Integer> excludedSongIds;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPlaytime() {
		return playtime;
	}
	public void setPlaytime(int playtime) {
		this.playtime = playtime;
	}
	public int getHintTime() {
		return hintTime;
	}
	public void setHintTime(int hintTime) {
		this.hintTime = hintTime;
	}
	public List<String> getGenre() {
		return genre;
	}
	public void setGenre(List<String> genre) {
		this.genre = genre;
	}
	public List<Integer> getExcludedSongIds() {
		return excludedSongIds;
	}
	public void setExcludedSongIds(List<Integer> excludedSongIds) {
		this.excludedSongIds = excludedSongIds;
	}
}
