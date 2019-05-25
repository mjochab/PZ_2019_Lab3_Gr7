package controllers;


import entity.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionFactory;

/**
 * Kontroler okna Analityka dotyczącego cen produktów.
 */
public class AnalystPricesViewController implements Initializable {
    @FXML
    public TableView<Product> priceTable;
    @FXML
    public TableColumn<Product, String> PRODUCTID;
    @FXML
    public TableColumn<Product, String> NAME;
    @FXML
    public TableColumn<Product, String> PRICE;
    @FXML
    public TableColumn<Product, String> DISCOUNT;
    @FXML
    public Button search;
    @FXML
    public TextField searchTF;

    private String nazwaProduktu = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PRODUCTID.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getProductId())));
        NAME.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getName()));
        PRICE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getPrice())));
        DISCOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getDiscount())));
        priceTable.setItems(getProducts());
        //System.out.println(getProducts().toString());
        setEditablePrice();
        setEditableDiscount();
    }


    /**
     * Metoda zwraca listę wszystkich produktów lub jeżeli pole wyszukiwania nie jest puste produktów których nazwa zawiera dany ciąg znaków.
     *
     * @return Zwraca ObservableList<Product>
     * @see Product
     */
    private ObservableList<Product> getProducts() {
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Product> eList;

        if (nazwaProduktu == null || nazwaProduktu.equals("")) {
            eList = session.createQuery("from Product").list();
        } else {
            eList = session.createQuery("from Product p" +
                    " where name like :produkt ").setParameter("produkt", "%" + nazwaProduktu + "%").list();
            searchTF.setText("");
        }

        System.out.println("getProducts " + eList);
        for (Product ent : eList) {
            Product opm = new Product();

            opm.setProductId(ent.getProductId());
            opm.setName(ent.getName());
            opm.setPrice(ent.getPrice());
            opm.setDiscount(ent.getDiscount());

            productsList.add(opm);
        }
        session.close();
        return productsList;
    }

    /**
     * Metoda odpowiedzialna za odświeżanie tabeli produktów.
     */
    public void searchAnalystPrices() {
        nazwaProduktu = searchTF.getText();
        priceTable.setItems(getProducts());
    }


    private void setEditablePrice() {
        PRICE.setCellFactory(TextFieldTableCell.forTableColumn());

        PRICE.setOnEditCommit(e -> { // dodać walidacje try catch
            System.out.println("klasa wpisanej wartości:" + e.getNewValue().getClass());
            Session session = sessionFactory.openSession();
            BigDecimal oldValue = new BigDecimal(e.getOldValue());
            try {
                BigDecimal newValue = new BigDecimal(e.getNewValue());
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setPrice(newValue);
                if (newValue.compareTo(BigDecimal.ZERO) < 0) {
                    throw new Exception("ValueBelowZero");
                }
                session.getTransaction().begin();
                Product priceToUpdate = e.getTableView().getSelectionModel().getSelectedItem();

                System.out.println("Obiekt ze zmienioną ceną:" + priceToUpdate.toString());

                session.update(priceToUpdate);
                session.getTransaction().commit();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Powodzenie");
                alert.setContentText("Cena została zaktualizowana");
                alert.showAndWait();
            } catch (NumberFormatException exc) {
                System.out.println("stara wartosc:" + oldValue.toString());
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setPrice(oldValue);
                session.getTransaction().rollback();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niepowodzenie");
                alert.setContentText("Wprowadzona wartość nie jest liczbą!");
                alert.showAndWait();
            } catch (PersistenceException exc) {
                System.out.println("stara wartosc:" + oldValue.toString());
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setPrice(oldValue);
                session.getTransaction().rollback();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niepowodzenie");
                alert.setContentText("Liczba wykracza poza dopuszczalny zakres!");
                alert.showAndWait();
            } catch (Exception exc) {
                System.out.println("exc to string:"+exc.getMessage());
                if (exc.getMessage().equals("ValueBelowZero")) {
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setPrice(oldValue);
                    session.getTransaction().rollback();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Niepowodzenie");
                    alert.setContentText("Wpisana cena nie może być ujemna!!");
                    alert.showAndWait();
                } else {
                    System.out.println("ERROR UWAGA!!!:" + exc);
                    session.getTransaction().rollback();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Niepowodzenie");
                    alert.setContentText("Niepowodzenie aktualizacji danych");
                    alert.showAndWait();
                }
            }
            session.close();
            priceTable.refresh();
        });
        priceTable.setEditable(true);
    }

    private void setEditableDiscount() {
        DISCOUNT.setCellFactory(TextFieldTableCell.forTableColumn());

        DISCOUNT.setOnEditCommit(e -> { // dodać walidacje try catch
            System.out.println("klasa wpisanej wartości:" + e.getNewValue().getClass());
            Session session = sessionFactory.openSession();
            BigDecimal oldValue = new BigDecimal(e.getOldValue());
            try {
                BigDecimal newValue = new BigDecimal(e.getNewValue());
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setDiscount(newValue.intValue());
                if (newValue.compareTo(BigDecimal.ZERO) < 0 || newValue.compareTo(new BigDecimal(100)) > 0) {
                    throw new Exception("ValueBelowZero");
                }
                session.getTransaction().begin();
                Product priceToUpdate = e.getTableView().getSelectionModel().getSelectedItem();

                System.out.println("Obiekt ze zmienioną zniżką:" + priceToUpdate.toString());

                session.update(priceToUpdate);
                session.getTransaction().commit();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Powodzenie");
                alert.setContentText("Zniżka została zaktualizowana");
                alert.showAndWait();
            } catch (NumberFormatException exc) {
                System.out.println("stara wartosc:" + oldValue.toString());
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setDiscount(oldValue.intValue());
                session.getTransaction().rollback();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niepowodzenie");
                alert.setContentText("Wprowadzona wartość nie jest liczbą!");
                alert.showAndWait();
            } catch (PersistenceException exc) {
                System.out.println("stara wartosc:" + oldValue.toString());
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setDiscount(oldValue.intValue());
                session.getTransaction().rollback();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niepowodzenie");
                alert.setContentText("Liczba wykracza poza dopuszczalny zakres!");
                alert.showAndWait();
            } catch (Exception exc) {
                System.out.println("exc to string:"+exc.getMessage());
                if (exc.getMessage().equals("ValueBelowZero")) {
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setDiscount(oldValue.intValue());
                    session.getTransaction().rollback();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Niepowodzenie");
                    alert.setContentText("Wpisana żniżka nie może być ujemna! lub przekraczać 100%");
                    alert.showAndWait();
                } else {
                    System.out.println("ERROR UWAGA!!!:" + exc);
                    session.getTransaction().rollback();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Niepowodzenie");
                    alert.setContentText("Niepowodzenie aktualizacji danych");
                    alert.showAndWait();
                }
            }
            session.close();
            priceTable.refresh();
        });
        priceTable.setEditable(true);
    }
    public void refreshPricesTable(){
        priceTable.getItems().clear();
        priceTable.setItems(getProducts());
    }
}
