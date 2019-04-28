package entity;

import javax.persistence.*;

@Entity
@Table(name = "Shop")
public class Shop {

    @Id
    @Column(name = "ShopId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shopId;

    @Column(name = "ZipCode",nullable = false)
    private String zipCode;

    @Column(name = "City",nullable = false)
    private String city;

    @Column(name = "Street",nullable = false)
    private String street;

    @Column(name = "IsShop",nullable = false)
    private Boolean idShop;

    public Shop() {

    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Boolean getIdShop() {
        return idShop;
    }

    public void setIdShop(Boolean idShop) {
        this.idShop = idShop;
    }

    @Override
    public String toString() {
        return getCity() + ", " + getStreet() + ", " + getZipCode();
    }
}
