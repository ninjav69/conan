/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.core.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Alan.Pickard
 */
public class JPA2Gateway implements Gateway {

    protected EntityManager entityManager;

    public JPA2Gateway(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void beginTransaction() {
        if (entityManager != null && !entityManager.getTransaction().isActive()) 
            entityManager.getTransaction().begin();
    }
    
    public void commitTransaction() {
        if (entityManager != null && entityManager.getTransaction().isActive())
            entityManager.getTransaction().commit();
    }

    public void rollbackTransaction() {
        if (entityManager != null) {
            entityManager.getTransaction().rollback();
        }
    }
}
