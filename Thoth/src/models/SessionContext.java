package models;

import entity.Shop;
import entity.User;
import entity.UserShop;

public class SessionContext {
    private User currentLoggedUser;
    private Shop currentLoggedShop;

    public SessionContext(UserShop userShop) {
        setCurrentLoggedUser(userShop.getUserId());
        setCurrentLoggedShop(userShop.getShopId());
    }

    public SessionContext(User user, Shop shop) {
        setCurrentLoggedUser(user);
        setCurrentLoggedShop(shop);
    }

    public SessionContext(User user) {
        setCurrentLoggedUser(user);
    }

    public User getCurrentLoggedUser() {
        return currentLoggedUser;
    }

    private void setCurrentLoggedUser(User currentLoggedUser) {
        this.currentLoggedUser = currentLoggedUser;
    }

    public Shop getCurrentLoggedShop() {
        return currentLoggedShop;
    }

    public void setCurrentLoggedShop(Shop currentLoggedShop) {
        this.currentLoggedShop = currentLoggedShop;
    }
}
