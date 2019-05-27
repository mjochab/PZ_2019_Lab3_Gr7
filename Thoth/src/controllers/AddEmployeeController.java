package controllers;


import entity.Role;
import entity.Shop;
import entity.User;
import entity.UserShop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import log.ThothLoggerConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;
import static utils.Alerts.shownotNameAlert;
import static utils.Validation.nameValidation;

/**
 * Kontroller okna dodawania użytkowników w panelu administratora
 */
public class AddEmployeeController implements Initializable {

    private static final Logger logger = Logger.getLogger(AddEmployeeController.class);

    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private ComboBox<Role> comboRoleList;
    @FXML
    private ComboBox<Shop> comboShopList;
    @FXML
    private Button btnAddEmployee;

    /**
     * Metoda odczytuje pola znajdujące się w formularzu dodawania pracownika.
     * Jeżeli wpisane wartości nie sa puste metoda zapisuje wpisane wartości do bazy danych.
     */
    @FXML
    public void saveEmployee() { //dodawanie użytkownika do bazy
        User u = new User();
        UserShop us = new UserShop();

        if(!nameValidation(tfFirstName.getText())){
            shownotNameAlert(tfFirstName.getPromptText());
            return;
        }
        if(!nameValidation(tfLastName.getText())){
            shownotNameAlert(tfLastName.getPromptText());
            return;
        }


        if (tfFirstName.getText() == ""
                || tfLastName.getText() == ""
                || tfLogin.getText() == ""
                || tfPassword.getText() == ""
                || comboRoleList.getSelectionModel().getSelectedItem() == null
                || comboShopList.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Niepowodzenie");
            logger.warn("Niepowodzenie");
            alert.setContentText("Nie wybrano wszystkich danych!");
            logger.warn("Nie wybrano wszystkich danych");
            alert.showAndWait();

            return;
        }
        // ustawiam pracownika
        u.setFirstName(tfFirstName.getText());
        u.setLastName(tfLastName.getText());
        u.setLogin(tfLogin.getText());
        u.setPassword(tfPassword.getText());
        u.setState(1);
        u.setRoleId(comboRoleList.getSelectionModel().getSelectedItem());

        //ustawiam relacje pracownik-obiekt
        // ustawiam Shop
        us.setShopId(comboShopList.getSelectionModel().getSelectedItem());

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        boolean isValid = session.createQuery("from User where Login = :login")
                .setParameter("login", u.getLogin()).getResultList().isEmpty();

        if (isValid) {
            //zapisuje usera do bazy
            try {
                session.save(u);
            } catch (Exception e) {
                logger.warn("Nie udalo sie zapisac usera do bazy");
                session.getTransaction().rollback();
                session.close();
                return;
            }

            us.setUserId(u);
            try {
                session.save(us);
            } catch (Exception e) {
                logger.warn("Nie udalo sie zapisac UserShop do bazy");
                session.getTransaction().rollback();
                session.close();
                return;
            }

            session.getTransaction().commit();
            session.close();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdzenie");
            alert.setContentText("Uzytkownik zostal dodany");
            alert.showAndWait();

            tfFirstName.clear();
            tfLastName.clear();
            tfPassword.clear();
            tfLogin.clear();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Niepowodzenie");
            alert.setContentText("Użytkownik o podanym loginie juz istnieje");
            alert.showAndWait();
        }
    }

    private ObservableList<Shop> getShops() { // wybiera listę sklepów z bazy
        ObservableList<Shop> shops = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Shop> shopsList = session.createQuery("from Shop").list();

        for (Shop s : shopsList) {
            logger.warn(s.toString());
        }

        if (shopsList.size() > 0) {
            shops.addAll(shopsList);
        }
        session.close();
        return shops;
    }

    private void setComboShopList() {
        this.comboShopList.getItems().clear();
        this.comboShopList.getItems().addAll(getShops());
    }

    private ObservableList<Role> getRoles() {             // wybiera listę ról z bazy
        ObservableList<Role> roles = FXCollections.observableArrayList();
        Session session2 = sessionFactory.openSession();
        Role rl = new Role();
        List<Role> rolesList = session2.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        roles.addAll(rolesList);
        session2.close();

        if (roles.size() > 0) {
            rl.setPosition(roles.get(0).getPosition());
            session2.close();
        }
        session2.close();
        return roles;
    }

    private void setComboRoleList() {
        this.comboRoleList.getItems().clear();
        this.comboRoleList.getItems().addAll(getRoles());
    }

    public void reloadView() {
        setComboShopList();
        setComboRoleList();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());
        this.setComboRoleList();
        this.setComboShopList();
    }
}
