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
        alert.setContentText("Dostepny przedział od "+a+" do "+b);
        alert.showAndWait();
    }

    public static void showNoIthemsAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Niepowodzenie");
        alert.setHeaderText("Lista jest pusta.");
        alert.showAndWait();
    }

    public static void newAlertCustom(String title, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    // powodzenie

    public static void showSuccesAllert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Powodzenie");
        alert.setHeaderText("Operacja przebiegła pomyślnie.");
        alert.showAndWait();
    }

    public static void newAlertOrder(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
