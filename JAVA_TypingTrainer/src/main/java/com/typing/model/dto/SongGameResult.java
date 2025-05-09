package com.typing.model.dto;

import java.util.ArrayList;
import java.util.List;

public class SongGameResult {
	private int userId;
	private int playtime;
	private int hintTime;
	private int correctCount;
	private List<String> genre = new ArrayList<>();
	private List<Integer> excludedSongIds;
	
	public SongGameResult(int userId, int playtime, int hintTime, int correctCount, List<String> genre) {
		this.userId = userId;
		this.playtime = playtime;
		this.hintTime = hintTime;
		this.correctCount = correctCount;
		this.genre = genre; 
	}
	public int getUserId() {
		return userId;
	}
	public int getPlaytime() {
		return playtime;
	}
	public int getHintTime() {
		return hintTime;
	}
	public List<String> getGenre() {
		return genre;
	}
	public List<Integer> getExcludedSongIds() {
		return excludedSongIds;
	}
	public int getCorrectCount() {
		return correctCount;
	}
	
}
