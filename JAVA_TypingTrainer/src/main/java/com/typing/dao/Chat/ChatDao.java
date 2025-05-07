package com.typing.dao.Chat;

import java.util.List;

import com.typing.model.dto.ChatMessageDto;
import com.typing.model.entity.Chat;

public interface ChatDao {
	
	String insertSql = "INSERT INTO CHATS(CONTENT, CREATED_AT, USER_ID) VALUES (?,?,?)";
	String selectAllSql = "SELECT * "
			+ "FROM (SELECT C.*, U.NICKNAME "
			+ "FROM CHATS C JOIN USERS U ON C.USER_ID = U.USER_ID "
			+ "ORDER BY C.CHAT_ID DESC "
			+ "LIMIT 30"
			+ ") AS RECENT_CHATS "
			+ "ORDER BY RECENT_CHATS.CHAT_ID ASC";

	ChatMessageDto save(Chat chat);
	List<ChatMessageDto>findTop30ByOrderByCreatedAtDesc();
}
