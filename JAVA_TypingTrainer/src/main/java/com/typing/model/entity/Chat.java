package com.typing.model.entity;

import java.util.Date;

public class Chat {
    private Long chatId;
    private String content;
    private Date createdAt;
    private Long userId;

    
    public Chat(String content, Date createdAt, Long userId) {
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public void setChatId(Long chatId) {this.chatId = chatId;}
    public Long getChatId() { return chatId; }
    public String getContent() { return content; }
    public Date getCreatedAt() { return createdAt; }
    public Long getUserId() { return userId; }
}