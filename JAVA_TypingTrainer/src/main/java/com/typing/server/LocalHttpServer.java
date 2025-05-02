package com.typing.server;

import com.sun.net.httpserver.HttpServer;
import com.typing.controller.ChatController;
import com.typing.controller.UserController;
import com.typing.model.dto.ChatMessageDto;
import com.typing.model.dto.UserDto;
import com.typing.util.CORSFilter;
import com.typing.util.JsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.List;

/*
 * api는 httpServer.createContext() 이용해 controller의 메소드와 연결 
 * http 요청(ex. CRUD기능)할 때 CORSFilter 꼭 사용 필요
 * 적용 순서 -> handlePreflight 함수 실행 
 * 		   -> if("GET" 또는 "POST".equals(exchange~~~){applyCors 함수 실행}
 * httpServer.createContext("/chat", exchange -> {} 부분 참고해주세요.
 * */
public class LocalHttpServer {
	private int port;
	private final ChatController chatController = new ChatController();
	private final UserController userController = new UserController();
	
	public LocalHttpServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        // 서버 객체 준비
        InetSocketAddress address = new InetSocketAddress(port);
        HttpServer httpServer = HttpServer.create(address, 0);
        
        // HTTP 요청을 처리할 핸들러 설정
        httpServer.createContext("/", exchange -> {
            // 요청 처리
            if ("POST".equals(exchange.getRequestMethod())) {
                // 요청 바디 읽고 DTO로 변환
                // userController.signup(dto) 호출
            }
            // 응답 반환
        });

        httpServer.createContext("/signup", exchange -> {
        	
        	// 프리플라이트 처리 완료
        	if (CORSFilter.handlePreflight(exchange)) {
                return;
            }
        	
        	if("POST".equals(exchange.getRequestMethod())) {
        		// CORS 설정
        		CORSFilter.applyCORS(exchange);
        		
        		// 요청 바디 읽고 DTO로 변환
        		InputStream inputStream = exchange.getRequestBody();
        		byte[] bytes = inputStream.readAllBytes();
        		String body = new String(bytes);
        		UserDto dto = JsonUtil.fromJson(body,UserDto.class);
        		System.out.println("signup requestbody"+body);
        		
                // userController.signup(dto) 호출
        		try {
        			userController.signup(dto);
 			
        			// 응답 헤더 설정
                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

                    // 응답 전송
                    exchange.sendResponseHeaders(200,-1);  // 200 OK, -1이 기본값인듯
                    exchange.close();
        		}catch(Exception e) {//dao, service에서 던진 예외들 여기서 처리 
        			// 예외 발생 시: 에러 응답
                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

                    // 에러 메시지 설정
                    String errorMessage = "{\"message\":\"" + e.getMessage() + "\"}";  // 예외 메시지 ex. throw new Exception("") 여기서 설정한 메세지
                    byte[] responseBytes = errorMessage.getBytes("UTF-8");
                    System.out.println("signup error response"+errorMessage);	
                    // 상태 코드 설정 (500 Internal Server Error 등)
                    exchange.sendResponseHeaders(500, responseBytes.length);  // 500 Internal Server Error
                    exchange.getResponseBody().write(responseBytes);
                    exchange.close();
        		}
        	}
        });
        
        httpServer.createContext("/login", exchange -> {
            // POST /login -> userController.login 호출
        });

        httpServer.createContext("/chat", exchange -> {
        	// CORSFilter : 
        	if (CORSFilter.handlePreflight(exchange)) {
                return; // 프리플라이트 처리 완료
            }

        	if ("GET".equals(exchange.getRequestMethod())) {
        		
        		
        		// CORS 헤더 적용
                CORSFilter.applyCORS(exchange);
        	   
                // Chat 기록을 가져옴
                List<ChatMessageDto> history = chatController.getChatHistory();

                // JSON으로 변환
                String json = JsonUtil.toJson(history);

                // 응답 헤더 설정
                exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

                // 응답 전송
                byte[] responseBytes = json.getBytes("UTF-8");
                exchange.sendResponseHeaders(200, responseBytes.length);
                exchange.getResponseBody().write(responseBytes);
                exchange.close();
        	} else {
                // 허용되지 않은 메서드일 경우
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                exchange.close();
            }
        });
        //httpServer 시작
        httpServer.start();
    }
}
