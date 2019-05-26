package controllers;


import entity.Shop;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import log.ThothLoggerConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.net.URL;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;
import static utils.Alerts.newAlertCustom;
import static utils.Alerts.showSuccesAllert;
import static utils.Validation.*;

/**
 * Kontroller okna dodawania użytkowników w panelu administratora
 */
public class AddShopController implements Initializable {
    private static final Logger logger = Logger.getLogger(AddShopController.class);
    @FXML
    private TextField ZIPCODE;
    @FXML
    private TextField CITY;
    @FXML
    private TextField STREET;
    @FXML
    private Button btnAddShop;

    /**
     * Metoda odczytuje pola znajdujące się w formularzu dodawania pracownika.
     * Jeżeli wpisane wartości nie sa puste metoda zapisuje wpisane wartości do bazy danych.
     */
    @FXML
    public void saveShop() { //dodawanie użytkownika do bazy
        if (!zipCodeValidation(ZIPCODE.getText())) {
            newAlertCustom("Nieprawidłowo uzupełnione pole zip-code", "Wprowadź zip code w formacie 00-000");
            return;
        }
        if (!onlyCharsValidation(CITY.getText())) {
            newAlertCustom("Nieprawidłowo uzupełnione pole Miasto", "Nazwy miast nie mogą zawierać znaków specjalnych(bez polskich znaków)");
            return;
        }
        if (!streetValidation(STREET.getText())) {
            newAlertCustom("Nieprawidłowo uzupełnione pole Ulica", "Pole powinno zawierać nazwę uliczy oraz numer budynku np. Powstancow warszawy 34 (bez polskich znaków)");
            return;
        }
        Session session = sessionFactory.openSession();
        Shop shop = new Shop(ZIPCODE.getText(), CITY.getText(), STREET.getText(), true);
        session.save(shop);
        session.beginTransaction().commit();
        session.close();
        ZIPCODE.clear();
        CITY.clear();
        STREET.clear();
        showSuccesAllert();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());
    }
}
