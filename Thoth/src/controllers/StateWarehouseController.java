package controllers;

import entity.*;
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
    public TableColumn<State_of_indent, String> CITY;
    @FXML
    public TableColumn<State_of_indent, String> STATE;
    @FXML
    public TableColumn<State_of_indent, String> ORDERNR;
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

    @FXML
    public TableColumn<Indent_product, String> PRODUCTID_ORDER;
    @FXML
    public TableColumn<Indent_product, String> NAME_ORDER;
    @FXML
    public TableColumn<Indent_product, String> PRICE_ORDER;
    @FXML
    public TableColumn<Indent_product, String> AMOUNT_ORDER;


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
            PRODUCTID_ORDER.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
            NAME_ORDER.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
            PRICE_ORDER.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
            AMOUNT_ORDER.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
            newOrderShop.setItems(getOrderProducts());
            System.out.println("xdddddddddddddddd"+getOrderProducts());
        }
        if (location.toString().contains("state_order_warehouse")) {
            CITY.setCellValueFactory(produktData-> new SimpleStringProperty(produktData.getValue().getIndentId().getShopId_delivery().getCity()));
            STATE.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getStateId().getName()));
            ORDERNR.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getIndentId().getIndentId())));
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

    private ObservableList<State_of_indent> getOrder() {
        ObservableList<State_of_indent> orderList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_of_indent> eList = session.createQuery("FROM State_of_indent WHERE indentId.shopId_need.shopId = :idshop "
        ).setParameter("idshop", sessionContext.getCurrentLoggedShop().getShopId()).list();
        System.out.println("getOrder " + eList);
        orderList.addAll(eList);
        session.close();
        return orderList;
    }

    private ObservableList<Indent_product> getOrderProducts() {
        ObservableList<Indent_product> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Indent_product> eList = session.createQuery("FROM Indent_product WHERE indentId.shopId_need.shopId = :idshop AND indentId.customerId is not null"
        ).setParameter("idshop", sessionContext.getCurrentLoggedShop().getShopId()).list();
        System.out.println("getOrderProducts " + eList);
        productList.addAll(eList);
        session.close();
        return productList;
    }

    private ObservableList<State_on_shop> getProductsForOtherShop(int id) {
        ObservableList<State_on_shop> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_on_shop> eList = session.createQuery("FROM State_on_shop WHERE shopId.shopId = :idshop AND amount > 0"
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

    public void changeOrderStatus(){
        if(stateOrderWarehouse.getSelectionModel().getSelectedItem() != null ){
            System.out.println(stateOrderWarehouse.getSelectionModel().getSelectedItem().toString());
            StateOrderModel model = (StateOrderModel) stateOrderWarehouse.getSelectionModel().getSelectedItem();
            //System.out.println(model.getCity());

//            Session session = sessionFactory.openSession();
//            session.beginTransaction();
//            State p = (State) stateOrderWarehouse.getSelectionModel().getSelectedItem();
//            System.out.println(p.getStateId());
//            session.close();

        }

    }
}