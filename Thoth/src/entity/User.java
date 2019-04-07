package entity;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {

    @Id
    @Column(name = "UserId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "Login", unique = true, nullable = false)
    private String login;

    @Column(name = "FirstName",nullable = false)
    private String firstName;

    @Column(name = "LastName",nullable = false)
    private String lastName;

    @Column(name = "Password",nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "RoleId",nullable = false)
    private Role roleId;

    @Column(name = "State",nullable = false)
    private int state;

    @ManyToOne
    @JoinColumn(name = "ObjectId",nullable = false)
    private Shop objectId;

    public User() {
    }

    public User(String login, String firstName, String lastName, String password, Role roleId, int state, Shop objectId) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roleId = roleId;
        this.state = state;
        this.objectId = objectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Shop getObjectId() {
        return objectId;
    }

    public void setObjectId(Shop objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", state=" + state +
                ", objectId=" + objectId +
                '}';
    }
}
