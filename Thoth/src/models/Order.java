package models;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Order {
    private SimpleStringProperty number;
    private ArrayList<OrderProductRecord> productList;

    public Order()
    {
        number = new SimpleStringProperty("");
        productList = new ArrayList<>();
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
}
