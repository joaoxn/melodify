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

    public static int areNull(Object[] objects) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public static boolean password(String passwordDecrypted, boolean nullable) {
        if(passwordDecrypted == null) {
            return nullable;
        }
        return length(passwordDecrypted, 8, 50, false) &&
                patternMatches(passwordDecrypted,
                        "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-])");

    }

    public static boolean email(String email) {
        return patternMatches(email, "^(.+)@(\\S+)$");
    }
}
