package com.typing.dao.Chat;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.typing.model.dto.ChatMessageDto;
import com.typing.model.entity.Chat;
import com.typing.util.DBUtil;

public class ChatDaoImpl implements ChatDao {
	private static final ThreadLocal<Connection> threadLocalConnection = ThreadLocal.withInitial(() -> DBUtil.getConnection());
	
	@Override
	public ChatMessageDto save(Chat chat) {
		Connection connection = threadLocalConnection.get();
		try (PreparedStatement ps = connection.prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS)) {
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(chat.getCreatedAt().getTime());            
			ps.setString(1, chat.getContent());
            ps.setTimestamp(2, sqlDate);
            ps.setLong(3, chat.getUserId());
            ps.executeUpdate();
            ChatMessageDto dto = new ChatMessageDto(
                    chat.getContent(),
                    chat.getCreatedAt(),
                    chat.getUserId()
                ); // chatId가 포함된 객체 리턴
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                Long generatedId = rs.getLong(1);
                dto.setChatId(generatedId);  // chat 객체에 chatId 세팅
            }
            connection.commit();
            rs.close();
            ps.close();
            return dto;  
        }catch(SQLException e) {
        	e.printStackTrace();
        	return null;
        }
	}

	@Override
	public List<ChatMessageDto> findTop30ByOrderByCreatedAtDesc() {
		List<ChatMessageDto> chats = new ArrayList<>();
		Connection connection = DBUtil.sharedConnection();
		try(
			PreparedStatement ps = connection.prepareStatement(selectAllSql);
	        ResultSet rs = ps.executeQuery();
			){
			while (rs.next()) {
                Long chatId = rs.getLong("chat_id");
                String content = rs.getString("content");
                Long userId = rs.getLong("user_id");          
                java.util.Date utilDate = new java.util.Date(rs.getTimestamp("created_at").getTime());
                Date createdAt = utilDate;
                String nickname = rs.getString("nickname");
                Chat chat = new Chat(content, createdAt, userId);
                chat.setChatId(chatId);
                ChatMessageDto dto = new ChatMessageDto(
                        chat.getContent(),
                        chat.getCreatedAt(),
                        chat.getUserId(),
                        nickname
                    );
                chats.add(dto);
            }
		}catch(SQLException e) {
			throw new RuntimeException("채팅 기록 조회 중 오류가 발생했습니다.");
		}
		return chats;
	}

}
