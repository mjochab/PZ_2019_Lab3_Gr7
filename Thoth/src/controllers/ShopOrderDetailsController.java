package controllers;

import entity.Indent;
import entity.Indent_product;
import entity.State;
import entity.State_of_indent;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import models.IndentProductsView;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

public class ShopOrderDetailsController implements Initializable {
    @FXML
    private TableView<IndentProductsView> orderProducts;
    @FXML
    private TableColumn<IndentProductsView, String> productName;
    @FXML
    private TableColumn<IndentProductsView, Integer> productQuantity;
    @FXML
    private Button backButton;
    @FXML
    private Label orderStatus;
    @FXML
    private Label operatingShop;
    private Indent order;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    public void setOrder(Indent order) {
        this.order = order;
    }

    // wyswietlanie zamowienia prostego
    public void initController() {
        productName.setCellValueFactory(indent -> new SimpleStringProperty(indent.getValue().getProducts().getProductId().getName()));
        productQuantity.setCellValueFactory(indent -> new SimpleIntegerProperty(indent.getValue().getProducts().getAmount()).asObject());
        ObservableList products = getIndentProducts(order.getIndentId());
        orderProducts.setItems(products);
    }


    // wyswietlanie zamowienia jako podzamowienia zamowienia zlozonego (complex)
    public void initSubOrderController() {
        initController();
        this.orderStatus.setText(getOrderStatus().getName());
        this.operatingShop.setText(order.getShopId_delivery().toString());
    }


    private State getOrderStatus() {
        Session session = sessionFactory.openSession();
        List<State_of_indent> soi = session.createQuery("from State_of_indent where IndentId = :iid").setParameter("iid", order.getIndentId()).list();
        // wybierz pierwszy (jedyny) element z listy i zwroc jego status (obiekt State)
        return soi.get(0).getStateId();
    }


    private ObservableList<IndentProductsView> getIndentProducts(int indentId) {
        ObservableList<IndentProductsView> products = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Indent_product> indent_products = session.createQuery("from Indent_product where IndentId = :iid").setParameter("iid", indentId).list();
        for (Indent_product indent_product : indent_products) {
            IndentProductsView indentProductsView = new IndentProductsView();
            indentProductsView.setProducts(indent_product);
            products.add(indentProductsView);
        }
        session.close();
        return products;
    }


    @FXML
    public void goBack(ActionEvent event) {
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent par = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlfiles/main_window_shop.fxml"));
            par = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        stg.setScene(new Scene(Objects.requireNonNull(par)));
    }
}
