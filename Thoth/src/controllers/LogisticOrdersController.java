package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import models.Order;
import models.Product;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LogisticOrdersController implements Initializable {
    @FXML private TableView ordersReadyForShipment;

    @FXML private TableView ordersInRealization;

    @FXML private Button toShipmentDetails;

    @FXML private Button inRealizationDetails;

    private ObservableList displayedOrdersReadyForShipment = FXCollections.observableArrayList();
    private ObservableList displayedOrdersInRealization = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(ordersReadyForShipment);
        System.out.println(ordersInRealization);

        Product product1 = new Product("Produkt 1", 15.0);
        Product product2 = new Product("Produkt 2", 25.0);
        Product product3 = new Product("Produkt 3", 20.0);

        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();

        order1.setNumber("abcd1");
        order2.setNumber("abcd2");
        order3.setNumber("abcd3");

        order1.addProduct(product1, 1);
        order1.addProduct(product2, 2);
        order2.addProduct(product1, 3);
        order3.addProduct(product3, 2);

        order1.setComplex(false);
        order2.setComplex(true);
        order3.setComplex(false);

        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<Order> orders2 = new ArrayList<>();

        orders.add(order1);
        orders.add(order2);
        orders2.add(order3);

        displayedOrdersReadyForShipment = FXCollections.observableList(orders);
        displayedOrdersInRealization = FXCollections.observableList(orders2);

        ObservableList<TableColumn> columns = ordersReadyForShipment.getColumns();

        for(TableColumn tc : columns)
        {
            tc.setCellValueFactory(new PropertyValueFactory<>("number"));
        }

        ObservableList<TableColumn> columns2 = ordersInRealization.getColumns();

        for(TableColumn tc : columns2)
        {
            tc.setCellValueFactory(new PropertyValueFactory<>("number"));
        }

        ordersReadyForShipment.setItems(displayedOrdersReadyForShipment);
        ordersInRealization.setItems(displayedOrdersInRealization);
    }

    @FXML public void toShipmentDetailsAction(ActionEvent event) throws IOException {
        Stage stg = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Order order = (Order) ordersReadyForShipment.getSelectionModel().getSelectedItem();
        FXMLLoader loader = null;

        if(order.isComplex())
        {
            loader = new FXMLLoader(getClass().getResource("../fxmlfiles/complex_order_details.fxml"));
        }
        else
        {
            loader = new FXMLLoader(getClass().getResource("../fxmlfiles/simple_order_details.fxml"));
        }

        Parent pane = loader.load();

        stg.setScene(new Scene(pane));
    }

    @FXML
    public void inRealizationDetailsAction(ActionEvent event) throws IOException {
        Stage stg = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Order order = (Order) ordersInRealization.getSelectionModel().getSelectedItem();
        FXMLLoader loader = null;

        if(order.isComplex())
        {
            loader = new FXMLLoader(getClass().getResource("../fxmlfiles/complex_order_details.fxml"));
        }
        else
        {
            loader = new FXMLLoader(getClass().getResource("../fxmlfiles/simple_order_details.fxml"));
        }

        Parent pane = loader.load();

        stg.setScene(new Scene(pane));
    }
}
