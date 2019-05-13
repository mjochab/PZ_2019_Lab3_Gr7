package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionContext;

public class AdminController implements Initializable {

    @FXML
    MenuItem logout;
    @FXML
    MenuItem back;
    @FXML
    Parent root;
    @FXML
    AddEmployeeController addEmployeeController;
    @FXML
    EmployeeViewController employeeViewController;


    Stage stage;

    @FXML
    public void reloadEmployeeView() {
        employeeViewController.reloadTableView();
    }

    public void switchscene(ActionEvent event) throws IOException { //zmiana sceny BUTTON
        System.out.println(event.getSource().toString());
        Parent temporaryLoginParent = null;
        Scene temporaryLoginScene = null;
        temporaryLoginScene = new Scene(temporaryLoginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(temporaryLoginScene);
        window.show();
    }

    public void menuItemAction(ActionEvent actionEvent) throws IOException { //powrót , wylogowanie na MENU ITEM
        System.out.println("ACTION EVENT"+actionEvent);
        stage = (Stage) root.getScene().getWindow();
        if (actionEvent.getSource() == logout) {
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/MainWindow.fxml"));
        } else {
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/choose_employee.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(sessionContext.getCurrentLoggedUser().getUserId() == 1){
            back.setVisible(true);
        }
    }
}
