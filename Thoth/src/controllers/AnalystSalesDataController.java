package controllers;

import com.pdfgeneratorlib.CreatePDF;
import com.pdfgeneratorlib.RaportModel;
import com.pdfgeneratorlib.RaportProductModel;
import com.pdfgeneratorlib.RaportUserModel;
import entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import log.ThothLoggerConfigurator;
import models.SalesDataModel;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

/**
 * Konstroler zakładki Okna Analityka dotyczącej zysków danego sklepu.
 */
public class AnalystSalesDataController implements Initializable {
    private static final Logger logger = Logger.getLogger(AnalystSalesDataController.class);
    @FXML
    private AnchorPane pane;
    @FXML
    private TableView<SalesDataModel> salesDataTable;
    @FXML
    private TableColumn ZIPCODE;
    @FXML
    private TableColumn CITY;
    @FXML
    private TableColumn STREET;
    @FXML
    private TableColumn PROFIT;
    @FXML
    private DatePicker START_DATE, END_DATE;

    /**
     * Metoda inicjalizuje dane w tabeli przechowującej dane sprzedażowe.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());
        ZIPCODE.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        CITY.setCellValueFactory(new PropertyValueFactory<Product, String>("city"));
        STREET.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("street"));
        PROFIT.setCellValueFactory(new PropertyValueFactory<Product, Integer>("profit"));
        salesDataTable.setItems(getRaport());
        logger.warn(getRaport().toString());
    }


    /**
     * Metoda zwraca listę sklepów oraz ich zysk.
     * Jeżeli START_DATE oraz END_DATE nie są puste, metoda zwraca zysk sklepów w danym okresie.
     *
     * @return zwraca ObservableList<SalesDataModel>.
     * @see SalesDataModel
     */
    private ObservableList<SalesDataModel> getRaport() {
        ObservableList<SalesDataModel> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<SalesDataModel> eList;
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
        logger.warn(eList);
        productList.addAll(eList);
        session.close();
        return productList;
    }


    /**
     * Metoda generująca raport w określonym przez nas folderze.
     *
     * @throws IOException Występuje gdy ścieżka do folderu jest niepoprawna.
     */
    public void generateRaport() throws IOException {
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

        logger.warn(shops.size());
        logger.warn(shops.toString());

        CreatePDF.createPdf(shops, path);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Raport utworzony pomyślnie");
        alert.setHeaderText("Utworzono w lokalizacji:");
        alert.setContentText(path);
        ButtonType confirm = new ButtonType("Ok", ButtonBar.ButtonData.APPLY);

        alert.getButtonTypes().setAll(confirm);

        alert.showAndWait();
    }

    /**
     * Metoda odświeża tabelę z Raportami sprzedaży.
     */
    public void showRaportForDate() {
        logger.warn("startdate:" + START_DATE.getValue() + "enddate:" + END_DATE.getValue());
        salesDataTable.setItems(getRaport());
    }
}
