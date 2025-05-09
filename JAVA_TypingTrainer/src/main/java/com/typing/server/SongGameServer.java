package com.typing.server;

import com.google.gson.*;
import com.typing.model.dto.SongDto;
import com.typing.model.dto.SongGameSetting;
import com.typing.service.Song.SongGameService;
import com.typing.service.Song.SongGameServiceImpl;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SongGameServer extends WebSocketServer {

    private final SongGameService gameService = new SongGameServiceImpl();
    private final Gson gson = new Gson();

    // 연결마다 힌트 타이머 관리
    private final Map<WebSocket, Timer> hintTimers = new HashMap<>();
    private final Map<WebSocket, Integer> hintStageMap = new HashMap<>();

    public SongGameServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("🎵 SongGame WebSocket connected: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        try {
            JsonObject json = JsonParser.parseString(message).getAsJsonObject();
            String type = json.get("type").getAsString();

            switch (type) {
                case "startGame":
                    handleStartGame(conn, json.getAsJsonObject("data"));
                    break;
                case "hint":
                    // 클라이언트에서는 더이상 수동 요청 없음
                    break;
                case "answer":
                    handleAnswer(conn, json.get("input").getAsString());
                    break;
                case "skip":
                    handleSkip(conn);
                    break;
                case "end":
                    handleEnd(conn);
                    break;
                default:
                    conn.send("{\"type\":\"error\",\"message\":\"알 수 없는 메시지 타입입니다.\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            conn.send("{\"type\":\"error\",\"message\":\"서버 처리 중 오류 발생\"}");
        }
    }

    private void handleStartGame(WebSocket conn, JsonObject data) {
        SongGameSetting setting = new SongGameSetting();

        try {
            int userId = data.get("userId").getAsInt();
            // setting.setUserId(userId);
        } catch (Exception e) {
            System.out.println("⚠️ userId 파싱 실패, 기본값 사용");
        }

        setting.setHintTime(data.get("hintInterval").getAsInt());
        setting.setPlaytime(data.get("playtime").getAsInt());

        JsonArray genres = data.getAsJsonArray("genres");
        for (JsonElement genre : genres) {
            setting.getGenre().add(genre.getAsString());
        }

        gameService.startGame(setting);
        sendNextQuestion(conn, setting.getHintTime());
    }

    private void handleAnswer(WebSocket conn, String input) {
        boolean correct = gameService.checkAnswer(input);
        conn.send("{\"type\":\"" + (correct ? "correct" : "wrong") + "\"}");

        if (correct) {
            stopHintTimer(conn);
            sendNextQuestion(conn, gameService.getCurrentSetting().getHintTime());
        }
    }

    private void handleSkip(WebSocket conn) {
        conn.send("{\"type\":\"skipped\"}");
        stopHintTimer(conn);
        sendNextQuestion(conn, gameService.getCurrentSetting().getHintTime());
    }

    private void handleEnd(WebSocket conn) {
        stopHintTimer(conn);
        gameService.endGame();
        conn.send("{\"type\":\"end\"}");
        conn.close();
    }

    private void sendNextQuestion(WebSocket conn, int hintInterval) {
        SongDto song = gameService.nextQuestion();
        if (song != null) {
            JsonObject nextQ = new JsonObject();
            nextQ.addProperty("type", "question");
            nextQ.addProperty("lyrics", song.getLyrics());
            conn.send(nextQ.toString());

            startHintTimer(conn, hintInterval);

        } else {
            conn.send("{\"type\":\"end\"}");
            conn.close();
        }
    }

    private void startHintTimer(WebSocket conn, int hintIntervalSeconds) {
        stopHintTimer(conn); // 이전 타이머 제거

        hintStageMap.put(conn, 0);
        Timer timer = new Timer();
        hintTimers.put(conn, timer);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int stage = hintStageMap.getOrDefault(conn, 0);
                if (stage >= 2) {
                    timer.cancel();
                    return;
                }

                String hint = gameService.viewHint();
                if (hint != null) {
                    JsonObject hintMsg = new JsonObject();
                    hintMsg.addProperty("type", "hint");
                    hintMsg.addProperty("data", hint);
                    conn.send(hintMsg.toString());

                    hintStageMap.put(conn, stage + 1);
                } else {
                    timer.cancel();
                }
            }
        }, hintIntervalSeconds * 1000L, hintIntervalSeconds * 1000L);
    }

    private void stopHintTimer(WebSocket conn) {
        Timer timer = hintTimers.remove(conn);
        if (timer != null) timer.cancel();
        hintStageMap.remove(conn);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        stopHintTimer(conn);
        System.out.println("🛑 SongGame disconnected: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("❌ WebSocket 오류 발생");
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("✅ SongGame 서버 시작 완료");
    }
}
