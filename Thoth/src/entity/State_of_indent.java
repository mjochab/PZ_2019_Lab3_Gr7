package entity;

import javax.persistence.*;

@Entity
@Table(name = "state_of_indent")
public class State_of_indent {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "UserId",nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "IndentId",nullable = false)
    private Indent indentId;

    @ManyToOne
    @JoinColumn(name = "StateId",nullable = false)
    private State stateId;

    public State_of_indent(){

    }

    public State_of_indent(User userId, Indent indentId, State stateId) {
        this.userId = userId;
        this.indentId = indentId;
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

    public Indent getIndentId() {
        return indentId;
    }

    public void setIndentId(Indent indentId) {
        this.indentId = indentId;
    }

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
    }
}
