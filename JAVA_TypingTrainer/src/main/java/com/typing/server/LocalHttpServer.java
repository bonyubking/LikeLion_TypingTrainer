package com.typing.server;

import com.sun.net.httpserver.HttpServer;
import com.typing.controller.ChatController;
import com.typing.controller.SongRecordController;
import com.typing.controller.TypingRecordController;
import com.typing.model.dto.ChatMessageDto;
import com.typing.model.dto.SongFilter;
import com.typing.model.dto.SongRecordDTO;
import com.typing.model.dto.TypingFilter;
import com.typing.model.dto.TypingRecordDTO;
import com.typing.util.CORSFilter;
import com.typing.util.JsonUtil;


import com.typing.util.QueryString;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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
        

        httpServer.createContext("/typing-records", exchange -> {

            if (CORSFilter.handlePreflight(exchange)) {
                return; // 프리플라이트 처리 완료
            }

            if ("GET".equals(exchange.getRequestMethod())) {
                // CORS 헤더 적용
                CORSFilter.applyCORS(exchange);
 
	            Map<String,String> qs = QueryString.parse(exchange.getRequestURI().getQuery());
	            
	            TypingFilter filter = new TypingFilter();
	            
	            if (qs.containsKey("userId")) 
	                filter.setUserId(Integer.parseInt(qs.get("userId")));
	            filter.setDifficulty(qs.get("difficulty"));
	            filter.setLanguage(qs.get("language"));
	            filter.setContentType(qs.get("content_type"));

	            	
	            System.out.println("GET /typing-records?"+exchange.getRequestURI().getQuery());
	            System.out.println("Filter → " + filter);

	            List<TypingRecordDTO> list = 
	                new TypingRecordController().getByFilter(filter);
	            
	            System.out.println("DAO 반환 개수 = " + list.size());
	
	            String json = JsonUtil.toJson(list);
	            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
	            byte[] responseBytes = json.getBytes("UTF-8");
	            exchange.sendResponseHeaders(200, responseBytes.length);
	            exchange.getResponseBody().write(responseBytes);
	            exchange.getResponseBody().close();
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            exchange.close();
        }
        });
        
        httpServer.createContext("/song-records", exchange -> {

            if (CORSFilter.handlePreflight(exchange)) {
                return; // 프리플라이트 처리 완료
            }

            if ("GET".equals(exchange.getRequestMethod())) {
                // CORS 헤더 적용
                CORSFilter.applyCORS(exchange);
 
	            Map<String,String> qs = QueryString.parse(exchange.getRequestURI().getQuery());
	            
	            SongFilter filter = new SongFilter();
	            
	            if (qs.containsKey("userId")) 
	                filter.setUserId(Integer.parseInt(qs.get("userId")));
	            filter.setGenre(qs.get("genre"));
	            
	            if (qs.containsKey("hint_time") && !qs.get("hint_time").isEmpty()) {
	                try {
	                    filter.setHintTime(Integer.parseInt(qs.get("hint_time")));
	                } catch (NumberFormatException e) {
	                    // 잘못된 형식이면 기본값(또는 무시) 처리
	                    filter.setHintTime(null);
	                }
	            }


	            	
	            System.out.println("GET /song-records?"+exchange.getRequestURI().getQuery());
	            System.out.println("Filter → " + filter);

	            List<SongRecordDTO> list = 
	                new SongRecordController().getByFilter(filter);
	            
	            System.out.println("DAO 반환 개수 = " + list.size());
	
	            String json = JsonUtil.toJson(list);
	            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
	            byte[] responseBytes = json.getBytes("UTF-8");
	            exchange.sendResponseHeaders(200, responseBytes.length);
	            exchange.getResponseBody().write(responseBytes);
	            exchange.getResponseBody().close();
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            exchange.close();
        }
        });


        //httpServer 시작
        httpServer.start();
    }
}
