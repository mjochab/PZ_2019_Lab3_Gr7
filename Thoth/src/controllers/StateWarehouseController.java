package controllers;

import entity.Product;
import entity.State_on_shop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.IndentTableView;
import models.SalesCreatorModel;
import models.StateOrderModel;
import org.hibernate.Session;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class StateWarehouseController implements Initializable {
    @FXML
    public TableView stateWarehouse;
    @FXML
    public TableView new_order;
    @FXML
    public TableView stateOrderWarehouse;
    @FXML
    public TableView newOrderShop;
    @FXML
    public TableColumn PRODUCTID;
    @FXML
    public TableColumn NAME;
    @FXML
    public TableColumn PRICE;
    @FXML
    public TableColumn AMOUNT;
    @FXML
    public TableColumn DISCOUNT;
    @FXML
    public TableColumn CITY;
    @FXML
    public TableColumn STATE;
    @FXML
    public TableColumn ORDERNR;
    @FXML
    public Button searchStateWarehouse;
    @FXML
    public TextField searchSWCity;
    @FXML
    MenuItem logout;
    @FXML
    Parent root;

    Stage stage;

    private ObservableList<IndentTableView> displayedOrdersReadyForShipment = FXCollections.observableArrayList();

    String nazwaProduktu = null; //nazwa magazynu do przeszukiwania zawartości

    public void menuitemaction(ActionEvent actionEvent) throws IOException { //wylogowanie na MENU ITEM
        stage = (Stage) root.getScene().getWindow();
        if (actionEvent.getSource() == logout) {
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/MainWindow.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (location.toString().contains("state_warehouse")) {
            PRODUCTID.setCellValueFactory(new PropertyValueFactory<>("productId"));
            NAME.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            PRICE.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));
            AMOUNT.setCellValueFactory(new PropertyValueFactory<State_on_shop, Integer>("amount"));
            DISCOUNT.setCellValueFactory(new PropertyValueFactory<Product, Integer>("discount"));
            stateWarehouse.setItems(getProducts(nazwaProduktu));
            System.out.println(getProducts(nazwaProduktu).toString());
        }
        if (location.toString().contains("new_order_warehouse")) {
            PRODUCTID.setCellValueFactory(new PropertyValueFactory<>("productId"));
            NAME.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            PRICE.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));
            AMOUNT.setCellValueFactory(new PropertyValueFactory<State_on_shop, Integer>("amount"));
            new_order.setItems(getProducts(nazwaProduktu));
            System.out.println(getProducts(nazwaProduktu).toString());
        }
        if (location.toString().contains("new_order_shop")) {
            PRODUCTID.setCellValueFactory(new PropertyValueFactory<>("productId"));
            NAME.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            PRICE.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));
            AMOUNT.setCellValueFactory(new PropertyValueFactory<State_on_shop, Integer>("amount"));
            newOrderShop.setItems(getOrderProducts(nazwaProduktu));
            System.out.println(getOrderProducts(nazwaProduktu).toString());
        }
        if (location.toString().contains("state_order_warehouse")) {
            CITY.setCellValueFactory(new PropertyValueFactory<>("city"));
            STATE.setCellValueFactory(new PropertyValueFactory<Product, String>("state"));
            ORDERNR.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("orderid"));
            stateOrderWarehouse.setItems(getOrder(nazwaProduktu));
            System.out.println(getOrder(nazwaProduktu).toString());
        }

    }


    public ObservableList<SalesCreatorModel> getProducts(String nazwaProduktu) {
        ObservableList<SalesCreatorModel> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<SalesCreatorModel> eList;
        if(nazwaProduktu == null || nazwaProduktu.equals("")){
            eList = session.createQuery("SELECT new models.SalesCreatorModel(p.productId, p.name, p.price, p.discount, s.amount) FROM Product p " +
                    "INNER JOIN State_on_shop s ON p.productId = s.productId INNER JOIN Shop k ON s.shopId = k.shopId WHERE s.amount > 0 AND k.city like 'R%'"
            ).list();
        } else
        {
            eList = session.createQuery("SELECT new models.SalesCreatorModel(p.productId, p.name, p.price, p.discount, s.amount) FROM Product p " +
                    "INNER JOIN State_on_shop s ON p.productId = s.productId INNER JOIN Shop k ON s.shopId = k.shopId WHERE s.amount > 0 AND k.city like 'R%' AND p.name like :produkt "
            ).setParameter("produkt",nazwaProduktu).list();
            searchSWCity.setText("");
            nazwaProduktu = null;
        }

        System.out.println(eList);
        for (SalesCreatorModel ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }


    public ObservableList<StateOrderModel> getOrder(String nazwaProduktu) {
        ObservableList<StateOrderModel> orderList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<StateOrderModel> eList = session.createQuery("SELECT new models.StateOrderModel(p.city, s.name, i.indentId) FROM State_of_indent v " +
                "INNER JOIN Indent i ON i.indentId = v.indentId  " +
                "INNER JOIN State s ON s.stateId = v.stateId  " +
                "INNER JOIN Shop p ON p.shopId = i.shopId_need " +
                "WHERE p.city like 'R%'"
        ).list();
        System.out.println(eList);
        for (StateOrderModel ent : eList) {
            orderList.add(ent);
        }
        session.close();
        return orderList;
    }


    public ObservableList<SalesCreatorModel> getOrderProducts(String nazwaProduktu) {
        ObservableList<SalesCreatorModel> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<SalesCreatorModel> eList = session.createQuery("SELECT new models.SalesCreatorModel(p.productId, p.name, p.price, p.discount, ip.amount) " +
                "FROM Shop sp " +
                "INNER JOIN State_on_shop sos ON sp.shopId = sos.shopId " +
                "INNER JOIN Product p ON sos.productId = p.productId " +
                "INNER JOIN Indent_product ip ON p.productId = ip.productId " +
                "INNER JOIN Indent i ON sp.shopId = i.shopId_need " +
                "INNER JOIN State_of_indent soi ON i.indentId = soi.indentId " +
                "INNER JOIN State s ON soi.stateId = s.stateId " +
                "WHERE sp.city like 'R%' AND s.name like 'W realizacji' "
        ).list();
        System.out.println(eList);
        for (SalesCreatorModel ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }


    public void searchStateWarehouse(ActionEvent event) throws IOException {
        nazwaProduktu = searchSWCity.getText();
        stateWarehouse.setItems(getProducts(nazwaProduktu));
    }

}
