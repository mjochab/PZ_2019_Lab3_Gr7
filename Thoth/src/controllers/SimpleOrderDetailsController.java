package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SimpleOrderDetailsController implements Initializable {
    @FXML
    private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void goBack(ActionEvent event)
    {
        Stage stg = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent par = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlfiles/main_view_logistic.fxml"));
            par = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        stg.setScene(new Scene(par));
    }
}
