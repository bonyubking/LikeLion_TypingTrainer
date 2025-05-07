package com.typing.model.dto;

import java.util.Date;

public class ChatMessageDto {
	private Long chatId;
    private String content;
    private Date createdAt;
    private Long userId;
    private String nickname;

    public ChatMessageDto(String content, Date createdAt, Long userId, String nickname) {
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
        this.nickname = nickname;
    }
    public ChatMessageDto(String content, Date createdAt, Long userId) {
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public void setChatId(Long chatId) {this.chatId = chatId;}
    public Long getChatId() { return chatId; }
    public String getContent() { return content; }
    public Date getCreatedAt() { return createdAt; }
    public Long getUserId() { return userId; }
    public void setNickname(String nickname) {this.nickname=nickname;}
    public String getNickname() {return nickname;}
}
