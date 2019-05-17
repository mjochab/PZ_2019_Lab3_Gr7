package entity;


import javax.persistence.*;

@Entity
@Table(name = "state_on_shop")
public class State_on_shop {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ProductId",nullable = false)
    private Product productId;

    @ManyToOne
    @JoinColumn(name = "ShopId",nullable = false)
    private Shop shopId;

    @Column(name = "Amount",nullable = false)
    private int amount;

    public State_on_shop(){

    }

    public State_on_shop(Product productId, Shop shopId, int amount) {
        this.productId = productId;
        this.shopId = shopId;
        this.amount = amount;
    }

    public State_on_shop(int id, Product productId, Shop shopId, long amount) {
        this.id = id;
        this.productId = productId;
        this.shopId = shopId;
        this.amount = (int) amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Shop getShopId() {
        return shopId;
    }

    public void setShopId(Shop shopId) {
        this.shopId = shopId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "State_on_shop{" +
                "id=" + id +
                ", productId=" + productId +
                ", shopId=" + shopId +
                ", amount=" + amount +
                '}';
    }
}
