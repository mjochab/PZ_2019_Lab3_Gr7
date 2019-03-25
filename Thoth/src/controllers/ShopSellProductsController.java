package controllers;

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
import models.Product;
import models.ProductShop;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShopSellProductsController implements Initializable {

    @FXML
    MenuItem logout;

    @FXML
    private TableView<ProductShop> productsTable;

    @FXML
    public TableColumn<ProductShop, Integer> id_col, amount_col;

    @FXML
    public TableColumn<ProductShop, String> name_col;

    @FXML
    public TableColumn<ProductShop, Double> price_col;


    Stage stage;
    @FXML Parent root;

    ObservableList<ProductShop> data = FXCollections.observableArrayList(
            new ProductShop(1,"Samsung Galaxy S8", 1999.90,4),
            new ProductShop(2,"produkt 2", 1999.90,6),
            new ProductShop(3,"produkt 3", 59.90,2),
            new ProductShop(4,"produkt 4", 1299.90,6),
            new ProductShop(5,"produkt 5", 2339.90,2),
            new ProductShop(6,"produkt 6", 19.90,3),
            new ProductShop(7,"produkt 7", 1999.90,5),
            new ProductShop(8,"produkt 8", 29.90,7),
            new ProductShop(9,"produtk 9", 199.90,12)
    );


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));

        productsTable.setItems(data);

    }

}

