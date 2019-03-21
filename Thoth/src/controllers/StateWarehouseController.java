package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StateWarehouseController implements Initializable {

    public void switchscene(ActionEvent event) throws IOException {
        System.out.println(event.getSource().toString());

        Parent temporaryLoginParent = null;
        if(event.getSource().toString().contains("logout") == true) //wylogowanie
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/FXMLDocument.fxml"));
        }
        if(event.getSource().toString().contains("new_order") == true) //nowe zamowienie
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/new_order.fxml"));
        }
        if(event.getSource().toString().contains("back") == true) //powrót do state_warehouse
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/state_warehouse.fxml"));
        }

        Scene temporaryLoginScene = new Scene(temporaryLoginParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(temporaryLoginScene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
