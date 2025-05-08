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

public class SongGameServer extends WebSocketServer {

    private final SongGameService gameService = new SongGameServiceImpl();
    private final Gson gson = new Gson();

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
                case "answer":
                    handleAnswer(conn, json.get("input").getAsString());
                    break;
                case "skip":
                    handleSkip(conn);
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
            int userId = data.get("userId").getAsInt(); // 필요시 설정에 적용
            // setting.setUserId(userId); // 필요 시 주석 해제
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
        sendNextQuestion(conn);
    }

    private void handleAnswer(WebSocket conn, String input) {
        boolean correct = gameService.checkAnswer(input);
        conn.send("{\"type\":\"" + (correct ? "correct" : "wrong") + "\"}");

        if (correct) {
            sendNextQuestion(conn);
        }
    }

    private void handleSkip(WebSocket conn) {
        conn.send("{\"type\":\"skipped\"}");
        sendNextQuestion(conn);
    }

    private void sendNextQuestion(WebSocket conn) {
        SongDto song = gameService.nextQuestion();
        if (song != null) {
            JsonObject nextQ = new JsonObject();
            nextQ.addProperty("type", "question");
            nextQ.addProperty("lyrics", song.getLyrics());
            conn.send(nextQ.toString());
        } else {
            System.out.println("Maybe something got errors. Song values is null.");
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
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
