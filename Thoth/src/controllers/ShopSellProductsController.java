package controllers;

import entity.*;
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
import models.StateOnShop;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static utils.Alerts.*;

import static controllers.MainWindowController.sessionContext;
import static controllers.MainWindowController.sessionFactory;
import static controllers.WarehouseNewProductController.isNumeric;

public class ShopSellProductsController implements Initializable {
    @FXML
    MenuItem logout;

    @FXML
    private TableView<StateOnShop> productsTableAdd;
    @FXML
    private TableColumn<StateOnShop, String> PRODUCTID_ADD;
    @FXML
    private TableColumn<StateOnShop, String> NAME_ADD;
    @FXML
    private TableColumn<StateOnShop, String> PRICE_ADD;
    @FXML
    public TableColumn<StateOnShop, String> AMOUNT_ADD;

    @FXML
    private TableView productsTable;
    @FXML
    public TableColumn<State_on_shop, String> PRODUCTID;
    @FXML
    public TableColumn<State_on_shop, String> NAME;
    @FXML
    public TableColumn<State_on_shop, String> PRICE;
    @FXML
    public TableColumn<State_on_shop, String> AMOUNT;

    @FXML
    public TextField serachShop;
    @FXML
    Parent root;
    Stage stage;

    private ObservableList<StateOnShop> list = FXCollections.observableArrayList();

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
                            StateOnShop sos = new StateOnShop();
                            sos.setStateOnShop((State_on_shop) productsTable.getSelectionModel().getSelectedItem());
                            sos.setAmount(1);
                            System.out.println("Wysłany " + productsTable.getSelectionModel().getSelectedItem().toString());
                            if (list.isEmpty()) {
                                list.add(sos);
                                addToTable(list);
                            } else {
                                for (StateOnShop ex : list) {
                                    if (ex.getStateOnShop().getId() == ((State_on_shop) productsTable.getSelectionModel().getSelectedItem()).getId()) {
                                        System.out.println("java FX <3");
                                        return;
                                    }
                                }
                                list.add(sos);
                                addToTable(list);
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
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    if (event.getClickCount() == 1) {
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
        productList.addAll(eList);
        session.close();
        return productList;
    }

    public void addToTable(ObservableList<StateOnShop> list) {
        PRODUCTID_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getStateOnShop().getProductId().getProductId())));
        NAME_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getStateOnShop().getProductId().getName()));
        PRICE_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getStateOnShop().getProductId().getPrice())));
        AMOUNT_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        System.out.println("Odebrane " + list.toString() + " rozmiar " + list.size());
        try {
            if (!list.isEmpty()) {
                productsTableAdd.setItems(list);
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
                int check = e.getRowValue().getStateOnShop().getAmount()-e.getRowValue().getStateOnShop().getLocked();
                if (!isNumeric(e.getNewValue())) {
                    throw new NumberFormatException();
                }
                if (Integer.valueOf(e.getNewValue()) > 0 && Integer.valueOf(e.getNewValue()) <= check) {
                    System.out.println("większe od 0 i mniejsze od " + check);
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setAmount(Integer.parseInt(e.getNewValue()));
                    System.out.println("PO" + e.getTableView().getSelectionModel().getSelectedItem().getAmount());
                } else {
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setAmount(Integer.valueOf(e.getOldValue()));
                    System.out.println("Ustawienie starej wartości + old value" + e.getOldValue() + "," + e.getNewValue());
                    productsTableAdd.refresh();
                    showNumberRangeAlert(1,check);
                }
            } catch (NumberFormatException exc) {
                System.out.println("Powrót do poprzedniej liczby");
                productsTableAdd.refresh();
                showNotNumberAlert();
            }
        });
    }

    public void confirm() {
        if (!list.isEmpty()) {
            System.out.println("Przygotowana list do zapytania " + list.toString());
            Session session = sessionFactory.openSession();
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
            String dateString = dateFormat.format(currentDate);
            System.out.println("Current date:" + dateString);

            Receipt receipt = new Receipt(sessionContext.getCurrentLoggedShop(), getTotalValue(list), sessionContext.getCurrentLoggedUser(), currentDate);
            System.out.println("Wartość zamówienia: " + getTotalValue(list));
            session.save(receipt);

            for (StateOnShop sos : list) {
                Product_receipt product_receipt = new Product_receipt(sos.getStateOnShop().getProductId(), receipt, sos.getAmount(), getSingleValue(sos));
                System.out.println(sos.getStateOnShop().getProductId().getName() + "Wartość produktu: " + getSingleValue(sos));
                session.save(product_receipt);
                System.out.println("wartosc pobrana: " + sos.getAmount());
                State_on_shop sosNew = session.get(State_on_shop.class, sos.getStateOnShop().getId());
                System.out.println("stara wartosc: " + sosNew.getAmount());
                sosNew.setAmount(sosNew.getAmount() - sos.getAmount());
                session.update(sosNew);
            }
            session.beginTransaction().commit();
            list.removeAll();
            productsTableAdd.getItems().clear();
            session.close();
        }
    }

    public BigDecimal getTotalValue(ObservableList<StateOnShop> list) {
        BigDecimal totalValue = BigDecimal.ZERO;
        for (StateOnShop sos : list) {
            totalValue = totalValue.add((sos.getStateOnShop().getProductId().getPrice().multiply(new BigDecimal((100 - sos.getStateOnShop().getProductId().getDiscount()) / 100))).multiply(new BigDecimal(sos.getAmount())));

        }
        System.out.println("wartosc zamowienia getTotalValue:" + totalValue);
        return totalValue;
    }

    public BigDecimal getSingleValue(StateOnShop sos) {
        BigDecimal totalValue = new BigDecimal(0);
        totalValue = totalValue.add((sos.getStateOnShop().getProductId().getPrice().multiply(new BigDecimal((100 - sos.getStateOnShop().getProductId().getDiscount()) / 100))).multiply(new BigDecimal(sos.getAmount())));
        return totalValue;
    }

}

