package controllers;

import entity.Product;
import entity.State_on_shop;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import org.hibernate.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

/**
 * Kontroler okna Analityka dotyczącego ustalania zniżek dla wielu produktów.
 */
public class AnalystSalesCreatorViewController implements Initializable {
    @FXML
    public TableView productsTable, discountTable;
    @FXML
    public TableColumn<Product, String> PRODUCTID, PRODUCTID_CHANGE;
    @FXML
    public TableColumn<Product, String> NAME, NAME_CHANGE;
    @FXML
    public TableColumn<Product, String> PRICE, PRICE_CHANGE;
    @FXML
    public TableColumn<Product, String> DISCOUNT, DISCOUNT_CHANGE;
    @FXML
    public TextField searchTF, DISCOUNT_TF;
    private String nazwaProduktu = null;
    private final ObservableList<Product> lista = FXCollections.observableArrayList();

    /**
     * Metoda inicjalizuje dane w tabeli wyświetlającej produkty oraz tabeli do której dodajemy produkty dla których wartość zniżka ma zostać zmieniona.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PRODUCTID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        NAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        PRICE.setCellValueFactory(new PropertyValueFactory("price"));
        DISCOUNT.setCellValueFactory(new PropertyValueFactory("discount"));
        productsTable.setItems(getProducts());
        System.out.println(getProducts().toString());
        productsTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    if (productsTable.getSelectionModel().getSelectedItem() != null) {
                        System.out.println("Wysłany " + productsTable.getSelectionModel().getSelectedItem().toString());
                        if (lista.isEmpty()) {
                            lista.add((Product) productsTable.getSelectionModel().getSelectedItem());
                            addToTable(lista);
                        } else {
                            if (lista.contains(productsTable.getSelectionModel().getSelectedItem())) {
                                System.out.println("Ten object już tam sie znajduje");
                            } else {
                                lista.add((Product) productsTable.getSelectionModel().getSelectedItem());
                                addToTable(lista);
                            }
                        }
                    }
                }
            }
        });
        discountTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                if (event.getClickCount() == 1) {
                    if (discountTable.getSelectionModel().getSelectedItem() != null) {
                        System.out.println("Usuwany object " + discountTable.getSelectionModel().getSelectedItem().toString());
                        lista.remove(discountTable.getSelectionModel().getSelectedItem());
                        addToTable(lista);
                    }
                }
            }
        });
    }

    /**
     * Metoda zwraca listę produktów. Jeżeli pole wyszukiwania nie jest puste,
     * zwraca liste produktów których nazwa zawiera ciąg znaków zawarty w polu wyszukiwania.
     *
     * @return Zwraca ObservableList<Product>
     * @see Product
     */
    private ObservableList<Product> getProducts() {
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_on_shop> eList;
        if (nazwaProduktu == null || nazwaProduktu.equals("")) {
            eList = session.createQuery("SELECT new entity.State_on_shop(sos.id, sos.productId, sos.shopId, SUM(sos.amount)) " +
                    "FROM State_on_shop sos GROUP by sos.productId ORDER BY SUM(sos.amount) DESC").list();
        } else {
            eList = session.createQuery("SELECT new entity.State_on_shop(sos.id, sos.productId, sos.shopId, SUM(sos.amount)) " +
                    "FROM State_on_shop sos WHERE name like :produkt GROUP by sos.productId ORDER BY SUM(sos.amount) DESC ")
                    .setParameter("produkt", "%" + nazwaProduktu + "%").list();
            searchTF.setText("");
            nazwaProduktu = null;
        }
        System.out.println("getProducts " + eList);
        for (State_on_shop ent : eList) {
            Product opm;
            opm = ent.getProductId();
            productsList.add(opm);
        }
        session.close();
        return productsList;
    }

    /**
     * Metoda odświeżająca listę produktów w tabeli.
     */
    public void searchAnalystPrices() {
        nazwaProduktu = searchTF.getText();
        productsTable.setItems(getProducts());
    }

    /**
     * Metoda dodaje obiekt do tabeli przechowującej produkty dla których będzie zmieniana zniżka.
     *
     * @param item obiekt klasy Product.
     */
    private void addToTable(ObservableList<Product> item) {
        PRODUCTID_CHANGE.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId())));
        NAME_CHANGE.setCellValueFactory(produktData ->
                new SimpleStringProperty(produktData.getValue().getName()));
        PRICE_CHANGE.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getPrice())));
        DISCOUNT_CHANGE.setCellValueFactory(produktData ->
                new SimpleStringProperty(String.valueOf(produktData.getValue().getDiscount())));
        System.out.println("Odebrane " + item.toString() + " rozmiar " + item.size());
        try {
            if (!item.isEmpty()) {
                discountTable.setItems(item);
            }  //naprawić

        } catch (NullPointerException e) {
            System.out.println("NullPointerException po odjęciu ostatniego elementu " + e);
        }
    }

    /**
     * Metoda aktualizuje dane w bazie danych na podstawie obiektów przechowywanych w zmiennej lista tego kontrollera.
     */
    public void changeDiscount() {
        if (!lista.isEmpty()) {
            System.out.println("Przygotowana lista do zapytania " + lista.get(0).getPrice().toString());
            Session session = sessionFactory.openSession();
            for (Product product : lista) {
                product.setDiscount(Integer.parseInt(DISCOUNT_TF.getText()));
                session.getTransaction().begin();
                session.update(product);
                session.getTransaction().commit();
            }
            session.close();
        }
        lista.removeAll();
        discountTable.getItems().clear();
        System.out.println("remove all products from lista:" + lista);
        discountTable.refresh();
        productsTable.refresh();
    }

    public void refreshProductTable(){
        productsTable.getItems().clear();
        productsTable.setItems(getProducts());
    }
}
