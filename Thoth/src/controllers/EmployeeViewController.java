package controllers;


import entity.Shop;
import entity.User;
import entity.UserShop;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import models.EmployeeView;
import org.hibernate.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;
import static utils.Alerts.showNotBoolean;
import static utils.Validation.isBolean;

/**
 * Kontroler okna administratora wyświetlającego dane o użytkownikach
 */
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
    public TableColumn<EmployeeView, String> STATE;
    @FXML
    public TableColumn<EmployeeView, String> ROLEID;
    @FXML
    public TableColumn<EmployeeView, String> OBJECTID;

    @FXML
    public TextField tfSearch;
    @FXML
    public Button btnSearch;


    /**
     * Metoda inicjalizuje dane w tabeli
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        USERID.setCellValueFactory(userData -> new SimpleIntegerProperty(userData.getValue().getUser().getUserId()).asObject());
        FIRSTNAME.setCellValueFactory(userData -> new SimpleStringProperty(userData.getValue().getUser().getFirstName()));
        LASTNAME.setCellValueFactory(userData -> new SimpleStringProperty(userData.getValue().getUser().getLastName()));
        LOGIN.setCellValueFactory(userData -> new SimpleStringProperty(userData.getValue().getUser().getLogin()));
        PASSWORD.setCellValueFactory(userData -> new SimpleStringProperty(userData.getValue().getUser().getPassword()));
        STATE.setCellValueFactory(userData -> new SimpleStringProperty(String.valueOf(userData.getValue().getUser().getState())));
        ROLEID.setCellValueFactory(userData -> new SimpleStringProperty(userData.getValue().getUser().getRoleId().getPosition()));
        OBJECTID.setCellValueFactory(userData -> new SimpleStringProperty(userData.getValue().getShop().toString()));
        employeeTable.setItems(getEmployee());
        setEditableStatus();
        System.out.println(getEmployee().toString());


    }


    private List<User> getUsers(String searchValue) {
        String searchParam = "%" + searchValue + "%";
        Session session = sessionFactory.openSession();

        return (List<User>) session.createQuery("from User where FirstName LIKE :searchParam OR LastName LIKE :searchParam")
                .setParameter("searchParam", searchParam)
                .list();
    }

    private ObservableList<EmployeeView> mapUsersToEmployeeView(List<User> userList) {
        ObservableList<EmployeeView> employeeViewList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        for (User us : userList) {
            EmployeeView ev = new EmployeeView();
            ev.setUser(us);
            List<UserShop> shops = session.createQuery("from UserShop WHERE userId = :uid").setInteger("uid", us.getUserId()).list();
            if (shops.size() > 0) {
                ev.setShop(shops.get(0).getShopId());
            } else {
                ev.setShop(new Shop());
            }

            employeeViewList.add(ev);

        }
        session.close();
        return employeeViewList;
    }

    private ObservableList<EmployeeView> getEmployee() {
        return mapUsersToEmployeeView(getUsers(""));
    }

    /**
     * Metoda zastępuje dane w tabeli danymi które pasują do wzoru z pola wyszukiwania.
     */
    public void searchButtonHandler() {
        ObservableList<EmployeeView> userSearchList = FXCollections.observableArrayList();

        employeeTable.getItems().clear();
        employeeTable.setItems(mapUsersToEmployeeView(getUsers(tfSearch.getText())));
    }

    /**
     * Metoda odświeża widok w tabeli wyświetlającej użytkowników
     */
    public void reloadTableView() {
        employeeTable.getItems().clear();
        employeeTable.getItems().addAll(getEmployee());
    }

    private void setEditableStatus() {
        STATE.setCellFactory(TextFieldTableCell.forTableColumn());

        STATE.setOnEditCommit(e -> {
            if (!isBolean(e.getNewValue())) {
                System.out.println("Nowa wartość: "+e.getNewValue());
                showNotBoolean("Wpriwadź 1 jeżeli użytkownik aktywny lub 0 by dezaktyowować konto użytkownika.");
                employeeTable.refresh();
                return;
            }
            e.getTableView().getItems().get(e.getTablePosition().getRow()).getUser().setState(Integer.parseInt(e.getNewValue()));
            System.out.println((e.getTableView().getSelectionModel().getSelectedItem().getUser().toString()));

            Session session = sessionFactory.openSession();

            session.getTransaction().begin();
            User userToUpdate = e.getTableView().getSelectionModel().getSelectedItem().getUser();

            try {
                session.update(userToUpdate);
                session.getTransaction().commit();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Powodzenie");
                alert.setContentText("Dane użytkownika zostaly zaktualizowane");
                alert.showAndWait();
            } catch (Exception exc) {
                System.out.println(exc);
                session.getTransaction().rollback();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Niepowodzenie");
                alert.setContentText("Niepowodzenie aktualizacji danych");
                alert.showAndWait();
            }

            session.close();
        });

        employeeTable.setEditable(true);
    }
}