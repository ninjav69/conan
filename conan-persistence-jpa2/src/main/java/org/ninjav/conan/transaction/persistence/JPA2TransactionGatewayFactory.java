/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Alan.Pickard
 */
public class JPA2TransactionGatewayFactory {
    public TransactionGateway createGateway() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        return new JPA2TransactionGateway(emf.createEntityManager());
    }
}
