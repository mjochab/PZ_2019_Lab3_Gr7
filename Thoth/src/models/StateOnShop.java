package models;

import entity.Product;
import entity.Shop;
import entity.State_on_shop;

public class StateOnShop {
    private State_on_shop stateOnShop;
    private int amount;
    public StateOnShop() {
    }

    public StateOnShop(State_on_shop stateOnShop) {
        this.stateOnShop = stateOnShop;
        this.amount = 1;
    }

    public StateOnShop(State_on_shop stateOnShop, int amount) {
        this.stateOnShop = stateOnShop;
        this.amount = amount;
    }

    public State_on_shop getStateOnShop() {
        return stateOnShop;
    }

    public void setStateOnShop(State_on_shop stateOnShop) {
        this.stateOnShop = stateOnShop;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
