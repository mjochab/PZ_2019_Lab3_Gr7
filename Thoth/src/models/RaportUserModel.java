package models;

public class RaportUserModel {
    private String firstName;
    private String lastName;
    private RaportProductModel[] soldProducts;

    public RaportUserModel(String firstName, String lastName, RaportProductModel[] soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.soldProducts = soldProducts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RaportProductModel[] getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(RaportProductModel[] soldProducts) {
        this.soldProducts = soldProducts;
    }
}
