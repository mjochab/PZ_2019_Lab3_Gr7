package controllers;


import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class AnalystPricesViewController implements Initializable {
    @FXML
    public TableView<Product> priceTable;
    @FXML
    public TableColumn<Product, Integer> PRODUCTID;
    @FXML
    public TableColumn<Product, String> NAME;
    @FXML
    public TableColumn<Product, BigDecimal> PRICE;
    @FXML
    public TableColumn<Product, Integer> DISCOUNT;
    @FXML
    public Button change,search;
    @FXML
    public TextField searchTF;

    String nazwaProduktu = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PRODUCTID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productId"));
        NAME.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        PRICE.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));
        DISCOUNT.setCellValueFactory(new PropertyValueFactory<Product, Integer>("discount"));
        priceTable.setItems(getProducts(nazwaProduktu));
        //System.out.println(getProducts().toString());
        //ediatbleCols();
    }

    public ObservableList<Product> getProducts(String nazwaProduktu) {
        ObservableList<Product> enseignantList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Product> eList;
        if(nazwaProduktu == null || nazwaProduktu.equals("")){
            eList = session.createQuery("from Product").list();
        } else {
            eList = session.createQuery("from Product where name like :produkt ").setParameter("produkt","%"+nazwaProduktu+"%").list();
            searchTF.setText("");
            nazwaProduktu = null;
        }
        System.out.println("getProducts "+eList);
        for (Product ent : eList) {
            enseignantList.add(ent);
        }
        session.close();
        return enseignantList;
    }

    public void searchAnalystPrices(){
        nazwaProduktu = searchTF.getText();
        priceTable.setItems(getProducts(nazwaProduktu));
    }

    public void ediatbleCols(){

        //PRICE.setCellFactory(TextFieldTableCell.forTableColumn());

        PRICE.setOnEditCommit(event -> {
            //event.getTableView().getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue());
        });

        priceTable.setEditable(true);
    }

}
