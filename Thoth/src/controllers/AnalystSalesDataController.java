package controllers;


import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.RaportModel;
import models.RaportProductModel;
import models.SalesCreatorModel;
import models.SalesDataModel;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class AnalystSalesDataController implements Initializable {
    @FXML
    public TableView<SalesDataModel> salesDataTable;
    @FXML
    public TableColumn ZIPCODE;
    @FXML
    public TableColumn CITY;
    @FXML
    public TableColumn STREET;
    @FXML
    public TableColumn PROFIT;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ZIPCODE.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        CITY.setCellValueFactory(new PropertyValueFactory<Product, String>("city"));
        STREET.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("street"));
        PROFIT.setCellValueFactory(new PropertyValueFactory<Product, Integer>("profit"));
        salesDataTable.setItems(getProducts());
        System.out.println(getProducts().toString());
    }


    public ObservableList<SalesDataModel> getProducts() {
        ObservableList<SalesDataModel> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<SalesDataModel> eList = session.createQuery("SELECT new models.SalesDataModel(s.zipCode, s.city, s.street, SUM(r.totalValue)) FROM Shop s INNER JOIN Receipt r on s.shopId = r.shopId GROUP BY s.shopId").list();
        System.out.println(eList);
        for (SalesDataModel ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }


    public void generateRaport(ActionEvent actionEvent) {

        Session session = sessionFactory.openSession();
//        List lista = session.createQuery("SELECT Shop.street, Shop.zipCode, Shop.City, SUM(receipt.TotalValue) FROM Receipt, Shop WHERE Receipt.shopId = Shop.shopId GROUP BY Receipt.shopId").list();
        List<RaportModel> shops = session.createQuery("SELECT new models.RaportModel(s.shopId, s.street, s.zipCode, s.city, SUM(r.totalValue)) from Shop s Left JOIN Receipt r ON s.shopId = r.shopId GROUP BY s.shopId").list();
//        List<RaportProductModel> products = session.createQuery("").list();
        System.out.println(shops.size());
        System.out.println(shops.toString());

        session.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Raport utworzony pomyślnie");
        alert.setHeaderText("Utworzono w lokalizacji:");
        alert.setContentText("c:/programy/raport.pdf");
        ButtonType view_raport = new ButtonType("Podgląd");
        ButtonType confirm = new ButtonType("Ok", ButtonBar.ButtonData.APPLY);

        alert.getButtonTypes().setAll(view_raport, confirm);
        alert.showAndWait();
    }
}
