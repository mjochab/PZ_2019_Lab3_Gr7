package models;

import java.util.Date;

class ShopOrders {

    private Integer id_order;
    private String status;
    private Date date;
    private String customer_name;
    private String customer_surname;
    private Integer phone_number;

    public ShopOrders(Integer id_order, String status, Date date, String customer_name, String customer_surname, Integer phone_number) {
        this.id_order = id_order;
        this.status = status;
        this.date = date;
        this.customer_name = customer_name;
        this.customer_surname = customer_surname;
        this.phone_number = phone_number;
    }

    public Integer getId_order() {
        return id_order;
    }

    public void setId_order(Integer id_order) {
        this.id_order = id_order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_surname() {
        return customer_surname;
    }

    public void setCustomer_surname(String customer_surname) {
        this.customer_surname = customer_surname;
    }

    public Integer getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(Integer phone_number) {
        this.phone_number = phone_number;
    }
}
