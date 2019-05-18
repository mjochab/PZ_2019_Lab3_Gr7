package controllers;

import entity.Shop;
import entity.State_on_shop;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import models.StateOrderModel;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionContext;
import static controllers.MainWindowController.sessionFactory;
import static controllers.WarehouseNewProductController.isNumeric;


public class StateWarehouseController implements Initializable {
    @FXML
    public TableView stateWarehouse;
    @FXML
    public TableView new_order, add_new_order;
    @FXML
    public TableView stateOrderWarehouse;
    @FXML
    public TableView newOrderShop;
    @FXML
    public TableColumn<State_on_shop, String> PRODUCTID;
    @FXML
    public TableColumn<State_on_shop, String> PRODUCTID_ADD;
    @FXML
    public TableColumn<State_on_shop, String> NAME;
    @FXML
    public TableColumn<State_on_shop, String> NAME_ADD;
    @FXML
    public TableColumn<State_on_shop, String> PRICE;
    @FXML
    public TableColumn<State_on_shop, String> PRICE_ADD;
    @FXML
    public TableColumn<State_on_shop, String> AMOUNT;
    @FXML
    public TableColumn<State_on_shop, String> AMOUNT_ADD;
    @FXML
    public TableColumn<State_on_shop, String> DISCOUNT;
    @FXML
    public TableColumn<StateOrderModel, String> CITY;
    @FXML
    public TableColumn<StateOrderModel, String> STATE;
    @FXML
    public TableColumn<StateOrderModel, String> ORDERNR;
    @FXML
    public Button searchStateWarehouse;
    @FXML
    public TextField searchSWCity;
    @FXML
    private ComboBox<Shop> comboList;
    @FXML
    MenuItem logout;
    @FXML
    MenuItem back;
    @FXML
    Parent root;

    private Stage stage;

    private ObservableList<State_on_shop> lista = FXCollections.observableArrayList();

    private String nazwaProduktu = null;

