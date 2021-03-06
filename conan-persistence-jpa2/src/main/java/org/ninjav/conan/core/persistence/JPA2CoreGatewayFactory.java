/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.core.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Alan.Pickard
 */
public class JPA2CoreGatewayFactory implements CoreGatewayFactory {

    public CoreGateway createGateway() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        return new JPA2CoreGateway(emf.createEntityManager());
    }
}
