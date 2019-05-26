package utils;

import java.util.regex.Pattern;

public class Validation {


    private static final Pattern pattern = Pattern.compile("[A-Z][a-z]+");

    public static Boolean nameValidation(String string) {
        return (pattern.matcher(string).matches());
    }

    public static Boolean isBolean(String string) {
        return string.equals("0") || string.equals("1");
    }
}
