package com.typing.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility for parsing URL query strings into a Map.
 */
public class QueryString {
    /**
     * Parses a raw query string (e.g., "a=1&b=two") into a Map.
     * Returns an empty map if rawQuery is null or empty.
     */
    public static Map<String, String> parse(String rawQuery) {
        if (rawQuery == null || rawQuery.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, String> result = new HashMap<>();
        String[] pairs = rawQuery.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            try {
                String key = URLDecoder.decode(keyValue[0], "UTF-8");
                String value = keyValue.length > 1 
                    ? URLDecoder.decode(keyValue[1], "UTF-8")
                    : "";
                result.put(key, value);
            } catch (UnsupportedEncodingException e) {
                // UTF-8 should always be supported
                e.printStackTrace();
            }
        }
        return result;
    }
}
