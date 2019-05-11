package controllers;


import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;
import org.hibernate.Session;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.pdfgeneratorlib.*;

import static controllers.MainWindowController.sessionFactory;

public class AnalystSalesDataController implements Initializable {
    @FXML
    public TableView<SalesDataModel> salesDataTable;
    @FXML
    public TableColumn ZIPCODE;
    @FXML
    public TableColumn CITY;
    @FXML
    public TableColumn STREET;
    @FXML
    public TableColumn PROFIT;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ZIPCODE.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        CITY.setCellValueFactory(new PropertyValueFactory<Product, String>("city"));
        STREET.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("street"));
        PROFIT.setCellValueFactory(new PropertyValueFactory<Product, Integer>("profit"));
        salesDataTable.setItems(getProducts());
        System.out.println(getProducts().toString());
    }


    public ObservableList<SalesDataModel> getProducts() {
        ObservableList<SalesDataModel> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<SalesDataModel> eList = session.createQuery("SELECT new models.SalesDataModel(s.zipCode, s.city, s.street, SUM(r.totalValue)) FROM Shop s INNER JOIN Receipt r on s.shopId = r.shopId GROUP BY s.shopId").list();
        System.out.println(eList);
        for (SalesDataModel ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }


    public void generateRaport(ActionEvent actionEvent) {

        Session session = sessionFactory.openSession();
//       select s.ShopId, p.Name, SUM(pr.Amount) ilosc_sprzedanych, SUM(pr.Price) cena_produktow  from receipt r INNER JOIN product_receipt pr ON r.ReceiptId = pr.ReceiptId INNER JOIN product p ON pr.ProductId = p.ProductId INNER JOIN shop s ON r.ShopId = s.ShopId where s.ShopId = 1 GROUP BY pr.ProductId
        List<RaportModel> shops = session.createQuery("SELECT new com.pdfgeneratorlib.RaportModel(s.shopId, s.street, s.zipCode, " +
                "s.city, SUM(r.totalValue)) from Shop s Left JOIN Receipt r ON s.shopId = r.shopId GROUP BY s.shopId").list();

        for (RaportModel shop : shops){
            Integer shopidentifier = shop.getShopId();
            List<RaportProductModel> products = session.createQuery("select new com.pdfgeneratorlib.RaportProductModel(" +
                    " s.shopId, p.name, SUM(pr.amount) as ilosc_sprzedanych, SUM(pr.price) as cena_produktow)  " +
                    "from Receipt r " +
                    "INNER JOIN Product_receipt pr ON r.receiptId = pr.receiptId " +
                    "INNER JOIN Product p ON pr.productId = p.productId " +
                    "INNER JOIN Shop s ON r.shopId = s.shopId " +
                    "where s.shopId = :shopidentifier GROUP BY pr.productId ").setParameter("shopidentifier",shopidentifier).list();
            shop.setProducts(products);

            List<RaportUserModel> users = session.createQuery("select new com.pdfgeneratorlib.RaportUserModel(u.userId, SUM(r.totalValue))" +
                    " from User u " +
                    "INNER JOIN UserShop us ON u.userId = us.userId " +
                    "INNER JOIN Shop s ON us.shopId = s.shopId " +
                    "LEFT JOIN Receipt r ON u.userId = r.userId " +
                    "WHERE u.roleId = 4 AND s.shopId = :shopidentifier GROUP BY u.userId").setParameter("shopidentifier",shopidentifier).list();

            shop.setUsers(users);
        }

            OutputStream ops = null;
            ObjectOutputStream objOps = null;
            try {
                ops = new FileOutputStream("ahops.object");
                objOps = new ObjectOutputStream(ops);
                objOps.writeObject(shops);
                objOps.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                try{
                    if(objOps != null) objOps.close();
                } catch (Exception ex){

                }
            }

//        List<RaportProductModel> products = session.createQuery("select new models.RaportProductModel(" +
//                " s.shopId, p.name, SUM(pr.amount) as ilosc_sprzedanych, SUM(pr.price) as cena_produktow)  " +
//                "from Receipt r " +
//                "INNER JOIN Product_receipt pr ON r.receiptId = pr.receiptId " +
//                "INNER JOIN Product p ON pr.productId = p.productId " +
//                "INNER JOIN Shop s ON r.shopId = s.shopId " +
//                "where s.shopId = 1 GROUP BY pr.productId ").list();
//
//
//        List<RaportUserModel> users = session.createQuery("select new models.RaportUserModel(u.userId, SUM(r.totalValue))" +
//                " from User u " +
//                "INNER JOIN UserShop us ON u.userId = us.userId " +
//                "INNER JOIN Shop s ON us.shopId = s.shopId " +
//                "LEFT JOIN Receipt r ON u.userId = r.userId " +
//                "WHERE u.roleId = 4 AND r.shopId = 1 GROUP BY u.userId").list();
        System.out.println(shops.size());
        System.out.println(shops.toString());


//        System.out.println(products.size());
//        System.out.println(products.toString());
//
//        System.out.println(users.size());
//        System.out.println(users.toString());
        session.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Raport utworzony pomyślnie");
        alert.setHeaderText("Utworzono w lokalizacji:");
        alert.setContentText("c:/programy/raport.pdf");
        ButtonType view_raport = new ButtonType("Podgląd");
        ButtonType confirm = new ButtonType("Ok", ButtonBar.ButtonData.APPLY);

        alert.getButtonTypes().setAll(view_raport, confirm);
        alert.showAndWait();
    }
}
