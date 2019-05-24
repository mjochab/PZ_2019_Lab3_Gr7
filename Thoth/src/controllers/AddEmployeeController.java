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
import org.hibernate.Session;

import static controllers.MainWindowController.sessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import static utils.Validation.*;
import static utils.Alerts.*;

public class AddEmployeeController implements Initializable {

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

    @FXML
    public void saveEmployee(ActionEvent actionEvent) throws IOException { //dodawanie użytkownika do bazy
        User u = new User();
        UserShop us = new UserShop();

        if(!nameValidation(tfFirstName.getText())){
            notNameAlert(tfFirstName.getPromptText());
            return;
        }
        if(!nameValidation(tfLastName.getText())){
            notNameAlert(tfLastName.getPromptText());
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
            alert.setContentText("Nie wybrano wszystkich danych!");
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
            } catch (Exception e) {
                System.out.println("Nie udalo sie zapisac usera do bazy");
                session.getTransaction().rollback();
                session.close();
                return;
            }

            us.setUserId(u);
            try {
                session.save(us);
            } catch (Exception e) {
                System.out.println("Nie udalo sie zapisac UserShop do bazy");
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


    public void addEmployee() { //dodawanie użytkownika do bazy
        User u = new User();
        UserShop us = new UserShop();
        u.setFirstName(tfFirstName.getText());
        u.setLastName(tfLastName.getText());
        u.setPassword(tfPassword.getText());
        //combo
    }

    private ObservableList<Shop> getShops() { // wybiera listę sklepów z bazy
        ObservableList<Shop> shops = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Shop> shopsList = session.createQuery("from Shop").list();

        for (Shop s : shopsList) {
            System.out.println(s.toString());
        }

        if (shopsList.size() > 0) {
            shops.addAll(shopsList);
        }
        session.close();
        return shops;
    }

    public void setComboShopList() {
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

    public void setComboRoleList() {
        this.comboRoleList.getItems().addAll(getRoles());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setComboRoleList();
        this.setComboShopList();
    }
}
