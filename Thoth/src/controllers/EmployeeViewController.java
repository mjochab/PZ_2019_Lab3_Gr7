package controllers;


import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class EmployeeViewController implements Initializable {

    @FXML
    public TableView<User> employeeTable;
    @FXML
    public TableColumn<User, Integer> USERID;
    @FXML
    public TableColumn<User, String> LOGIN;
    @FXML
    public TableColumn<User, String> FIRSTNAME;
    @FXML
    public TableColumn<User, String> LASTNAME;
    @FXML
    public TableColumn<User, String> PASSWORD;
    @FXML
    public TableColumn<User, Integer> STATE;
    @FXML
    public TableColumn<User, Integer> ROLEID;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        USERID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        LOGIN.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        FIRSTNAME.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        LASTNAME.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        PASSWORD.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        STATE.setCellValueFactory(new PropertyValueFactory<User, Integer>("state"));
        ROLEID.setCellValueFactory(new PropertyValueFactory<User, Integer>("roleId"));
        employeeTable.setItems(getEmployee());
        System.out.println(getEmployee().toString());
    }

    public ObservableList<User> getEmployee() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<User> usList = session.createQuery("from User ").list();
        for (User us : usList) {
            us.getRoleId().getPosition();
            userList.add(us);

        }
        session.close();
        return userList;
    }

}
