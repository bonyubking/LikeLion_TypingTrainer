package com.typing.service.Chat;

import java.util.List;
import java.util.stream.Collectors;

import com.typing.dao.Chat.ChatDao;
import com.typing.dao.Chat.ChatDaoImpl;
import com.typing.model.dto.ChatMessageDto;
import com.typing.model.entity.Chat;

public class ChatServiceImpl implements ChatService {
	private final ChatDao chatDao = new ChatDaoImpl();;

	@Override
	public ChatMessageDto sendMessage(ChatMessageDto dto) {
		Chat newChat = new Chat(dto.getContent(), dto.getCreatedAt(), dto.getUserId());
		ChatMessageDto savedDto = chatDao.save(newChat);
		savedDto.setNickname(dto.getNickname());
		return savedDto;
	}

	@Override
	public List<ChatMessageDto> getChatHistory() {
		return chatDao.findTop30ByOrderByCreatedAtDesc();
	}
}
