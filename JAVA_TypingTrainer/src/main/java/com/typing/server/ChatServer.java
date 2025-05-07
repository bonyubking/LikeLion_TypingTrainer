package com.typing.server;

import org.java_websocket.server.WebSocketServer;

import com.typing.controller.ChatController;
import com.typing.model.dto.ChatMessageDto;
import com.typing.util.JsonUtil;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatServer extends WebSocketServer {
	private Set<WebSocket> connections = Collections.synchronizedSet(new HashSet<>());
    private final ChatController chatController = new ChatController();;

    public ChatServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        connections.add(conn);
        System.out.println("새 연결: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
    	// 받은 메세지 확인
    	System.out.println("Received message: " + message);
    	
    	// JSON -> ChatMessageDto로 변환
    	ChatMessageDto dto = JsonUtil.fromJson(message, ChatMessageDto.class);
    	
    	// 메세지 저장
    	String response = JsonUtil.toJson(chatController.sendMessage(dto));
    	System.out.println(response);
    	// 전체 사용자에게 브로드캐스트
    	for (WebSocket client : getConnections()) {
            client.send(response);
        }

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    	
        connections.remove(conn);
        System.out.println("연결 종료: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}
	
	// 서버 종료 메서드 추가
    public void stopServer() {
        System.out.println("서버 종료 중...");
        try {
			this.stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("서버 종료됨");
    }
	
	private ChatMessageDto parseJsonToDto(String json) {
        // Jackson이나 Gson으로 구현 가능
        return JsonUtil.fromJson(json, ChatMessageDto.class);
    }
}
