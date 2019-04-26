package models;

import java.math.BigDecimal;

public class ShopSell {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private Integer amount;

    public ShopSell(){

    }

    public ShopSell(Integer productId, String name, BigDecimal price, Integer amount) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
