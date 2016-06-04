/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction.persistence;

import org.ninjav.conan.core.persistence.JPA2Gateway;
import org.ninjav.conan.transaction.model.*;

import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Alan.Pickard
 */
public class JPA2TransactionGateway extends JPA2Gateway implements TransactionGateway {

    public JPA2TransactionGateway(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<BankStmtTx> findAllTransactions() {
        List<BankStmtTx> transactions = entityManager.createQuery("from BankStmtTx t").getResultList();
        return transactions;
    }

    @Override
    public List<BankStmtTx> findLegacyCreditTransactionsMatchingReference(String filterText) {
        List<BankStmtTx> transactions = entityManager
                .createQuery("from BankStmtTx t where t.transactionReference like :filterText and t.transactionAmount >= 0")
                .setParameter("filterText", "%" + filterText + "%")
                .getResultList();

        return transactions;
    }

    @Override
    public List<BankStmtTx> findLegacyDebitTransactionsMatchingReference(String filterText) {
        List<BankStmtTx> transactions = entityManager
                .createQuery("from BankStmtTx t where t.transactionReference like :filterText and t.transactionAmount < 0")
                .setParameter("filterText", "%" + filterText + "%")
                .getResultList();

        return transactions;
    }

    @Override
    public void delete(BankStmtTx transaction) {
        throw new UnsupportedOperationException("Not to be implemented - probably ever.");
    }

    @Override
    public BankStmtTx findTransactionById(int id) {
        return entityManager.find(BankStmtTx.class, id);
    }

    @Override
    public List<BankStmtTx> findTransactionsWhereIdIn(List<Integer> ids) {
        return entityManager.createQuery("from BankStmtTx t where t.bankStmtTxpk in :ids", BankStmtTx.class)
                .setParameter("ids", ids)
                .getResultList();
    }

    @Override
    public BankStmtTxReconAuditStatus findReconAuditStatusByStatusText(String statusText) {
        return entityManager.createQuery(
                "from BankStmtTxReconAuditStatus s where s.status = :statusText", BankStmtTxReconAuditStatus.class)
                .setParameter("statusText", statusText)
                .getSingleResult();
    }

    @Override
    public BankStmtTxReconContra save(BankStmtTxReconContra reconContra) {
        entityManager.persist(reconContra);
        entityManager.refresh(reconContra);
        return reconContra;
    }

    @Override
    public BankStmtTxReconNote save(BankStmtTxReconNote reconNote) {
        entityManager.persist(reconNote);
        entityManager.refresh(reconNote);
        return reconNote;
    }

    @Override
    public BankStmtTxReconAudit save(BankStmtTxReconAudit reconAudit) {
        entityManager.persist(reconAudit);
        entityManager.refresh(reconAudit);
        return reconAudit;
    }

    @Override
    public void update(BankStmtTxReconAudit reconAudit) {
        entityManager.merge(reconAudit);
    }

    @Override
    public BankStmtTx save(BankStmtTx transaction) {
        entityManager.persist(transaction);
        entityManager.refresh(transaction);
        return transaction;
    }

}
