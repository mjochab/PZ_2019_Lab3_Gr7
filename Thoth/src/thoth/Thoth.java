
package thoth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Thoth extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxmlfiles/FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Thoth.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("update.cfg.xml")
                .buildSessionFactory();
        launch(args);
    }
    
}

