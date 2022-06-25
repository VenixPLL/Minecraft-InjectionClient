package me.dickmeister.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class URLUtils {

    public static String readAllFromUrl(String url) throws Exception{
        try (var is = new URL(url).openStream()) {
            var reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return readAll(reader);
        }
    }

    public static  String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
