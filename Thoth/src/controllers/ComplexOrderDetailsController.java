package controllers;

import entity.Indent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ComplexOrderDetailsController implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private Accordion indentsAccordion;

    private Indent order;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setOrder(Indent order)
    {
        this.order = order;
    }

    public void initController()
    {
        indentsAccordion.getPanes().clear();
        indentsAccordion.getPanes().add(new TitledPane("Id zamowienia to: " + String.valueOf(order.getIndentId()), new Button("Dupa")));
    }


    @FXML
    public void goBack(ActionEvent event)
    {
        Stage stg = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Parent par = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlfiles/main_view_logistic.fxml"));
            par = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        stg.setScene(new Scene(par));
    }
}
