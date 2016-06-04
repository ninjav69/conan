package org.ninjav.conan.debitorder.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by ninjav on 6/5/16.
 */
public class JPA2DebitOrderGatewayFactory implements DebitOrderGatewayFactory {
    @Override
    public DebitOrderGateway createGateway() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-test");
        return new JPA2DebitOrderGateway(emf.createEntityManager());
    }
}
