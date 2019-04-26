package controllers;

import entity.Shop;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ChooseShop;
import org.hibernate.Session;
import javafx.beans.property.SimpleStringProperty;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import static controllers.MainWindowController.sessionFactory;



public class ComboShop implements Initializable {

    @FXML
    public ComboBox<String> comboList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    comboList.getItems();

    }

    public ObservableList<ChooseShop> getShop() {
        ObservableList<ChooseShop> shopList = FXCollections.observableArrayList();
        ComboBox<String> comboBox = new ComboBox<String>();
        Session session = sessionFactory.openSession();
        List<Shop> shList = session.createQuery("from Shop").list();
        for (Shop sh : shList) {
            ChooseShop cs = new ChooseShop();
            comboBox.getItems().addAll();


        }

        return shopList;
    }

}


