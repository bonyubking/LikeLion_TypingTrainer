package com.typing.server;

import com.sun.net.httpserver.HttpServer;
import com.typing.server.TypingProblemServer;
import com.typing.controller.ChatController;
import com.typing.model.dto.ChatMessageDto;
import com.typing.util.CORSFilter;
import com.typing.util.JsonUtil;


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
/*
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
        
        // HTTP 요청을 처리할 핸들러 설정
        httpServer.createContext("/post", exchange -> {
            // CORS 필터 처리
            if (CORSFilter.handlePreflight(exchange)) {
                return; // 프리플라이트 처리 완료
            }

            if ("GET".equals(exchange.getRequestMethod())) {
                // CORS 헤더 적용
                CORSFilter.applyCORS(exchange);
                
                // 게시글 목록을 시간순으로 가져오기
                List<PostDTO> postList = postController.getPostsByTime();
                
                // JSON으로 변환
                String jsonResponse = JsonUtil.toJson(postList);
                
                // 응답 헤더 설정
                exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                byte[] responseBytes = jsonResponse.getBytes("UTF-8");
                exchange.sendResponseHeaders(200, responseBytes.length);
                exchange.getResponseBody().write(responseBytes);
                exchange.getResponseBody().close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                exchange.close();
            }
        });

        httpServer.createContext("/post/create", exchange -> {
            // CORS 필터 처리
            if (CORSFilter.handlePreflight(exchange)) {
                return;
            }

            if ("POST".equals(exchange.getRequestMethod())) {
                // CORS 헤더 적용
                CORSFilter.applyCORS(exchange);
                
                // 요청 바디에서 게시글 DTO 추출
                try {

                	String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                	PostDTO postDTO = JsonUtil.fromJson(requestBody, PostDTO.class);


                    int result = postController.createPost(postDTO);
                    String responseMessage = result > 0 ? "{\"status\":\"success\"}" : "{\"status\":\"error\"}";
                    byte[] responseBytes = responseMessage.getBytes("UTF-8");

                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                    exchange.sendResponseHeaders(200, responseBytes.length);
                    exchange.getResponseBody().write(responseBytes);
                } catch (Exception e) {
                    e.printStackTrace();
                    String error = "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}";
                    byte[] errorBytes = error.getBytes("UTF-8");
                    exchange.sendResponseHeaders(500, errorBytes.length);
                    exchange.getResponseBody().write(errorBytes);
                } finally {
                    exchange.getResponseBody().close();
                }
            }
        });

        httpServer.createContext("/post/", exchange -> {
            // CORS 필터 처리
            if (CORSFilter.handlePreflight(exchange)) {
                return;
            }
                
                if ("GET".equals(exchange.getRequestMethod())) {
                    // CORS 헤더 적용
                    CORSFilter.applyCORS(exchange);

                    // URL에서 게시글 ID 추출
                    String path = exchange.getRequestURI().getPath();
                    int postId = Integer.parseInt(path.split("/")[2]); // "/post/{id}" 형태에서 id 추출

                    // 게시글 ID로 게시글 조회
                    PostDTO post = postController.getPostById(postId);
                    
                    if (post != null) {
                        String jsonResponse = JsonUtil.toJson(post);
                        byte[] responseBytes = jsonResponse.getBytes("UTF-8");

                        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                        exchange.sendResponseHeaders(200, responseBytes.length);
                        exchange.getResponseBody().write(responseBytes);
                        exchange.getResponseBody().close(); // ← 이것도 필수
                    } else {
                        // 게시글이 없으면 404 오류 응답
                        exchange.sendResponseHeaders(404, -1); // Not Found
                    }
                    exchange.close();
                } else {
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
	            
	            if (qs.containsKey("duration") && !qs.get("duration").isEmpty()) {
	                try {
	                    filter.setDuration(Integer.parseInt(qs.get("duration")));
	                } catch (NumberFormatException e) {
	                    filter.setDuration(null);
	                }
	            }


	            	
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
                    filter.setDuration(null);
                }
            }
            
            if (qs.containsKey("duration") && !qs.get("duration").isEmpty()) {
                try {
                    filter.setDuration(Integer.parseInt(qs.get("duration")));
                } catch (NumberFormatException e) {
                    filter.setDuration(null);
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
        

		httpServer.createContext("/comment", exchange -> {
		    // 1) 프리플라이트 처리
		    if (CORSFilter.handlePreflight(exchange)) return;
		
		    // 2) GET /comment?postId=123 → 해당 게시글 댓글 조회
		    if ("GET".equals(exchange.getRequestMethod())) {
		        CORSFilter.applyCORS(exchange);
		
		        // 쿼리 파라미터 파싱 (간단히)
		        String query = exchange.getRequestURI().getQuery(); // e.g. "postId=1"
		        int postId = Integer.parseInt(query.split("=")[1]);
		
		        // 댓글 조회
		        List<CommentDTO> comments = new CommentController().selectCommentsBypostId(postId);
		
		        String json = JsonUtil.toJson(comments);
		        byte[] respBytes = json.getBytes(StandardCharsets.UTF_8);
		
		        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
		        exchange.sendResponseHeaders(200, respBytes.length);
		        exchange.getResponseBody().write(respBytes);
		        exchange.close();
		
		    // 3) POST /comment → 댓글 생성
		    } else if ("POST".equals(exchange.getRequestMethod())) {
		        CORSFilter.applyCORS(exchange);
		
		        // 요청 바디에서 DTO 역직렬화
		        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
		        CommentDTO dto = JsonUtil.fromJson(body, CommentDTO.class);
		
		        // 댓글 삽입
		        int result = new CommentController().createComment(dto);
		        if (result > 0) {
		            new CommentController().increaseCommentCount(dto.getPostId());
		        }
		        String res = result > 0
		            ? "{\"status\":\"success\"}"
		            : "{\"status\":\"error\"}";
		
		        byte[] respBytes = res.getBytes(StandardCharsets.UTF_8);
		        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
		        exchange.sendResponseHeaders(200, respBytes.length);
		        exchange.getResponseBody().write(respBytes);
		        exchange.close();
		
		    } else {
		        // 지원하지 않는 메서드
		        exchange.sendResponseHeaders(405, -1);
		        exchange.close();
		    }
		});
		*/
		// 타자게임 문제 불러오기 API
        httpServer.createContext("/api/problem/random", exchange -> {
            if (CORSFilter.handlePreflight(exchange)) return;

            if ("GET".equals(exchange.getRequestMethod())) {
                CORSFilter.applyCORS(exchange);

                String query = exchange.getRequestURI().getQuery();
                String language = null, difficulty = null, type = null;

                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    if (pair.length == 2) {
                        switch (pair[0]) {
                            case "lang" -> language = java.net.URLDecoder.decode(pair[1], "UTF-8");
                            case "diff" -> difficulty = java.net.URLDecoder.decode(pair[1], "UTF-8");
                            case "type" -> type = java.net.URLDecoder.decode(pair[1], "UTF-8");
                        }
                    }
                }

                try {
                    TypingProblemServer problemServer = new TypingProblemServer();  // 정상 선언
                    String json = problemServer.getProblemJson(language, difficulty, type);
                    System.out.println("🟢 최종 JSON 응답 → " + json);
                    byte[] responseBytes = json.getBytes(StandardCharsets.UTF_8);

                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                    exchange.sendResponseHeaders(200, responseBytes.length);
                    exchange.getResponseBody().write(responseBytes);
                } catch (Exception e) {
                    String error = "{\"message\":\"" + e.getMessage() + "\"}";
                    byte[] errorBytes = error.getBytes(StandardCharsets.UTF_8);

                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                    exchange.sendResponseHeaders(500, errorBytes.length);
                    exchange.getResponseBody().write(errorBytes);
                } finally {
                    exchange.getResponseBody().close();
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                exchange.close();
            }
        });

        //httpServer 시작
        httpServer.start();
    }
}
