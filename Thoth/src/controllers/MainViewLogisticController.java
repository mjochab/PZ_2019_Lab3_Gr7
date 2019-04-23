package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MainViewLogisticController implements Initializable {


    @FXML
    MenuItem logout;

    Stage stage;
    @FXML
    Parent root;

    public void menuitemaction(ActionEvent actionEvent) throws IOException { //wylogowanie na MENU ITEM
        stage = (Stage) root.getScene().getWindow();
        if(actionEvent.getSource() == logout)
        {
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/MainWindow.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML private LogisticOrdersController ordersController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Hello from MainController");
    }
}
