package models;

import java.math.BigDecimal;

public class RaportProductModel {
    private Integer shopId;
    private String name;
    private Long sold;
    private BigDecimal total_price;


    public RaportProductModel(Integer shopId, String name, Long sold, BigDecimal total_price) {
        this.shopId = shopId;
        this.name = name;
        this.sold = sold;
        this.total_price = total_price;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSold() {
        return sold;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "RaportProductModel{" +
                "shopId=" + shopId +
                ", name='" + name + '\'' +
                ", sold=" + sold +
                ", total_price=" + total_price +
                '}';
    }
}
