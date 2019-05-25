package controllers;

import entity.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import models.IndentTableView;
import models.StateOnShop;
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
import static utils.Alerts.*;


public class StateWarehouseController implements Initializable {
    @FXML
    public TableView stateWarehouse;
    @FXML
    public TableView new_order, add_new_order;
    @FXML
    public TableView<State_of_indent> stateOrderWarehouse;
    @FXML
    public TableView<State_of_indent> stateOrderToPrepareWarehouse;
    @FXML
    public TableView newOrderShop;
    @FXML
    public TableColumn<State_on_shop, String> PRODUCTID;
    @FXML
    public TableColumn<StateOnShop, String> PRODUCTID_ADD;
    @FXML
    public TableColumn<State_on_shop, String> NAME;
    @FXML
    public TableColumn<StateOnShop, String> NAME_ADD;
    @FXML
    public TableColumn<State_on_shop, String> PRICE;
    @FXML
    public TableColumn<StateOnShop, String> PRICE_ADD;
    @FXML
    public TableColumn<State_on_shop, String> AMOUNT;
    @FXML
    public TableColumn<StateOnShop, String> AMOUNT_ADD;
    @FXML
    public TableColumn<State_on_shop, String> DISCOUNT;
    @FXML
    public TableColumn<Indent_product, String> PRODUCTID_ORDER;
    @FXML
    public TableColumn<Indent_product, String> NAME_ORDER;
    @FXML
    public TableColumn<Indent_product, String> PRICE_ORDER;
    @FXML
    public TableColumn<Indent_product, String> AMOUNT_ORDER;
    @FXML
    public TableColumn<State_of_indent, String> CITY;
    @FXML
    public TableColumn<State_of_indent, String> STATE;
    @FXML
    public TableColumn<State_of_indent, String> ORDERNR;
    @FXML
    public Button searchStateWarehouse;
    @FXML
    public Button SHOW_DETAILS;
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
    private Label sessionInfo;

    private Stage stage;

    private ObservableList<StateOnShop> list = FXCollections.observableArrayList();

    private String nazwaProduktu = null;

