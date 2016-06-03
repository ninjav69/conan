package org.ninjav.conan.core.persistence;

import org.ninjav.conan.core.model.User;
import org.ninjav.conan.core.model.Entity;
import org.ninjav.conan.core.model.License;
import org.ninjav.conan.core.model.Module;

import java.util.ArrayList;
import java.util.List;

public class MockCoreGateway implements CoreGateway {

    private List<Module> modules;
    private List<User> users;
    private List<License> licenses;

    private static long nextId = 0;

    public MockCoreGateway() {
        modules = new ArrayList<Module>();
        users = new ArrayList<User>();
        licenses = new ArrayList<License>();
    }

    public List<Module> findAllModules() {
        return modules;
    }

    public void delete(Module module) {
        modules.remove(module);
    }

    public Module save(Module module) {
        modules.add((Module) establishId(module));
        return module;
    }

    public User save(User user) {
        users.add((User) establishId(user));
        return user;
    }

    private Entity establishId(Entity entity) {
        if (entity.getId() == null) {
            entity.setId(nextId++);
        }
        return entity;
    }

    public License save(License license) {
        licenses.add((License) establishId(license));
        return license;
    }

    public User findUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Module findModuleByName(String moduleTitle) {
        for (Module module : modules) {
            if (module.getName().equals(moduleTitle)) {
                return module;
            }
        }
        return null;
    }

    public List<License> findLicensesForUserAndModule(User user, Module module) {
        List<License> results = new ArrayList<License>();
        for (License license : licenses) {
            if (license.getUser().isSame(user) && license.getModule().isSame(module)) {
                results.add(license);
            }
        }
        return results;
    }


    public List<User> findAllUsers() {
        return users;
    }

    public void delete(User user) {
        users.remove(user);
    }

    public User findUserByCredentials(String userName, String password) {
        for (User u : users) {
            System.out.println(" Checking user " + u.getUserName() + " and " + u.getPassword());
            if (u.getUserName().equals(userName) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }


    public void beginTransaction() {
        // Not a transactional resource
    }

    public void commitTransaction() {
        // Not a transactional resource
    }

    public void rollbackTransaction() {
        // Not a transactional resource
    }


}
