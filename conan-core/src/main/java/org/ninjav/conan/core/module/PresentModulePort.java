/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.core.module;

import org.ninjav.conan.core.model.Module;
import org.ninjav.conan.core.model.User;
import java.util.List;

/**
 *
 * @author ninjav
 */
public interface PresentModulePort {
    List<PresentableModule> presentModules(User loggedInUser);
    boolean isLicensedToViewModule(User user, Module module);
}
