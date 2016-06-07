/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.core;

import java.util.ArrayList;
import org.ninjav.conan.core.model.User;
import org.ninjav.conan.core.module.PresentModulePort;
import org.ninjav.conan.core.module.PresentableModule;
import org.ninjav.conan.core.user.PresentableUser;
import org.ninjav.conan.core.user.SignInPort;
import org.ninjav.conan.core.user.SignOutPort;
import org.ninjav.conan.logger.LogEventUseCase;

import java.util.List;

/**
 *
 * @author ninjav
 */
public class MainPresenter implements ModuleEventSource {

    private MainView view;
    private MainModel model;

    private SignInPort signInPort;
    private SignOutPort signOutPort;
    private PresentModulePort modulesPort;
    
    private List<ModuleEventSink> moduleEventSinks = new ArrayList<>();

    public MainPresenter(MainView view, MainModel model) {
        this.view = view;
        this.model = model;
    }

    public void setSignInPort(SignInPort signInPort) {
        this.signInPort = signInPort;
    }

    public void setSignOutPort(SignOutPort signOutPort) {
        this.signOutPort = signOutPort;
    }

    public void setModulesPort(PresentModulePort modulesPort) {
        this.modulesPort = modulesPort;
    }

    public void userSignedIn(PresentableUser user) {
        if (user != null) {
            unlock(user);
        } else {
            lock();
        }
    }

    public void userSignedOut() {
        lock();
    }

    public void signIn(String userName, String password) {
        PresentableUser loggedInUser = signInPort.signIn(userName, password);
        if (loggedInUser != null) {
            unlock(loggedInUser);
            new LogEventUseCase().logInfo("Sign-in success for user U[" + userName + "]");
        } else {
            new LogEventUseCase().logError("Sign-in failed. Please try again.");
            lock();
        }
    }

    public void signOut() {
        signOutPort.signOut(model.getLoggedInUser());
        lock();
    }

    private void lock() {
        model.setLoggedInUser(null);
        model.setLoggedInUserId(null);
        view.updateWelcomeLabel("Not signed in");
        view.showUserLoginPanel();
        view.updateModules();
        view.reset();
    }

    private void unlock(PresentableUser user) {
        model.setLoggedInUser(user.userName);
        model.setLoggedInUserId(user.userId);
        view.updateWelcomeLabel("Welcome " + model.getLoggedInUser());
        view.resetUserControls();
        view.showUserLogoutPanel();
        view.updateModules();
        view.reset();
    }

    public void getModulesForLoggedInUser() {
        User u = new User();
        u.setUserName(model.getLoggedInUser() != null ? model.getLoggedInUser() : "");
        u.setId(model.getLoggedInUserId() != null ? model.getLoggedInUserId() : 0L);
        List<PresentableModule> modules = modulesPort.presentModules(u);
        view.refreshModules(modules);
    }

    @Override
    public void attach(ModuleEventSink sink) {
        moduleEventSinks.add(sink);
    }

    @Override
    public void detach(ModuleEventSink sink) {
        moduleEventSinks.remove(sink);
    }

    public void changeModuleSelection(String moduleName) {
        for (ModuleEventSink s : moduleEventSinks) {
            s.handleModuleSelect(moduleName);
        }
    }

}
