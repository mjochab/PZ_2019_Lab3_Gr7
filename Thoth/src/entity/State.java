package entity;

import javax.persistence.*;

@Entity
@Table(name = "state")
public class State {

    @Id
    @Column(name = "StateId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int stateId;

    @Column(name = "Name",nullable = true)
    private String name;
}
