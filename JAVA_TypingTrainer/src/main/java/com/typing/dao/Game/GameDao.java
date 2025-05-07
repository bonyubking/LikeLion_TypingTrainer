package com.typing.dao.Game;

import com.typing.model.entity.GameRecord;

public interface GameDao {
    void save(GameRecord record);
}