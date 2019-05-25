package controllers;

import entity.*;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import models.IndentTableView;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionContext;
import static controllers.MainWindowController.sessionFactory;

/**
 * Kontroler glownego okna modulu logistyki
 */
public class LogisticOrdersController implements Initializable {
    @FXML
    private TableView<IndentTableView> ordersReadyForShipment;
    @FXML
    private TableView<IndentTableView> ordersInRealization;
    @FXML
    private Button toShipmentDetails;
    @FXML
    private Button inRealizationDetails;
    @FXML
    private Button takeOrder;
    @FXML
    private Button deliverOrder;

    // for shipment table view
    @FXML
    private TableColumn<IndentTableView, String> fromForShipment;
    @FXML
    private TableColumn<IndentTableView, String> toForShipment;
    @FXML
    private TableColumn<IndentTableView, Integer> idForShipment;
    @FXML
    private TableColumn<IndentTableView, String> stateForShipment;

    // in realization table view
    @FXML
    private TableColumn<IndentTableView, String> fromInRealization;
    @FXML
    private TableColumn<IndentTableView, String> toInRealization;
    @FXML
    private TableColumn<IndentTableView, Integer> idInRealization;
    @FXML
    private TableColumn<IndentTableView, String> stateInRealization;

    private ObservableList<IndentTableView> displayedOrdersReadyForShipment = FXCollections.observableArrayList();
    private ObservableList displayedOrdersInRealization = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // value factories dla tableview z zamowieniami oczekujacymi na transport
        fromForShipment.setCellValueFactory(indentData -> new SimpleStringProperty(indentData.getValue().getOrder().getShopId_delivery().getCity()));
        toForShipment.setCellValueFactory(indentData -> new SimpleStringProperty(indentData.getValue().getOrder().getShopId_need().getCity()));
        idForShipment.setCellValueFactory(indentData -> new SimpleIntegerProperty(indentData.getValue().getOrder().getIndentId()).asObject());
        stateForShipment.setCellValueFactory(indentData -> new SimpleStringProperty(indentData.getValue().getState().getStateId().getName()));

        displayedOrdersReadyForShipment.addAll(getIndentsReadyForShipment());
        ordersReadyForShipment.setItems(displayedOrdersReadyForShipment);


        // value factories dla tabelview z zamowieniami w realizacji
        fromInRealization.setCellValueFactory(indentData -> new SimpleStringProperty(indentData.getValue().getOrder().getShopId_delivery().getCity()));
        toInRealization.setCellValueFactory(indentData -> new SimpleStringProperty(indentData.getValue().getOrder().getShopId_need().getCity()));
        idInRealization.setCellValueFactory(indentData -> new SimpleIntegerProperty(indentData.getValue().getOrder().getIndentId()).asObject());
        stateInRealization.setCellValueFactory(indentData -> new SimpleStringProperty(indentData.getValue().getState().getStateId().getName()));

