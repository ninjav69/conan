package org.ninjav.conan.account.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by ninjav on 6/5/16.
 */
public class JPA2AccountGatewayFactory implements AccountGatewayFactory {
    @Override
    public AccountGateway createGateway() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-test");
        return new JPA2AccountGateway(emf.createEntityManager());
    }
}
