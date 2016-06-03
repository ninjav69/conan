/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.core.fixtures;

import org.ninjav.conan.core.Context;
import org.ninjav.conan.core.GateKeeper;
import org.ninjav.conan.core.model.License;
import org.ninjav.conan.core.persistence.MockCoreGateway;
import org.ninjav.conan.core.model.Module;
import org.ninjav.conan.core.module.PresentModuleUseCase;
import org.ninjav.conan.core.module.PresentableModule;
import org.ninjav.conan.core.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alan.Pickard
 */
public class ModulePresentation {

    private PresentModuleUseCase useCase = new PresentModuleUseCase();
    public static GateKeeper gateKeeper = new GateKeeper();

    public ModulePresentation() {
        Context.coreGateway = new MockCoreGateway();
    }

    public boolean addUser(String username) {
        Context.coreGateway.save(new User(username));
        return true;
    }

    public boolean loginUser(String username) {
        User user = Context.coreGateway.findUser(username);
        if (user != null) {
            gateKeeper.setLoggedInUser(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean createLicenseForViewing(String username, String moduleTitle) {
        User user = Context.coreGateway.findUser(username);
        Module module = Context.coreGateway.findModuleByName(moduleTitle);
        License license = new License(user, module);
        Context.coreGateway.save(license);
        return useCase.isLicensedToViewModule(user, module);
    }

    public String presentationUser() {
        return gateKeeper.getLoggedInUser().getUserName();
    }

    public boolean clearModules() {
        List<Module> modules = Context.coreGateway.findAllModules();
        for (Module module : new ArrayList<Module>(modules)) {
            Context.coreGateway.delete(module);
        }
        return Context.coreGateway.findAllModules().isEmpty();
    }

    public int countOfModulesPresented() {
        List<PresentableModule> presentations = useCase.presentModules(gateKeeper.getLoggedInUser());
        return presentations.size();
    }
}
