/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.core.fixtures;

import org.ninjav.conan.core.Context;
import org.ninjav.conan.core.model.User;

/**
 *
 * @author ninjav
 */
public class GivenUsers {

    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void execute() {
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        Context.coreGateway.save(user);
    }
}
