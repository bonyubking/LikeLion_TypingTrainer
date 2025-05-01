package com.typing.service.Chat;

import java.util.List;

import com.typing.model.dto.ChatMessageDto;

public interface ChatService {
	ChatMessageDto sendMessage(ChatMessageDto dto);
    List<ChatMessageDto> getChatHistory();
}
