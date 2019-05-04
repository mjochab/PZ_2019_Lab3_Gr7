package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import models.SalesCreatorModel;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import static controllers.MainWindowController.sessionFactory;


public class WarehouseNewProductController implements Initializable {

    @FXML
    public TextField NAME;
    @FXML
    public TextField AMOUNT;
    @FXML
    public TextField PRICE;
    @FXML
    public Button addInsert;

    String[] tab = {"SELECT new models.SalesCreatorModel(p.productId ) FROM Product p WHERE p.name like "};

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addInsert(ActionEvent event) throws IOException {
        if(isNumeric(AMOUNT.getText()) && isNumeric(PRICE.getText())){ //wprowadzono liczby
            if(getNameProduct().size() == 0){ //brak takiego produktu, dodać do bazy
                System.out.println("można dodać do bazy");
                insertToDataBase();
            } else { //produkt jest już w bazie
                System.out.println("Jest w bazie "+getNameProduct().get(0).getProductId());
            }
        } else {
            System.out.println("Wprowadź poprawne dane");
        }
    }

    public ObservableList<SalesCreatorModel> getNameProduct(){
        ObservableList<SalesCreatorModel> productList = FXCollections.observableArrayList();
        Session session = sessionFactory.openSession();
        List<SalesCreatorModel> eList = session.createQuery(tab[0]+":nameDevice "
        ).setParameter("nameDevice",NAME.getText()).list();
        System.out.println("getOrder "+eList);
        for (SalesCreatorModel ent : eList) {
            productList.add(ent);
        }
        session.close();
        return productList;
    }

    public void insertToDataBase(){
        Session session = sessionFactory.openSession();
        //uery qry = session.createQuery("INSERT INTO Product (ProductId, Name, Price, Discount) " +
        //        "SELECT new models.ProductShop(p.productId,p.name,p.price,p.discount) FROM Product p " +
         //       "WHERE p.productId = null AND p.name = 'teskt' AND p.price = 900 AND p.discount = 0 ");
        //int result = qry.executeUpdate();
        //System.out.println("Ilość dodanych rekordów "+result);
        session.close();
    }

    // brak isDigit/isNumeric
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
