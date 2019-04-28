package models;

import entity.Shop;
import entity.User;

public class EmployeeView {
    private User user;
    private Shop shop;

    public EmployeeView() {}

    public EmployeeView(User user, Shop shop)
    {
        setUser(user);
        setShop(shop);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
