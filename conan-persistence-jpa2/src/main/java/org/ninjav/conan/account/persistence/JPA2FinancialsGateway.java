package org.ninjav.conan.account.persistence;

import org.ninjav.conan.core.persistence.JPA2Gateway;
import org.ninjav.conan.debitorder.model.DebitOrder;

import javax.persistence.EntityManager;

/**
 * Created by ninjav on 6/7/16.
 */
public class JPA2FinancialsGateway extends JPA2Gateway implements FinancialsGateway {

    public JPA2FinancialsGateway(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public double findTotalFunds() {
        return entityManager.createQuery("select sum(d.amount) from DebitOrder d", Double.class)
                .getSingleResult();
    }

    @Override
    public double findOwedFunds() {
        return entityManager.createQuery("select sum(d.amount) from DebitOrder d where d.resultCode <> :resultCode", Double.class)
                .setParameter("resultCode", DebitOrder.PAID)
                .getSingleResult();
    }

    @Override
    public double findPaidFunds() {
        return entityManager.createQuery("select sum(d.amount) from DebitOrder d where d.resultCode = :resultCode", Double.class)
                .setParameter("resultCode", DebitOrder.PAID)
                .getSingleResult();
    }

    @Override
    public long findNumberOfAccounts() {
        return entityManager.createQuery(
                "select count(a.reference) from Account a", Long.class)
                .getSingleResult();
    }

    @Override
    public long findNumberOfAccountsInArrears() {
        return entityManager.createQuery(
                "select count(a.reference) from DebitOrder d join d.account as a where d.resultCode <> :resultCode", Long.class)
                .setParameter("resultCode", DebitOrder.PAID)
                .getSingleResult();
    }

    @Override
    public long findTotalDebitOrders() {
        return entityManager.createQuery("select count(d.transactionId) from DebitOrder d", Long.class)
                .getSingleResult();
    }

    @Override
    public long findTotalPaidDebitOrders() {
        return entityManager.createQuery("select count(d.transactionId) from DebitOrder d where d.resultCode = :resultCode", Long.class)
                .setParameter("resultCode", DebitOrder.PAID)
                .getSingleResult();
    }

    @Override
    public long findTotalUnpaidDebitOrders() {
        return entityManager.createQuery("select count(d.transactionId) from DebitOrder d where d.resultCode <> :resultCode", Long.class)
                .setParameter("resultCode", DebitOrder.PAID)
                .getSingleResult();
    }
}
