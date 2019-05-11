package controllers;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import models.SalesCreatorModel;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class AnalystPricesViewController implements Initializable {
    @FXML
    public TableView<SalesCreatorModel> priceTable;
    @FXML
    public TableColumn<SalesCreatorModel,String> PRODUCTID;
    @FXML
    public TableColumn<SalesCreatorModel,String> NAME;
    @FXML
    public TableColumn<SalesCreatorModel,String> PRICE;
    @FXML
    public TableColumn<SalesCreatorModel,String> DISCOUNT;
    @FXML
    public Button change,search;
    @FXML
    public TextField searchTF;

    String nazwaProduktu = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PRODUCTID.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().toString()));
        NAME.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getName()));
        PRICE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getPrice())));
        DISCOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getDiscount())));
        priceTable.setItems(getProducts(nazwaProduktu));
        //System.out.println(getProducts().toString());
        setEditableAmount();
    }

    public ObservableList<SalesCreatorModel> getProducts(String nazwaProduktu) {
        ObservableList<SalesCreatorModel> enseignantList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<SalesCreatorModel> eList;
        if(nazwaProduktu == null || nazwaProduktu.equals("")){
            eList = session.createQuery("SELECT new models.SalesCreatorModel (p.productId,p.name,p.price,p.discount) from Product p ").list();
        } else {
            eList = session.createQuery("SELECT new models.SalesCreatorModel (p.productId,p.name,p.price,p.discount) from Product p" +
                    " where name like :produkt ").setParameter("produkt","%"+nazwaProduktu+"%").list();
            searchTF.setText("");
            nazwaProduktu = null;
        }
        System.out.println("getProducts "+eList);
        for (SalesCreatorModel ent : eList) {
            enseignantList.add(ent);
        }
        session.close();
        return enseignantList;
    }

    public void searchAnalystPrices(){
        nazwaProduktu = searchTF.getText();
        priceTable.setItems(getProducts(nazwaProduktu));
    }

    public void setEditableAmount(){
        PRICE.setCellFactory(TextFieldTableCell.forTableColumn());

        PRICE.setOnEditCommit(e -> { // dodać walidacje try catch
            System.out.println("PRZED"+e.getTableView().getSelectionModel().getSelectedItem().getPrice().toString());
            BigDecimal check = new BigDecimal(e.getTableView().getSelectionModel().getSelectedItem().getPrice().intValue());
            BigDecimal b = new BigDecimal(e.getNewValue());
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPrice(b);
            System.out.println("PO"+e.getTableView().getSelectionModel().getSelectedItem().getPrice().toString());
            if(e.getTableView().getSelectionModel().getSelectedItem().getPrice().intValue() > 0){
                System.out.println("większe od 0 ");
            } else {
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setPrice(check);
                System.out.println("Powrót do poprzedniej liczby");
                priceTable.refresh();
            }
        });
        priceTable.setEditable(true);
    }

}
