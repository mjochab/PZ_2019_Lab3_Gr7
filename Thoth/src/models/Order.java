package models;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

class Order {
    private Boolean isComplex;
    private SimpleStringProperty number;
    private final ArrayList<OrderProductRecord> productList;

    public Order()
    {
        isComplex = false;
        number = new SimpleStringProperty("");
        productList = new ArrayList<>();
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
}
