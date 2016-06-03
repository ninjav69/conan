/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.core.fixtures;

import org.ninjav.conan.core.Context;
import org.ninjav.conan.core.GateKeeper;
import org.ninjav.conan.core.model.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ninjav
 */
public class UserSignIn {

    private GateKeeper gateKeeper;
    
    private String userName;
    private String password;

    public UserSignIn() {
        gateKeeper = new GateKeeper();
    }

    public boolean clearUsers() {
        List<User> users = Context.coreGateway.findAllUsers();
        for (User u : new ArrayList<User>(users)) {
            Context.coreGateway.delete(u);
        }
        return Context.coreGateway.findAllUsers().isEmpty();
    }

    public void updateUsername(String userName) {
        this.userName = userName;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
    
    public boolean resultOfSignin() {
        //SignInUseCase useCase = new SignInUseCase();
        User u = null; // FIX IT UP useCase.signIn(userName, password);
        gateKeeper.setLoggedInUser(u);         
        return gateKeeper.getLoggedInUser() == null ? false : true;
    }
    
    public String loggedInUser() {
        return gateKeeper.getLoggedInUser().getUserName();
    }
}
