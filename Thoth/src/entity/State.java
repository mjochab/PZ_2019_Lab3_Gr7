package entity;

import javax.persistence.*;

@Entity
@Table(name = "state")
public class State {

    @Id
    @Column(name = "StateId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stateId;

    @Column(name = "Name")
    private String name;

    public State(){

    }

    public State(String name) {
        this.name = name;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "State{" +
                "stateId=" + stateId +
                ", name='" + name + '\'' +
                '}';
    }
}
