package models;

import entity.Product;
import entity.Shop;

public class StateOnShop {
    private int id;
    private int amount;
    private Product productid;
    private Shop shopid;

    public StateOnShop() {
    }

    public StateOnShop(int id, int amount, Product productid, Shop shopid) {
        this.id = id;
        this.amount = amount;
        this.productid = productid;
        this.shopid = shopid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProductid() {
        return productid;
    }

    public void setProductid(Product productid) {
        this.productid = productid;
    }

    public Shop getShopid() {
        return shopid;
    }

    public void setShopid(Shop shopid) {
        this.shopid = shopid;
    }
}
