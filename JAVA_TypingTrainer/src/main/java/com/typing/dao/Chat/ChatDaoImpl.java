package com.typing.dao.Chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			java.sql.Date sqlDate = new java.sql.Date(chat.getCreatedAt().getTime());
            ps.setString(1, chat.getContent());
            ps.setDate(2, sqlDate);
            ps.setLong(3, chat.getUserId());
            ps.executeUpdate();
            System.out.println("정상 저장 완료");
            ChatMessageDto dto = new ChatMessageDto(
                    chat.getContent(),
                    chat.getCreatedAt(),
                    chat.getUserId()
                ); // chatId가 포함된 객체 리턴
            dto.setNickname("홍길동"); // 닉네임데이터 저장 로직 수정 예정
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
			Statement stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(selectAllSql)){
			while (rs.next()) {
                Long chatId = rs.getLong("chat_id");
                String content = rs.getString("content");
                Long userId = rs.getLong("user_id");          
                java.util.Date utilDate = new java.util.Date(rs.getDate("created_at").getTime());
                Date createdAt = utilDate;
                Chat chat = new Chat(content, createdAt, userId);
                chat.setChatId(chatId);
                ChatMessageDto dto = new ChatMessageDto(
                        chat.getContent(),
                        chat.getCreatedAt(),
                        chat.getUserId()
                    );
                dto.setNickname("홍길동"); // 닉네임데이터 저장 로직 수정 예정
                chats.add(dto);
            }
		}catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println(chats);
		return chats;
	}

}
