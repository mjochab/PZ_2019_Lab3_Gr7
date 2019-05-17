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
import org.apache.log4j.BasicConfigurator;
import org.hibernate.Session;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

import static controllers.MainWindowController.*;


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

    String[] tab = {"SELECT new models.ObservablePriceModel(p.productId ) FROM Product p WHERE p.name like "};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BasicConfigurator.configure();

    }

    public void addInsert(ActionEvent event) throws IOException {
        if(isNumeric(AMOUNT.getText()) && isNumeric(PRICE.getText())){ //wprowadzono liczby
            if(getNameProduct(tab[0]).size() == 0){ //brak takiego produktu, dodać do bazy
                logger.info("można dodać do bazy");
                insertToDataBase();
            } else { //produkt jest już w bazie
                logger.info("Jest w bazie "+getNameProduct(tab[0]).get(0).getProductId());
            }
        } else {
            logger.info("Wprowadź poprawne dane");
        }
    }

    public ObservableList<ObservablePriceModel> getNameProduct(String qry){
        ObservableList<ObservablePriceModel> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<ObservablePriceModel> eList = session.createQuery(qry+":nameDevice "
        ).setParameter("nameDevice",NAME.getText()).list();
        logger.info("getNameProduct "+eList);
        for (ObservablePriceModel ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }

    public ObservableList<Shop> getObjectShop(){
        ObservableList<Shop> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Shop> eList = session.createQuery("from Shop where shopId = :pid").setParameter("pid", sessionContext.getCurrentLoggedShop().getShopId()).list();
        logger.info("getObjectShop "+eList);
        for (Shop ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }

    public void insertToDataBase()
    {
        Session session = sessionFactory.openSession();
        BigDecimal wojtekDziekiZaSuperTypy = new BigDecimal(PRICE.getText());
        Product productOB =  new Product(NAME.getText(),wojtekDziekiZaSuperTypy ,0);
        session.save(productOB);
        logger.info("Dodano produkt");
        session.close();
        session = sessionFactory.openSession();
        State_on_shop product = new State_on_shop(productOB,getObjectShop().get(0),Integer.parseInt(AMOUNT.getText()));
        session.save(product);
        session.close();
        logger.info("Dodano ID sklepu do produktu");
        NAME.setText("");
        PRICE.setText("");
        AMOUNT.setText("");
    }

    // brak isDigit/isNumeric
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