    public void menuItemAction(ActionEvent actionEvent) throws IOException { //powrót , wylogowanie na MENU ITEM
        stage = (Stage) root.getScene().getWindow();
        if (actionEvent.getSource() == logout) {
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/MainWindow.fxml"));
        } else {
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/choose_employee.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (sessionContext.getCurrentLoggedUser().getUserId() == 1) {
            if (back != null) {
                back.setVisible(true);
            } else {
                System.out.println("BACK is null");
            }
        }
        if (location.toString().contains("state_warehouse")) {
            PRODUCTID.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
            NAME.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
            PRICE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
            AMOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
            DISCOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getDiscount())));
            stateWarehouse.setItems(getProducts(nazwaProduktu));
            System.out.println(getProducts(nazwaProduktu).toString());
            setEditableAmountInWarehouse();
        }
        if (location.toString().contains("new_order_warehouse")) {
            PRODUCTID.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
            NAME.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
            PRICE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
            AMOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
            new_order.setItems(getProductsForOtherShop(sessionContext.getCurrentLoggedShop().getShopId()));
            setComboList();
            //System.out.println(getProducts(nazwaProduktu).toString());
            new_order.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        if (new_order.getSelectionModel().getSelectedItem() != null) {
                            System.out.println("Wysłany " + new_order.getSelectionModel().getSelectedItem().toString());
                            if (lista.isEmpty()) {
                                lista.add((State_on_shop) new_order.getSelectionModel().getSelectedItem());
                                addToTable(lista);
                            } else {
                                if (lista.contains(new_order.getSelectionModel().getSelectedItem())) {
                                    System.out.println("Ten object już tam sie znajduje");
                                } else {
                                    lista.add((State_on_shop) new_order.getSelectionModel().getSelectedItem());
                                    addToTable(lista);
                                }
                            }
                        }
                    }
                }
            });
            //-------------------------------------------------------------------------------------------------------
            add_new_order.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 3) {
                        if (add_new_order.getSelectionModel().getSelectedItem() != null) {
                            System.out.println("Usuwany object " + add_new_order.getSelectionModel().getSelectedItem().toString());
                            lista.remove(add_new_order.getSelectionModel().getSelectedItem());
                            addToTable(lista);
                        }
                    }
                }
            });
            setEditableAmount();
        }
        if (location.toString().contains("new_order_shop")) {
            PRODUCTID.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().toString()));
            NAME.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
            PRICE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
            AMOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
            newOrderShop.setItems(getOrderProducts());
        }
        if (location.toString().contains("state_order_warehouse")) {
            CITY.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getCity()));
            STATE.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getState()));
            ORDERNR.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getOrderid())));
            stateOrderWarehouse.setItems(getOrder());
            //System.out.println(getOrder(nazwaProduktu).toString());
        }
    }

    private ObservableList<State_on_shop> getProducts(String nazwaProduktu) {
        ObservableList<State_on_shop> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_on_shop> eList;
        if (nazwaProduktu == null || nazwaProduktu.equals("")) {
            eList = session.createQuery("FROM State_on_shop WHERE ShopId = :idshop GROUP by productId"
            ).setParameter("idshop", sessionContext.getCurrentLoggedShop().getShopId()).list();
        } else {
            eList = session.createQuery("FROM State_on_shop WHERE shopId.shopId = :idshop AND productId.name like :produkt GROUP by productId")
                    .setParameter("idshop", sessionContext.getCurrentLoggedShop().getShopId())
                    .setParameter("produkt", "%"+nazwaProduktu+"%").list();
            searchSWCity.setText("");
        }
        System.out.println("getProducts " + eList);
        productList.addAll(eList);
        session.close();
        return productList;
    }

    private ObservableList<StateOrderModel> getOrder() {
        ObservableList<StateOrderModel> orderList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<StateOrderModel> eList = session.createQuery("SELECT new models.StateOrderModel(p.city, s.name, i.indentId) FROM State_of_indent v " +
                "INNER JOIN Indent i ON i.indentId = v.indentId  " +
                "INNER JOIN State s ON s.stateId = v.stateId  " +
                "INNER JOIN Shop p ON p.shopId = i.shopId_need " +
                "WHERE p.shopId = :idshop "
        ).setParameter("idshop", sessionContext.getCurrentLoggedShop().getShopId()).list();
        System.out.println("getOrder " + eList);
        orderList.addAll(eList);
        session.close();
        return orderList;
    }

    private ObservableList<State_on_shop> getOrderProducts() {
        ObservableList<State_on_shop> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_on_shop> eList = session.createQuery("FROM Shop sp " +
                "INNER JOIN State_on_shop sos ON sp.shopId = sos.shopId " +
                "INNER JOIN Product p ON sos.productId = p.productId " +
                "INNER JOIN Indent_product ip ON p.productId = ip.productId " +
                "INNER JOIN Indent i ON sp.shopId = i.shopId_need " +
                "INNER JOIN State_of_indent soi ON i.indentId = soi.indentId " +
                "INNER JOIN State s ON soi.stateId = s.stateId " +
                "WHERE sp.shopId = :idshop AND s.name like 'W realizacji' "
        ).setParameter("idshop", sessionContext.getCurrentLoggedShop().getShopId()).list();
        System.out.println("getOrderProducts " + eList);
        productList.addAll(eList);
        session.close();
        return productList;
    }

    private ObservableList<State_on_shop> getProductsForOtherShop(int id) {
        ObservableList<State_on_shop> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_on_shop> eList = session.createQuery("FROM State_on_shop WHERE ShopId = :idshop AND amount > 0"
        ).setParameter("idshop", id).list();
        nazwaProduktu = null;
        System.out.println("getProductsForOtherShop " + eList);
        productList.addAll(eList);
        session.close();
        return productList;
    }

    public void searchStateWarehouse() {
        nazwaProduktu = searchSWCity.getText();
        stateWarehouse.setItems(getProducts(nazwaProduktu));
    }

    private ObservableList<Shop> getShops() {
        ObservableList<Shop> shops = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Shop> shopsList = session.createQuery("from Shop").list();

        shops.addAll(shopsList);

        session.close();
        System.out.println("Zwracam sklepy");
        return shops;

    }

    private void setComboList() {
        this.comboList.getItems().addAll(getShops());
        comboList.getSelectionModel().select(sessionContext.getCurrentLoggedShop().getShopId() - 1);
    }

    public void searchNewOrderWarehouse() {
        //System.out.println("COMOBOBOX "+comboList.getItems().get(0).toString());
        int id = comboList.getSelectionModel().getSelectedItem().getShopId();
        PRODUCTID.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
        NAME.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
        PRICE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
        AMOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        new_order.setItems(getProductsForOtherShop(id));
    }

    private void addToTable(ObservableList<State_on_shop> item) {
        PRODUCTID_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
        NAME_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
        PRICE_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
        AMOUNT_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        System.out.println("Odebrane " + item.toString() + " rozmiar " + item.size());
        try {
            if (!item.isEmpty()) {
                add_new_order.setItems(item);
            } else {
                //naprawić
            }
        } catch (NullPointerException e) {
            System.out.println("NullPointerException po odjęciu ostatniego elementu " + e);
        }
    }

    public void newOrderWarehouse() { //add_new_order.getItems().clear();
        System.out.println(lista.toString());
        if (!lista.isEmpty()) {
            //zapytanie do bazy
        }
    } //button zapisz

    private void setEditableAmount() {
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
                    add_new_order.refresh();
                }
            } catch (NumberFormatException exc) {
                System.out.println("Powrót do poprzedniej liczby");
                add_new_order.refresh();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niepowodzenie");
                alert.setContentText("Wprowadzona wartość nie jest liczbą!");
                alert.showAndWait();
            }
        });
    }

    private void setEditableAmountInWarehouse() {
        AMOUNT.setCellFactory(TextFieldTableCell.forTableColumn());


        AMOUNT.setOnEditCommit(e -> {
            try {
                System.out.println("PRZED" + e.getTableView().getSelectionModel().getSelectedItem().getAmount());
                int check = e.getTableView().getSelectionModel().getSelectedItem().getAmount();
                if (!isNumeric(e.getNewValue())) {
                    throw new NumberFormatException();
                }
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setAmount(Integer.parseInt(e.getNewValue()));
                System.out.println("PO" + e.getTableView().getSelectionModel().getSelectedItem().getAmount());
                if (e.getTableView().getSelectionModel().getSelectedItem().getAmount() > 0) {
                    System.out.println("większe od 0 i mniejsze od ");
                } else {
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setAmount(check);
                    System.out.println("Powrót do poprzedniej liczby");
                    stateWarehouse.refresh();
                }
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                State_on_shop p = e.getTableView().getSelectionModel().getSelectedItem();
                System.out.println("testowy print obiektu" + p.toString());
                session.update(p);

                session.getTransaction().commit();
                session.close();

            } catch (NumberFormatException exc) {
                System.out.println("Powrót do poprzedniej liczby");
                stateWarehouse.refresh();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niepowodzenie");
                alert.setContentText("Wprowadzona wartość nie jest liczbą!");
                alert.showAndWait();
            }
        });
    }
}