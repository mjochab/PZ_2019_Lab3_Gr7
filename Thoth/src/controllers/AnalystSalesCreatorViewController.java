package controllers;

import entity.Product;
import entity.State_on_shop;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import models.ObservablePriceModel;
import models.ShopSell;
import org.hibernate.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class AnalystSalesCreatorViewController implements Initializable {
    @FXML
    public TableView productsTable,discountTable;
    @FXML
    public TableColumn<Product,String> PRODUCTID, PRODUCTID_CHANGE;
    @FXML
    public TableColumn<Product,String> NAME, NAME_CHANGE;
    @FXML
    public TableColumn<Product,String> PRICE, PRICE_CHANGE;
    @FXML
    public TableColumn<Product,String> DISCOUNT, DISCOUNT_CHANGE;
    @FXML
    public TextField searchTF;

    String nazwaProduktu = null;
    private ObservableList<Product> lista = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PRODUCTID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        NAME.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        PRICE.setCellValueFactory(new PropertyValueFactory("price"));
        DISCOUNT.setCellValueFactory(new PropertyValueFactory("discount"));
        productsTable.setItems(getProducts());
        System.out.println(getProducts().toString());
        productsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 2){
                        if(productsTable.getSelectionModel().getSelectedItem() != null){
                            System.out.println("Wysłany "+productsTable.getSelectionModel().getSelectedItem().toString());
                            if(lista.isEmpty()){
                                lista.add((Product) productsTable.getSelectionModel().getSelectedItem());
                                addToTable(lista);
                            } else {
                                if(lista.contains(productsTable.getSelectionModel().getSelectedItem())){
                                    System.out.println("Ten object już tam sie znajduje");
                                } else {
                                    lista.add((Product) productsTable.getSelectionModel().getSelectedItem());
                                    addToTable(lista);
                                }
                            }
                        }
                    }
                }
            }
        });

        discountTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 2){
                        if(discountTable.getSelectionModel().getSelectedItem() != null){
                            lista.remove(discountTable.getSelectionModel().getSelectedItem());
                            addToTable(lista);
                            System.out.println("Usuwany object "+discountTable.getSelectionModel().getSelectedItem().toString());
                        }
                    }
                }
            }
        });
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

    public void addToTable(ObservableList<Product> item) {
        PRODUCTID_CHANGE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId())));
        NAME_CHANGE.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getName()));
        PRICE_CHANGE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getPrice())));
        DISCOUNT_CHANGE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getDiscount())));
        System.out.println("Odebrane " + item.toString() + " rozmiar " + item.size());
        try {
            if (!item.isEmpty()) {
                discountTable.setItems(item);
            } else {
                //naprawić
            }
        } catch (NullPointerException e) {
            System.out.println("NullPointerException po odjęciu ostatniego elementu " + e);
        }
    }
}
