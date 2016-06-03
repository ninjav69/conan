/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction;

import org.ninjav.conan.core.Context;
import org.ninjav.conan.transaction.model.BankStmtTx;
import org.ninjav.conan.transaction.persistence.TransactionGateway;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ninjav
 */
public class PresentDebitTransactionUseCase implements PresentDebitTransactionPort {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private String filterText = "";

    public String getFilterText() {
        return filterText;
    }

    @Override
    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    @Override
    public List<PresentableTransaction> presentLegacyTransactions() {
        getGateway().beginTransaction();

        List<PresentableTransaction> results = new ArrayList<>();
        if (filterText.length() >= 3) {
            List<BankStmtTx> transactions = getGateway().findLegacyDebitTransactionsMatchingReference(filterText);
            transactions.stream().map((tx) -> {
                PresentableTransaction pt = new PresentableTransaction();
                pt.id = tx.getBankStmtTxpk();
                pt.date = dateFormat.format(tx.getTransactionDate());
                pt.reference = tx.getTransactionReference();
                pt.amount = formatAmount(tx.getTransactionAmount());
                return pt;
            }).forEach((pt) -> {
                results.add(pt);
            });
        }

        getGateway().commitTransaction();

        return results;
    }

    private String formatAmount(int transactionAmount) {
        double canonicalAmount = transactionAmount / 100;
        return String.format("%1$,.2f", canonicalAmount);
    }

    private TransactionGateway getGateway() {
        return Context.transactionGateway;
    }
}
