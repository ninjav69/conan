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
public abstract class MainView {

    protected MainPresenter presenter;
    
    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    public abstract void reset();

    public abstract void refreshModules(List<PresentableModule> modules);

    public abstract void resetUserControls();

    public abstract void showUserLoginPanel();

    public abstract void showUserLogoutPanel();

    public abstract void updateModules();

    public abstract void updateWelcomeLabel(String string);

}
