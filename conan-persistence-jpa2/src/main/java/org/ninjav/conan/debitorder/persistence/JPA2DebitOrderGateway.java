package org.ninjav.conan.debitorder.persistence;

import org.ninjav.conan.core.persistence.JPA2Gateway;
import org.ninjav.conan.debitorder.model.DebitOrder;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by ninjav on 6/4/16.
 */
public class JPA2DebitOrderGateway extends JPA2Gateway implements DebitOrderGateway {

    public JPA2DebitOrderGateway(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public DebitOrder save(DebitOrder debitOrder) {
        entityManager.persist(debitOrder);
        return debitOrder;
    }

    @Override
    public void delete(DebitOrder debitOrder) {
        entityManager.merge(debitOrder);
        entityManager.remove(debitOrder);
    }

    @Override
    public DebitOrder findDebitOrderByTransactionId(Long transactionId) {
        return entityManager.find(DebitOrder.class, transactionId);
    }

    @Override
    public List<DebitOrder> findDebitOrdersWhereTransactionIdIn(List<Long> list) {
        return entityManager.createQuery(
                "select d from DebitOrder d where d.transactionId in :transactionIds", DebitOrder.class)
                .setParameter("transactionIds", list)
                .getResultList();
    }

    @Override
    public List<DebitOrder> findAllDebitOrders() {
        return entityManager.createQuery(
                "select d from DebitOrder d")
                .getResultList();
    }

    @Override
    public List<DebitOrder> findDebitOrdersMatchingAccountReference(String accountReference) {
        return entityManager.createQuery(
                "select d from DebitOrder d where d.account.reference = :accountReference", DebitOrder.class)
                .setParameter("accountReference", accountReference)
                .getResultList();
    }
}
