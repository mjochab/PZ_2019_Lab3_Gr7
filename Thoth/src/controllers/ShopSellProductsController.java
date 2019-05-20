package controllers;

import entity.State;
import entity.State_on_shop;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionContext;
import static controllers.MainWindowController.sessionFactory;
import static controllers.WarehouseNewProductController.isNumeric;

public class ShopSellProductsController implements Initializable {
    @FXML
    MenuItem logout;
    @FXML
    private TableView productsTable, productsTableAdd;
    @FXML
    public TableColumn<State_on_shop, String> PRODUCTID, PRODUCTID_ADD;
    @FXML
    public TableColumn<State_on_shop, String> NAME, NAME_ADD;
    @FXML
    public TableColumn<State_on_shop, String> PRICE, PRICE_ADD;
    @FXML
    public TableColumn<State_on_shop, String> AMOUNT, AMOUNT_ADD;
    @FXML
    public TextField serachShop;
    @FXML
    Parent root;
    Stage stage;

    private ObservableList<State_on_shop> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PRODUCTID.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
        NAME.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
        PRICE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
        AMOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount() - produktData.getValue().getLocked())));
        productsTable.setItems(getProducts(null));
        productsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        if (productsTable.getSelectionModel().getSelectedItem() != null) {
                            System.out.println("Wysłany " + productsTable.getSelectionModel().getSelectedItem().toString());
                            if (list.isEmpty()) {
                                list.add((State_on_shop) productsTable.getSelectionModel().getSelectedItem());
                                addToTable(list);
                            } else {
                                if (list.contains(productsTable.getSelectionModel().getSelectedItem())) {
                                    System.out.println("Ten object już tam sie znajduje");
                                } else {
                                    list.add((State_on_shop) productsTable.getSelectionModel().getSelectedItem());
                                    addToTable(list);
                                }
                            }
                        }
                    }
                }
            }
        });
        //----------------------------------------------------------------------------------------
        productsTableAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 3) {
                        if (productsTableAdd.getSelectionModel().getSelectedItem() != null) {
                            System.out.println("Usuwany object " + productsTableAdd.getSelectionModel().getSelectedItem().toString());
                            list.remove(productsTableAdd.getSelectionModel().getSelectedItem());
                            addToTable(list);
                        }
                    }
                }
            }
        });
        setEditableAmount();
    }


    public ObservableList<State_on_shop> getProducts(String productName) {
        ObservableList<State_on_shop> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_on_shop> eList;
        if (productName == null || productName.equals("")) {
            eList = session.createQuery("FROM State_on_shop s WHERE s.amount > 0 AND s.shopId.shopId = :idsklepu")
                    .setParameter("idsklepu", sessionContext.getCurrentLoggedShop().getShopId())
                    .list();
        } else {
            eList = session.createQuery("FROM State_on_shop s WHERE s.amount > 0 AND s.shopId.shopId = :idsklepu AND s.productId.name like :produkt")
                    .setParameter("idsklepu", sessionContext.getCurrentLoggedShop().getShopId())
                    .setParameter("produkt", "%" + productName + "%").list();
            serachShop.setText("");
        }
        for (State_on_shop ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }

    public void addToTable(ObservableList<State_on_shop> item) {
        PRODUCTID_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
        NAME_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
        PRICE_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
        AMOUNT_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        System.out.println("Odebrane " + item.toString() + " rozmiar " + item.size());
        try {
            if (!item.isEmpty()) {
                productsTableAdd.setItems(item);
            } else {
                //naprawić
            }
        } catch (NullPointerException e) {
            System.out.println("NullPointerException po odjęciu ostatniego elementu " + e);
        }
    }

    public void searchStateShop() {
        productsTable.setItems(getProducts(serachShop.getText()));
    }

    private void setEditableAmount() {
        productsTableAdd.setEditable(true);
        AMOUNT_ADD.setCellFactory(TextFieldTableCell.forTableColumn());

        AMOUNT_ADD.setOnEditCommit(e -> { // dodać walidacje try catch
            try {
                System.out.println("PRZED" + e.getTableView().getSelectionModel().getSelectedItem().getAmount());
                int check = e.getTableView().getSelectionModel().getSelectedItem().getAmount();
                if (!isNumeric(e.getNewValue())) {
                    throw new NumberFormatException();
                }
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setAmount(Integer.parseInt(e.getNewValue()));
                System.out.println("PO" + e.getTableView().getSelectionModel().getSelectedItem().getAmount());
                if (e.getTableView().getSelectionModel().getSelectedItem().getAmount() > 0 && e.getTableView().getSelectionModel().getSelectedItem().getAmount() <= check) {
                    System.out.println("większe od 0 i mniejsze od ");
                } else {
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setAmount(check);
                    System.out.println("Powrót do poprzedniej liczby");
                    productsTableAdd.refresh();
                }
            } catch (NumberFormatException exc) {
                System.out.println("Powrót do poprzedniej liczby");
                productsTableAdd.refresh();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niepowodzenie");
                alert.setContentText("Wprowadzona wartość nie jest liczbą!");
                alert.showAndWait();
            }

        });
    }

    public void confirm() {
        if (!list.isEmpty()) {
            System.out.println("Przygotowana list do zapytania " + list.toString());
            Session session = sessionFactory.openSession();
            for (State_on_shop sos : list) {
                session.getTransaction();
            }
        }
    }

}

