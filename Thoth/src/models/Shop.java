package models;

public class Shop {

    private int shopId;
    private String zipCode;
    private String city;
    private String street;
    private Boolean idShop;

    public Shop(int shopId) {
        this.shopId = shopId;
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
}
