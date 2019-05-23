package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import models.ShopOrders;
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
    public TableColumn<ShopOrders,String> id_order;
    @FXML
    public TableColumn<ShopOrders,String> status;
    @FXML
    public TableColumn<ShopOrders,String> date;
    @FXML
    public TableColumn<ShopOrders,String> customer_name;
    @FXML
    public TableColumn<ShopOrders,String> customer_surname;
    @FXML
    public TableColumn<ShopOrders,String> phone_number;
    @FXML
    Parent root;

    Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_order.setCellValueFactory(orderData -> new SimpleStringProperty(orderData.getValue().getId_order().toString()));
        status.setCellValueFactory(orderData -> new SimpleStringProperty(orderData.getValue().getStatus()));
        date.setCellValueFactory(orderData -> new SimpleStringProperty(orderData.getValue().getDate().toString()));
        customer_name.setCellValueFactory(orderData -> new SimpleStringProperty(orderData.getValue().getCustomer_name()));
        customer_surname.setCellValueFactory(orderData -> new SimpleStringProperty(orderData.getValue().getCustomer_name()));
        phone_number.setCellValueFactory(orderData -> new SimpleStringProperty(orderData.getValue().getPhone_number().toString()));
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

    public void confirm(){
        ordersTable.getItems().clear();
        ordersTable.setItems(getOrders());
    }
}
