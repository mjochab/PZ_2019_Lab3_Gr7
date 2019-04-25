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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.SalesCreatorModel;
import org.hibernate.Session;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class StateWarehouseController implements Initializable {

    @FXML
    public TableView stateWarehouse,new_order;
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
    MenuItem logout;

    Stage stage;
    @FXML Parent root;

    String nazwaMagazynu = null; //nazwa magazynu do przechukiwania zawartości

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
            stateWarehouse.setItems(getProducts(nazwaMagazynu));
            System.out.println(getProducts(nazwaMagazynu).toString());
        }
        if(location.toString().contains("new_order"))
        {
            PRODUCTID.setCellValueFactory(new PropertyValueFactory<>("productId"));
            NAME.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            PRICE.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));
            AMOUNT.setCellValueFactory(new PropertyValueFactory<State_on_shop, Integer>("amount"));
            //DISCOUNT.setCellValueFactory(new PropertyValueFactory<Product, Integer>("discount"));
            new_order.setItems(getProducts(nazwaMagazynu));
            System.out.println(getProducts(nazwaMagazynu).toString());
        }
    }

    public ObservableList<SalesCreatorModel> getProducts(String nazwaMagazynu) {
        ObservableList<SalesCreatorModel> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<SalesCreatorModel> eList = session.createQuery("SELECT new models.SalesCreatorModel(p.productId, p.name, p.price, p.discount, s.amount) FROM Product p " +
                "INNER JOIN State_on_shop s ON p.productId = s.productId INNER JOIN Shop k ON s.shopId = k.shopId WHERE s.amount > 0 AND k.city like 'R%'"
        ).list();
        System.out.println(eList);
        for (SalesCreatorModel ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }
}
