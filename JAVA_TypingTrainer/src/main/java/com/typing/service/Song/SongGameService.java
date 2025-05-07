package com.typing.service.Song;

import com.typing.model.dto.SongDto;
import com.typing.model.dto.SongGameSetting;

public interface SongGameService {
	void startGame(SongGameSetting setting);  // 게임 시작
	SongDto nextQuestion();  // 다음 문제 불러오기
	boolean checkAnswer(String input);  // 정답 확인
	String viewHint();  // 힌트 표출	
	void endGame();  // 게임 종료	
}
