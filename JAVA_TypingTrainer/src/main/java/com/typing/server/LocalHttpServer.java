package com.typing.server;

import com.sun.net.httpserver.HttpServer;
import com.typing.controller.ChatController;
import com.typing.controller.UserController;
import com.typing.model.dto.ChatMessageDto;
import com.typing.model.dto.UserDto;
import com.typing.controller.CommentController;
import com.typing.controller.PostController;
import com.typing.model.dto.ChatMessageDto;
import com.typing.model.dto.CommentDTO;
import com.typing.model.dto.PostDTO;
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
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
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
/* 예외 종류에 따른 상태코드 설정은 마지막에 작업 예정
 * */
public class LocalHttpServer {
	private int port;
	private final ChatController chatController = new ChatController();
	private final UserController userController = new UserController();
	private final PostController postController = new PostController();

	
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

        // 회원가입 
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
        
        // 로그인
        httpServer.createContext("/login", exchange -> {
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
        		UserDto req = JsonUtil.fromJson(body,UserDto.class); // 없는 필드는 Null이나 기본값으로 설정됨 
        		System.out.println("login requestbody"+body);
        		
                // userController.login(dto) 호출
        		try {
        			UserDto res = userController.login(req);
        			
        			// JSON으로 변환(password, uid 제거) 
                    String json = JsonUtil.toJson(res).replaceAll("\"(password|uId)\"\\s*:\\s*\"[^\"]*\",?", "");
                    System.out.println("변환된 json: "+json);
        			
                    // 응답 헤더 설정
                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

                 // 응답 전송
                    byte[] responseBytes = json.getBytes("UTF-8");
                    exchange.sendResponseHeaders(200, responseBytes.length);
                    exchange.getResponseBody().write(responseBytes);
                    exchange.close();
        		}catch(Exception e) {//dao, service에서 던진 예외들 여기서 처리 
        			// 예외 발생 시: 에러 응답
                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

                    // 에러 메시지 설정
                    String errorMessage = "{\"message\":\"" + e.getMessage() + "\"}";  // 예외 메시지 ex. throw new Exception("") 여기서 설정한 메세지
                    byte[] responseBytes = errorMessage.getBytes("UTF-8");
                    System.out.println("login error response"+errorMessage);	
                    // 상태 코드 설정 (500 Internal Server Error 등)(추가 예외 설정은 작업 마지막에 진행 예정) 
                    exchange.sendResponseHeaders(500, responseBytes.length);  // 500 Internal Server Error
                    exchange.getResponseBody().write(responseBytes);
                    exchange.close();
        		}
        	}
        });
        
        // 닉네임 중복 확인
        httpServer.createContext("/nickname", exchange -> {
        	// CORSFilter : 
        	if (CORSFilter.handlePreflight(exchange)) {
                return; // 프리플라이트 처리 완료
            }

        	if ("GET".equals(exchange.getRequestMethod())) {

        		// CORS 헤더 적용
                CORSFilter.applyCORS(exchange);
                
                // 사용자가 입력한 닉네임을 쿼리 파라미터 이용해서 읽음 ex. /nickname?nickname=abc
                String query = exchange.getRequestURI().getQuery();
                String nickname = null;
                
                if (query != null) {
                    for (String param : query.split("&")) {
                        String[] keyValue = param.split("=");
                        if (keyValue.length == 2 && keyValue[0].equals("nickname")) {
                            nickname = URLDecoder.decode(keyValue[1], "UTF-8");
                            break;
                        }
                    }
                }

                try {	
                	boolean result = userController.checkNickname(nickname);
                	String successMessage = result
                							? "{\"message\":\"사용 가능한 닉네임입니다.\"}" : "{\"message\":\"이미 사용 중인 닉네임입니다.\"}";
                	int statusCode = result ? 200: 409;
                	// 응답 헤더 설정
                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

                    // 응답 전송
                    byte[] responseBytes = successMessage.getBytes("UTF-8");
                    exchange.sendResponseHeaders(statusCode, responseBytes.length);
                    exchange.getResponseBody().write(responseBytes);
                    exchange.close();
                }catch(Exception e) {
                	// 예외 발생 시: 에러 응답
                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

                    // 에러 메시지 설정
                    String errorMessage = "{\"message\":\"" + e.getMessage() + "\"}";  // 예외 메시지 ex. throw new Exception("") 여기서 설정한 메세지
                    byte[] responseBytes = errorMessage.getBytes("UTF-8");
                    System.out.println("nickname error response"+errorMessage);	
                    // 상태 코드 설정 (500 Internal Server Error 등)(추가 예외 설정은 작업 마지막에 진행 예정) 
                    exchange.sendResponseHeaders(500, responseBytes.length);  // 500 Internal Server Error
                    exchange.getResponseBody().write(responseBytes);
                    exchange.close();	
                }
        	} 
        });
        
     // 회원 아이디 중복 확인
        httpServer.createContext("/uid", exchange -> {
        	// CORSFilter : 
        	if (CORSFilter.handlePreflight(exchange)) {
                return; // 프리플라이트 처리 완료
            }

        	if ("POST".equals(exchange.getRequestMethod())) {

        		// CORS 헤더 적용
                CORSFilter.applyCORS(exchange);
                
                // requestBody 데이터 처리
                InputStream inputStream = exchange.getRequestBody();
                byte[] bytes = inputStream.readAllBytes();
                String body = new String(bytes,StandardCharsets.UTF_8);

                try {	
                	// getStringValueByKey 이용해 json -> key가 uid인 string 값 추출 
                    String uid = JsonUtil.getStringValueByKey(body, "uid");
                	boolean result = userController.checkUid(uid);
                	String successMessage = result
                							? "{\"message\":\"사용 가능한 아이디입니다.\"}" : "{\"message\":\"이미 사용 중인 아이디입니다.\"}";
                	int statusCode = result ? 200: 409;
                	// 응답 헤더 설정
                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                 

                    // 응답 전송
                    byte[] responseBytes = successMessage.getBytes("UTF-8");
                    exchange.sendResponseHeaders(statusCode, responseBytes.length);
                    exchange.getResponseBody().write(responseBytes);
                    exchange.close();
                }catch(Exception e) {
                	// 예외 발생 시: 에러 응답
                    exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");

                    // 에러 메시지 설정
                    String errorMessage = "{\"message\":\"" + e.getMessage() + "\"}";  // 예외 메시지 ex. throw new Exception("") 여기서 설정한 메세지
                    byte[] responseBytes = errorMessage.getBytes("UTF-8");
                    System.out.println("uid error response"+errorMessage);	
                    // 상태 코드 설정 (500 Internal Server Error 등)(추가 예외 설정은 작업 마지막에 진행 예정) 
                    exchange.sendResponseHeaders(500, responseBytes.length);  // 500 Internal Server Error
                    exchange.getResponseBody().write(responseBytes);
                    exchange.close();	
                }
        	} 
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
 


        //httpServer 시작
        httpServer.start();
    }
}
