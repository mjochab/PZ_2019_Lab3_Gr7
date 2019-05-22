package utils;

import javafx.scene.control.Alert;

public class Alerts {
    public static void showNotNumberAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Niepowodzenie");
        alert.setHeaderText("Wprowadzona wartość nie jest liczbą!");
        alert.showAndWait();
    }


    public static void showNumberRangeAlert(int a, int b){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Niepowodzenie");
        alert.setHeaderText("Wprowadzona wartość wykracza poza dostępny zakres.");
        alert.setContentText("Dostepny przediał od "+a+" do "+b);
        alert.showAndWait();
    }
}
