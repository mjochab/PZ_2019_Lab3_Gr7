package controllers;

import entity.*;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.IndentTableView;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.MainWindowController.sessionContext;
import static controllers.MainWindowController.sessionFactory;
import static utils.Alerts.showPrductPickedByCustomer;
import static utils.Alerts.showProductInTransport;

/**
 * Kontroler widoku z modułu sklep wyświetlający zamówienia klientów
 */
public class ShopShowOrdersController implements Initializable {
    @FXML
    private TableView<IndentTableView> ordersTable;
    @FXML
    public TableColumn<IndentTableView, String> ID;
    @FXML
    public TableColumn<IndentTableView, String> STATUS;
    @FXML
    public TableColumn<IndentTableView, String> DATE;
    @FXML
    public TableColumn<IndentTableView, String> FIRST_NAME;
    @FXML
    public TableColumn<IndentTableView, String> LAST_NAME;
    @FXML
    public TableColumn<IndentTableView, String> PHONE_NUMBER;
    Stage stage;

    public TextField searchTF;
    public Button SEARCH, SHOWORDER, DELETE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ID.setCellValueFactory(orderData ->
                new SimpleStringProperty(String.valueOf(orderData.getValue().getOrder().getIndentId())));
        STATUS.setCellValueFactory(orderData ->
                new SimpleStringProperty(orderData.getValue().getState().getStateId().getName()));
        DATE.setCellValueFactory(orderData ->
                new SimpleStringProperty(String.valueOf(orderData.getValue().getOrder().getDateOfOrder())));
        FIRST_NAME.setCellValueFactory(orderData ->
                new SimpleStringProperty(orderData.getValue().getOrder().getCustomerId().getFirstName()));
        LAST_NAME.setCellValueFactory(orderData ->
                new SimpleStringProperty(orderData.getValue().getOrder().getCustomerId().getLastName()));
        PHONE_NUMBER.setCellValueFactory(orderData ->
                new SimpleStringProperty(String.valueOf(orderData.getValue().getOrder().getCustomerId().getPhoneNumber())));
        ordersTable.setItems(getOrders());
        System.out.println(getOrders());
    }


    private ObservableList<IndentTableView> getOrders() {
        ObservableList<IndentTableView> enseignantList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<State_on_shop> sos = session.createQuery("from State_on_shop where shopId = :shopId").setParameter("shopId", sessionContext.getCurrentLoggedShop()).list();
        List<Indent> indents;
        if (searchTF.getText().isEmpty()) {
            indents = session.createQuery("from Indent i where i.customerId is not null and i.shopId_need = :curentShop")
                    .setParameter("curentShop", sessionContext.getCurrentLoggedShop()).list();
        } else {
            indents = session.createQuery("from Indent i where i.customerId is not null AND i.customerId.phoneNumber like :searchPhoneNumber AND i.shopId_need = :curentShop")
                    .setParameter("curentShop", sessionContext.getCurrentLoggedShop())
                    .setParameter("searchPhoneNumber", Integer.valueOf(searchTF.getText())).list();
        }
        for (Indent ind : indents) {
            List<Indent_product> ip = session.createQuery("from Indent_product where indentId = :ip").setParameter("ip", ind).list();
            boolean isIndentComplete = false;
            for (Indent_product indent_product : ip) {
                boolean isProductAvilable = false;
                for (State_on_shop state_on_shop : sos) {
                    if (indent_product.getProductId() == state_on_shop.getProductId() && indent_product.getAmount() <= (state_on_shop.getAmount() - state_on_shop.getLocked())) {
                        isProductAvilable = true;
                        isIndentComplete = true;
                    }
                }
                if (!isProductAvilable) {
                    break;
                }
            }
            if (!isIndentComplete) {
                break;
            }
            try {
                State_of_indent soi = (State_of_indent) session.createQuery("from State_of_indent where indentId = :soi AND stateId.stateId = :stateid").setParameter("soi", ind).setParameter("stateid", 64).getSingleResult();
                State state = (State) session.createQuery("from State where stateId = :stateid").setParameter("stateid", 65).getSingleResult();
                soi.setStateId(state);
                session.update(soi);
                session.beginTransaction().commit();
            }catch(Exception e){
                System.out.println("brak zamowien do zmiany statusu");
                System.out.println(e.getMessage());
            }
        }

        List<State_of_indent> stateofindents = session.createQuery("from State_of_indent soi where soi.indentId.shopId_need = :shopId")
                .setParameter("shopId", sessionContext.getCurrentLoggedShop()).list();
        for (Indent ent : indents) {
            IndentTableView indentTableView = new IndentTableView();
            indentTableView.setOrder(ent);
            for (State_of_indent soi : stateofindents) {
                if (ent.getIndentId() == soi.getIndentId().getIndentId()) {
                    indentTableView.setState(soi);
                }
            }
            enseignantList.add(indentTableView);
        }
        session.close();
        return enseignantList;
    }

    public String setStatus(IndentTableView is) {
        if (is.getState() == null) {
            return "Oczekuje na potwierdzenie";
        } else {
            return is.getState().getStateId().getName();
        }
    }

    public void search() {
        ordersTable.setItems(getOrders());
        searchTF.setText("");
    }


    @FXML
    public void inRealizationDetailsAction(ActionEvent event) throws IOException {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();

        IndentTableView orderView = ordersTable.getSelectionModel().getSelectedItem();

        if (orderView == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmlfiles/shop_order_details.fxml"));


        Parent pane = loader.load();

        // wstrzykniecie wybranego obiektu do widoku szczegolowego
        ShopOrderDetailsController controller = loader.getController();
        controller.setOrder(orderView.getOrder());
        controller.initController();

        stg.setScene(new Scene(pane));
    }

    /**
     * Metoda aktualizująca status zamowienia klienta w bazie danych.
     */
    @FXML
    public void setAsPickedUp() {

        IndentTableView orderView = ordersTable.getSelectionModel().getSelectedItem();

        if (orderView.getState().getStateId().getStateId() == 65) {
            Session session = sessionFactory.openSession();
            State state = (State) session.createQuery("from State where stateId = 66").getSingleResult();
            orderView.getState().setStateId(state);
            session.saveOrUpdate(orderView.getState());
            session.beginTransaction().commit();
            showPrductPickedByCustomer();
        } else {
            showProductInTransport();
        }
    }

    /**
     * Metoda odświeża tabelę z zamówieniami oraz aktualizuje statusy
     */
    public void refreshAndChangeStatus() {
        ordersTable.getItems().clear();
        ordersTable.setItems(getOrders());
    }


}