    public void menuItemAction(ActionEvent actionEvent) throws IOException { //powrót , wylogowanie na MENU ITEM
        stage = (Stage) root.getScene().getWindow();
        if (actionEvent.getSource() == logout) {
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/MainWindow.fxml"));
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlfiles/choose_employee.fxml"));
            root = loader.load();
            MainWindowController mainController = loader.getController();
            mainController.setComboList();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (location.toString().contains("main_window_warehouse.fxml")) {
            sessionInfo.setText(" Zalogowano jako: " + sessionContext.getCurrentLoggedUser().getFirstName() + " " + sessionContext.getCurrentLoggedUser().getLastName() + " / Lokalizacja: " + sessionContext.getCurrentLoggedShop().getCity());
        }
        System.out.println(location.toString() + "\n tutaj sie znajduje");
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
        if (location.toString().contains("state_prepare_order_warehouse")) {
            overlapOrdersToPrepareWarehouse();
        }
        System.out.println("SESSION LOGGED USER:" + sessionContext.getCurrentLoggedShop().toString());
        System.out.println("SESSION LOGGED USER:" + sessionContext.getCurrentLoggedUser().toString());
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
            eList = session.createQuery("FROM State_on_shop WHERE shopId.shopId = :idshop GROUP by productId"
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
                showNotNumberAlert();
            }
        });
    }

    //ZAKŁADKA(3) NOWE ZAMÓWIENIE----------------------------------------------------------------------------------------------------------------------------

    public void overlapNewOrderWarehouse() {
        setComboList();
        PRODUCTID.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
        NAME.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
        PRICE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
        AMOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount() - produktData.getValue().getLocked())));
        new_order.setItems(getProductsForOtherShop(comboList.getSelectionModel().getSelectedItem().getShopId()));
        //System.out.println(getProducts(nazwaProduktu).toString());
        new_order.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    if (new_order.getSelectionModel().getSelectedItem() != null) {
                        StateOnShop sos = new StateOnShop();
                        sos.setStateOnShop((State_on_shop) new_order.getSelectionModel().getSelectedItem());
                        sos.setAmount(1);
                        System.out.println("Wysłany " + new_order.getSelectionModel().getSelectedItem().toString());
                        if (list.isEmpty()) {
                            list.add(sos);
                            addToTable(list);
                        } else {
                            for (StateOnShop ex : list) {
                                if (ex.getStateOnShop().getId() == ((State_on_shop) new_order.getSelectionModel().getSelectedItem()).getId()) {
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
        });
        //-------------------------------------------------------------------------------------------------------
        add_new_order.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                if (event.getClickCount() == 1) {
                    if (add_new_order.getSelectionModel().getSelectedItem() != null) {
                        System.out.println("Usuwany object " + add_new_order.getSelectionModel().getSelectedItem().toString());
                        list.remove(add_new_order.getSelectionModel().getSelectedItem());
                        addToTable(list);
                    }
                }
            }
        });
        setEditableAmount();
    }

    private ObservableList<State_on_shop> getProductsForOtherShop(int id) {
        ObservableList<State_on_shop> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_on_shop> eList = session.createQuery("FROM State_on_shop WHERE shopId.shopId = :idshop AND amount - locked > 0"
        ).setParameter("idshop", id).list();
        nazwaProduktu = null;
        //System.out.println("getProductsForOtherShop " + eList);
        productList.addAll(eList);
        session.close();
        return productList;
    }

    private void setComboList() {
        this.comboList.getItems().addAll(getShops());
        comboList.getSelectionModel().select(0);
    }

    public void changeShop() {
        new_order.setItems(getProductsForOtherShop(comboList.getSelectionModel().getSelectedItem().getShopId()));
    }

    private ObservableList<Shop> getShops() {
        ObservableList<Shop> shops = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Shop> shopsList = session.createQuery("from Shop").list();
        for (Shop shop : shopsList) {
            if (shop.getShopId() != sessionContext.getCurrentLoggedShop().getShopId()) {
                shops.add(shop);
            }
        }
        session.close();
        System.out.println("Zwracam sklepy");
        return shops;
    }

    private void setEditableAmount() {
        AMOUNT_ADD.setCellFactory(TextFieldTableCell.forTableColumn());

        AMOUNT_ADD.setOnEditCommit(e -> {
            try {
                System.out.println("PRZED" + e.getTableView().getSelectionModel().getSelectedItem().getAmount());
                int check = e.getRowValue().getStateOnShop().getAmount() - e.getRowValue().getStateOnShop().getLocked();
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
                    add_new_order.refresh();
                    showNumberRangeAlert(1, check);
                }
            } catch (NumberFormatException exc) {
                System.out.println("Powrót do poprzedniej liczby");
                add_new_order.refresh();
                showNotNumberAlert();
            }
        });
    }

    public void searchNewOrderWarehouse() {
        //System.out.println("COMOBOBOX "+comboList.getItems().get(0).toString());
        new_order.setItems(getProductsForOtherShop(comboList.getSelectionModel().getSelectedItem().getShopId()));
    }

    private void addToTable(ObservableList<StateOnShop> item) {
        PRODUCTID_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getStateOnShop().getProductId().getProductId())));
        NAME_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getStateOnShop().getProductId().getName()));
        PRICE_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getStateOnShop().getProductId().getPrice())));
        AMOUNT_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        add_new_order.setItems(item);
    }

    public void newOrderWarehouse() throws ParseException {  //button zapisz

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = format.parse(dtf.format(now));
        System.out.println(date);

        ArrayList<Integer> listOfStores = new ArrayList<Integer>();
        if (!list.isEmpty()) {
            System.out.println(list.get(0).toString());
            for (StateOnShop state_on_shop : list) {
                listOfStores.add(state_on_shop.getStateOnShop().getShopId().getShopId());
            }
            if (simpleOrComplex(listOfStores)) { //SIMPLE
                simpleOrder(date, listOfStores.get(0));
                newAlertOrder("Sukces", "Zamówiono towar (Simple)");
            } else { //COMPLEX
                complexOrder(date, listOfStores);
                newAlertOrder("Sukces", "Zamówiono towar (Complex)");
            }
        }
        list.removeAll();
        new_order.getItems().clear();
        new_order.setItems(getProductsForOtherShop(comboList.getSelectionModel().getSelectedItem().getShopId()));
        add_new_order.getItems().clear();
    }

    public boolean simpleOrComplex(ArrayList list) {
        boolean result = true;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) != list.get(0)) {
                result = false;
            } else {
                result = true;
            }
        }
        return result;
    }

    public void simpleOrder(Date date, int id) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Shop shopIdNeed = (Shop) session.get(Shop.class, sessionContext.getCurrentLoggedShop().getShopId());
        Shop shopIdDelivery = (Shop) session.get(Shop.class, id);
        State state = (State) session.get(State.class, 1);
        System.out.println("to jest status mojego zamowienia" + state.toString());

        System.out.println("need " + shopIdNeed.getShopId() + " delivery " + shopIdDelivery.getShopId());
        Indent simpleOrder = new Indent(shopIdNeed, shopIdDelivery, null, date, null, false);
        session.save(simpleOrder);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStateOnShop().getShopId().getShopId() == id) {
                System.out.println(list.get(i).getStateOnShop().getLocked() + " LOCKED oraz AMOUNT " + list.get(i).getAmount());
                State_on_shop newLocked = (State_on_shop) session.get(State_on_shop.class, list.get(i).getStateOnShop().getId());
                newLocked.setLocked(newLocked.getLocked() + list.get(i).getAmount());
                session.update(newLocked);
            }
        }
        for (StateOnShop state_on_shop : list) {
            Indent_product indent_product = new Indent_product(simpleOrder, state_on_shop.getStateOnShop().getProductId(), state_on_shop.getAmount());
            session.save(indent_product);
        }
        State_of_indent state_of_indent = new State_of_indent(sessionContext.getCurrentLoggedUser(), simpleOrder, state);
        session.save(state_of_indent);

        session.getTransaction().commit();
        session.close();
    }

    public void complexOrder(Date date, ArrayList idShops) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Shop shopIdNeed = (Shop) session.get(Shop.class, sessionContext.getCurrentLoggedShop().getShopId());
        State state = (State) session.get(State.class, 1);
        //COMPLEX
        Indent complexOrder = new Indent(shopIdNeed, null, null, date, null, true);
        session.save(complexOrder);
        session.getTransaction().commit();
        session.beginTransaction();
        while (!idShops.isEmpty()) {
            int id = (int) idShops.get(0);
            idShops.remove(0);
            Shop shopIdDelivery = (Shop) session.get(Shop.class, id);
            System.out.println("need " + shopIdNeed.getShopId() + " delivery " + shopIdDelivery.getShopId());
            ObservableList<State_on_shop> products = FXCollections.observableArrayList();
            if (idShops.contains(id)) {
                continue;
            } else { //SIMPLE
                //Odseparowanie produktów dla danego sklepu
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getStateOnShop().getShopId().getShopId() == id) {
                        products.add(list.get(i).getStateOnShop());
                        System.out.println(list.get(i).getStateOnShop().getLocked() + " LOCKED oraz AMOUNT " + list.get(i).getAmount());
                        State_on_shop newLocked = (State_on_shop) session.get(State_on_shop.class, list.get(i).getStateOnShop().getId());
                        newLocked.setLocked(newLocked.getLocked() + list.get(i).getAmount());
                        session.update(newLocked);
                    }
                }
                //SIMPLE
                Indent simpleOrder = new Indent(shopIdNeed, shopIdDelivery, null, date, complexOrder, false);
                session.save(simpleOrder);
                for (State_on_shop state_on_shop : products) {
                    System.out.println("Chce zamowic " + state_on_shop.getProductId().getName() + " " + state_on_shop.getAmount());
                    Indent_product indent_product = new Indent_product(simpleOrder, state_on_shop.getProductId(), state_on_shop.getAmount());
                    session.save(indent_product);
                }
                State_of_indent state_of_indent = new State_of_indent(sessionContext.getCurrentLoggedUser(), simpleOrder, state);
                session.save(state_of_indent);
            }
        }
        session.getTransaction().commit();
        session.close();
        new_order.getItems().clear();
        new_order.setItems(getProductsForOtherShop(sessionContext.getCurrentLoggedShop().getShopId()));
    }

    //ZAKŁADKA(4) NOWE ZAMÓWIENIE ZE SKLEPU----------------------------------------------------------------------------------------------------

    public void overlapNewOrderShop() {
        PRODUCTID_ORDER.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
        NAME_ORDER.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().getName()));
        PRICE_ORDER.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
        AMOUNT_ORDER.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        newOrderShop.setItems(getOrderProducts());
        System.out.println(" zamowienia: " + getOrderProducts());
    }

    private ObservableList<Indent_product> getOrderProducts() {
        ObservableList<Indent_product> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Indent_product> eList = session.createQuery("Select new Indent_product(ip.id, ip.indentId, ip.productId, SUM(ip.amount)) FROM Indent_product ip left join State_of_indent soi " +
                "on ip.indentId = soi.indentId Where ip.indentId.shopId_need.shopId = :idshop and soi.stateId = 64 group by ip.productId ")
                .setParameter("idshop", sessionContext.getCurrentLoggedShop().getShopId())
                .list();
        System.out.println("getOrderProducts " + eList);
        productList.addAll(eList);
        session.close();
        return productList;
    }

    public void confirmOrder() { //button
        if (!newOrderShop.getItems().isEmpty()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Indent_product p = (Indent_product) newOrderShop.getSelectionModel().getSelectedItem();
            System.out.println(p.getProductId().getName());
            p.getIndentId().setIndentId(2);
            session.save(p);
            session.getTransaction().commit();
            session.close();
            refreshTableOrderShop();
        }
    }

    public void refreshTableOrderShop() {
        newOrderShop.getItems().clear();
        newOrderShop.setItems(getOrderProducts());
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
        List<State_of_indent> eList = session.createQuery("FROM State_of_indent soi " +
                "WHERE soi.indentId.shopId_delivery.shopId = :idshop AND soi.stateId.stateId <= :status")
                .setParameter("idshop", sessionContext.getCurrentLoggedShop().getShopId())
                .setParameter("status", 10).list(); // zmiana statusu z 4 na 5
        System.out.println("getOrder " + eList);
        orderList.addAll(eList);
        session.close();
        return orderList;
    }

    public void changeOrderStatus() {
        if (stateOrderWarehouse.getSelectionModel().getSelectedItem() != null) {
            System.out.println(stateOrderWarehouse.getSelectionModel().getSelectedItem().toString());
            State_of_indent model = (State_of_indent) stateOrderWarehouse.getSelectionModel().getSelectedItem();
            if (model.getStateId().getStateId() == 4) {
                System.out.println(model);
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                State_of_indent p = (State_of_indent) stateOrderWarehouse.getSelectionModel().getSelectedItem();
                System.out.println(p.getStateId().getName());
                p.getStateId().setStateId(5);
                session.update(p);
                session.getTransaction().commit();
                session.close();
                refreshStateOrderWarehouse();
            } else {
                showProductInTransport();
            }

        }
    }

    public void refreshStateOrderWarehouse() {
        stateOrderWarehouse.getItems().clear();
        stateOrderWarehouse.setItems(getOrder());
    }

    @FXML
    public void inRealizationDetailsAction(ActionEvent event) throws IOException {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();

        State_of_indent orderView = stateOrderWarehouse.getSelectionModel().getSelectedItem();

        if (orderView == null)
            return;

        FXMLLoader loader = null;

        if (orderView.getIndentId().isComplex()) {
            loader = new FXMLLoader(getClass().getResource("../fxmlfiles/complex_order_details.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("../fxmlfiles/simple_order_details.fxml"));
        }

        Parent pane = loader.load();

        // wstrzykniecie wybranego obiektu do widoku szczegolowego
        if (orderView.getIndentId().isComplex()) {
            ComplexOrderDetailsController controller = loader.getController();
            controller.setLoader("../fxmlfiles/main_window_warehouse.fxml");
            controller.setOrder(orderView.getIndentId());
            controller.initController();
        } else {
            SimpleOrderDetailsController controller = loader.getController();
            controller.setLoader("../fxmlfiles/main_window_warehouse.fxml");
            controller.setOrder(orderView.getIndentId());
            controller.initController();
        }

        stg.setScene(new Scene(pane));
    }


    //ZAKŁADKA(6) ZAMOWIENIA DO PRZYGOtOWANIA---------------------------------------------------------------------------------------------------------------

    public void overlapOrdersToPrepareWarehouse() {
        CITY.setCellValueFactory(orderData -> new SimpleStringProperty(orderData.getValue().getIndentId().getShopId_delivery().getCity()));
        STATE.setCellValueFactory(orderData -> new SimpleStringProperty(orderData.getValue().getStateId().getName()));
        ORDERNR.setCellValueFactory(orderData -> new SimpleStringProperty(String.valueOf(orderData.getValue().getIndentId().getIndentId())));
        stateOrderToPrepareWarehouse.setItems(getOrderToPrepare());
        //System.out.println(getOrder(nazwaProduktu).toString());
    }

    private ObservableList<State_of_indent> getOrderToPrepare() {
        ObservableList<State_of_indent> orderList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_of_indent> eList = session.createQuery("FROM State_of_indent soi " +
                "WHERE soi.indentId.shopId_need.shopId = :idshop AND soi.stateId.stateId <= :status")
                .setParameter("idshop", sessionContext.getCurrentLoggedShop().getShopId())
                .setParameter("status", 10).list(); // zmiana statusu z 4 na 5
        System.out.println("getOrder " + eList);
        orderList.addAll(eList);
        session.close();
        return orderList;
    }

    public void changeOrderToPrepareStatus() {
        if (stateOrderToPrepareWarehouse.getSelectionModel().getSelectedItem() != null) {
            System.out.println(stateOrderToPrepareWarehouse.getSelectionModel().getSelectedItem().toString());
            State_of_indent model = (State_of_indent) stateOrderToPrepareWarehouse.getSelectionModel().getSelectedItem();
            if (model.getStateId().getStateId() == 1) {
                System.out.println(model);
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                State_of_indent p = (State_of_indent) stateOrderToPrepareWarehouse.getSelectionModel().getSelectedItem();
                System.out.println(p.getStateId().getName());
                p.getStateId().setStateId(2);
                session.update(p);
                session.getTransaction().commit();
                session.close();
                refreshStateOrderToPrepareWarehouse();
            } else {
                showProductInTransport();
            }

        }
    }

    public void refreshStateOrderToPrepareWarehouse() {
        stateOrderWarehouse.getItems().clear();
        stateOrderWarehouse.setItems(getOrder());
    }



}