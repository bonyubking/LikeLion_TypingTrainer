package com.typing.model.dto;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalTime;

public class TypingRecordDTO {
		
	private int recordId;
	private int userId;
	private String Uid;
	private String nickname;
	private int duration;
	private int correctCount;
	private int typingSpeed;
	private BigDecimal accuracy;
	private String playedAt;
	private String contentType;
	private String difficulty;
	
	
	
	public TypingRecordDTO(int recordId, int userId, String Uid, String nickname, int duration, int correctCount, int typingSpeed,
			BigDecimal accuracy, String playedAt, String contentType, String difficulty, String language) {

		this.recordId = recordId;
		this.userId = userId;
		this.Uid = Uid;
		this.nickname = nickname;
		this.duration = duration;
		this.correctCount = correctCount;
		this.typingSpeed = typingSpeed;
		this.accuracy = accuracy;
		this.playedAt = playedAt;
		this.contentType = contentType;
		this.difficulty = difficulty;
		this.language = language;
	}



	public String getUid() {
		return Uid;
	}

	public void setUid(String uid) {
		Uid = uid;
	}
	
	public String getNickname() {
		return nickname;
	}



	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getCorrectCount() {
		return correctCount;
	}

	public void setCorrectCount(int correctCount) {
		this.correctCount = correctCount;
	}

	public int getTypingSpeed() {
		return typingSpeed;
	}

	public void setTypingSpeed(int typingSpeed) {
		this.typingSpeed = typingSpeed;
	}

	public BigDecimal getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(BigDecimal accuracy) {
		this.accuracy = accuracy;
	}

	public String getPlayedAt() {
		return playedAt;
	}

	public void setPlayedAt(String playedAt) {
		this.playedAt = playedAt;
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

	private String language;
	
    public TypingRecordDTO() {
    
    }
	
    
	

}
