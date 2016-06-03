/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction.fixtures;

import org.ninjav.conan.transaction.PresentDebitTransactionUseCase;
import org.ninjav.conan.transaction.PresentableTransaction;
import org.ninjav.conan.core.Context;
import org.ninjav.conan.core.persistence.MockCoreGateway;
import org.ninjav.conan.transaction.ReconcileTransactionSelection;
import org.ninjav.conan.transaction.ReconcileTransactionUseCase;
import org.ninjav.conan.transaction.model.BankStmtTx;
import org.ninjav.conan.transaction.persistence.MockTransactionGateway;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ninjav
 */
public class BankAccountTransactionPresentation {

    public static String filterText;

    private ReconcileTransactionSelection transactionSelection = new ReconcileTransactionSelection();
    private PresentDebitTransactionUseCase presentTransactionsUseCase = new PresentDebitTransactionUseCase();
    private ReconcileTransactionUseCase reconcileTransactionsUseCase = new ReconcileTransactionUseCase();

    public BankAccountTransactionPresentation() {
        Context.coreGateway = new MockCoreGateway();
        Context.transactionGateway = new MockTransactionGateway();
    }

    public boolean clearTransactions() {
        List<BankStmtTx> transactions = Context.transactionGateway.findAllTransactions();
        for (BankStmtTx transaction : new ArrayList<>(transactions)) {
            Context.transactionGateway.delete(transaction);
        }
        return Context.transactionGateway.findAllTransactions().isEmpty();
    }

    public boolean clearCreditTransactionSelection() {
        transactionSelection.creditTransactionIds.clear();
        return transactionSelection.creditTransactionIds.isEmpty();
    }

    public boolean clearDebitTransactionSelection() {
        transactionSelection.debitTransactionIds = null;
        return transactionSelection.debitTransactionIds == null;
    }

    public void updateCreditSelection(String id) {
        transactionSelection.creditTransactionIds.add(Integer.parseInt(id));
    }

    public void updateDebitSelection(String id) {
        transactionSelection.debitTransactionIds.add(Integer.parseInt(id));
    }

    public boolean selectionChronological() {
        return reconcileTransactionsUseCase.selectionIsChronological(transactionSelection);
    }
    
    public boolean reconEnabled() {
        return reconcileTransactionsUseCase.reconcileIsPossible(transactionSelection);
    }

    public void updateFilter(String filterText) {
        BankAccountTransactionPresentation.filterText = filterText;
        presentTransactionsUseCase.setFilterText(filterText);
    }

    public String currentFilter() {
        return presentTransactionsUseCase.getFilterText();
    }

    public int countOfTransactionsPresented() {
        List<PresentableTransaction> transactions = presentTransactionsUseCase.presentLegacyTransactions();
        return transactions.size();
    }
    
    public boolean selectionAmountBalances() {
        return reconcileTransactionsUseCase.selectionIsBalancing(transactionSelection);
    }
    
    public int currentBalance() {
        return reconcileTransactionsUseCase.getBalanceForSelection(transactionSelection);
    }
}
