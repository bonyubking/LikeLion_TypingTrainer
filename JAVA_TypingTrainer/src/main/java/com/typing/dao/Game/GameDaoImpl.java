package com.typing.dao.Game;
import com.typing.model.entity.GameRecord;
import com.typing.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GameDaoImpl implements GameDao {
    @Override
    public void save(GameRecord record) {
        String sql = "INSERT INTO game_records (level, time, mode) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, record.getLevel());
            pstmt.setString(2, record.getTime());
            pstmt.setString(3, record.getMode());
            pstmt.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}