package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="user")
public class User {

    @Id
    @Column(name = "UserId")
    private int userId;

    @Column(name = "Login")
    private String login;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Password")
    private String password;

    @Column(name = "RoleId")
    private int roleId;

    @Column(name = "State")
    private int status;

    @Column(name = "State")
    private int state;

    @Column(name = "ObjectId")
    private int objectId;

    public User() {
    }

    public User(String login, String firstName, String lastName, String password, int roleId, int state, int objectId) {
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
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
                ", status=" + status +
                '}';
    }
}
