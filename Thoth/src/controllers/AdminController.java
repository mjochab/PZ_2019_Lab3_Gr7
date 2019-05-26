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
import log.ThothLoggerConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionContext;

/**
 * Kontroller głównego okna administratora(okno wyboru użytkowników).
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
    private
    EmployeeViewController employeeViewController;
    @FXML
    private Label sessionInfo;

    /**
     * Metoda odświeża tabelę przecohwującą informacje o kontach użytkowników.
     */
    @FXML
    public void reloadEmployeeView() {
        employeeViewController.reloadTableView();
    }

    public void switchscene(ActionEvent event) { //zmiana sceny BUTTON
        logger.warn(event.getSource().toString());
        Parent temporaryLoginParent = null;
        Scene temporaryLoginScene = null;
        temporaryLoginScene = new Scene(Objects.requireNonNull(temporaryLoginParent));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(temporaryLoginScene);
        window.show();
    }

    /**
     * Metoda ładuje głowny widok aplikacji gdy actionEvent jest przyciskiem wyloguj(logout) lub widok wyboru pracownika gdy źródło actionEvent jest inne niż logout.
     *
     * @param actionEvent zdarzenie (wciśnięcie przycisku)
     * @throws IOException występuje podczas nieudanej próby zapisu/odczytu danych
     */
    public void menuItemAction(ActionEvent actionEvent) throws IOException { //powrót , wylogowanie na MENU ITEM
        logger.warn("ACTION EVENT" + actionEvent);
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

    /**
     * Metoda inicjalizująca widok.
     * Sprawia że przycisk powrót jest widoczny oraz wyświetla informację o aktualnie zalogowanym użytkowniku w etykiecie sessionInfo.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());
        if (sessionContext.getCurrentLoggedUser().getUserId() == 1) {
            back.setVisible(true);
        }
        sessionInfo.setText(" Zalogowano jako: " + sessionContext.getCurrentLoggedUser().getFirstName() + " " + sessionContext.getCurrentLoggedUser().getLastName() + " / Lokalizacja: GLOBAL");
    }
}
