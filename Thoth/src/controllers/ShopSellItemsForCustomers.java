package controllers;

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
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;
import static controllers.MainWindowController.sessionContext;
import static controllers.WarehouseNewProductController.isNumeric;

public class ShopSellItemsForCustomers implements Initializable {
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
    public TableColumn<State_on_shop, String> DISCOUNT, DISCOUNT_ADD;
    @FXML
    public TextField serachShop, nameTF, lastNameTF, numerPhoneTF;
    @FXML
    Parent root;

    Stage stage;

    private ObservableList<State_on_shop> lista = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PRODUCTID.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
        NAME.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
        PRICE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
        AMOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        DISCOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getDiscount())));
        productsTable.setItems(getProducts(null));
        System.out.println(getProducts(null));
        productsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        if (productsTable.getSelectionModel().getSelectedItem() != null) {
                            System.out.println("Wysłany " + productsTable.getSelectionModel().getSelectedItem().toString());
                            if (lista.isEmpty()) {
                                lista.add((State_on_shop) productsTable.getSelectionModel().getSelectedItem());
                                addToTable(lista);
                            } else {
                                if (lista.contains(productsTable.getSelectionModel().getSelectedItem())) {
                                    System.out.println("Ten object już tam sie znajduje");
                                } else {
                                    lista.add((State_on_shop) productsTable.getSelectionModel().getSelectedItem());
                                    addToTable(lista);
                                }
                            }
                        }
                    }
                }
            }
        });
        //-------------------------------------------------------------------------------------
        productsTableAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 3) {
                        if (productsTableAdd.getSelectionModel().getSelectedItem() != null) {
                            System.out.println("Usuwany object " + productsTableAdd.getSelectionModel().getSelectedItem().toString());
                            lista.remove(productsTableAdd.getSelectionModel().getSelectedItem());
                            addToTable(lista);
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
            eList = session.createQuery("FROM State_on_shop s WHERE s.amount > 0 AND s.shopId.shopId <> :idsklepu")
                    .setParameter("idsklepu", sessionContext.getCurrentLoggedShop().getShopId())
                    .list();
        } else {
            eList = session.createQuery("FROM State_on_shop s WHERE s.amount > 0 AND s.shopId.shopId <> :idsklepu AND s.productId.name like :produkt")
                    .setParameter("idsklepu", sessionContext.getCurrentLoggedShop().getShopId())
                    .setParameter("produkt", "%" + productName + "%").list(); //za wyjątkiem własnego sklepu!! poprawić
            serachShop.setText("");
        }
        productList.addAll(eList);
        session.close();
        return productList;
    }

    public void addToTable(ObservableList<State_on_shop> item) {
        PRODUCTID_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
        NAME_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
        PRICE_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
        AMOUNT_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount()-produktData.getValue().getLocked())));
        DISCOUNT_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getDiscount())));
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

        AMOUNT_ADD.setOnEditCommit(e -> {
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
        if (!lista.isEmpty()) {
            System.out.println("Przygotowane dane do wysłąnia " + lista + " " + nameTF.getText() + " " + lastNameTF.getText() + " " + numerPhoneTF.getText());
            System.out.println("Dodaj walidacje textfield żeby nie były puste");
        }
    }
}

