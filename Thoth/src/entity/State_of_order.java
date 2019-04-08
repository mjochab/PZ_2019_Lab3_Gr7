package entity;

import javax.persistence.*;

@Entity
@Table(name = "state_of_order")
public class State_of_order {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "UserId",nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "OrderId",nullable = false)
    private Order orderId;

    @ManyToOne
    @JoinColumn(name = "StateId",nullable = false)
    private State stateId;

    public State_of_order(){

    }

    public State_of_order(User userId, Order orderId, State stateId) {
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
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
