package controllers;

import entity.Product;
import entity.Shop;
import entity.State_on_shop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import log.ThothLoggerConfigurator;
import models.ObservablePriceModel;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionContext;
import static controllers.MainWindowController.sessionFactory;
import static utils.Alerts.newAlertCustom;
import static utils.Alerts.showSuccesAllert;


/**
 * Kontroler dodawania noweho produktu z oknie magazynu
 */
public class WarehouseNewProductController implements Initializable {
    private static final Logger logger = Logger.getLogger(WarehouseNewProductController.class);
    @FXML
    public TextField NAME;
    @FXML
    public TextField AMOUNT;
    @FXML
    public TextField PRICE;
    @FXML
    public Button addInsert;

    private final String[] tab = {"SELECT new models.ObservablePriceModel(p.productId ) FROM Product p WHERE p.name like "};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());
    }

    /**
     * Metoda sprawdza czy łańcóch znaków zawiera liczby.
     *
     * @param str łańcuch znaków dla którego sprawdzamy warunek
     * @return true jezeli parsowanie do typu int jest możliwe false w przeciwnym wypadku
     */
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private ObservableList<ObservablePriceModel> getNameProduct(String qry) {
        ObservableList<ObservablePriceModel> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<ObservablePriceModel> eList = session.createQuery(qry + ":nameDevice "
        ).setParameter("nameDevice", NAME.getText()).list();
        logger.warn("getNameProduct " + eList);
        productList.addAll(eList);
        session.close();
        return productList;
    }

    private ObservableList<Shop> getObjectShop() {
        ObservableList<Shop> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Shop> eList = session.createQuery("from Shop where shopId = :pid").setParameter("pid", sessionContext.getCurrentLoggedShop().getShopId()).list();
        logger.warn("getObjectShop " + eList);
        productList.addAll(eList);
        session.close();
        return productList;
    }

    private void insertToDataBase() {
        Session session = sessionFactory.openSession();
        BigDecimal big = new BigDecimal(PRICE.getText());
        Product productOB = new Product(NAME.getText(), big, 0);
        session.save(productOB);
        logger.warn("Dodano produkt");
        List<Shop> shops = session.createQuery("from Shop ").list();
        for (Shop shop : shops) {
            if (shop.getShopId() == sessionContext.getCurrentLoggedShop().getShopId()) {
                State_on_shop product = new State_on_shop(productOB, sessionContext.getCurrentLoggedShop(), Integer.parseInt(AMOUNT.getText()));
                session.saveOrUpdate(product);
                continue;
            }
            State_on_shop productOther = new State_on_shop(productOB, shop, 0);
            session.saveOrUpdate(productOther);
        }
        session.close();
        logger.warn("Dodano ID sklepu do produktu");
        NAME.setText("");
        PRICE.setText("");
        AMOUNT.setText("");
    }

    private boolean isBigDeciaml(String str) {
        try {
            BigDecimal big = new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Metoda sprawdza wpisane dane oraz dodaje rekord do bazy danych.
     */
    public void addInsert() {
        if (isNumeric(AMOUNT.getText()) && isBigDeciaml(PRICE.getText())) { //wprowadzono liczby
            if (getNameProduct(tab[0]).size() == 0) { //brak takiego produktu, dodać do bazy
                logger.warn("można dodać do bazy");
                insertToDataBase();
                NAME.setText("");
                PRICE.setText("");
                AMOUNT.setText("");
                showSuccesAllert();
            } else { //produkt jest już w bazie
                logger.warn("Jest w bazie " + getNameProduct(tab[0]).get(0).getProductId());
                newAlertCustom("Niepowodzenie", "Produkt jest już w bazie");
            }
        } else {
            newAlertCustom("Niepowodzenie", "Wprowadzono złe dane");
            logger.warn("Wprowadź poprawne dane");
        }
    }
}
