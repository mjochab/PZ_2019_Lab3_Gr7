package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StateWarehouseController implements Initializable {

    public void switchscene(ActionEvent event) throws IOException { //zmiana sceny BUTTON
        System.out.println(event.getSource().toString());
        Parent temporaryLoginParent = null;
        Scene temporaryLoginScene = null;
        if(event.getSource().toString().contains("new_order") == true) //nowe zamowienie
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/new_order.fxml"));
        }
        temporaryLoginScene = new Scene(temporaryLoginParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(temporaryLoginScene);
        window.show();
    }

    @FXML
    MenuItem logout, back;

    Stage stage;
    @FXML Parent root;

    public void menuitemaction(ActionEvent actionEvent) throws IOException { //cofanie i wylogowanie na MENU ITEM
        stage = (Stage) root.getScene().getWindow();
        if(actionEvent.getSource() == back)
        {
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_warehouse.fxml"));
        }
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
