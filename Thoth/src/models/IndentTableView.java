package models;

import entity.Indent;
import entity.State_of_indent;

public class IndentTableView {
    private Indent order;
    private State_of_indent state;

    public IndentTableView() {
    }

    public Indent getOrder() {
        return order;
    }

    public void setOrder(Indent order) {
        this.order = order;
    }

    public State_of_indent getState() {
        return state;
    }

    public void setState(State_of_indent state) {
        this.state = state;
    }
}
