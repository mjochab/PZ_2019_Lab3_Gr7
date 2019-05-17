package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.ShopSell;
import org.hibernate.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

import static controllers.MainWindowController.sessionContext;
import static controllers.MainWindowController.sessionFactory;
import static controllers.WarehouseNewProductController.isNumeric;

public class ShopSellItemsForCustomers implements Initializable {

    private static final Logger logger = Logger.getLogger(ShopSellItemsForCustomers.class);

    @FXML
    MenuItem logout;
    @FXML
    private TableView productsTable,productsTableAdd;
    @FXML
    public TableColumn<ShopSell,String> PRODUCTID,PRODUCTID_ADD;
    @FXML
    public TableColumn<ShopSell,String> NAME,NAME_ADD;
    @FXML
    public TableColumn<ShopSell,String> PRICE,PRICE_ADD;
    @FXML
    public TableColumn<ShopSell,String> AMOUNT,AMOUNT_ADD;
    @FXML
    public TableColumn<ShopSell,String> DISCOUNT,DISCOUNT_ADD;
    @FXML
    public TextField serachShop,nameTF,lastNameTF,numerPhoneTF;
    @FXML
    Parent root;

    Stage stage;

    private ObservableList<ShopSell> lista = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PRODUCTID.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().toString()));
        NAME.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getName()));
        PRICE.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getPrice())));
        AMOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        DISCOUNT.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getDiscount())));
        productsTable.setItems(getProducts(null));
        productsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 2){
                        if(productsTable.getSelectionModel().getSelectedItem() != null){
                            logger.info("Wysłany "+productsTable.getSelectionModel().getSelectedItem().toString());
                            if(lista.isEmpty()){
                                lista.add((ShopSell) productsTable.getSelectionModel().getSelectedItem());
                                addToTable(lista);
                            } else {
                                if(lista.contains((ShopSell) productsTable.getSelectionModel().getSelectedItem())){
                                    logger.info("Ten object już tam sie znajduje");
                                } else {
                                    lista.add((ShopSell) productsTable.getSelectionModel().getSelectedItem());
                                    addToTable(lista);
                                }
                            }
                        }
                    }
                }
            }
        });
        //-------------------------------------------------------------------------------------
        productsTableAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 3){
                        if(productsTableAdd.getSelectionModel().getSelectedItem() != null){
                            logger.info("Usuwany object "+productsTableAdd.getSelectionModel().getSelectedItem().toString());
                            lista.remove(productsTableAdd.getSelectionModel().getSelectedItem());
                            addToTable(lista);
                        }
                    }
                }
            }
        });
        setEditableAmount();

    }

    public ObservableList<ShopSell> getProducts(String productName) {
        ObservableList<ShopSell> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<ShopSell> eList;
        if(productName == null || productName.equals("")){
            eList = session.createQuery("SELECT new models.ShopSell(p.productId, p.name, p.price, p.discount, s.amount) FROM Product p " +
                    "INNER JOIN State_on_shop s ON p.productId = s.productId INNER JOIN Shop k ON s.shopId = k.shopId WHERE k.shopId = :idshop AND s.amount > 0  "
            ).setParameter("idshop",sessionContext.getCurrentLoggedShop().getShopId()).list();
        } else {
            eList = session.createQuery("SELECT new models.ShopSell(p.productId, p.name, p.price, p.discount, s.amount) FROM Product p " +
                    "INNER JOIN State_on_shop s ON p.productId = s.productId INNER JOIN Shop k ON s.shopId = k.shopId WHERE k.shopId = :idshop AND s.amount > 0 AND p.name like :produkt "
            ).setParameter("produkt","%"+productName+"%").setParameter("idshop",sessionContext.getCurrentLoggedShop().getShopId()).list();
            serachShop.setText("");
        }
        for (ShopSell ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }

    public void addToTable(ObservableList<ShopSell> item) {
        PRODUCTID_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getProductId().toString()));
        NAME_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(produktData.getValue().getName()));
        PRICE_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getPrice())));
        AMOUNT_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        DISCOUNT_ADD.setCellValueFactory(produktData -> new SimpleStringProperty(String.valueOf(produktData.getValue().getAmount())));
        logger.info("Odebrane " + item.toString() + " rozmiar " + item.size());
        try {
            if (!item.isEmpty()) {
                productsTableAdd.setItems(item);
            } else {
                //naprawić
            }
        } catch (NullPointerException e) {
            logger.error("NullPointerException po odjęciu ostatniego elementu " + e);
        }
    }

    public void searchStateShop(){
        productsTable.setItems(getProducts(serachShop.getText()));
    }

    private void setEditableAmount(){
        productsTableAdd.setEditable(true);
        AMOUNT_ADD.setCellFactory(TextFieldTableCell.forTableColumn());

        AMOUNT_ADD.setOnEditCommit(e -> {
            try{
                logger.info("PRZED"+e.getTableView().getSelectionModel().getSelectedItem().getAmount().toString());
                int check = e.getTableView().getSelectionModel().getSelectedItem().getAmount().intValue();
                if(!isNumeric(e.getNewValue())) {
                    throw new NumberFormatException();
                }
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setAmount(Integer.parseInt(e.getNewValue()));
                logger.info("PO"+e.getTableView().getSelectionModel().getSelectedItem().getAmount().toString());
                if(e.getTableView().getSelectionModel().getSelectedItem().getAmount().intValue() > 0 && e.getTableView().getSelectionModel().getSelectedItem().getAmount().intValue() <= check){
                    logger.info("większe od 0 i mniejsze od ");
                } else {
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setAmount(check);
                    logger.info("Powrót do poprzedniej liczby");
                    productsTableAdd.refresh();
                }
            } catch (NumberFormatException exc){
                logger.warn("Powrót do poprzedniej liczby");
                productsTableAdd.refresh();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niepowodzenie");
                logger.info("Niepowodzenie");
                alert.setContentText("Wprowadzona wartość nie jest liczbą!");
                logger.info("Wprowadzona wartość nie jest liczbą!");
                alert.showAndWait();
            }

        });

        DISCOUNT_ADD.setCellFactory(TextFieldTableCell.forTableColumn());

        DISCOUNT_ADD.setOnEditCommit(e -> {
            try{
                logger.info("PRZED"+e.getTableView().getSelectionModel().getSelectedItem().getDiscount().toString());
                int check = e.getTableView().getSelectionModel().getSelectedItem().getDiscount().intValue();
                if(!isNumeric(e.getNewValue())) {
                    throw new NumberFormatException();
                }
                e.getTableView().getItems().get(e.getTablePosition().getRow()).setDiscount(Integer.parseInt(e.getNewValue()));
                logger.info("PO"+e.getTableView().getSelectionModel().getSelectedItem().getDiscount().toString());
                if(e.getTableView().getSelectionModel().getSelectedItem().getDiscount().intValue() > 0 && e.getTableView().getSelectionModel().getSelectedItem().getDiscount().intValue() <= 100){
                    logger.info("większe od 0 i mniejsze od 100 ");
                } else {
                    e.getTableView().getItems().get(e.getTablePosition().getRow()).setDiscount(check);
                    logger.info("Powrót do poprzedniej liczby");
                    productsTableAdd.refresh();
                }
            } catch (NumberFormatException exc){
                logger.warn("Powrót do poprzedniej liczby");
                productsTableAdd.refresh();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Niepowodzenie");
                logger.info("Niepowodzenie");
                alert.setContentText("Wprowadzona wartość nie jest liczbą!");
                logger.info("Wprowadzona wartość nie jest liczbą!");
                alert.showAndWait();
            }

        });
    }

    public void confirm(){
        if(!lista.isEmpty()){
            logger.info("Przygotowane dane do wysłąnia "+lista+" "+nameTF.getText()+" "+lastNameTF.getText()+" "+numerPhoneTF.getText());
            logger.info("Dodaj walidacje textfield żeby nie były puste");
        }
    }
}

