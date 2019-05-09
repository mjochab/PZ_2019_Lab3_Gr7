package controllers;

import entity.Role;
import entity.Shop;
import entity.User;
import entity.UserShop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.*;
import org.hibernate.Session;

public class AdminController implements Initializable {

    @FXML
    MenuItem logout;
    @FXML
    MenuItem back;
    @FXML
    Parent root;

    Stage stage;

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


    public void switchscene(ActionEvent event) throws IOException { //zmiana sceny BUTTON
        System.out.println(event.getSource().toString());
        Parent temporaryLoginParent = null;
        Scene temporaryLoginScene = null;
        temporaryLoginScene = new Scene(temporaryLoginParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(temporaryLoginScene);
        window.show();
    }

    public void menuitemaction(ActionEvent actionEvent) throws IOException { //cofanie i wylogowanie na MENU ITEM
        stage = (Stage) root.getScene().getWindow();
        if (actionEvent.getSource() == logout) {
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/MainWindow.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        Shop sp = new Shop();
        List<Shop> shopsList = session.createQuery("from Shop").list();
        if (shops.size() > 0) {
            sp.setCity(shops.get(0).getCity());
            sp.setStreet(shops.get(0).getStreet());
            sp.setZipCode(shops.get(0).getZipCode());
            shops.addAll(shopsList);
            session.close();
        }
        return shops;
    }

    public void setComboShopList() {
        this.comboShopList.getItems().addAll(getShops());
    }

    private ObservableList<Role> getRoles() {// wybiera listę rul z bazy
        ObservableList<Role> roles = FXCollections.observableArrayList();
        Session session2 = sessionFactory.openSession();
        Role rl = new Role();
        List<Role> rolesList = session2.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        roles.addAll(rolesList);
        session2.close();

        if (roles.size() > 0) {
            rl.setPosition(roles.get(0).getPosition());
            // rl.addAll(rolesList);
            session2.close();
        }
        session2.close();
        return roles;
    }

    public void setComboRoleList () {
        // this.comboRoleList.getItems.addAll(getRoles());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
