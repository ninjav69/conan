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


public class MainViewSpy extends MainView {

    private int refreshModulesCalled;
    private int resetUserControlsCalled;
    private int showUserLoginPanelCalled;
    private int showUserLogoutPanelCalled;
    private int updateModulesCalled;
    private int updateWelcomeLabelCalled;
    private int resetCalled;

    public int getRefreshModulesCalled() {
        return refreshModulesCalled;
    }

    public int getResetUserControlsCalled() {
        return resetUserControlsCalled;
    }

    public int getShowUserLoginPanelCalled() {
        return showUserLoginPanelCalled;
    }

    public int getShowUserLogoutPanelCalled() {
        return showUserLogoutPanelCalled;
    }

    public int getUpdateModulesCalled() {
        return updateModulesCalled;
    }

    public int getUpdateWelcomeLabelCalled() {
        return updateWelcomeLabelCalled;
    }

    public int getResetCalled() {
        return resetCalled;
    }

    @Override
    public void refreshModules(List<PresentableModule> modules) {
        refreshModulesCalled++;
    }

    @Override
    public void resetUserControls() {
        resetUserControlsCalled++;
    }

    @Override
    public void showUserLoginPanel() {
        showUserLoginPanelCalled++;
    }

    @Override
    public void showUserLogoutPanel() {
        showUserLogoutPanelCalled++;
    }

    @Override
    public void updateModules() {
        updateModulesCalled++;
    }

    @Override
    public void updateWelcomeLabel(String string) {
        updateWelcomeLabelCalled++;
    }

    @Override
    public void reset() {
        resetCalled++;
    }

}
