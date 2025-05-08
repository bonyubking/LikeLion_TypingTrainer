package com.typing.service.Song;

import com.typing.dao.Song.SongDao;
import com.typing.dao.Song.SongDaoImpl;
import com.typing.model.dto.SongDto;
import com.typing.model.dto.SongGameSetting;
//import com.typing.model.dto.SongRecordDTO;

import java.util.ArrayList;
import java.util.List;

public class SongGameServiceImpl implements SongGameService {

    private final SongDao songDao = new SongDaoImpl();

    private SongGameSetting setting;
    private List<Integer> usedSongIds = new ArrayList<>();

    private SongDto currentSong;
    private int currentHintIndex = 0;
    private int correctCount = 0;

    @Override
    public void startGame(SongGameSetting setting) {
        this.setting = setting;
        this.usedSongIds.clear();
        this.correctCount = 0;
        this.currentHintIndex = 0;
        System.out.println("=== start game ===");
    }

    @Override
    public SongDto nextQuestion() {
        setting.setExcludedSongIds(usedSongIds);
        List<SongDto> songs = songDao.getRandomSongsByGenre(setting);

        if (songs.isEmpty()) {
            System.out.println("failed to load song data");
            return null;
        }

        currentSong = songs.get(0);
        usedSongIds.add(currentSong.getId());
        currentHintIndex = 0;

        System.out.println("출제 가사: " + currentSong.getLyrics());
        return currentSong;
    }

    @Override
    public boolean checkAnswer(String input) {
        if (currentSong == null) return false;

        // 띄어쓰기, 대소문자 관계없이 정답 처리
        String normalizedInput = input.replaceAll("\\s+", "").toLowerCase();
        String normalizedAnswer = currentSong.getTitle().replaceAll("\\s+", "").toLowerCase();

        boolean isCorrect = normalizedInput.equals(normalizedAnswer);

        if (isCorrect) {
            correctCount++;
            System.out.println("정답! 현재 정답 수: " + correctCount);
        } else {
            System.out.println("오답!");
        }

        return isCorrect;
    }

    @Override
    public String viewHint() {
        if (currentSong == null) return null;

        if (currentHintIndex == 0) {
            currentHintIndex++;
            return "가수 힌트: " + currentSong.getSinger();
        } else if (currentHintIndex == 1) {
            currentHintIndex++;
            return "초성 힌트: " + currentSong.getInitial();
        }

        return null;
    }

    @Override
    public void endGame() {
    	//To-Do::게임 결과 데이터 파싱 및 전달
    }
}
