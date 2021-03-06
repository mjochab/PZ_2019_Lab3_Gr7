package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import log.ThothLoggerConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionContext;

/**
 * Kontroler głównego okna analityka. Odpowiada za inicjalizację interfejsu oraz przechowuje metodę która umożliwia wylogowanie użytkownika.
 */
public class MainViewAnalystController implements Initializable {
    private static final Logger logger = Logger.getLogger(ComplexOrderDetailsController.class);
    @FXML
    MenuItem logout;
    @FXML
    MenuItem back;
    @FXML
    Parent root;
    @FXML
    private Label sessionInfo;

    /**
     * Metoda obsługijąca prycik powrotu i wylogowywania.
     * Wczytuje odpowiedni widok w zależności w któryym oknie się znajdujemy.
     *
     * @param actionEvent pozwala zlokalizować z jakiego okna wywołano metodę
     * @throws IOException występuje przy odczycie/zapisie pliku
     */
    public void menuItemAction(ActionEvent actionEvent) throws IOException { //cofanie i wylogowanie na MENU ITEM
        Stage stage = (Stage) root.getScene().getWindow();
        if (actionEvent.getSource() == logout) {
            root = FXMLLoader.load(getClass().getResource("/fxmlfiles/MainWindow.fxml"));
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlfiles/choose_employee.fxml"));
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
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());
        if (sessionContext.getCurrentLoggedUser().getUserId() == 1) {
            if (back != null) {
                back.setVisible(true);
            } else {
                logger.warn("BACK is null");
            }
        }
        sessionInfo.setText(" Zalogowano jako: "+sessionContext.getCurrentLoggedUser().getFirstName()+" "+sessionContext.getCurrentLoggedUser().getLastName()+" / Lokalizacja: GLOBAL");
    }
}
