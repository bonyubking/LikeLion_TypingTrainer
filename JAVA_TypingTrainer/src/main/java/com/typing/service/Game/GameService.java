package com.typing.service.Game;

import com.typing.dao.Game.GameDao;
import com.typing.dao.Game.GameDaoImpl;
import com.typing.model.dto.GameRequestDto;
import com.typing.model.entity.GameRecord;

public class GameService {
    private GameDao gameDao = new GameDaoImpl();

    public void saveGameSettings(GameRequestDto dto) {
        if (dto.getLevel() == null || dto.getTime() == null || dto.getMode() == null) {
            throw new RuntimeException("입력값 누락");
        }
        GameRecord record = new GameRecord(dto.getLevel(), dto.getTime(), dto.getMode());
        gameDao.save(record);
    }
}