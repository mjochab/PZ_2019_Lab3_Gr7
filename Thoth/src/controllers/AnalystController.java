package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AnalystController implements Initializable {

    public void switchscene(ActionEvent event) throws IOException { //zmiana sceny BUTTON
        System.out.println(event.getSource().toString());
        Parent temporaryLoginParent = null;
        Scene temporaryLoginScene = null;
        temporaryLoginScene = new Scene(temporaryLoginParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(temporaryLoginScene);
        window.show();
    }

    @FXML
    MenuItem logout, back;
    Stage stage;

    @FXML
    Parent root;

    public void generate_raport_alert (ActionEvent actionEvent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Raport utworzony pomyślnie");
        alert.setHeaderText("Utworzono w lokalizacji:");
        alert.setContentText("c:/programy/raport.pdf");
        ButtonType view_raport = new ButtonType("Podgląd");
        ButtonType confirm = new ButtonType("Ok", ButtonBar.ButtonData.APPLY);

        alert.getButtonTypes().setAll(view_raport, confirm);
        alert.showAndWait();
    }

    public void menuitemaction(ActionEvent actionEvent) throws IOException { //cofanie i wylogowanie na MENU ITEM
        stage = (Stage) root.getScene().getWindow();
        if(actionEvent.getSource() == logout)
        {
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/FXMLDocument.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
