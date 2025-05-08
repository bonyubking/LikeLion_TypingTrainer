package com.typing.util;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpUtil {

    public static void sendJsonResponse(OutputStream out, String json, int statusCode) {
        try {
            byte[] responseBytes = json.getBytes(StandardCharsets.UTF_8);

            out.write(("HTTP/1.1 " + statusCode + " OK\r\n").getBytes());
            out.write("Content-Type: application/json; charset=UTF-8\r\n".getBytes());
            out.write(("Content-Length: " + responseBytes.length + "\r\n").getBytes());
            out.write("\r\n".getBytes());
            out.write(responseBytes);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
