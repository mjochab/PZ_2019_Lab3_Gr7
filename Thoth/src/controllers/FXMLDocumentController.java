/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private StackPane mainStackPane;

    /**
     * To zmieni scene na inną
     */

    public void switchscene(ActionEvent event) throws IOException {
        Parent temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/TemporaryLogin.fxml"));
        Scene temporaryLoginScene = new Scene(temporaryLoginParent);

        // To pobiera informacje o scenie
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(temporaryLoginScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
