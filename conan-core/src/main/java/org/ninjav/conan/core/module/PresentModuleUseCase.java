package org.ninjav.conan.core.module;

import org.ninjav.conan.core.Context;
import org.ninjav.conan.core.model.User;
import org.ninjav.conan.core.model.License;
import org.ninjav.conan.core.model.Module;
import org.ninjav.conan.core.persistence.CoreGateway;

import java.util.ArrayList;
import java.util.List;

public class PresentModuleUseCase implements PresentModulePort {

    @Override
    public List<PresentableModule> presentModules(User loggedInUser) {
        ArrayList<PresentableModule> presentableModules = new ArrayList<>();
        List<Module> allModules = getGateway().findAllModules();
        allModules.stream().map((module) -> {
            PresentableModule cc = new PresentableModule();
            cc.name = module.getName();
            cc.description = module.getDescription();
            cc.isViewable = isLicensedToViewModule(loggedInUser, module);
            return cc;
        }).forEach((cc) -> {
            presentableModules.add(cc);
        });
        return presentableModules;
    }

    public boolean isLicensedToViewModule(User user, Module module) {
        List<License> licenses = getGateway().findLicensesForUserAndModule(user, module);
        boolean licenced = !licenses.isEmpty();
        return licenced;
    }

    private CoreGateway getGateway() {
        return Context.coreGateway;
    }
}
