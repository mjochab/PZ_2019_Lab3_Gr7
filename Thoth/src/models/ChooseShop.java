package models;

import entity.Shop;
import entity.User;

public class ChooseShop {

    private int shopId;
    private String city;
    private String street;
    private String zipCode;


    public ChooseShop() {}

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public ChooseShop(int shopId, String city, String street, String zipCode) {
        this.shopId = shopId;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }
}
