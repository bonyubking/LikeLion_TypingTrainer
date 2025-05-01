package com.typing.dao.Chat;

import java.util.List;

import com.typing.model.dto.ChatMessageDto;
import com.typing.model.entity.Chat;

public interface ChatDao {
	
	String insertSql = "INSERT INTO CHATS(CONTENT, CREATED_AT, USER_ID) VALUES (?,?,?)";
	String selectAllSql = "SELECT * FROM CHATS";
	
	ChatMessageDto save(Chat chat);
	List<ChatMessageDto>findTop30ByOrderByCreatedAtDesc();
}
