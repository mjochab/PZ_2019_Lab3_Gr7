package controllers;

import entity.Customer;
import entity.Indent;
import entity.State_of_indent;
import entity.State;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import models.ShopOrders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class ShopShowOrdersController implements Initializable {
    @FXML
    MenuItem logout;
    @FXML
    private TableView ordersTable;
    @FXML
    public TableColumn checkbox_fld;
    @FXML
    public TableColumn id_order;
    @FXML
    public TableColumn status;
    @FXML
    public TableColumn date;
    @FXML
    public TableColumn customer_name;
    @FXML
    public TableColumn customer_surname;
    @FXML
    public TableColumn phone_number;
    @FXML
    Parent root;

    Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkbox_fld.setCellValueFactory(new PropertyValueFactory<>("checkbox_fld"));
        id_order.setCellValueFactory(new PropertyValueFactory<>("id_order"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customer_name.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        customer_surname.setCellValueFactory(new PropertyValueFactory<>("customer_surname"));
        phone_number.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        ordersTable.setItems(getOrders());
    }


    public ObservableList<ShopOrders> getOrders() {
        ObservableList<ShopOrders> enseignantList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<ShopOrders> eList = session.createQuery("select new models.ShopOrders(ind.indentId, st.name, ind.dateOfOrder, cus.firstName, cus.lastName, cus.phoneNumber) from Indent ind, State_of_indent soi, State st, Customer cus " +
                "where st.stateId = soi.stateId AND soi.indentId = ind.indentId AND cus.customerId = ind.customerId").list();
        System.out.println(eList.toString());
        for (ShopOrders ent : eList) {
            enseignantList.add(ent);
            System.out.println(ent.getCustomer_name() + ent.getId_order() + ent.getStatus());
        }
        session.close();
        return enseignantList;
    }
}
