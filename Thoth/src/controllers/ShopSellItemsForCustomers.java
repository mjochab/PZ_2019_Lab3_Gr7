package controllers;

import entity.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.StateOnShop;
import org.hibernate.Session;

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

import static controllers.MainWindowController.sessionContext;
import static controllers.MainWindowController.sessionFactory;
import static controllers.WarehouseNewProductController.isNumeric;
import static utils.Alerts.*;

public class ShopSellItemsForCustomers implements Initializable {
    @FXML
    MenuItem logout;
    @FXML
    private TableView productsTable, productsTableAdd;
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
    public TableColumn<StateOnShop, String> DISCOUNT_ADD;
    @FXML
    public TextField serachShop, nameTF, lastNameTF, numerPhoneTF;
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
        DISCOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getDiscount())));
        productsTable.setItems(getProducts(null));
        System.out.println(getProducts(null));
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
        //-------------------------------------------------------------------------------------
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

    public void addToTable(ObservableList<StateOnShop> item) {
        PRODUCTID_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getStateOnShop().getProductId().getProductId())));
        NAME_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getStateOnShop().getProductId().getName()));
        PRICE_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getStateOnShop().getProductId().getPrice())));
        AMOUNT_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        DISCOUNT_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getStateOnShop().getProductId().getDiscount())));
        System.out.println("Odebrane " + item.toString() + " rozmiar " + item.size());
        productsTableAdd.setItems(item);
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
                    productsTableAdd.refresh();
                    showNumberRangeAlert(1, check);
                }
            } catch (NumberFormatException exc) {
                System.out.println("Powrót do poprzedniej liczby");
                productsTableAdd.refresh();
                showNotNumberAlert();
            }
        });
    }

    public void confirm() throws ParseException {
        if (!list.isEmpty()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = format.parse(dtf.format(now));
            System.out.println(date);

            ArrayList<Integer> listOfStores = new ArrayList<Integer>();
            if (!list.isEmpty() && correctTextField() == true) {
                System.out.println("POPRAWNE DANE");
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
            productsTable.getItems().clear();
            productsTable.setItems(getProducts(null));
            productsTableAdd.getItems().clear();
        }
    }

    public boolean correctTextField() {
        if (isNumeric(numerPhoneTF.getText()) == true && (!nameTF.getText().isEmpty() && !lastNameTF.getText().isEmpty()) == true) {
            return true;
        }
        return false;
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
        Customer customer = new Customer(nameTF.getText(),lastNameTF.getText(),Integer.parseInt(numerPhoneTF.getText()));
        session.save(customer);

        System.out.println("need " + shopIdNeed.getShopId() + " delivery " + shopIdDelivery.getShopId());
        Indent simpleOrder = new Indent(shopIdNeed, shopIdDelivery, customer, date, null, false);
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

        //session.getTransaction().commit();
        session.close();
    }

    public void complexOrder(Date date, ArrayList idShops) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Shop shopIdNeed = (Shop) session.get(Shop.class, sessionContext.getCurrentLoggedShop().getShopId());
        State state = (State) session.get(State.class, 1);
        Customer customer = new Customer(nameTF.getText(),lastNameTF.getText(),Integer.parseInt(numerPhoneTF.getText()));
        session.save(customer);

        //COMPLEX
        Indent complexOrder = new Indent(shopIdNeed, null, customer, date, null, true);
        session.save(complexOrder);
        //session.getTransaction().commit();
        //session.beginTransaction();
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
        //session.getTransaction().commit();
        session.close();
        productsTable.getItems().clear();
        productsTable.setItems(getProducts(null));
    }
}

