package com.typing.service.Song;

import com.typing.dao.Song.SongDao;
import com.typing.dao.Song.SongDaoImpl;
import com.typing.model.dto.SongDto;
import com.typing.model.dto.SongGameSetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongGameServiceImpl implements SongGameService {

    private final SongDao songDao = new SongDaoImpl();

    private SongGameSetting setting;
    private List<Integer> usedSongIds = new ArrayList<>();

    private SongDto currentSong;
    private List<String> lyricLines;
    private int currentHintIndex = 0;
    private int correctCount = 0;

    public void startGame(SongGameSetting setting) {
        this.setting = setting;
        this.usedSongIds.clear();
        this.correctCount = 0;
        System.out.println("🎮 게임 시작!");
    }

    public SongDto nextQuestion() {
        setting.setExcludedSongIds(usedSongIds);
        List<SongDto> songs = songDao.getRandomSongsByGenre(setting);

        if (songs.isEmpty()) {
            System.out.println("⚠️ 출제할 노래가 없습니다.");
            return null;
        }

        currentSong = songs.get(0);
        usedSongIds.add(currentSong.getId());

        lyricLines = Arrays.asList(currentSong.getLyrics().split("\n"));
        currentHintIndex = 0;

        System.out.println("📘 문제 출제: " + currentSong.getLyrics());
        return currentSong;
    }

    public boolean checkAnswer(String input) {
        if (currentSong == null) return false;

        boolean isCorrect = input.trim().equalsIgnoreCase(currentSong.getTitle());

        if (isCorrect) {
            correctCount++;
            System.out.println("✅ 정답입니다!");
        } else {
            System.out.println("❌ 오답입니다.");
        }

        return isCorrect;
    }

    public String viewHint() {
        if (lyricLines == null || currentHintIndex >= lyricLines.size()) {
            return null;
        }

        String hint = lyricLines.get(currentHintIndex++);
        System.out.println("💡 힌트: " + hint);
        return hint;
    }

    public void endGame() {
        System.out.println("🛑 게임 종료!");
        System.out.println("총 정답 수: " + correctCount);
    }
}
