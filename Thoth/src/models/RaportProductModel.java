package models;

import java.math.BigDecimal;

public class RaportProductModel {
    private String name;
    private BigDecimal price;
    private Integer stateOnShop;
    private Integer soldAmount;

    public RaportProductModel(String name, BigDecimal price, Integer stateOnShop, Integer soldAmount) {
        this.name = name;
        this.price = price;
        this.stateOnShop = stateOnShop;
        this.soldAmount = soldAmount;
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

    public Integer getStateOnShop() {
        return stateOnShop;
    }

    public void setStateOnShop(Integer stateOnShop) {
        this.stateOnShop = stateOnShop;
    }

    public Integer getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(Integer soldAmount) {
        this.soldAmount = soldAmount;
    }
}
