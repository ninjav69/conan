package org.ninjav.conan.debitorder.persistence;

import org.ninjav.conan.core.persistence.JPA2Gateway;
import org.ninjav.conan.debitorder.model.RecoveryWorkflow;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by ninjav on 6/9/16.
 */
public class JPA2RecoveryWorkflowGateway extends JPA2Gateway implements RecoveryWorkflowGateway {
    public JPA2RecoveryWorkflowGateway(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public RecoveryWorkflow save(RecoveryWorkflow recoveryWorkflow) {
        entityManager.persist(recoveryWorkflow);
        return recoveryWorkflow;
    }

    @Override
    public void delete(RecoveryWorkflow recoveryWorkflow) {
        entityManager.merge(recoveryWorkflow);
        entityManager.remove(recoveryWorkflow);
    }

    @Override
    public RecoveryWorkflow findRecoveryByDebitOrderTransactionId(Long transactionId) {
        RecoveryWorkflow result = entityManager.find(RecoveryWorkflow.class, transactionId);
        if (result != null) {
            result.getDebitOrder();
        }
        return result;
    }

    @Override
    public List<RecoveryWorkflow> findRecoveryWhereDebitOrderTransactionIdIn(List<Long> transactionIds) {
        return entityManager.createQuery(
                "select r from RecoveryWorkflow r join fetch r.debitOrder where r.debitOrder.transactionId in :transactionIds", RecoveryWorkflow.class)
                .setParameter("transactionIds", transactionIds)
                .getResultList();
    }

    @Override
    public List<RecoveryWorkflow> findAllRecoveries() {
        return entityManager.createQuery(
                "select r from RecoveryWorkflow r join fetch r.debitOrder", RecoveryWorkflow.class)
                .getResultList();
    }

    @Override
    public List<RecoveryWorkflow> findRecoveriesByStatus(RecoveryWorkflow.Status status) {
        return entityManager.createQuery(
                "select r from RecoveryWorkflow r join fetch r.debitOrder where r.status = :status", RecoveryWorkflow.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public List<RecoveryWorkflow> findRecoveriesWhereStatusIn(List<RecoveryWorkflow.Status> statuses) {
        return entityManager.createQuery(
                "select r from RecoveryWorkflow r join fetch r.debitOrder where r.status in :statuses", RecoveryWorkflow.class)
                .setParameter("statuses", statuses)
                .getResultList();
    }
}
