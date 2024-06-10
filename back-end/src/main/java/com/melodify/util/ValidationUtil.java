package com.melodify.util;

import lombok.SneakyThrows;

import java.util.regex.Pattern;

public class ValidationUtil {
    @SneakyThrows
    public static void validadeOrThrow(boolean validation, Throwable exception) {
        if (!validation) {
            throw exception;
        }
    }

    public static boolean patternMatches(String text, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(text)
                .matches();
    }

    public static boolean length(String string, int min, int max, boolean nullable) {
        if (string == null) {
            return nullable;
        }
        return string.length() >= min && string.length() <= max;
    }

    public static boolean email(String email) {
        return patternMatches(email, "^(.+)@(\\S+)$");
    }
}
