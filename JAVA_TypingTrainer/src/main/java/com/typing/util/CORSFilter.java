package com.typing.util;

import com.sun.net.httpserver.HttpExchange;

/*
 * CORS 설정
 * */
public class CORSFilter {

    // CORS 헤더 설정
    public static void applyCORS(HttpExchange exchange) { 	
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }

    // OPTIONS 프리플라이트 요청 처리
    public static boolean handlePreflight(HttpExchange exchange) {
    	System.out.println("이게 호출되냐?");
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            applyCORS(exchange);
            try {
                exchange.sendResponseHeaders(204, -1); // 본문 없이 응답
                exchange.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}