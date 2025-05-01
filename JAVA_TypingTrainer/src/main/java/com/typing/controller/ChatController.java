package com.typing.controller;

import java.util.List;

import com.typing.model.dto.ChatMessageDto;
import com.typing.service.Chat.ChatService;
import com.typing.service.Chat.ChatServiceImpl;

// ChatMessage 관련 Controller 
public class ChatController {
	private final ChatService chatService = new ChatServiceImpl();

    public ChatMessageDto sendMessage(ChatMessageDto dto) {
        return chatService.sendMessage(dto);
    }

    public List<ChatMessageDto> getChatHistory() {
    	List<ChatMessageDto> dto = chatService.getChatHistory();
        return dto;
    }
}
