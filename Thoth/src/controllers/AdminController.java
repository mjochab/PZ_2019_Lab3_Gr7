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
import log.ThothLoggerConfigurator;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionContext;

/**
 * Kontroler okna admina
 */

public class AdminController implements Initializable {

    private static final Logger logger = Logger.getLogger(AdminController.class);

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

    /**
     * Metoda przeladowujaca tabele pracownikow
     */

    @FXML
    public void reloadEmployeeView() {
        employeeViewController.reloadTableView();
    }

    /**
     * Metoda  do zmiany sceny
     * @param event ActionEvent
     * @throws IOException wyjatek IOException
     */
    public void switchscene(ActionEvent event) throws IOException { //zmiana sceny BUTTON
        logger.info(event.getSource().toString());
        Parent temporaryLoginParent = null;
        Scene temporaryLoginScene = null;
        temporaryLoginScene = new Scene(temporaryLoginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(temporaryLoginScene);
        window.show();
    }

    /**
     * Metoda sluzaca do wylogowania z menu item
     * @param actionEvent actionEvent
     * @throws IOException wyjatek IOexception
     */

    public void menuItemAction(ActionEvent actionEvent) throws IOException { //powrót , wylogowanie na MENU ITEM
        logger.info("ACTION EVENT"+actionEvent);
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
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());
        if(sessionContext.getCurrentLoggedUser().getUserId() == 1){
            back.setVisible(true);
        }
    }
}
