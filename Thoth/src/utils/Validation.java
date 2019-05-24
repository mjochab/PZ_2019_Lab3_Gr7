package utils;

import javafx.scene.control.Alert;

import java.util.regex.Pattern;

public class Validation {


    private static Pattern pattern = Pattern.compile("[A-Z][a-z]+");

    public static Boolean nameValidation(String string){
        return (pattern.matcher(string).matches());
    }
}
