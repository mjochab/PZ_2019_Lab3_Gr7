package controllers;

import entity.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import models.StateOnShop;
import org.hibernate.Session;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static utils.Alerts.*;

import static controllers.MainWindowController.sessionFactory;
import static controllers.MainWindowController.sessionContext;
import static controllers.WarehouseNewProductController.isNumeric;

public class ShopOrderItemsForCustomers implements Initializable {

    @FXML
    private TableView<State_on_shop> PRODUCTS_TABLE;
    @FXML
    public TableColumn<State_on_shop, String> PRODUCTID;
    @FXML
    public TableColumn<State_on_shop, String> NAME;
    @FXML
    public TableColumn<State_on_shop, String> PRICE;
    @FXML
    public TableColumn<State_on_shop, String> AMOUNT;
    @FXML
    public TableColumn<State_on_shop, String> DISCOUNT;
    @FXML
    public TableColumn<State_on_shop, String> CITY;

    @FXML
    private TableView<StateOnShop> WAREHOUSE_SHORTAGES_TABLE;
    public TableColumn<StateOnShop, String> PRODUCTID_RECEIPT;
    public TableColumn<StateOnShop, String> NAME_RECEIPT;
    public TableColumn<StateOnShop, String> PRICE_RECEIPT;
    public TableColumn<StateOnShop, String> AMOUNT_RECEIPT;
    public TableColumn<StateOnShop, String> DISCOUNT_RECEIPT;
    @FXML
    public TextField searchTF, nameTF, lastNameTF, numerPhoneTF;

    Stage stage;

