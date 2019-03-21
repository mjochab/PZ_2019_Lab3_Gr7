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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ViewUsersController implements Initializable {

    public void switchscene(ActionEvent event) throws IOException {
        System.out.println(event.getSource().toString());

        Parent temporaryLoginParent = null;
        if(event.getSource().toString().contains("logout") == true) //wylogowanie
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/FXMLDocument.fxml"));
        }
        if(event.getSource().toString().contains("back") == true) //powrót
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/TemporaryLogIn.fxml"));
        }
        if(event.getSource().toString().contains("add_employee") == true) // dodawanie pracownika
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/add_employee.fxml"));
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
