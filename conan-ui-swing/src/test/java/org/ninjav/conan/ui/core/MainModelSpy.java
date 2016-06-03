/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.core;

import org.ninjav.conan.core.module.PresentableModule;

import java.util.List;

/**
 *
 * @author Alan.Pickard
 */
public class MainModelSpy extends MainModel {

    private int setModulesCalled;
    private int setLoggedInUserIdCalled;
    private int setLoggedInUserCalled;

    public int getSetModulesCalled() {
        return setModulesCalled;
    }

    public int getSetLoggedInUserIdCalled() {
        return setLoggedInUserIdCalled;
    }

    public int getSetLoggedInUserCalled() {
        return setLoggedInUserCalled;
    }

    @Override
    void setModules(List<PresentableModule> modules) {
        setModulesCalled++;
        super.setModules(modules); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLoggedInUserId(Long loggedInUserId) {
        setLoggedInUserIdCalled++;
        super.setLoggedInUserId(loggedInUserId); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLoggedInUser(String loggedInUser) {
        setLoggedInUserCalled++;
        super.setLoggedInUser(loggedInUser); //To change body of generated methods, choose Tools | Templates.
    }

}
