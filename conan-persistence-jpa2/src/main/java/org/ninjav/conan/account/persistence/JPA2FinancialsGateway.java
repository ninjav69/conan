package org.ninjav.conan.account.persistence;

import org.ninjav.conan.core.persistence.JPA2Gateway;
import org.ninjav.conan.debitorder.model.DebitOrder;
import org.ninjav.conan.debitorder.model.RecoveryWorkflow;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by ninjav on 6/7/16.
 */
public class JPA2FinancialsGateway extends JPA2Gateway implements FinancialsGateway {

    public JPA2FinancialsGateway(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public double findTotalFunds() {
        List<Double> results = entityManager.createQuery(
                "select sum(d.amount) from DebitOrder d", Double.class)
                .getResultList();
        if (results == null || results.isEmpty()) {
            return 0.0;
        } else {
            return safeGetDouble(results);
        }
    }

    @Override
    public double findOwedFunds() {
        List<Double> results = entityManager.createQuery(
                "select sum(d.amount) from DebitOrder d where d.resultCode <> :resultCode", Double.class)
                .setParameter("resultCode", DebitOrder.PAID)
                .getResultList();
        if (results == null || results.isEmpty()) {
            return 0.0;
        } else {
            return safeGetDouble(results);
        }
    }

    @Override
    public double findPaidFunds() {
        List<Double> results =  entityManager.createQuery(
                "select sum(d.amount) from DebitOrder d where d.resultCode = :resultCode", Double.class)
                .setParameter("resultCode", DebitOrder.PAID)
                .getResultList();
        if (results == null || results.isEmpty()) {
            return 0.0;
        } else {
            return safeGetDouble(results);
        }
    }

    @Override
    public double findWrittenOffFunds() {
        List<Double> results = entityManager.createQuery(
                "select sum(r.debitOrder.amount) from RecoveryWorkflow r where r.status = :status", Double.class)
                .setParameter("status", RecoveryWorkflow.Status.WRITTEN_OFF)
                .getResultList();
        if (results == null || results.isEmpty()) {
            return 0.0;
        } else {
            return safeGetDouble(results);
        }
    }

    @Override
    public double findRecoveredFunds() {
        List<Double> results = entityManager.createQuery(
                "select sum(r.debitOrder.amount) from RecoveryWorkflow r where r.status = :status", Double.class)
                .setParameter("status", RecoveryWorkflow.Status.RECOVERED)
                .getResultList();
        if (results == null || results.isEmpty()) {
            return 0.0;
        } else {
            return safeGetDouble(results);
        }
    }

    @Override
    public long findNumberOfAccounts() {
        List<Long> results = entityManager.createQuery(
                "select count(a.reference) from Account a", Long.class)
                .getResultList();
        if (results == null || results.isEmpty()) {
            return 0L;
        } else {
            return safeGetLong(results);
        }
    }

    @Override
    public long findNumberOfAccountsInArrears() {
        List<Long> results = entityManager.createQuery(
                "select count(a.reference) from DebitOrder d join d.account as a where d.resultCode <> :resultCode", Long.class)
                .setParameter("resultCode", DebitOrder.PAID)
                .getResultList();
        if (results == null || results.isEmpty()) {
            return 0L;
        } else {
            return safeGetLong(results);
        }
    }

    @Override
    public long findTotalDebitOrders() {
        List<Long> results = entityManager.createQuery(
                "select count(d.transactionId) from DebitOrder d", Long.class)
                .getResultList();
        if (results == null || results.isEmpty()) {
            return 0L;
        } else {
            return safeGetLong(results);
        }
    }

    @Override
    public long findTotalPaidDebitOrders() {
        List<Long> results = entityManager.createQuery(
                "select count(d.transactionId) from DebitOrder d where d.resultCode = :resultCode", Long.class)
                .setParameter("resultCode", DebitOrder.PAID)
                .getResultList();
        if (results == null || results.isEmpty()) {
            return 0L;
        } else {
            return safeGetLong(results);
        }
    }

    @Override
    public long findTotalUnpaidDebitOrders() {
        List<Long> results = entityManager.createQuery(
                "select count(d.transactionId) from DebitOrder d where d.resultCode <> :resultCode", Long.class)
                .setParameter("resultCode", DebitOrder.PAID)
                .getResultList();
        if (results == null || results.isEmpty()) {
            return 0L;
        } else {
            return safeGetLong(results);
        }
    }

    private Double safeGetDouble(List<Double> results) {
        Double result = results.get(0);
        return result == null ? 0.0 : result;
    }

    private Long safeGetLong(List<Long> results) {
        Long result = results.get(0);
        return result == null ? 0L : result;
    }
}
