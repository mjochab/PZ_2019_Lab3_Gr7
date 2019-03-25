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

public class FXMLDocumentController implements Initializable {

    public void switchscene(ActionEvent event) throws IOException {
        System.out.println(event.getSource().toString());

        Parent temporaryLoginParent = null;
        if(event.getSource().toString().contains("login_btn") == true) //logowanie
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/TemporaryLogIn.fxml"));
        }
        if(event.getSource().toString().contains("back") == true) //tu bedzie id = "back", przycisk powrotu
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/FXMLDocument.fxml"));
        }
        if(event.getSource().toString().contains("employee_warehouse") == true) //okno magazynu
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_warehouse.fxml"));
        }
        if(event.getSource().toString().contains("employee_shop") == true) //okno sklepu
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_shop.fxml"));
        }
        if(event.getSource().toString().contains("analyst") == true) //okno analityka
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_analyst.fxml"));
        }
        if(event.getSource().toString().contains("employee_logistic") == true) // okno widoku pracownika działy logistycznego
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_view_logistic.fxml"));
        }
        if(event.getSource().toString().contains("admin_view") == true) // okno widoku admina
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/main_window_admin.fxml"));
        }
        if(event.getSource().toString().contains("admin_choose_employee") == true) // okno widoku admina
        {
            temporaryLoginParent = FXMLLoader.load(getClass().getResource("../fxmlfiles/choose_employee.fxml"));
        }


        Scene temporaryLoginScene = new Scene(temporaryLoginParent);

        // To pobiera informacje o scenie
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(temporaryLoginScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
