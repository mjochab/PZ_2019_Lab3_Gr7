package controllers;


import entity.Role;
import entity.Shop;
import entity.User;
import entity.UserShop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import log.ThothLoggerConfigurator;
import org.apache.log4j.BasicConfigurator;
import org.hibernate.Session;
import org.apache.log4j.Logger;


import static controllers.MainWindowController.sessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Kontroler dodawania pracownika
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
     *
     * @param actionEvent action event
     * @throws IOException wyjatek IOexception przy dodawaniu uzytkownika
     */
    @FXML
    public void saveEmployee(ActionEvent actionEvent) throws IOException { //dodawanie użytkownika do bazy
        User u = new User();
        UserShop us = new UserShop();

        if(tfFirstName.getText() == ""
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

        boolean isValid = session.createQuery(new String("from User where Login = :login"))
                .setParameter("login", u.getLogin()).getResultList().isEmpty();

        if (isValid) {
            //zapisuje usera do bazy
            try {
                session.save(u);
            }
            catch(Exception e) {
                logger.error("Nie udalo sie zapisac usera do bazy");
                session.getTransaction().rollback();
                session.close();
                return;
            }

            us.setUserId(u);
            try {
                session.save(us);
            }
            catch (Exception e) {
                logger.error("Nie udalo sie zapisac UserShop do bazy");
                session.getTransaction().rollback();
                session.close();
                return;
            }

            session.getTransaction().commit();
            session.close();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdzenie");
            logger.info("Potwierdzenie");
            alert.setContentText("Uzytkownik zostal dodany");
            logger.info("Uzytkownik zostal dodany");
            alert.showAndWait();

            tfFirstName.clear();
            tfLastName.clear();
            tfPassword.clear();
            tfLogin.clear();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Niepowodzenie");
            logger.warn("Niepowodzenie");
            alert.setContentText("Użytkownik o podanym loginie juz istnieje");
            logger.warn("Użytkownik o podanym loginie juz istnieje");
            alert.showAndWait();
        }
    }

    /**
     * Metoda wybierajaca liste sklepow z bazy
     * @return zwraca ObservableList<Shops>
     * @see Shop
     */
    private ObservableList<Shop> getShops() { // wybiera listę sklepów z bazy
        ObservableList<Shop> shops = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Shop> shopsList = session.createQuery("from Shop").list();

        for(Shop s: shopsList) {
            logger.info(s.toString());
        }

        if (shopsList.size() > 0) {
            shops.addAll(shopsList);
        }
        session.close();
        return shops;
    }

    /**
     * Metoda wypelnia combobox sklepami
     */
    public void setComboShopList() {
        this.comboShopList.getItems().addAll(getShops());
    }

    /**
     * Metoda wybierajaca liste rol z bazy
     * @return zwraca ObservableList<Role>
     * @see Role
     */
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

    /**
     * Metoda wypelnia role w combobox
     */

    public void setComboRoleList() {
        this.comboRoleList.getItems().addAll(getRoles());
    }

    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());
        this.setComboRoleList();
        this.setComboShopList();
    }
}
