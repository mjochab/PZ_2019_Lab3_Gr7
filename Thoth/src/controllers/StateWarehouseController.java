package controllers;

import entity.Indent;
import entity.Shop;
import entity.State_of_indent;
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
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.*;
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
            overlapStateWarehouse();
        }
        if (location.toString().contains("new_order_warehouse")) {
            overlapNewOrderWarehouse();
        }
        if (location.toString().contains("new_order_shop")) {
            overlapNewOrderShop();
        }
        if (location.toString().contains("state_order_warehouse")) {
            overlapStateOrderWarehouse();
        }
    }

    //ZAKŁADKA(1) STAN MAGAZYNU----------------------------------------------------------------------------------------------------------------------------

    public void overlapStateWarehouse() {
        PRODUCTID.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
        NAME.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
        PRICE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
        AMOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        DISCOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getDiscount())));
        stateWarehouse.setItems(getProducts(nazwaProduktu));
        System.out.println(getProducts(nazwaProduktu).toString());
        setEditableAmountInWarehouse();
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
                    .setParameter("produkt", "%" + nazwaProduktu + "%").list();
            searchSWCity.setText("");
        }
        System.out.println("getProducts " + eList);
        productList.addAll(eList);
        session.close();
        return productList;
    }

    public void searchStateWarehouse() {
        nazwaProduktu = searchSWCity.getText();
        stateWarehouse.setItems(getProducts(nazwaProduktu));
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
                newAlert("Niepowodzenie", "Wprowadzona wartość nie jest liczbą!");
            }
        });
    }

    //ZAKŁADKA(3) NOWE ZAMÓWIENIE----------------------------------------------------------------------------------------------------------------------------

    public void overlapNewOrderWarehouse() {
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

    private ObservableList<State_on_shop> getProductsForOtherShop(int id) {
        ObservableList<State_on_shop> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_on_shop> eList = session.createQuery("FROM State_on_shop WHERE ShopId = :idshop AND amount > 0"
        ).setParameter("idshop", id).list();
        nazwaProduktu = null;
        //System.out.println("getProductsForOtherShop " + eList);
        productList.addAll(eList);
        session.close();
        return productList;
    }

    private void setComboList() {
        this.comboList.getItems().addAll(getShops());
        comboList.getSelectionModel().select(sessionContext.getCurrentLoggedShop().getShopId() - 1);
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
                newAlert("Niepowodzenie", "Wprowadzona wartość nie jest liczbą!");
            }
        });
    }

    public void searchNewOrderWarehouse() {
        //System.out.println("COMOBOBOX "+comboList.getItems().get(0).toString());
        new_order.setItems(getProductsForOtherShop(comboList.getSelectionModel().getSelectedItem().getShopId()));
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

    public void newOrderWarehouse() throws ParseException {  //button zapisz

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = format.parse(dtf.format(now));
        System.out.println(date);

        ArrayList<Integer> listOfStores = new ArrayList<Integer>();
        if (!lista.isEmpty()) {
            System.out.println(lista.get(0).toString());
            Session session = sessionFactory.openSession();
            session.getTransaction().begin();
            for (State_on_shop state_on_shop : lista) {
                listOfStores.add(state_on_shop.getShopId().getShopId());
            }
            for (int i = 1; i < listOfStores.size(); i++) {
                if (listOfStores.get(i) == listOfStores.get(0)) { //SIMPLE
                    Shop shopIdNeed = (Shop) session.get(Shop.class,sessionContext.getCurrentLoggedShop().getShopId());
                    Shop shopIdDelivery = (Shop) session.get(Shop.class,listOfStores.get(0));

                    System.out.println("need "+shopIdNeed.getShopId()+ " delivery "+shopIdDelivery.getShopId());
                    Indent simpleOrder = new Indent(shopIdNeed,shopIdDelivery,null,date,null,null);
                    session.save(simpleOrder);
                } else { //COMPLEX
                    System.out.println("przeglądanie listy");
                    System.out.println(listOfStores.get(0));
                    System.out.println(listOfStores.get(i));
                }
            }
            session.getTransaction().commit();
            session.close();
        }
        lista.removeAll();
        new_order.getItems().clear();
        new_order.setItems(getProductsForOtherShop(comboList.getSelectionModel().getSelectedItem().getShopId()));
        add_new_order.getItems().clear();
    }

    //ZAKŁADKA(4) NOWE ZAMÓWIENIE ZE SKLEPU----------------------------------------------------------------------------------------------------

    public void overlapNewOrderShop() {
        PRODUCTID.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().toString()));
        NAME.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
        PRICE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
        AMOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        newOrderShop.setItems(getOrderProducts());
    }

    private ObservableList<State_on_shop> getOrderProducts() { //niekompletne
        ObservableList<State_on_shop> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_on_shop> eList = session.createQuery("FROM Shop sp " +
                "INNER JOIN State_on_shop sos ON sp.shopId = sos.shopId " +
                "INNER JOIN Product p ON sos.productId = p.productId " +
                "INNER JOIN Indent_product ip ON p.productId = ip.productId " +
                "INNER JOIN Indent i ON sp.shopId = i.shopId_need " +
                "INNER JOIN State_of_indent soi ON i.indentId = soi.indentId " +
                "INNER JOIN State s ON soi.stateId = s.stateId " +
                "WHERE sp.shopId = :idshop AND s.name = 'W realizacji' "
        ).setParameter("idshop", sessionContext.getCurrentLoggedShop().getShopId()).list();
        System.out.println("getOrderProducts " + eList);
        productList.addAll(eList);
        session.close();
        return productList;
    }

    public void confirmOrder() { //button
        if (!newOrderShop.getItems().isEmpty()) {

        }
    }

    //ZAKŁADKA(5) ZMIANA STATUSU---------------------------------------------------------------------------------------------------------------

    public void overlapStateOrderWarehouse() {
        CITY.setCellValueFactory(orderData -> new SimpleStringProperty(orderData.getValue().getIndentId().getShopId_delivery().getCity()));
        STATE.setCellValueFactory(orderData -> new SimpleStringProperty(orderData.getValue().getStateId().getName()));
        ORDERNR.setCellValueFactory(orderData -> new SimpleStringProperty(String.valueOf(orderData.getValue().getIndentId().getIndentId())));
        stateOrderWarehouse.setItems(getOrder());
        //System.out.println(getOrder(nazwaProduktu).toString());
    }

    private ObservableList<State_of_indent> getOrder() {
        ObservableList<State_of_indent> orderList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_of_indent> eList = session.createQuery("FROM State_of_indent WHERE indentId.shopId_need.shopId = :idshop "
        ).setParameter("idshop", sessionContext.getCurrentLoggedShop().getShopId()).list();
//        "SELECT new models.StateOrderModel(p.city, s.name, i.indentId) FROM State_of_indent v " +
//                "INNER JOIN Indent i ON i.indentId = v.indentId  " +
//                "INNER JOIN State s ON s.stateId = v.stateId  " +
//                "INNER JOIN Shop p ON p.shopId = i.shopId_need " +
//                "WHERE p.shopId = :idshop "
        System.out.println("getOrder " + eList);
        orderList.addAll(eList);
        session.close();
        return orderList;
    }

    public void changeOrderStatus() {
        if (stateOrderWarehouse.getSelectionModel().getSelectedItem() != null) {
            System.out.println(stateOrderWarehouse.getSelectionModel().getSelectedItem().toString());
            State_of_indent model = (State_of_indent) stateOrderWarehouse.getSelectionModel().getSelectedItem();
            System.out.println(model);

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            State_of_indent p = (State_of_indent) stateOrderWarehouse.getSelectionModel().getSelectedItem();
            System.out.println(p.getStateId().getName());
            p.getStateId().setStateId(3);
            session.update(p);
            session.getTransaction().commit();
            session.close();
//            stateOrderWarehouse.getItems().clear();
//            stateOrderWarehouse.setItems(getOrderProducts());
        } else {

        }
    }
}