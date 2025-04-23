package com.typing.http;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
	private int port;
	
	public SimpleHttpServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        // 서버 객체 준비
        InetSocketAddress address = new InetSocketAddress(port);
        HttpServer httpServer = HttpServer.create(address, 0);
        
        // HTTP 요청을 처리할 핸들러 설정
        httpServer.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "<html><body><h1>Welcome to Simple HTTP Server!</h1></body></html>";
                
                // 응답 헤더 설정
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes().length); // HTTP 상태 코드 200 (OK)
                
                // 응답 본문 전송
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });
        
        // 서버 시작
        System.out.println("HTTP 서버 시작: http://localhost:" + port);
        httpServer.start();
    }

}
