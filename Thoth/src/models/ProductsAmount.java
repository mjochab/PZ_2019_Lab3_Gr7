package models;

import entity.Product;

class ProductsAmount {
    private Product product;
    private int amount;

    public ProductsAmount(Product product) {
        this.product = product;
        this.amount = 1;
    }

    public ProductsAmount(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
