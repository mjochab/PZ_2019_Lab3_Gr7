package controllers;

import entity.Product;
import entity.State_on_shop;
import models.ShopSell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class ShopSellProductsController implements Initializable {
    @FXML
    MenuItem logout;
    @FXML
    private TableView productsTable;
    @FXML
    public TableColumn id_col;
    @FXML
    public TableColumn name_col;
    @FXML
    public TableColumn price_col;
    @FXML
    public TableColumn amount_col;
    @FXML
    Parent root;
    Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_col.setCellValueFactory(new PropertyValueFactory<>("productId"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));

        productsTable.setItems(getProducts());

    }


    public ObservableList<ShopSell> getProducts() {
        ObservableList<ShopSell> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<ShopSell> eList = session.createQuery("select new models.ShopSell(pr.productId, pr.name, pr.price, sop.amount) from State_on_shop sop inner join sop.productId pr where sop.shopId=1").list();
        for (ShopSell ent : eList) {
            productList.add(ent);
            System.out.println(ent.getProductId() + " " + ent.getName());
        }
        session.close();
        return productList;
    }


}

