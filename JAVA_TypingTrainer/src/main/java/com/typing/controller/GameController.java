package com.typing.controller;

import com.typing.model.dto.GameRequestDto;
import com.typing.service.Game.GameService;
import com.typing.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class GameController implements HttpHandler {
    private final GameService service = new GameService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "utf-8"));
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBody.append(line);
            }

            GameRequestDto dto = JsonUtil.fromJson(requestBody.toString(), GameRequestDto.class);
            try {
                service.saveGameSettings(dto);
                String response = "게임 설정 저장 완료";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (RuntimeException e) {
                String response = e.getMessage();
                exchange.sendResponseHeaders(400, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}
