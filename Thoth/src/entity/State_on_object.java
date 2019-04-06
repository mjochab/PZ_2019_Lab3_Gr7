package entity;


import javax.persistence.*;

@Entity
@Table(name = "state_on_object")
public class State_on_object {

    @ManyToOne
    @JoinColumn(name = "ProductId")
    private int productId;

    @ManyToOne
    @JoinColumn(name = "ObjectId")
    private int objectId;

    @Column(name = "Amount")
    private int amount;

    public State_on_object(){

    }

    public State_on_object(int productId, int objectId, int amount) {
        this.productId = productId;
        this.objectId = objectId;
        this.amount = amount;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
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
                "productId=" + productId +
                ", objectId=" + objectId +
                ", amount=" + amount +
                '}';
    }
}
