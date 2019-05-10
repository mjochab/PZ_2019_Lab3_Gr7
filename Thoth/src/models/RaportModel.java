package models;

import java.math.BigDecimal;
import java.util.Arrays;

public class RaportModel {
    private Integer shopId;
    private String street;
    private String zipCode;
    private String city;
    private BigDecimal profit;
    private RaportProductModel[] products;
    private RaportUserModel[] users;

    public RaportModel(Integer shopId, String street, String zipCode, String city, BigDecimal profit, RaportProductModel[] products, RaportUserModel[] users) {
        this.shopId = shopId;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.profit = profit;
        this.products = products;
        this.users = users;
    }

    public RaportModel(Integer shopId, String street, String zipCode, String city, BigDecimal profit) {
        this.shopId = shopId;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        if(profit == null){
            this.profit = new BigDecimal(0);
        }
        else {
            this.profit = profit;
        }
    }

    public RaportModel(Integer shopId, String street, String zipCode, String city) {
        this.shopId = shopId;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public RaportProductModel[] getProducts() {
        return products;
    }

    public void setProducts(RaportProductModel[] products) {
        this.products = products;
    }

    public RaportUserModel[] getUsers() {
        return users;
    }

    public void setUsers(RaportUserModel[] users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "RaportModel{" +
                "shopId=" + shopId +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", profit=" + profit +
                ", products=" + Arrays.toString(products) +
                ", users=" + Arrays.toString(users) +
                '}';
    }
}

