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
import java.util.Objects;
import java.util.ResourceBundle;

import log.ThothLoggerConfigurator;
import org.apache.log4j.Logger;

import static controllers.MainWindowController.sessionContext;

/**
 * Kontroler głównego okna analityka. Odpowiada za inicjalizację interfejsu oraz przechowuje metodę która umożliwia wylogowanie użytkownika.
 */
public class MainViewAnalystController implements Initializable {

    private static final Logger logger = Logger.getLogger(MainViewAnalystController.class);

    @FXML
    MenuItem logout;
    @FXML
    MenuItem back;
    @FXML
    Parent root;

    public void menuItemAction(ActionEvent actionEvent) throws IOException { //cofanie i wylogowanie na MENU ITEM
        Stage stage = (Stage) root.getScene().getWindow();
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

        if (sessionContext.getCurrentLoggedUser().getUserId() == 1) {
            if (back != null) {
                back.setVisible(true);
            } else {
                logger.warn("BACK is null");
            }
        }
    }
}
