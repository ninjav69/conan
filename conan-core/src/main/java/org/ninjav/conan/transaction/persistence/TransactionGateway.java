/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction.persistence;

import org.ninjav.conan.core.persistence.Gateway;
import org.ninjav.conan.transaction.model.BankStmtTx;
import org.ninjav.conan.transaction.model.BankStmtTxReconAudit;
import org.ninjav.conan.transaction.model.BankStmtTxReconAuditStatus;
import org.ninjav.conan.transaction.model.BankStmtTxReconContra;
import org.ninjav.conan.transaction.model.BankStmtTxReconNote;

import java.util.List;

/**
 *
 * @author Alan.Pickard
 */
public interface TransactionGateway extends Gateway {
    BankStmtTx save(BankStmtTx transaction);

    void delete(BankStmtTx transaction);
    
    BankStmtTxReconAudit save(BankStmtTxReconAudit audit);

    BankStmtTx findTransactionById(int id);
    
    List<BankStmtTx> findTransactionsWhereIdIn(List<Integer> ids);

    List<BankStmtTx> findAllTransactions();

    List<BankStmtTx> findLegacyCreditTransactionsMatchingReference(String filterText);

    List<BankStmtTx> findLegacyDebitTransactionsMatchingReference(String filterText);

    BankStmtTxReconAuditStatus findReconAuditStatusByStatusText(String interCompany_Transfer);

    BankStmtTxReconContra save(BankStmtTxReconContra reconContra);
    
    BankStmtTxReconNote save(BankStmtTxReconNote reconNote);

    void update(BankStmtTxReconAudit reconAudit);
}
