package controllers;

import entity.Product;
import entity.State_on_shop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ObservablePriceModel;
import org.hibernate.Session;

import java.net.URL;
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
    @FXML
    public TextField searchTF;

    String nazwaProduktu = null;

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


    public ObservableList<Product> getProducts() {
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_on_shop> eList;

        if (nazwaProduktu == null || nazwaProduktu.equals("")) {
            eList = session.createQuery("SELECT new entity.State_on_shop(sos.id, sos.productId, sos.shopId, SUM(sos.amount)) FROM State_on_shop sos GROUP by sos.productId ORDER BY SUM(sos.amount) DESC").list();
        } else {
            eList = session.createQuery("SELECT new entity.State_on_shop(sos.id, sos.productId, sos.shopId, SUM(sos.amount)) FROM State_on_shop sos WHERE name like :produkt GROUP by sos.productId ORDER BY SUM(sos.amount) DESC ").setParameter("produkt", "%" + nazwaProduktu + "%").list();
            searchTF.setText("");
            nazwaProduktu = null;
        }

        System.out.println("getProducts " + eList);
        for (State_on_shop ent : eList) {
            Product opm;
            opm = ent.getProductId();
            productsList.add(opm);
        }
        session.close();
        return productsList;
    }

    public void searchAnalystPrices() {
        nazwaProduktu = searchTF.getText();
        productsTable.setItems(getProducts());
    }
}
