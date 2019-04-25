package models;

import entity.Indent;
import entity.Indent_product;

public class IndentProductsView {
    private Indent_product products;
    private Indent order;

    public IndentProductsView() {}

    public Indent_product getProducts() {
        return products;
    }

    public void setProducts(Indent_product products) {
        this.products = products;
    }

    public Indent getOrder() {
        return order;
    }

    public void setOrder(Indent order) {
        this.order = order;
    }
}
