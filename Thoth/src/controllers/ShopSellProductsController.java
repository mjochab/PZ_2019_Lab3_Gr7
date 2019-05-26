package controllers;

import entity.Product_receipt;
import entity.Receipt;
import entity.State_on_shop;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import log.ThothLoggerConfigurator;
import models.StateOnShop;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionContext;
import static controllers.MainWindowController.sessionFactory;
import static controllers.WarehouseNewProductController.isNumeric;
import static utils.Alerts.*;

/**
 * Kontroler widoku sprzedaży produktów
 */
public class ShopSellProductsController implements Initializable {
    private static final Logger logger = Logger.getLogger(ShopSellProductsController.class);
    @FXML
    private TableView<StateOnShop> RECEIPT_TABLE;
    @FXML
    private TableColumn<StateOnShop, String> PRODUCTID_RECEIPT;
    @FXML
    private TableColumn<StateOnShop, String> NAME_RECEIPT;
    @FXML
    private TableColumn<StateOnShop, String> PRICE_RECEIPT;
    @FXML
    public TableColumn<StateOnShop, String> AMOUNT_RECEIPT;

    @FXML
    private TableView PRODUCT_TABLE;
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
    private Label TOTALVALUE;
    @FXML
    public TextField serachShop;

    private final ObservableList<StateOnShop> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());
        addToProductsTable(getProducts(null));
        PRODUCT_TABLE.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    if (PRODUCT_TABLE.getSelectionModel().getSelectedItem() != null) {
                        StateOnShop sos = new StateOnShop();
                        sos.setStateOnShop((State_on_shop) PRODUCT_TABLE.getSelectionModel().getSelectedItem());
                        sos.setAmount(1);
                        logger.warn("Wysłany " + PRODUCT_TABLE.getSelectionModel().getSelectedItem().toString());
                        if (list.isEmpty()) {
                            list.add(sos);
                            addToReceiptTable(list);
                        } else {
                            for (StateOnShop ex : list) {
                                if (ex.getStateOnShop().getId() == ((State_on_shop) PRODUCT_TABLE.getSelectionModel().getSelectedItem()).getId()) {
                                    showIthemAlreadyExistAlert();
                                    logger.warn("java FX <3");
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
        RECEIPT_TABLE.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                if (event.getClickCount() == 1) {
                    if (RECEIPT_TABLE.getSelectionModel().getSelectedItem() != null) {
                        logger.warn("Usuwany object " + RECEIPT_TABLE.getSelectionModel().getSelectedItem().toString());
                        list.remove(RECEIPT_TABLE.getSelectionModel().getSelectedItem());
                        addToReceiptTable(list);
                    }
                }
            }
        });
        setEditableAmount();
    }


    private ObservableList<State_on_shop> getProducts(String productName) {
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
        PRODUCT_TABLE.setItems(list);
    }


    private void addToReceiptTable(ObservableList<StateOnShop> list) {
        PRODUCTID_RECEIPT.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getStateOnShop().getProductId().getProductId())));
        NAME_RECEIPT.setCellValueFactory(produktData ->
                new SimpleStringProperty(produktData.getValue().getStateOnShop().getProductId().getName()));
        //noinspection BigDecimalMethodWithoutRoundingCalled
        PRICE_RECEIPT.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getStateOnShop().getProductId().getPrice()
                        .multiply(BigDecimal.ONE
                                .subtract(BigDecimal.valueOf(produktData.getValue().getStateOnShop().getProductId().getDiscount())
                                        .divide(BigDecimal.valueOf(100)))).setScale(2, BigDecimal.ROUND_UP))));
        AMOUNT_RECEIPT.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        logger.warn("Odebrane " + list.toString() + " rozmiar " + list.size());
        RECEIPT_TABLE.setItems(list);
        TOTALVALUE.setText(getTotalValue(this.list).toString());
    }

    /**
     * Metoda wyświetla w tabeli rekordy z bazy pasujące to wzory z pola searchShop.
     */
    public void searchStateShop() {
        PRODUCT_TABLE.setItems(getProducts(serachShop.getText()));
    }

    private void setEditableAmount() {
        RECEIPT_TABLE.setEditable(true);
        AMOUNT_RECEIPT.setCellFactory(TextFieldTableCell.forTableColumn());

        AMOUNT_RECEIPT.setOnEditCommit(e -> { // dodać walidacje try catch
            try {
                logger.warn("PRZED" + e.getTableView().getSelectionModel().getSelectedItem().getAmount());
                int check = e.getRowValue().getStateOnShop().getAmount() - e.getRowValue().getStateOnShop().getLocked();
                if (!isNumeric(e.getNewValue())) {
                    throw new NumberFormatException();
                }
                if (Integer.valueOf(e.getNewValue()) > 0 && Integer.valueOf(e.getNewValue()) <= check) {
                    logger.warn("większe od 0 i mniejsze od " + check);
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setAmount(Integer.parseInt(e.getNewValue()));
                    logger.warn("PO" + e.getTableView().getSelectionModel().getSelectedItem().getAmount());
                    TOTALVALUE.setText(getTotalValue(list).toString());
                } else {
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setAmount(Integer.valueOf(e.getOldValue()));
                    logger.warn("Ustawienie starej wartości + old value" + e.getOldValue() + "," + e.getNewValue());
                    RECEIPT_TABLE.refresh();
                    showNumberRangeAlert(1, check);
                }
            } catch (NumberFormatException exc) {
                logger.warn("Powrót do poprzedniej liczby");
                RECEIPT_TABLE.refresh();
                showNotNumberAlert();
            }
        });
    }

    /**
     * Metoda służy do potwierdzenia sprzedaży.
     * Aktualizuje rekordy w bazie danych oraz tworzy nowy paragon.
     */
    public void confirm() {
        if (!list.isEmpty()) {
            logger.warn("Przygotowana list do zapytania " + list.toString());
            Session session = sessionFactory.openSession();
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
            String dateString = dateFormat.format(currentDate);
            logger.warn("Current date:" + dateString);

            Receipt receipt = new Receipt(sessionContext.getCurrentLoggedShop(), getTotalValue(list), sessionContext.getCurrentLoggedUser(), currentDate);
            logger.warn("Wartość zamówienia: " + getTotalValue(list));
            session.save(receipt);

            for (StateOnShop sos : list) {
                Product_receipt product_receipt = new Product_receipt(sos.getStateOnShop().getProductId(), receipt, sos.getAmount(), getSingleValue(sos));
                logger.warn(sos.getStateOnShop().getProductId().getName() + " // Wartość produktu: " + getSingleValue(sos));
                session.save(product_receipt);
                logger.warn(product_receipt.toString());
                logger.warn("wartosc pobrana: " + sos.getAmount());
                State_on_shop sosNew = session.get(State_on_shop.class, sos.getStateOnShop().getId());
                logger.warn("stara wartosc: " + sosNew.getAmount());
                sosNew.setAmount(sosNew.getAmount() - sos.getAmount());
                session.update(sosNew);
            }
            session.beginTransaction().commit();

            String produkty = "";
            for (StateOnShop product : list) {
                produkty += product.getStateOnShop().getProductId().getName();
                produkty += " | cena: " + getSingleValue(product);
                produkty += " | ilosc: " + product.getAmount() + "\n";
            }
            produkty += "całkowita wartość zamówienia: " + getTotalValue(list);
            newAlertOrder("Sprzedano następujące produkty:", produkty);


            list.removeAll();
            RECEIPT_TABLE.getItems().clear();
            PRODUCT_TABLE.setItems(getProducts(null));
            session.close();
            showSuccesAllert();
        } else {
            showNoIthemsAlert();
        }
    }

    private BigDecimal getTotalValue(ObservableList<StateOnShop> list) {
        BigDecimal totalValue = BigDecimal.ZERO;
        for (StateOnShop sos : list) {
            totalValue = totalValue.add(sos.getStateOnShop().getProductId().getPrice()
                    .multiply(BigDecimal.valueOf(sos.getAmount()))
                    .multiply(BigDecimal.ONE
                            .subtract(BigDecimal.valueOf(sos.getStateOnShop().getProductId().getDiscount())
                                    .divide(BigDecimal.valueOf(100))))).setScale(2, BigDecimal.ROUND_UP);
        }
        return totalValue;
    }

    private BigDecimal getSingleValue(StateOnShop sos) {
        BigDecimal totalValue = BigDecimal.ZERO;
        totalValue = totalValue.add(sos.getStateOnShop().getProductId().getPrice()
                .multiply(BigDecimal.valueOf(sos.getAmount()))
                .multiply(BigDecimal.ONE
                        .subtract(BigDecimal.valueOf(sos.getStateOnShop().getProductId().getDiscount())
                                .divide(BigDecimal.valueOf(100))))).setScale(2, BigDecimal.ROUND_UP);
        return totalValue;
    }

}

