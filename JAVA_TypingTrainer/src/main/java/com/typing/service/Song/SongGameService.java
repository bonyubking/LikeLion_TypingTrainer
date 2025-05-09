package com.typing.service.Song;

import com.typing.model.dto.SongDto;
import com.typing.model.dto.SongGameResult;
import com.typing.model.dto.SongGameSetting;

public interface SongGameService {
	SongGameSetting getCurrentSetting();  // 설정 정보 저장
	void startGame(SongGameSetting setting);  // 게임 시작
	SongDto nextQuestion();  // 다음 문제 불러오기
	boolean checkAnswer(String input);  // 정답 확인
	int getHintIndex();  // 힌트 표출 - 힌트 인덱스
	String viewHint();  // 힌트 표출	
	void endGame(SongGameResult result);  // 게임 종료	
}
