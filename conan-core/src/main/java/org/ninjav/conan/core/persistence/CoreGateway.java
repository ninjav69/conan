/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.core.persistence;

import org.ninjav.conan.core.model.License;
import org.ninjav.conan.core.model.Module;
import org.ninjav.conan.core.model.User;

import java.util.List;

/**
 *
 * @author Alan.Pickard
 */
public interface CoreGateway extends Gateway {
    
    List<Module> findAllModules();

    void delete(Module module);

    Module save(Module module);

    User save(User user);

    License save(License license);

    User findUser(String username);

    Module findModuleByName(String moduleName);

    List<License> findLicensesForUserAndModule(User user, Module module);

    List<User> findAllUsers();
    
    void delete(User user);

    User findUserByCredentials(String userName, String password);
}