    private ObservableList<StateOnShop> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addToProductsTable(getProducts(null));
        PRODUCTS_TABLE.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    if (PRODUCTS_TABLE.getSelectionModel().getSelectedItem() != null) {
                        StateOnShop sos = new StateOnShop();
                        sos.setStateOnShop((State_on_shop) PRODUCTS_TABLE.getSelectionModel().getSelectedItem());
                        sos.setAmount(1);
                        System.out.println("Wysłany " + PRODUCTS_TABLE.getSelectionModel().getSelectedItem().toString());
                        if (list.isEmpty()) {
                            list.add(sos);
                            addToReceiptTable(list);
                        } else {
                            for (StateOnShop ex : list) {
                                if (ex.getStateOnShop().getProductId().getProductId() == PRODUCTS_TABLE.getSelectionModel().getSelectedItem().getProductId().getProductId()) {
                                    System.out.println("java FX <3");
                                    showIthemAlreadyExistAlert();
                                    return;
                                }
                            }
                            list.add(sos);
                            addToReceiptTable(list);
                        }
                    }
                }
            }
        });
        //----------------------------------------------------------------------------------------
        WAREHOUSE_SHORTAGES_TABLE.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                if (event.getClickCount() == 1) {
                    if (WAREHOUSE_SHORTAGES_TABLE.getSelectionModel().getSelectedItem() != null) {
                        System.out.println("Usuwany object " + WAREHOUSE_SHORTAGES_TABLE.getSelectionModel().getSelectedItem().toString());
                        list.remove(WAREHOUSE_SHORTAGES_TABLE.getSelectionModel().getSelectedItem());
                        addToReceiptTable(list);
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
            eList = session.createQuery("FROM State_on_shop s WHERE s.amount > 0 AND s.shopId.shopId <> :idsklepu ORDER BY amount ")
                    .setParameter("idsklepu", sessionContext.getCurrentLoggedShop().getShopId())
                    .list();
        } else {
            eList = session.createQuery("FROM State_on_shop s WHERE s.amount > 0 AND s.shopId.shopId <> :idsklepu AND s.productId.name like :produkt ORDER BY AMOUNT")
                    .setParameter("idsklepu", sessionContext.getCurrentLoggedShop().getShopId())
                    .setParameter("produkt", "%" + productName + "%").list(); //za wyjątkiem własnego sklepu!! poprawić
            searchTF.setText("");
        }
        productList.addAll(eList);
        session.close();
        return productList;
    }

    private void addToProductsTable(ObservableList<State_on_shop> list) {
        PRODUCTID.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getProductId())));
        NAME.setCellValueFactory(produktData ->
                new SimpleStringProperty(produktData.getValue().getProductId().getName()));
        PRICE.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getPrice())));
        AMOUNT.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount() - produktData.getValue().getLocked())));
        DISCOUNT.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId().getDiscount())));
        CITY.setCellValueFactory(productData ->
                new SimpleStringProperty(productData.getValue().getShopId().getCity()));
        PRODUCTS_TABLE.setItems(list);
    }

    private void addToReceiptTable(ObservableList<StateOnShop> list) {
        PRODUCTID_RECEIPT.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getStateOnShop().getProductId().getProductId())));
        NAME_RECEIPT.setCellValueFactory(produktData ->
                new SimpleStringProperty(produktData.getValue().getStateOnShop().getProductId().getName()));
        //noinspection BigDecimalMethodWithoutRoundingCalled
        PRICE_RECEIPT.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getStateOnShop().getProductId().getPrice())));
        DISCOUNT_RECEIPT.setCellValueFactory(productData ->
                new SimpleStringProperty(String.valueOf(productData.getValue().getStateOnShop().getProductId().getDiscount())));
        AMOUNT_RECEIPT.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        System.out.println("Odebrane " + list.toString() + " rozmiar " + list.size());
        WAREHOUSE_SHORTAGES_TABLE.setItems(list);
    }

    public void searchStateShop() {
        PRODUCTS_TABLE.setItems(getProducts(searchTF.getText()));
    }

    private void setEditableAmount() {
        WAREHOUSE_SHORTAGES_TABLE.setEditable(true);
        AMOUNT_RECEIPT.setCellFactory(TextFieldTableCell.forTableColumn());

        AMOUNT_RECEIPT.setOnEditCommit(e -> { // dodać walidacje try catch
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
                    WAREHOUSE_SHORTAGES_TABLE.refresh();
                    showNumberRangeAlert(1, check);
                }
            } catch (NumberFormatException exc) {
                System.out.println("Powrót do poprzedniej liczby");
                WAREHOUSE_SHORTAGES_TABLE.refresh();
                showNotNumberAlert();
            }
        });
    }

    public void confirm() {
        if (!list.isEmpty()) {
            System.out.println("Przygotowane dane do wysłąnia " + list + " " + nameTF.getText() + " " + lastNameTF.getText() + " " + numerPhoneTF.getText());
            if (!nameTF.getText().isEmpty() && !lastNameTF.getText().isEmpty() && !numerPhoneTF.getText().isEmpty()) {
                Session session = sessionFactory.openSession();

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
                String dateString = dateFormat.format(currentDate);
                System.out.println("Current date:" + dateString);
                if(numerPhoneTF.getText().length() != 9){
                    session.close();
                    newAlertCustom("Niepowodzenie","Numer telefonu składa się z 9 cyfr");
                    return;
                }
                Customer customer = new Customer(nameTF.getText(), lastNameTF.getText(), Integer.valueOf(numerPhoneTF.getText()));

                session.save(customer);
                System.out.println(customer.toString());

                Indent indent = new Indent(sessionContext.getCurrentLoggedShop(), customer, currentDate);
                session.save(indent);
                State state64 = (State)session.createQuery("from State s Where s.stateId = 64").getSingleResult();
                State_of_indent state_of_indent = new State_of_indent(sessionContext.getCurrentLoggedUser(),indent,state64);
                session.save(state_of_indent);
                System.out.println(indent.toString());

                for (StateOnShop sos : list) {
                    Indent_product indent_product = new Indent_product(indent, sos.getStateOnShop().getProductId(), sos.getAmount());
                    session.save(indent_product);
                }
                session.beginTransaction().commit();
                list.removeAll();
                WAREHOUSE_SHORTAGES_TABLE.getItems().clear();
                PRODUCTS_TABLE.setItems(getProducts(null));
                session.close();
                showSuccesAllert();
                nameTF.setText(""); lastNameTF.setText(""); numerPhoneTF.setText("");
            } else {
                showAllFieldsRequiredAlert("Pola " + nameTF.getPromptText() + ", " + lastNameTF.getPromptText() + ", " + numerPhoneTF.getPromptText() + " nie mogą być puste.");
            }

        } else {
            showNoIthemsAlert();
        }
    }
}

