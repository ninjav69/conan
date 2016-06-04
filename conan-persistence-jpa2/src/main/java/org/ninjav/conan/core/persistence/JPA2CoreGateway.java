/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.core.persistence;

import org.ninjav.conan.core.model.License;
import org.ninjav.conan.core.model.Module;
import org.ninjav.conan.core.model.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Alan.Pickard
 */
public class JPA2CoreGateway extends JPA2Gateway implements CoreGateway {

    public JPA2CoreGateway(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Module> findAllModules() {
        return entityManager.createQuery("from Module u").getResultList();
    }

    @Override
    public void delete(Module module) {
        Module moduleToDelete = entityManager.find(Module.class, module.getId());
        entityManager.remove(moduleToDelete);
    }

    @Override
    public Module save(Module module) {
        entityManager.persist(module);
        entityManager.refresh(module);
        return module;
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        entityManager.refresh(user);
        return user;
    }

    @Override
    public License save(License license) {
        entityManager.persist(license);
        entityManager.refresh(license);
        return license;
    }

    @Override
    public User findUser(String username) {
        List<User> users = entityManager.createQuery("from User u where u.userName = :userName")
                .setParameter("userName", username)
                .setMaxResults(1)
                .getResultList();

        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Module findModuleByName(String moduleName) {
        List<Module> modules = entityManager.createQuery("from Module m where m.name = :moduleName")
                .setParameter("moduleName", moduleName)
                .setMaxResults(1)
                .getResultList();

        if (modules.size() == 1) {
            return modules.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<License> findLicensesForUserAndModule(User user, Module module) {
        List<License> licences = entityManager
                .createQuery("from License l where l.user.id = :userId and l.module.id = :moduleId")
                .setParameter("userId", user.getId())
                .setParameter("moduleId", module.getId())
                .setMaxResults(1)
                .getResultList();

        return licences;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = entityManager.createQuery("from User u").getResultList();
        return users;
    }

    @Override
    public void delete(User user) {
        User userToDelete = entityManager.find(User.class, user.getId());
        entityManager.remove(userToDelete);
    }

    @Override
    public User findUserByCredentials(String userName, String password) {
        
        List<User> users = entityManager
                .createQuery("from User u where u.userName = :userName and u.password = :password")
                .setParameter("userName", userName)
                .setParameter("password", password)
                .setMaxResults(1)
                .getResultList();
        
        if (users.size() >= 1) {
            return users.get(0);
        } else {
            return null;
        }
    }
}
