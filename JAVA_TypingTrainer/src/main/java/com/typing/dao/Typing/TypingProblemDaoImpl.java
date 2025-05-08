package com.typing.dao.Typing;

import com.typing.model.entity.TypingProblem;
import com.typing.util.DBUtil;

import java.sql.*;

public class TypingProblemDaoImpl implements TypingProblemDao {

    private final Connection conn = DBUtil.getConnection();

    @Override
    public TypingProblem findRandomProblem(String language, String difficulty, String type) {
        String tableName;

        if (conn == null) {
            System.out.println("❌ DB 연결이 null 입니다!");
            return null;
        }

        // type 파라미터 값이 실제 테이블 이름으로 결정되도록
        switch (type) {
            case "문장" -> tableName = "Sentences";
            case "밈" -> tableName = "Meme";
            case "단어" -> tableName = "Words";
            default -> {
                System.out.println("❌ 잘못된 type 값: " + type);
                return null;
            }
        }

        // 한/영으로 테이블에서 조회
        String langCode = language.equals("한국어") ? "한" : "영";

        String sql = "SELECT * FROM " + tableName + " WHERE difficulty = ? AND language = ? ORDER BY RAND() LIMIT 1";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, difficulty);
            pstmt.setString(2, langCode);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new TypingProblem(
                    rs.getInt("id"),
                    rs.getString("content"),
                    language,
                    difficulty,
                    type
                );
            } else {
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    
}



