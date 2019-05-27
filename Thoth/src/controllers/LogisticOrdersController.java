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
import log.ThothLoggerConfigurator;
import models.IndentTableView;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionContext;
import static controllers.MainWindowController.sessionFactory;

/**
 * Kontroler glownego okna modulu logistyki
 */
public class LogisticOrdersController implements Initializable {
    private static final Logger logger = Logger.getLogger(ComplexOrderDetailsController.class);
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

    private final ObservableList<IndentTableView> displayedOrdersReadyForShipment = FXCollections.observableArrayList();
    private final ObservableList displayedOrdersInRealization = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.addAppender(ThothLoggerConfigurator.getFileAppender());
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
    private List<State_of_indent> getIndentsByState(String state) {
        Session session = sessionFactory.openSession();

        // pobranie odpowiedniego stanu
        State stateObject = (State) session.createQuery("from State where name = :state")
                .setParameter("state", state)
                .getSingleResult();

        logger.warn(stateObject == null);

        // pobranie zamowien o stanie pobranym wyzej
        List<State_of_indent> state_of_indents = session.createQuery("from State_of_indent where StateId = :sid")
                .setParameter("sid", Objects.requireNonNull(stateObject).getStateId()).list();

        List<State_of_indent> state_of_indents_shop_delivery = new ArrayList<>();

        for (State_of_indent soi : state_of_indents) {
            if (soi.getIndentId().getShopId_delivery().getShopId() == sessionContext.getCurrentLoggedShop().getShopId()) {
                logger.warn("Ten sam sklep!");
                state_of_indents_shop_delivery.add(soi);
            }
        }

        logger.warn(state_of_indents.size());

        for (State_of_indent soi : state_of_indents_shop_delivery) {
            logger.warn("parent id = " + soi.getIndentId().getIndentId());
            List<Indent> subIndents = session.createQuery("From Indent indent where ParentId = :pid")
                    .setParameter("pid", soi.getIndentId().getIndentId()).list();

            logger.warn(subIndents.size());

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
    private ObservableList<IndentTableView> getIndentsReadyForShipment() {
        ObservableList<IndentTableView> listOfIndents = FXCollections.observableArrayList();

        for (State_of_indent soi : getIndentsByState("Oczekuje na transport")) {
            IndentTableView indentTableView = new IndentTableView();
            indentTableView.setOrder(soi.getIndentId());
            indentTableView.setState(soi);

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
    private ObservableList<IndentTableView> getIndentsInRealization() {
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

    /**
     * Metoda obslugujaca wyswietlenie szczegolow zamowienia oczekujacego na transport
     * @param event
     * @throws IOException
     */
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
        logger.warn(orderView.getOrder().isComplex());
        if (orderView.getOrder().isComplex()) {
            loader = new FXMLLoader(getClass().getResource("/fxmlfiles/complex_order_details.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/fxmlfiles/simple_order_details.fxml"));
        }

        Parent pane = loader.load();

        // wstrzykniecie wybranego obiektu do widoku szczegolowego
        if (orderView.getOrder().isComplex()) {
            ComplexOrderDetailsController controller = loader.getController();
            //ustawienie ścieżki powrotu
            controller.setLoader("/fxmlfiles/main_view_logistic.fxml");
            controller.setOrder(orderView.getOrder());
            controller.initController();
        } else {
            SimpleOrderDetailsController controller = loader.getController();
            //ustawienie ścieżki powrotu
            controller.setLoader("/fxmlfiles/main_view_logistic.fxml");
            controller.setOrder(orderView.getOrder());
            controller.initController();
        }

        stg.setScene(new Scene(pane));
    }

    /**
     * Metoda obslugujaca wyswietlenie szczegolow zamowienia w transporcie
     * @param event
     * @throws IOException
     */
    @FXML
    public void inRealizationDetailsAction(ActionEvent event) throws IOException {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();

        IndentTableView orderView = ordersInRealization.getSelectionModel().getSelectedItem();

        if (orderView == null)
            return;

        FXMLLoader loader = null;

        if (orderView.getOrder().isComplex()) {
            loader = new FXMLLoader(getClass().getResource("/fxmlfiles/complex_order_details.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/fxmlfiles/simple_order_details.fxml"));
        }

        Parent pane = loader.load();

        // wstrzykniecie wybranego obiektu do widoku szczegolowego
        if (orderView.getOrder().isComplex()) {
            ComplexOrderDetailsController controller = loader.getController();
            controller.setLoader("/fxmlfiles/main_view_logistic.fxml");
            controller.setOrder(orderView.getOrder());
            controller.initController();
        } else {
            SimpleOrderDetailsController controller = loader.getController();
            controller.setLoader("/fxmlfiles/main_view_logistic.fxml");
            controller.setOrder(orderView.getOrder());
            controller.initController();
        }

        stg.setScene(new Scene(pane));
    }

    /**
     * Metoda zmienia stan zamowienia
     * @param indentToStateChange Zamowienie ktorego stan ma zostac zmieniony
     * @param state Nowy stan zamowienia
     */
    private void changeOrderState(Indent indentToStateChange, String state) {
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
            logger.warn("Nie udalo sie zmienic stanu!");
            session.getTransaction().rollback();
        }
    }

    /**
     * Metoda obslugujaca przycisk odebrania do transportu
     */
    @FXML
    public void takeOrderHandler() {
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

    /**
     * Metoda obslugujaca przycisniecie przycisku "Dostarcz"
     */
    @FXML
    public void deliverOrderHandler() {
        if (ordersInRealization.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        IndentTableView indentToTake = ordersInRealization.getSelectionModel().getSelectedItem();

        Indent indentToStateChange = indentToTake.getOrder();

        changeOrderState(indentToStateChange, "Oczekuje na potwierdzenie odbioru");
        ordersInRealization.getItems().clear();
        ordersInRealization.setItems(getIndentsInRealization());
    }
}
