package controllers;

import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.SalesCreatorModel;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class AnalystSalesCreatorViewController implements Initializable {
    @FXML
    public TableView productsTable;
    @FXML
    public TableColumn PRODUCTID;
    @FXML
    public TableColumn NAME;
    @FXML
    public TableColumn PRICE;
    @FXML
    public TableColumn DISCOUNT;
    @FXML
    public TableColumn AMOUNT;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PRODUCTID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        NAME.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        PRICE.setCellValueFactory(new PropertyValueFactory("price"));
        DISCOUNT.setCellValueFactory(new PropertyValueFactory("discount"));
        AMOUNT.setCellValueFactory(new PropertyValueFactory("amount"));
        productsTable.setItems(getProducts());
        System.out.println(getProducts().toString());
    }

    public ObservableList<SalesCreatorModel> getProducts() {
        ObservableList<SalesCreatorModel> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<SalesCreatorModel> eList = session.createQuery("SELECT new models.SalesCreatorModel(p.productId, p.name, p.price, p.discount, SUM(s.amount)) FROM Product p INNER JOIN State_on_shop s on p.productId = s.productId GROUP by p.productId ORDER by SUM(s.amount) DESC").list();
        System.out.println(eList);
        for (SalesCreatorModel ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }
}
