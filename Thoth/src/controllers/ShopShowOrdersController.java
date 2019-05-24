package controllers;

import entity.Indent;
import entity.State;
import entity.State_of_indent;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.IndentTableView;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static utils.Alerts.*;
import static controllers.MainWindowController.sessionContext;
import static controllers.MainWindowController.sessionFactory;

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
                new SimpleStringProperty(setStatus(orderData.getValue())));
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


    public ObservableList<IndentTableView> getOrders() {
        ObservableList<IndentTableView> enseignantList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<Indent> indents;
        if (searchTF.getText().isEmpty()) {
            indents = session.createQuery("from Indent i where i.customerId is not null and i.shopId_need = :curentShop")
                    .setParameter("curentShop", sessionContext.getCurrentLoggedShop()).list();
        } else {
            indents = session.createQuery("from Indent i where i.customerId is not null AND i.customerId.phoneNumber like :searchPhoneNumber AND i.shopId_need = :curentShop")
                    .setParameter("curentShop", sessionContext.getCurrentLoggedShop())
                    .setParameter("searchPhoneNumber", Integer.valueOf(searchTF.getText())).list();
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

    public void confirm() {
        //button
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

    @FXML
    public void setAsPickedUp(ActionEvent event) throws IOException {

        IndentTableView orderView = ordersTable.getSelectionModel().getSelectedItem();

        Session session = sessionFactory.openSession();
        List<State_of_indent> soi = session.createQuery("from State_of_indent soi where soi.indentId = :indent")
                .setParameter("indent", orderView.getOrder()).list();
        System.out.println("Lista powinna mieś rozmiar 1 aktualny rozmiar :"+soi.size());
        State state = (State)session.createQuery("from State where stateId = 66").getSingleResult();
        if (orderView == null) {
            session.close();
            return;
        } else {
            System.out.println("program dziala");
            if (soi.isEmpty()) {
                session.close();
                showProductInTransport();
            } else {
                if (orderView.getState().getStateId().getStateId() == 5) {
                    soi.get(0).setStateId(state);
                    System.out.println(soi.get(0).getStateId().getStateId());
                    session.update(soi.get(0));
                    session.beginTransaction().commit();
                    showSuccesAllert();
                }
                if(orderView.getState().getStateId().getStateId() > 5){
                    showPrductPickedByCustomer();
                }
            }

        }
    }


}
