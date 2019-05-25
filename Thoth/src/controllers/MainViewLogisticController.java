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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import log.ThothLoggerConfigurator;
import org.apache.log4j.Logger;

import static controllers.MainWindowController.sessionContext;

public class MainViewLogisticController implements Initializable {

    private static final Logger logger = Logger.getLogger(MainViewLogisticController.class);

    @FXML
    MenuItem logout, back;
    @FXML
    Parent root;
    @FXML
    private Label sessionInfo;
    Stage stage;


    public void menuItemAction(ActionEvent actionEvent) throws IOException { //wylogowanie na MENU ITEM
        stage = (Stage) root.getScene().getWindow();
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
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());

        if (sessionContext.getCurrentLoggedUser().getUserId() == 1) {
            if (back != null) {
                back.setVisible(true);
            } else {
                logger.warn("BACK is null");
            }
        }
        sessionInfo.setText(" Zalogowano jako: "+sessionContext.getCurrentLoggedUser().getFirstName()+" "+sessionContext.getCurrentLoggedUser().getLastName()+" / Lokalizacja: "+sessionContext.getCurrentLoggedShop().getCity());
        logger.info("Aktualnie zaloogwany User: " + sessionContext.getCurrentLoggedUser());
        logger.info("Obiekt zalogowanego User'a: " + sessionContext.getCurrentLoggedShop());
        logger.info("Hello from MainController");
    }
}
