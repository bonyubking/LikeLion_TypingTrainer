package com.typing;

import com.typing.http.SimpleHttpServer;
import com.typing.websocket.ChatServer;

public class Main {

	public static void main(String[] args) {
		// HTTP 서버
        Thread httpServerThread = new Thread(() -> {
            try {
                SimpleHttpServer httpServer = new SimpleHttpServer(8080);
                httpServer.start();
                System.out.println("HTTP 서버 시작: http://localhost:8080");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // WebSocket 서버
        Thread wsServerThread = new Thread(() -> {
            try {
                ChatServer wsServer = new ChatServer(8081);  // WebSocket 포트 다르게 설정
                wsServer.start();
                System.out.println("WebSocket 서버 시작: ws://localhost:8081");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 두 서버를 각각 다른 스레드에서 실행
        httpServerThread.start();
        wsServerThread.start();

	}

}
