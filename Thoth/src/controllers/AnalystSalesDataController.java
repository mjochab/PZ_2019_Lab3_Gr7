package controllers;


import com.itextpdf.kernel.pdf.PdfDocument;
import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.*;

import org.hibernate.Session;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.pdfgeneratorlib.*;

import static controllers.MainWindowController.sessionFactory;

public class AnalystSalesDataController implements Initializable {

    @FXML
    private AnchorPane pane;
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
    @FXML
    public DatePicker START_DATE, END_DATE;

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
        List<SalesDataModel> eList = null;
        if (START_DATE.getValue() == null && END_DATE.getValue() == null) {
            eList = session.createQuery("SELECT new models.SalesDataModel(" +
                    "s.zipCode, s.city, s.street, SUM(r.totalValue)) FROM Shop s INNER JOIN Receipt r on s.shopId = r.shopId " +
                    "GROUP BY s.shopId")
                    .list();
        } else {
            eList = session.createQuery("SELECT new models.SalesDataModel(" +
                    "s.zipCode, s.city, s.street, SUM(r.totalValue)) FROM Shop s INNER JOIN Receipt r on s.shopId = r.shopId " +
                    "Where date Between :startDate AND :endDate GROUP BY s.shopId")
                    .setParameter("startDate", Date.from(START_DATE.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    .setParameter("endDate", Date.from(END_DATE.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    .list();
            START_DATE.setValue(null);
            END_DATE.setValue(null);
        }
        System.out.println(eList);
        for (SalesDataModel ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }


    public void generateRaport(ActionEvent actionEvent) throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        Stage stage = (Stage) pane.getScene().getWindow();
        File file = chooser.showDialog(stage);
        String path = null;

        if (file != null) {
            path = file.getAbsolutePath();
        }

        Session session = sessionFactory.openSession();
        List<RaportModel> shops = session.createQuery("SELECT new com.pdfgeneratorlib.RaportModel(s.shopId, s.street, s.zipCode, " +
                "s.city, SUM(r.totalValue)) from Shop s Left JOIN Receipt r ON s.shopId = r.shopId GROUP BY s.shopId").list();

        for (RaportModel shop : shops) {
            Integer shopidentifier = shop.getShopId();
            List<RaportProductModel> products = session.createQuery("select new com.pdfgeneratorlib.RaportProductModel(" +
                    " s.shopId, p.name, SUM(pr.amount) as ilosc_sprzedanych, SUM(pr.price) as cena_produktow)  " +
                    "from Receipt r " +
                    "INNER JOIN Product_receipt pr ON r.receiptId = pr.receiptId " +
                    "INNER JOIN Product p ON pr.productId = p.productId " +
                    "INNER JOIN Shop s ON r.shopId = s.shopId " +
                    "where s.shopId = :shopidentifier GROUP BY pr.productId ").setParameter("shopidentifier", shopidentifier).list();
            shop.setProducts(products);

            List<RaportUserModel> users = session.createQuery("select new com.pdfgeneratorlib.RaportUserModel(u.userId, SUM(r.totalValue))" +
                    " from User u " +
                    "INNER JOIN UserShop us ON u.userId = us.userId " +
                    "INNER JOIN Shop s ON us.shopId = s.shopId " +
                    "LEFT JOIN Receipt r ON u.userId = r.userId " +
                    "WHERE u.roleId = 4 AND s.shopId = :shopidentifier GROUP BY u.userId").setParameter("shopidentifier", shopidentifier).list();

            shop.setUsers(users);
        }
        session.close();

        System.out.println(shops.size());
        System.out.println(shops.toString());

        CreatePDF.createPdf(shops, path);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Raport utworzony pomyślnie");
        alert.setHeaderText("Utworzono w lokalizacji:");
        alert.setContentText(path);
        ButtonType confirm = new ButtonType("Ok", ButtonBar.ButtonData.APPLY);

        alert.getButtonTypes().setAll(confirm);

        alert.showAndWait();
    }

    public void showRaportForDate() {
        System.out.println("startdate:" + START_DATE.getValue() + "enddate:" + END_DATE.getValue());
        salesDataTable.setItems(getProducts());
    }
}
