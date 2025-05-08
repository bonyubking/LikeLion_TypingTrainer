package com.typing.model.dto;

public class SongRecordDTO {
	
	private int recordId;
	private int userId;
	private String Uid;
	private String nickname;
	private int duration;
	private int correctCount;
	private String playedAt;
	private String genre;
	private int hintTime;
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUid() {
		return Uid;
	}

	public void setUid(String uid) {
		Uid = uid;
	}

	public int getCorrectCount() {
		return correctCount;
	}

	public void setCorrectCount(int correctCount) {
		this.correctCount = correctCount;
	}

	public String getPlayedAt() {
		return playedAt;
	}

	public void setPlayedAt(String playedAt) {
		this.playedAt = playedAt;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getHintTime() {
		return hintTime;
	}

	public void setHintTime(int hintTime) {
		this.hintTime = hintTime;
	}

	public SongRecordDTO(int recordId, int userId, String uid, String nickname, int duration, int correctCount, String playedAt, String genre,
			int hintTime) {
		
		this.recordId = recordId;
		this.userId = userId;
		this.Uid = uid;
		this.nickname = nickname;
		this.duration = duration;
		this.correctCount = correctCount;
		this.playedAt = playedAt;
		this.genre = genre;
		this.hintTime = hintTime;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public SongRecordDTO() {
		
	}
	
	
	
	

}
