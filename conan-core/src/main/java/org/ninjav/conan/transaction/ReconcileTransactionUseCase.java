/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction;

import org.ninjav.conan.core.Context;
import org.ninjav.conan.transaction.model.BankStmtTx;
import org.ninjav.conan.transaction.model.BankStmtTxRecon;
import org.ninjav.conan.transaction.model.BankStmtTxReconAudit;
import org.ninjav.conan.transaction.model.BankStmtTxReconAuditStatus;
import org.ninjav.conan.transaction.model.BankStmtTxReconContra;
import org.ninjav.conan.transaction.model.BankStmtTxReconNote;
import org.ninjav.conan.transaction.persistence.TransactionGateway;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Alan.Pickard
 */
public class ReconcileTransactionUseCase implements ReconcileTransactionPort {

    private final List<ReconcileTransactionListener> listeners = new ArrayList<>();

    @Override
    public void addListener(ReconcileTransactionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ReconcileTransactionListener listener) {
        listeners.remove(listener);
    }

    public void notify(ReconcileEvent event) {
        listeners.stream().forEach((l) -> {
            l.onReconcileEvent(event);
        });
    }

    private static boolean selectionIsComplete(ReconcileTransactionSelection selection) {
        return selectionIsMade(selection.creditTransactionIds)
                && selectionIsMade(selection.debitTransactionIds);
    }

    private static boolean selectionIsMade(List<Integer> selection) {
        return selection != null && !selection.isEmpty();
    }

    @Override
    public boolean selectionIsChronological(ReconcileTransactionSelection selection) {
        if (selectionIsComplete(selection)) {

            getGateway().beginTransaction();

            List<BankStmtTx> creditTransactions = getGateway().findTransactionsWhereIdIn(selection.creditTransactionIds);
            if (creditTransactions == null || creditTransactions.isEmpty()) {
                return false;
            }

            List<BankStmtTx> debitTransactions = getGateway().findTransactionsWhereIdIn(selection.debitTransactionIds);
            if (debitTransactions == null || debitTransactions.isEmpty()) {
                return false;
            }

            boolean isChronological = datesAreSameOrBefore(creditTransactions, debitTransactions);

            getGateway().commitTransaction();

            return isChronological;

        } else {
            return false;
        }
    }

