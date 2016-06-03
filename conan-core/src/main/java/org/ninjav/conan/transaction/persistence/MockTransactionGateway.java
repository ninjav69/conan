/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction.persistence;

import org.ninjav.conan.transaction.model.BankStmtTx;
import org.ninjav.conan.transaction.model.BankStmtTxReconAudit;
import org.ninjav.conan.transaction.model.BankStmtTxReconAuditStatus;
import org.ninjav.conan.transaction.model.BankStmtTxReconContra;
import org.ninjav.conan.transaction.model.BankStmtTxReconNote;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alan.Pickard
 */
public class MockTransactionGateway implements TransactionGateway {

    private List<BankStmtTx> transactions;
    
    private static long nextId = 0;
    
    public MockTransactionGateway() {
        transactions = new ArrayList<BankStmtTx>();
    }

    public BankStmtTxReconAudit save(BankStmtTxReconAudit audit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(BankStmtTxReconAudit ra) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BankStmtTx findTransactionById(int id) {
        for (BankStmtTx tx : transactions) {
            if (tx.getBankStmtTxpk() == id) {
                return tx;
            }
        }
        return null;
    }

    public List<BankStmtTx> findTransactionsWhereIdIn(List<Integer> ids) {
        List<BankStmtTx> results = new ArrayList<BankStmtTx>();
        for (BankStmtTx tx : transactions) {
            if (ids.contains(tx.getBankStmtTxpk())) {
                results.add(tx);
            }
        }
        return results;
    }

    public BankStmtTxReconAuditStatus findReconAuditStatusByStatusText(String interCompany_Transfer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BankStmtTxReconContra save(BankStmtTxReconContra reconContra) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BankStmtTxReconNote save(BankStmtTxReconNote reconNote) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public BankStmtTx save(BankStmtTx transaction) {
        transaction.setBankStmtTxpk((int) nextId++);
        transactions.add(transaction);
        return transaction;
    }

    public List<BankStmtTx> findAllTransactions() {
        return transactions;
    }
    
    public List<BankStmtTx> findLegacyCreditTransactionsMatchingReference(String filterText) {
        List<BankStmtTx> results = new ArrayList<BankStmtTx>();
        for (BankStmtTx tx : transactions) {
            if (tx.getTransactionReference().contains(filterText) && tx.getTransactionAmount() >= 0) {
                results.add(tx);
            }
        }
        return results;
    }

    public List<BankStmtTx> findLegacyDebitTransactionsMatchingReference(String filterText) {
        List<BankStmtTx> results = new ArrayList<BankStmtTx>();
        for (BankStmtTx tx : transactions) {
            if (tx.getTransactionReference().contains(filterText) && tx.getTransactionAmount() < 0) {
                results.add(tx);
            }
        }
        return results;
    }

    public void delete(BankStmtTx transaction) {
        transactions.remove(transaction);
    }

    public void beginTransaction() {
        // Not a transactional resource
    }

    public void commitTransaction() {
        // Not a transactional resource
    }

    public void rollbackTransaction() {
        // Not a transactional resource
    }
    

}
