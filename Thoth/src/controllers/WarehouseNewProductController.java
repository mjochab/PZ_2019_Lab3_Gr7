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
import javafx.event.ActionEvent;
import models.ObservablePriceModel;
import org.hibernate.Session;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.*;
import static utils.Alerts.newAlertCustom;
import static utils.Alerts.showSuccesAllert;


public class WarehouseNewProductController implements Initializable {

    @FXML
    public TextField NAME;
    @FXML
    public TextField AMOUNT;
    @FXML
    public TextField PRICE;
    @FXML
    public Button addInsert;

    String[] tab = {"SELECT new models.ObservablePriceModel(p.productId ) FROM Product p WHERE p.name like "};

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addInsert(ActionEvent event) throws IOException {
        if (isNumeric(AMOUNT.getText()) && isBigDeciaml(PRICE.getText())) { //wprowadzono liczby
            if (getNameProduct(tab[0]).size() == 0) { //brak takiego produktu, dodać do bazy
                System.out.println("można dodać do bazy");
                insertToDataBase();
                NAME.setText("");
                PRICE.setText("");
                AMOUNT.setText("");
                showSuccesAllert();
            } else { //produkt jest już w bazie
                System.out.println("Jest w bazie " + getNameProduct(tab[0]).get(0).getProductId());
                newAlertCustom("Niepowodzenie", "Produkt jest już w bazie");
            }
        } else {
            newAlertCustom("Niepowodzenie", "Wprowadzono złe dane");
            System.out.println("Wprowadź poprawne dane");
        }
    }

    public ObservableList<ObservablePriceModel> getNameProduct(String qry) {
        ObservableList<ObservablePriceModel> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<ObservablePriceModel> eList = session.createQuery(qry + ":nameDevice "
        ).setParameter("nameDevice", NAME.getText()).list();
        System.out.println("getNameProduct " + eList);
        for (ObservablePriceModel ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }

    public ObservableList<Shop> getObjectShop() {
        ObservableList<Shop> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Shop> eList = session.createQuery("from Shop where shopId = :pid").setParameter("pid", sessionContext.getCurrentLoggedShop().getShopId()).list();
        System.out.println("getObjectShop " + eList);
        for (Shop ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }

    public void insertToDataBase() {
        Session session = sessionFactory.openSession();
        BigDecimal big = new BigDecimal(PRICE.getText());
        Product productOB = new Product(NAME.getText(), big, 0);
        session.save(productOB);
        System.out.println("Dodano produkt");
        session.close();
        session = sessionFactory.openSession();
        State_on_shop product = new State_on_shop(productOB, getObjectShop().get(0), Integer.parseInt(AMOUNT.getText()));
        session.save(product);
        session.close();
        System.out.println("Dodano ID sklepu do produktu");
        NAME.setText("");
        PRICE.setText("");
        AMOUNT.setText("");
    }

    public boolean isBigDeciaml(String str) {
        try{
            BigDecimal big = new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // brak isDigit/isNumeric
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
