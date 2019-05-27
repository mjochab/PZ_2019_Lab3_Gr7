package utils;

import java.util.regex.Pattern;

public class Validation {


    private static final Pattern pattern = Pattern.compile("[A-Z][a-z]+");

    public static Boolean nameValidation(String string) {
        return (pattern.matcher(string).matches());
    }

    /**
     * Metoda sprawdza czy String jest 0 lub 1
     *
     * @param string ciąg znaków do przetestowania
     * @return true jeżeli string równy 0 lub 1 false w przeciwnym przypadku
     */
    public static Boolean isBolean(String string) {
        return string.equals("0") || string.equals("1");
    }

    public static Boolean zipCodeValidation(String string) {
        Pattern pattern = Pattern.compile("[0-9][0-9]" + "-" + "[0-9][0-9][0-9]");
        return (pattern.matcher(string).matches());
    }

    public static Boolean onlyCharsValidation(String string) {
        Pattern pattern = Pattern.compile("[ a-zA-Z]+");
        return (pattern.matcher(string).matches());
    }

    public static Boolean streetValidation(String string) {
        Pattern pattern = Pattern.compile("[ a-zA-Z0-9]+");
        return (pattern.matcher(string).matches());
    }

}