        displayedOrdersInRealization.addAll(getIndentsInRealization());
        ordersInRealization.setItems(displayedOrdersInRealization);
    }

    /**
     * Metooda pobiera z bazy wszystkie zamowienia o okreslonym stanie
     *
     * @param state stan zamowien
     * @return Lista obiektow {@link State_of_indent} State_of_indent
     */
    public List<State_of_indent> getIndentsByState(String state) {
        Session session = sessionFactory.openSession();

        // pobranie odpowiedniego stanu
        State stateObject = (State) session.createQuery("from State where name = :state")
                .setParameter("state", state)
                .getSingleResult();

        System.out.println(stateObject == null);

        // pobranie zamowien o stanie pobranym wyzej
        List<State_of_indent> state_of_indents = session.createQuery("from State_of_indent where StateId = :sid")
                .setParameter("sid", stateObject.getStateId()).list();

        List<State_of_indent> state_of_indents_shop_delivery = new ArrayList<>();

        for (State_of_indent soi : state_of_indents) {
            if (soi.getIndentId().getShopId_delivery().getShopId() == sessionContext.getCurrentLoggedShop().getShopId()) {
                System.out.println("Ten sam sklep!");
                state_of_indents_shop_delivery.add(soi);
            }
        }

        System.out.println(state_of_indents.size());

        for (State_of_indent soi : state_of_indents_shop_delivery) {
            System.out.println("parent id = " + soi.getIndentId().getIndentId());
            List<Indent> subIndents = session.createQuery("From Indent indent where ParentId = :pid")
                    .setParameter("pid", soi.getIndentId().getIndentId()).list();

            System.out.println(subIndents.size());

            if (subIndents.size() > 0) {
                soi.getIndentId().setIsComplex(true);
            } else {
                soi.getIndentId().setIsComplex(false);
            }
        }

        session.close();

        return state_of_indents_shop_delivery;
    }

    /**
     * Metoda zwraca {@link ObservableList} reprezentujaca zamowienia o stanie "Oczekuje na transport"
     *
     * @return Lista obserwowalna obiektow {@link IndentTableView}
     */
    public ObservableList<IndentTableView> getIndentsReadyForShipment() {
        ObservableList<IndentTableView> listOfIndents = FXCollections.observableArrayList();

        for (State_of_indent soi : getIndentsByState("Oczekuje na transport")) {
            IndentTableView indentTableView = new IndentTableView();
            indentTableView.setOrder(soi.getIndentId());
            indentTableView.setState(soi);

            /*
            // jesli zamowienie jest podzamowieniem, nie wyswietlaj go w glownej liscie
            if (soi.getIndentId().getParentId() != null) {
                continue;
            }
            */

            if (soi.getIndentId().isComplex()) {
                continue;
            }

            listOfIndents.add(indentTableView);
        }

        return listOfIndents;
    }

    /**
     * Metoda zwraca {@link ObservableList} reprezentujaca zamowienia o stanie "Oczekuje potwierdzenie odbioru"
     *
     * @return Lista obserwowalna obiektow {@link IndentTableView}
     */
    public ObservableList<IndentTableView> getIndentsInRealization() {
        ObservableList<IndentTableView> listOfIndents = FXCollections.observableArrayList();

        for (State_of_indent soi : getIndentsByState("W transporcie")) {
            IndentTableView indentTableView = new IndentTableView();
            indentTableView.setOrder(soi.getIndentId());
            indentTableView.setState(soi);

            /*
            // jesli zamowienie jest podzamowieniem, nie wyswietlaj go w glownej liscie
            if (soi.getIndentId().getParentId() != null) {
                continue;
            }
            */

            if (soi.getIndentId().isComplex()) {
                continue;
            }

            listOfIndents.add(indentTableView);
        }

        return listOfIndents;
    }

    @FXML
    public void toShipmentDetailsAction(ActionEvent event) throws IOException {
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // pobranie wybranego wiersza
        IndentTableView orderView = ordersReadyForShipment.getSelectionModel().getSelectedItem();

        // sprawdzenie czy jakikolwiek wiersz zostal wybrany
        if (orderView == null)
            return;

        FXMLLoader loader = null;

        // czy wybrany wiersz zawiera zamowienie zlozone
        // tak -> zaladuj widok zamowienia zlozonego (complex)
        // nie -> zaladuj widok zamowienia prostego
        System.out.println(orderView.getOrder().isComplex());
        if (orderView.getOrder().isComplex()) {
            loader = new FXMLLoader(getClass().getResource("../fxmlfiles/complex_order_details.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("../fxmlfiles/simple_order_details.fxml"));
        }

        Parent pane = loader.load();

        // wstrzykniecie wybranego obiektu do widoku szczegolowego
        if (orderView.getOrder().isComplex()) {
            ComplexOrderDetailsController controller = loader.getController();
            //ustawienie ścieżki powrotu
            controller.setLoader("../fxmlfiles/main_view_logistic.fxml");
            controller.setOrder(orderView.getOrder());
            controller.initController();
        } else {
            SimpleOrderDetailsController controller = loader.getController();
            //ustawienie ścieżki powrotu
            controller.setLoader("../fxmlfiles/main_view_logistic.fxml");
            controller.setOrder(orderView.getOrder());
            controller.initController();
        }

        stg.setScene(new Scene(pane));
    }


    @FXML
    public void inRealizationDetailsAction(ActionEvent event) throws IOException {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();

        IndentTableView orderView = ordersInRealization.getSelectionModel().getSelectedItem();

        if (orderView == null)
            return;

        FXMLLoader loader = null;

        if (orderView.getOrder().isComplex()) {
            loader = new FXMLLoader(getClass().getResource("../fxmlfiles/complex_order_details.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("../fxmlfiles/simple_order_details.fxml"));
        }

        Parent pane = loader.load();

        // wstrzykniecie wybranego obiektu do widoku szczegolowego
        if (orderView.getOrder().isComplex()) {
            ComplexOrderDetailsController controller = loader.getController();
            controller.setLoader("../fxmlfiles/main_view_logistic.fxml");
            controller.setOrder(orderView.getOrder());
            controller.initController();
        } else {
            SimpleOrderDetailsController controller = loader.getController();
            controller.setLoader("../fxmlfiles/main_view_logistic.fxml");
            controller.setOrder(orderView.getOrder());
            controller.initController();
        }

        stg.setScene(new Scene(pane));
    }

    public void changeOrderState(Indent indentToStateChange, String state) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        try {
            State stateToSet = (State) session.createQuery("from State where name = :name")
                    .setParameter("name", state)
                    .getSingleResult();

            State_of_indent newIndentState = (State_of_indent) session.createQuery("from State_of_indent where IndentId = :iid")
                    .setParameter("iid", indentToStateChange.getIndentId())
                    .getSingleResult();

            newIndentState.setStateId(stateToSet);

            session.update(newIndentState);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Nie udalo sie zmienic stanu!");
            session.getTransaction().rollback();
        }
    }

    @FXML
    public void takeOrderHandler(ActionEvent event) {
        if (ordersReadyForShipment.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        IndentTableView indentToTake = ordersReadyForShipment.getSelectionModel().getSelectedItem();

        Indent indentToStateChange = indentToTake.getOrder();

        changeOrderState(indentToStateChange, "W transporcie");

        ordersReadyForShipment.getItems().clear();
        ordersReadyForShipment.setItems(getIndentsReadyForShipment());

        ordersInRealization.getItems().clear();
        ordersInRealization.setItems(getIndentsInRealization());
    }

    @FXML
    public void deliverOrderHandler(ActionEvent event) {
        if (ordersInRealization.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        IndentTableView indentToTake = ordersInRealization.getSelectionModel().getSelectedItem();

        Indent indentToStateChange = indentToTake.getOrder();

        /*
            for produkt in zamowienie
                pobirz ilosc produktu na magazynie dostarczajacym
                pobirz ilosc produktu na magazynie docelowym
                zmniejsz ilosc produktu na magazyne dostarczajacym
                zmniejsz ilosc zablokowanych produktow na magazynie dostarczajacym
                zwieksz ilosc produktu na magazynie docelowym

         */
        Session session = sessionFactory.openSession();
        List<Indent_product> productsToDeliver = session.createQuery("from Indent_product where IndentId = :iid")
                .setParameter("iid", indentToStateChange.getIndentId())
                .getResultList();
        Shop shopSource = indentToStateChange.getShopId_delivery();
        Shop shopDestination = indentToStateChange.getShopId_need();

        boolean success = true;

        for (Indent_product indentProduct : productsToDeliver) {
            Product product = indentProduct.getProductId();
            int amountOfProduct = indentProduct.getAmount();

            State_on_shop stateOnShopSource = (State_on_shop) session.createQuery("from State_on_shop where ShopId = :sid and ProductId = :pid")
                    .setParameter("sid", shopSource.getShopId())
                    .setParameter("pid", product.getProductId())
                    .getSingleResult();

            State_on_shop stateOnShopDestination;
            //jeżeli produkt jeszcze nie istnieje w danym sklepie to utworz stan produktu
            try {
                stateOnShopDestination = (State_on_shop) session.createQuery("from State_on_shop where ShopId = :sid and ProductId = :pid")
                        .setParameter("sid", shopDestination.getShopId())
                        .setParameter("pid", product.getProductId())
                        .getSingleResult();
            } catch (Exception e) {
                stateOnShopDestination = new State_on_shop(product,shopDestination,0);
                session.save(stateOnShopDestination);
            }


            try {
                // odejmuje z magazynu ktory dostarczyl produkt
                stateOnShopSource.setAmount(stateOnShopSource.getAmount() - amountOfProduct);
                stateOnShopSource.setLocked(stateOnShopSource.getLocked() - amountOfProduct);
                session.update(stateOnShopSource);
                // dodaje do magazynu ktory zamowil produkt
                stateOnShopDestination.setAmount(stateOnShopDestination.getAmount() + amountOfProduct);
                session.update(stateOnShopDestination);
                session.beginTransaction().commit();
                System.out.println("Pomyslnie zakonczono zmiane ilosc produktu");
            } catch (Exception e) {
                System.out.println("Nastapil blad, wycofuje zmiany");
                System.out.println(e.getMessage());
                session.getTransaction().rollback();
                success = false;
                break;
            }
        }

        session.close();

        if (success) {
            changeOrderState(indentToStateChange, "Oczekuje na potwierdzenie odbioru");
            ordersInRealization.getItems().clear();
            ordersInRealization.setItems(getIndentsInRealization());
        } else {
            System.out.println("Nie udalo sie dostarczyc zamowienia");
        }
    }
}
