
package thoth;

import controllers.AddEmployeeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import log.ThothLoggerConfigurator;
import org.apache.log4j.Logger;

import static utils.Alerts.*;
/**
 * Główna klasa aplikacji.
 * Obsługuje metodę start która wczytuje główny widok aplikacji, style css oraz tworzy scenę główną.
 */
public class Thoth extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            ThothLoggerConfigurator.configure();
            Parent root = FXMLLoader.load(getClass().getResource("/fxmlfiles/MainWindow.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Thoth.class.getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            newAlertCustom("ERROR 404", "Nie udało się załadować widoku MainWindow.fxml" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

