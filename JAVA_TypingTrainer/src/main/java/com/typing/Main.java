package com.typing;

import com.typing.server.*;

/*
 * Main 시작과 동시에 웹소켓 서버오 http 서버가 동시에 시작됨
 * 각각 다른 포트, 다른 스레드에서 실행됨
 * HttpServer -> 8080 
 * WebSocketServer -> 8081
 * SongGame WebSocketServer -> 8082
 * 
 * 각 서버는 Main 함수 종료때 같이 종료됨 
 * */

public class Main {
	private static ChatServer wsServer; // Websocket 서버 
    private static LocalHttpServer httpServer; // Http 서버 
	public static void main(String[] args) {
		
		// HTTP 서버
        Thread httpServerThread = new Thread(() -> {
            try {
                httpServer = new LocalHttpServer(8080);
                httpServer.start();
                System.out.println("HTTP 서버 시작: http://localhost:8080");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // WebSocket 서버
        Thread wsServerThread = new Thread(() -> {
            try {
                wsServer = new ChatServer(8081);  // WebSocket 포트 다르게 설정
                wsServer.start();
                System.out.println("WebSocket 서버 시작: ws://localhost:8081");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
     // SongGame WebSocket 서버
        Thread songGameWsThread = new Thread(() -> {
            try {
                SongGameServer songGameServer = new SongGameServer(8082);
                songGameServer.start();
                System.out.println("SongGame WebSocket 서버 시작: ws://localhost:8082/game/song");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 세 서버를 각각 다른 스레드에서 실행
        httpServerThread.start();
        wsServerThread.start();
        songGameWsThread.start();
        
        // Websocket 서버 종료를 위한 shutdown hook 설정
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("서버가 종료 중입니다...");
            try {
                if (wsServer != null) {
                    wsServer.stopServer(); 
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("서버가 종료되었습니다.");
        }));
	}

}
