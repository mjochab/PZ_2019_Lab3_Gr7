package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Order {
    private Boolean isComplex;
    private SimpleStringProperty number;
    private ArrayList<OrderProductRecord> productList;
    private Button button;
    private HBox buttons;

    public Order()
    {
        isComplex = false;
        number = new SimpleStringProperty("");
        productList = new ArrayList<>();
        button = new Button("Podglad");
        buttons = new HBox();

        button.setOnAction(event -> {
            Stage stg = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Parent par = null;
            try {
                FXMLLoader loader = null;

                if(isComplex)
                    loader = new FXMLLoader(getClass().getResource("../fxmlfiles/logistic_complex_order_details.fxml"));
                else
                    loader = new FXMLLoader(getClass().getResource("../fxmlfiles/logistic_simple_order_details.fxml"));

                par = loader.load();

            } catch (IOException e) {
                e.printStackTrace();
            }
            stg.setScene(new Scene(par));
        });

        buttons.getChildren().addAll(button);
    }

    public Boolean isComplex()
    {
        return this.isComplex;
    }

    public void setComplex(Boolean bool)
    {
        this.isComplex = bool;
    }

    public String getNumber() {
        return number.get();
    }

    public void setNumber(String number) {
        this.number = new SimpleStringProperty(number);
    }

    public void addProduct(Product product, Integer count)
    {
        this.productList.add(new OrderProductRecord(product, count));
    }

    public Button getButton()
    {
        return this.button;
    }

    public void setButtonText(String text)
    {
        this.button.setText(text);
    }
}
