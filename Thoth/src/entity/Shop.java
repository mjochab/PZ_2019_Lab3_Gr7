package entity;

import javax.persistence.*;

@Entity
@Table(name = "object")
public class Shop {

    @Id
    @Column(name = "ObjectId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int objectId;

    @Column(name = "ZipCode")
    private String zipCode;

    @Column(name = "City")
    private String city;

    @Column(name = "Street")
    private String street;

    @Column(name = "IsShop")
    private Boolean idShop;

    public Shop(){

    }

    public Shop(String zipCode, String city, String street, Boolean idShop) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.idShop = idShop;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
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
        return "Shop{" +
                "objectId=" + objectId +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", idShop=" + idShop +
                '}';
    }
}
