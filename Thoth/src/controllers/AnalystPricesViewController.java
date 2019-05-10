package controllers;


import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.hibernate.Session;

import javax.naming.Name;
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
    public TableColumn<Product, BigDecimal> PRICE; //<-zmiana z BigDecimal
    @FXML
    public TableColumn<Product, Integer> DISCOUNT;
    @FXML
    public Button change;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PRODUCTID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productId"));
        NAME.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        PRICE.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));
        DISCOUNT.setCellValueFactory(new PropertyValueFactory<Product, Integer>("discount"));
        priceTable.setItems(getProducts());
        System.out.println(getProducts().toString());
        //ediatbleCols();
    }

    public ObservableList<Product> getProducts() {
        ObservableList<Product> enseignantList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Product> eList = session.createQuery("from Product").list();
        for (Product ent : eList) {
            enseignantList.add(ent);
        }
        session.close();
        return enseignantList;
    }

    public void ediatbleCols(){

        //PRICE.setCellFactory(TextFieldTableCell.forTableColumn());

        PRICE.setOnEditCommit(event -> {
            //event.getTableView().getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue());
        });

        priceTable.setEditable(true);
    }

}
