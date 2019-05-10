package models;

public class Who {

    private Integer shopID;
    private String shopName;

    public Who(Integer shopID, String shopName) {
        this.shopID = shopID;
        this.shopName = shopName;
    }

    public Integer getShopID() {
        return shopID;
    }

    public void setShopID(Integer shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
