package models;

public class StateOrderModel {

    private String city;
    private String state;
    private Integer orderid;

    public StateOrderModel(String city, String state, Integer orderid) {
        this.city = city;
        this.state = state;
        this.orderid = orderid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }
}
