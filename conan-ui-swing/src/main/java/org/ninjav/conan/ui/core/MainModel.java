/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.core;

import org.ninjav.conan.core.module.PresentableModule;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ninjav
 */
public class MainModel {

    private static Long loggedInUserId;
    private static String loggedInUser;
    
    private static List<PresentableModule> modules = new ArrayList<>();
    
    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        MainModel.loggedInUser = loggedInUser;
    }

    public Long getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(Long loggedInUserId) {
        MainModel.loggedInUserId = loggedInUserId;
    }

    void setModules(List<PresentableModule> modules) {
        MainModel.modules = modules;
    }
}
