package com.typing.dao.Typing;

import com.typing.model.entity.TypingProblem;
import com.typing.util.DBManager;

import java.sql.*;

public class TypingProblemDaoImpl implements TypingProblemDao {
    private final Connection conn = DBManager.getConnection();

    @Override
    public TypingProblem findRandomProblem(String language, String difficulty, String type) {
        String sql = "SELECT * FROM problems WHERE language = ? AND difficulty = ? AND type = ? ORDER BY RAND() LIMIT 1";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, language);
            pstmt.setString(2, difficulty);
            pstmt.setString(3, type);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new TypingProblem(
                    rs.getInt("problem_id"),
                    rs.getString("content"),
                    rs.getString("language"),
                    rs.getString("difficulty"),
                    rs.getString("type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}