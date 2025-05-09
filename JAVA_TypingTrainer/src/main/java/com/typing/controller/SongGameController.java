package com.typing.controller;

import com.typing.model.dto.SongDto;
import com.typing.model.dto.SongGameSetting;
import com.typing.service.Song.SongGameService;
import com.typing.service.Song.SongGameServiceImpl;

public class SongGameController {

    private final SongGameService gameService = new SongGameServiceImpl();

    public void startGame(SongGameSetting setting) {
        gameService.startGame(setting);
    }

    public SongDto nextQuestion() {
        return gameService.nextQuestion();
    }

    public boolean checkAnswer(String input) {
        return gameService.checkAnswer(input);
    }

    public String viewHint() {
        return gameService.viewHint();
    }

    public void endGame() {
      //  gameService.endGame();
    }
}