    private boolean datesAreSameOrBefore(List<BankStmtTx> creditTransactions, List<BankStmtTx> debitTransactions) {
        for (BankStmtTx creditTransaction : creditTransactions) {
            for (BankStmtTx debitTransaction : debitTransactions) {
                if (!dateIsSameOrBefore(
                        creditTransaction.getTransactionDate(),
                        debitTransaction.getTransactionDate())) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean dateIsSameOrBefore(Date currentDate, Date comparedToDate) {
        if (currentDate.equals(comparedToDate)) {
            return true;
        } else if (currentDate.before(comparedToDate)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean selectionIsBalancing(ReconcileTransactionSelection selection) {
        if (selectionIsComplete(selection)) {
            return getBalanceForSelection(selection) == 0;
        } else {
            return false;
        }
    }

    @Override
    public int getBalanceForSelection(ReconcileTransactionSelection selection) {
        if (selectionIsComplete(selection)) {

            getGateway().beginTransaction();

            int creditsTotal = getTotalCreditsForSelection(selection);
            int debitsTotal = getTotalDebitsForSelection(selection);
            int balance = creditsTotal + debitsTotal;

            getGateway().commitTransaction();

            return balance;
        }
        throw new TransactionSelectionInvalid();
    }

    @Override
    public boolean reconcileIsPossible(ReconcileTransactionSelection selection) {
        return selectionIsComplete(selection)
                && selectionIsChronological(selection)
                && selectionIsBalancing(selection);
    }

    @Override
    public int getTotalCreditsForSelection(ReconcileTransactionSelection selection) {
        if (selectionIsMade(selection.creditTransactionIds)) {

            return getTransactionTotalAmount(selection.creditTransactionIds);

        } else {
            throw new TransactionSelectionInvalid();
        }
    }

    @Override
    public int getTotalDebitsForSelection(ReconcileTransactionSelection selection) {
        if (selectionIsMade(selection.debitTransactionIds)) {

            return getTransactionTotalAmount(selection.debitTransactionIds);

        } else {
            throw new TransactionSelectionInvalid();
        }
    }

    private int getTransactionTotalAmount(List<Integer> transactionIds) {
        getGateway().beginTransaction();

        List<BankStmtTx> transactions = getGateway().findTransactionsWhereIdIn(transactionIds);
        int amount = 0;
        amount = transactions.stream().map((t) -> t.getTransactionAmount()).reduce(amount, Integer::sum);

        getGateway().commitTransaction();

        return amount;
    }

    @Override
    public void reconcileSelection(String userName, String note, ReconcileTransactionSelection selection) {
        if (reconcileIsPossible(selection)) {

            getGateway().beginTransaction();

            // Find the new value for the reconstatus
            BankStmtTxReconAuditStatus newStatus = getGateway().findReconAuditStatusByStatusText("Inter-Company Transfer");

            // Find the transaction entries
            List<BankStmtTx> creditEntries = getGateway().findTransactionsWhereIdIn(selection.creditTransactionIds);
            List<BankStmtTx> debitEntries = getGateway().findTransactionsWhereIdIn(selection.debitTransactionIds);

            notify(new ReconcileEvent("Starting recon between statement CID["
                    + getTransactionIdsAsString(creditEntries)
                    + "] and DID["
                    + getTransactionIdsAsString(debitEntries)
                    + "]"));

            // Update audit status
            creditEntries.stream().forEach((t) -> {
                updateAuditStatus(t, newStatus);
            });

            debitEntries.stream().forEach((t) -> {
                updateAuditStatus(t, newStatus);
            });

            // Insert recon contra entry for each credit/debit pair (on all recon entries anyways)
            for (BankStmtTx c : creditEntries) {
                for (BankStmtTx d : debitEntries) {
                    joinReconsWithContraEntries(c.getBankStmtTxReconSet(),
                            d.getBankStmtTxReconSet(), userName);
                }
            }

            // Insert recon note entry for each recon entry (Both legs?)
            if (note != null && !note.isEmpty()) {
                for (BankStmtTx t : creditEntries) {
                    addNoteToReconEntries(t.getBankStmtTxReconSet(), note, userName);
                }
                for (BankStmtTx t : debitEntries) {
                    addNoteToReconEntries(t.getBankStmtTxReconSet(), note, userName);
                }

            } else {
                notify(new ReconcileEvent("No note provided this time. No note created."));
            }

            getGateway().commitTransaction();

            notify(new ReconcileEvent("Recon between statement CID["
                    + getTransactionIdsAsString(creditEntries)
                    + "] and DID["
                    + getTransactionIdsAsString(debitEntries)
                    + "] completed."));

        } else {
            throw new ReconcileNotPossible();
        }
    }

    // NOTE: Needs a transaction!
    private void updateAuditStatus(BankStmtTx transaction, BankStmtTxReconAuditStatus newStatus) {
        // Update audit status to new status
        for (BankStmtTxRecon r : transaction.getBankStmtTxReconSet()) {
            for (BankStmtTxReconAudit ra : r.getBankStmtTxReconAuditSet()) {
                notify(new ReconcileEvent("Updating audit status of credit recon audit ID["
                        + ra.getBankStmtTxReconAuditpk() + "] to ST[" + newStatus.getStatus()
                        + "]  STID[" + newStatus.getBankStmtTxReconAuditStatuspk() + "]"));
                ra.setBankStmtTxReconAuditStatusfk(newStatus);
                getGateway().update(ra);
            }
        }
    }

    private String getTransactionIdsAsString(List<BankStmtTx> creditEntries) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < creditEntries.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(creditEntries.get(i).getBankStmtTxpk());
        }
        return sb.toString();
    }

    // NOTE: Needs a transaction!
    private void joinReconsWithContraEntries(
            Set<BankStmtTxRecon> creditReconSet, Set<BankStmtTxRecon> debitReconSet, String userName) {

        for (BankStmtTxRecon cr : creditReconSet) {
            for (BankStmtTxRecon dr : debitReconSet) {

                notify(new ReconcileEvent("Adding recon contra entry between recon CID["
                        + cr.getBankStmtTxReconpk() + "] and DID[" + dr.getBankStmtTxReconpk() + "]"));

                BankStmtTxReconContra contraEntry = new BankStmtTxReconContra();
                contraEntry.setAuditDate(new Date());
                contraEntry.setUserId(userName);
                contraEntry.setBankStmtTxReconfk(dr);
                contraEntry.setOrigBankStmtTxReconToContrafk(cr);
                cr.getBankStmtTxReconContraSet().add(contraEntry);
                dr.getBankStmtTxReconContraSet().add(contraEntry);
                getGateway().save(contraEntry);
            }
        }
    }

    // NOTE: Needs a transaction!
    private void addNoteToReconEntries(Set<BankStmtTxRecon> reconSet, String note, String userName) {
        for (BankStmtTxRecon r : reconSet) {

            notify(new ReconcileEvent("Adding recon note to recon ID["
                    + r.getBankStmtTxReconpk() + "]"));

            BankStmtTxReconNote newNote = new BankStmtTxReconNote();
            newNote.setAuditDate(new Date());
            newNote.setBankStmtTxReconfk(r);
            newNote.setUserId(userName);
            newNote.setNote(note);
            getGateway().save(newNote);
        }
    }

    private TransactionGateway getGateway() {
        return Context.transactionGateway;
    }

    public static class TransactionNotFound extends RuntimeException {

        public TransactionNotFound(String message) {
            super(message);
        }

    }

    public static class TransactionSelectionInvalid extends RuntimeException {

    }

    public static class ReconcileNotPossible extends RuntimeException {

    }
}
