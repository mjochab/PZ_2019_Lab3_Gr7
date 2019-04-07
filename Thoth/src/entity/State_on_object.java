package entity;


import javax.persistence.*;

@Entity
@Table(name = "state_on_object")
public class State_on_object {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ProductId",nullable = false)
    private Product productId;

    @ManyToOne
    @JoinColumn(name = "ObjectId",nullable = false)
    private Shop objectId;

    @Column(name = "Amount",nullable = false)
    private int amount;

    public State_on_object(){

    }

    public State_on_object(Product productId, Shop objectId, int amount) {
        this.productId = productId;
        this.objectId = objectId;
        this.amount = amount;
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

    public Shop getObjectId() {
        return objectId;
    }

    public void setObjectId(Shop objectId) {
        this.objectId = objectId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "State_on_object{" +
                "id=" + id +
                ", productId=" + productId +
                ", objectId=" + objectId +
                ", amount=" + amount +
                '}';
    }
}
