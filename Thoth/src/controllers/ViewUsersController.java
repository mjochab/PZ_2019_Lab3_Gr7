package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

class ViewUsersController implements Initializable {
    @FXML
    private
    MenuItem logout;
    @FXML
    MenuItem back;
    @FXML
    private
    Parent root;

    public void logout(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == logout) {
            Stage stage = (Stage) root.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/MainWindow.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
