package org.ninjav.conan.core.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@javax.persistence.Entity
@Table(name = "AppUser")
public class User extends Entity {

    @Size(max = 80)
    @Column(nullable = false, unique = true)
    private String userName;

    @Size(max = 30)
    @Column(nullable = false)
    private String password;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
