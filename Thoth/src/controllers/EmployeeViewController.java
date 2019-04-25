package controllers;


import entity.Shop;
import entity.User;
import entity.UserShop;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.EmployeeView;
import org.hibernate.Session;
import javafx.beans.property.SimpleStringProperty;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class EmployeeViewController implements Initializable {

    @FXML
    public TableView<EmployeeView> employeeTable;
    @FXML
    public TableColumn<EmployeeView, Integer> USERID;
    @FXML
    public TableColumn<EmployeeView, String> LOGIN;
    @FXML
    public TableColumn<EmployeeView, String> FIRSTNAME;
    @FXML
    public TableColumn<EmployeeView, String> LASTNAME;
    @FXML
    public TableColumn<EmployeeView, String> PASSWORD;
    @FXML
    public TableColumn<EmployeeView, Integer> STATE;
    @FXML
    public TableColumn<EmployeeView, String> ROLEID;
    @FXML
    public TableColumn<EmployeeView, String> OBJECTID;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        USERID.setCellValueFactory(userData -> new SimpleIntegerProperty(userData.getValue().getUser().getUserId()).asObject());
        FIRSTNAME.setCellValueFactory(userData -> new SimpleStringProperty(userData.getValue().getUser().getFirstName()));
        LASTNAME.setCellValueFactory(userData -> new SimpleStringProperty(userData.getValue().getUser().getLastName()));
        LOGIN.setCellValueFactory(userData -> new SimpleStringProperty(userData.getValue().getUser().getLogin()));
        PASSWORD.setCellValueFactory(userData -> new SimpleStringProperty(userData.getValue().getUser().getPassword()));
        STATE.setCellValueFactory(userData -> new SimpleIntegerProperty(userData.getValue().getUser().getState()).asObject());
        ROLEID.setCellValueFactory(userData -> new SimpleStringProperty(userData.getValue().getUser().getRoleId().getPosition()));
        OBJECTID.setCellValueFactory(userData -> new SimpleStringProperty(userData.getValue().getShop().toString()));
        employeeTable.setItems(getEmployee());
        System.out.println(getEmployee().toString());
    }

    public ObservableList<EmployeeView> getEmployee() {
        ObservableList<EmployeeView> userList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<User> usList = session.createQuery("from User").list();
        for (User us : usList) {
            EmployeeView ev = new EmployeeView();
            ev.setUser(us);
            List<UserShop> shops = session.createQuery("from UserShop WHERE userId = :uid").setInteger("uid", us.getUserId()).list();
            if (shops.size() > 0) {
                System.out.println("Sa elementy!");
                ev.setShop(shops.get(0).getShopId());
            }
            else {
                System.out.println("Brak elementow!");
                ev.setShop(new Shop());
            }

            userList.add(ev);

        }
        session.close();
        return userList;
    }

}
