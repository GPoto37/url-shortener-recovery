package dev.smirg.url_shortener.util;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();

    public String encode(long value) {
        StringBuilder builder = new StringBuilder();
        while (value > 0) {
            builder.append(ALPHABET.charAt((int) (value % BASE)));
            value /= BASE;
        }
        return builder.reverse().toString();
    }

    public long decode(String shortUrl) {
        long result = 0L;
        for (char c : shortUrl.toCharArray()) {
            result = result * BASE + ALPHABET.indexOf(c);
        }
        return result;
    }
}
