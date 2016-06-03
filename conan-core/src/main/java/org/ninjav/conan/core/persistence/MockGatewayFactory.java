/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.core.persistence;

import org.ninjav.conan.core.model.License;
import org.ninjav.conan.core.model.Module;
import org.ninjav.conan.core.model.User;

/**
 *
 * @author Alan.Pickard
 */
public class MockGatewayFactory implements CoreGatewayFactory {

    public CoreGateway createGateway() {
        MockCoreGateway gw = new MockCoreGateway();

        User u = gw.save(createUser("alan.pickard", "secret"));
        Module m = gw.save(createModule("Reconciler", "Reconciles legacy transactions"));
        gw.save(createLicence(u, m));
        return gw;
    }

    private User createUser(String username, String secret) {
        User u = new User();
        u.setUserName(username);
        u.setPassword(secret);
        return u;
    }

    private Module createModule(String name, String description) {
        Module m = new Module();
        m.setName(name);
        m.setDescription(description);
        return m;
    }

    private License createLicence(User u, Module m) {
        License l = new License(u, m);
        return l;
    }
}
