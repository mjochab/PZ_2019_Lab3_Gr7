package controllers;

import entity.Product;
import entity.State_on_shop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class StateWarehouseController implements Initializable {

    @FXML
    public TableView<Product> stateWarehouse;
    @FXML
    public TableColumn<Product, Integer> PRODUCTID;
    @FXML
    public TableColumn<Product, String> NAME;
    @FXML
    public TableColumn<Product, BigDecimal> PRICE;
    @FXML
    public TableColumn<State_on_shop,Integer> AMOUNT;
    @FXML
    public TableColumn<Product, Integer> DISCOUNT;

    @FXML
    MenuItem logout;

    Stage stage;
    @FXML Parent root;

    public void menuitemaction(ActionEvent actionEvent) throws IOException { //wylogowanie na MENU ITEM
        stage = (Stage) root.getScene().getWindow();
        if(actionEvent.getSource() == logout)
        {
            root = FXMLLoader.load(getClass().getResource("../fxmlfiles/MainWindow.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(location.toString().contains("state_warehouse"))
        {
            PRODUCTID.setCellValueFactory(new PropertyValueFactory<>("productId"));
            NAME.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            PRICE.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));
            AMOUNT.setCellValueFactory(new PropertyValueFactory<State_on_shop, Integer>("amount"));
            DISCOUNT.setCellValueFactory(new PropertyValueFactory<Product, Integer>("discount"));
            stateWarehouse.setItems(getProducts());
            System.out.println(getProducts().toString());
        }
    }

    public ObservableList<Product> getProducts() {
        ObservableList<Product> enseignantList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Product> eList = session.createQuery("from Product").list();

        for (Product ent : eList) {
            enseignantList.add(ent);
        }
        session.close();
        return enseignantList;
    }

}
