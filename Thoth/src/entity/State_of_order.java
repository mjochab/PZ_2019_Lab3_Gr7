package entity;

import javax.persistence.*;

@Entity
@Table(name = "state_of_order")
public class State_of_order {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "UserId")
    private int userId;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "OrderId")
    private int orderId;

    @ManyToOne(targetEntity = State.class)
    @JoinColumn(name = "StateId")
    private int stateId;

    public State_of_order(){

    }

    public State_of_order(int userId, int orderId, int stateId) {
        this.userId = userId;
        this.orderId = orderId;
        this.stateId = stateId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    @Override
    public String toString() {
        return "State_of_order{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", stateId=" + stateId +
                '}';
    }
}
