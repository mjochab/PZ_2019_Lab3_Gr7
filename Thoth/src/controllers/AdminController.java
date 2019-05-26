package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
    private
    EmployeeViewController employeeViewController;
    @FXML
    private Label sessionInfo;


    @FXML
    public void reloadEmployeeView() {
        employeeViewController.reloadTableView();
    }

    public void switchscene(ActionEvent event) { //zmiana sceny BUTTON
        System.out.println(event.getSource().toString());
        Parent temporaryLoginParent = null;
        Scene temporaryLoginScene = null;
        temporaryLoginScene = new Scene(Objects.requireNonNull(temporaryLoginParent));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(temporaryLoginScene);
        window.show();
    }

    public void menuItemAction(ActionEvent actionEvent) throws IOException { //powrót , wylogowanie na MENU ITEM
        System.out.println("ACTION EVENT"+actionEvent);
        Stage stage = (Stage) root.getScene().getWindow();
        if (actionEvent.getSource() == logout) {
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/MainWindow.fxml"));
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlfiles/choose_employee.fxml"));
            root = loader.load();
            MainWindowController mainController = loader.getController();
            mainController.setComboList();
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
        sessionInfo.setText(" Zalogowano jako: "+sessionContext.getCurrentLoggedUser().getFirstName()+" "+sessionContext.getCurrentLoggedUser().getLastName()+" / Lokalizacja: GLOBAL");
    }
}
