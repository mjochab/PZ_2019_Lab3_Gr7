package controllers;

        import entity.Product;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.MenuBar;
        import javafx.scene.control.MenuItem;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.stage.Stage;
        import org.hibernate.Session;

        import java.io.IOException;
        import java.net.URL;
        import java.util.Arrays;
        import java.util.List;
        import java.util.ResourceBundle;

        import static controllers.MainWindowController.sessionFactory;

public class ShopSellItemsForCustomers implements Initializable {

    @FXML
    MenuItem logout;

    @FXML
    private TableView productsTable;

    @FXML
    public TableColumn id_col;

    @FXML
    public TableColumn name_col;

    @FXML
    public TableColumn price_col;

    @FXML
    public TableColumn discount_col;


    Stage stage;
    @FXML
    Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id_col.setCellValueFactory(new PropertyValueFactory<>("productId"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price"));
        discount_col.setCellValueFactory(new PropertyValueFactory<>("discount"));

        productsTable.setItems(getProducts());

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

