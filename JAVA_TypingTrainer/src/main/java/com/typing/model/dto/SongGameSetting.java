package com.typing.model.dto;

import java.util.List;

public class SongGameSetting {
	private int playtime;
	private int hintTime;
	private List<String> genre;
	private List<Integer> excludedSongIds;
	
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
