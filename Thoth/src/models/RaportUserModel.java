package models;

public class RaportUserModel {
    private String firstName;
    private String lastName;
    private RaportProductModel[] soldProducts;
    private RaportUserModel[] users;

    public RaportUserModel(String firstName, String lastName, RaportProductModel[] soldProducts, RaportUserModel[] users) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.soldProducts = soldProducts;
        this.users = users;
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

    public RaportUserModel[] getUsers() {
        return users;
    }

    public void setUsers(RaportUserModel[] users) {
        this.users = users;
    }
}
