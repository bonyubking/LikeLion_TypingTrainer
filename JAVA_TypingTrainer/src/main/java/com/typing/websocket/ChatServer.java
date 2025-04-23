package com.typing.websocket;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatServer extends WebSocketServer {
    private Set<WebSocket> connections = Collections.synchronizedSet(new HashSet<>());

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
        System.out.println("메시지 수신: " + message);
        for (WebSocket socket : connections) {
            socket.send("[클라이언트] " + message);  // 모두에게 메시지 보냄 (broadcast)
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
}
