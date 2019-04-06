package entity;

import javax.persistence.*;

@Entity
@Table(name = "state_of_order")
public class State_of_order {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private int userId;

    @ManyToOne
    @JoinColumn(name = "OrderId")
    private int orderId;

    @ManyToOne
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

    public int getUserId() {
        return userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getStateId() {
        return stateId;
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
