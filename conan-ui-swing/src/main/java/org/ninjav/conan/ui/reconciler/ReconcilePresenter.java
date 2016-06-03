/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.reconciler;

import org.ninjav.conan.ui.core.MainModel;
import org.ninjav.conan.logger.LogEventPort;
import org.ninjav.conan.logger.LogEventUseCase;
import org.ninjav.conan.transaction.*;

import java.util.List;

/**
 *
 * @author ninjav
 */
public class ReconcilePresenter implements ReconcileTransactionListener {

    private ReconcileView view;

    private PresentCreditTransactionPort creditsPort;
    private PresentDebitTransactionPort debitsPort;
    private ReconcileTransactionPort reconcilePort;

    private ReconcileTransactionSelection selection;
    private String notes = "";

    private LogEventPort logger;

    public ReconcilePresenter(ReconcileView view) {
        this.view = view;
        selection = new ReconcileTransactionSelection();
    }

    public void setCreditsPort(PresentCreditTransactionPort creditsPort) {
        this.creditsPort = creditsPort;
    }

    public void setDebitsPort(PresentDebitTransactionPort debitsPort) {
        this.debitsPort = debitsPort;
    }

    public void setReconcilePort(ReconcileTransactionPort reconcilePort) {
        this.reconcilePort = reconcilePort;
    }

    public void setCreditSelection(List<Integer> transactionIds) {
        try {
            selection.creditTransactionIds.clear();
            selection.creditTransactionIds.addAll(transactionIds);
            int amount = reconcilePort.getTotalCreditsForSelection(selection);
            view.updateSelectedCreditsLabel(formatRandAmount(amount));
        } catch (Exception e) {
            new LogEventUseCase().logError("Unable to select transactions: "
                    + idsToString(transactionIds) + ": " + e.getMessage());
        }
        checkSelectionIsValid();
    }

    public void setDebitSelection(List<Integer> transactionIds) {
        try {
            selection.debitTransactionIds.clear();
            selection.debitTransactionIds.addAll(transactionIds);
            int amount = reconcilePort.getTotalDebitsForSelection(selection);
            view.updateSelectedDebitsLabel(formatRandAmount(amount));
        } catch (Exception e) {
            new LogEventUseCase().logError("Unable to select transaction: "
                    + idsToString(transactionIds) + ": " + e.getMessage());
        }
        checkSelectionIsValid();
    }

    private String idsToString(List<Integer> transactionIds) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < transactionIds.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(transactionIds.get(i));
        }
        return sb.toString();
    }

    public void setNotes(String text) {
        this.notes = text;
        checkSelectionIsValid();
    }

    void findCreditTransactionsForFilter(List<String> filterTextList) {
        new LogEventUseCase().logInfo("Searching for credit transactions...");

        view.clearCreditTransactions();
        for (String filterText : filterTextList) {
            creditsPort.setFilterText(filterText);
            List<PresentableTransaction> transactions = creditsPort.presentLegacyTransactions();
            if (transactions != null && !transactions.isEmpty()) {
                new LogEventUseCase().logInfo(transactions.size() + " results found for filter F[" + filterText + "]");
                view.appendCreditTransactions(transactions);
            } else {
                new LogEventUseCase().logError("No credit transactions found for filter F[" + filterText + "]");
            }
        }
    }

    void findDebitTransactionsForFilter(List<String> filterTextList) {
        new LogEventUseCase().logInfo("Searching for debit transactions...");

        view.clearDebitTransactions();
        for (String filterText : filterTextList) {
            debitsPort.setFilterText(filterText);
            List<PresentableTransaction> transactions = debitsPort.presentLegacyTransactions();
            if (transactions != null && !transactions.isEmpty()) {
                view.appendDebitTransactions(transactions);
                new LogEventUseCase().logInfo(transactions.size() + " results found for filter F[" + filterText + "]");
            } else {
                new LogEventUseCase().logError("No debit transactions found for filter F[" + filterText + "]");
            }
        }
    }

    void clearCreditSelection() {
        selection.creditTransactionIds.clear();
    }

    void clearDebitSelection() {
        selection.debitTransactionIds.clear();
    }

    private String formatRandAmount(Integer amount) {
        return String.format("R %1$,.2f", toRand(amount));
    }

    private double toRand(int amount) {
        return amount / 100;
    }

    private void checkSelectionIsValid() {
        checkSelectionBalanceIsValid();
        checkSelectionChronologyIsValid();
        checkSelectionIsReconcilable();
    }

    private void checkSelectionIsReconcilable() {
        try {
            if (selectionIsIncomplete()) {
                return;
            }
            boolean canReconcile = reconcilePort.reconcileIsPossible(selection);
            if (canReconcile && !notes.isEmpty()) {
                view.enableReconcileButton();
            } else {
                view.disableReconcileButton();
            }
        } catch (Exception e) {
            new LogEventUseCase().logError(
                    "Unable to determine if reconciliation is possible: " + e.getMessage());
        }
    }

    private void checkSelectionChronologyIsValid() {
        try {
            if (selectionIsIncomplete()) {
                return;
            }
            boolean isChronological = reconcilePort.selectionIsChronological(selection);
            view.updateSelectedIsChronological(isChronological ? "YES" : "NO");
        } catch (Exception e) {
            new LogEventUseCase().logError(
                    "Unable to determine if selection is chronological: " + e.getMessage());
        }
    }

    private void checkSelectionBalanceIsValid() {
        try {
            if (selectionIsIncomplete()) {
                return;
            }
            int balance = reconcilePort.getBalanceForSelection(selection);
            view.updateSelectedBalanceLabel(formatRandAmount(balance));
        } catch (Exception e) {
            new LogEventUseCase().logError(
                    "Unable to get balance for selection: " + e.getMessage());
        }
    }

    private boolean selectionIsIncomplete() {
        return selection.creditTransactionIds.isEmpty() || selection.debitTransactionIds.isEmpty();
    }

    public void reconcileSelection() {
        try {
            String userName = new MainModel().getLoggedInUser();
            reconcilePort.reconcileSelection(userName, notes, selection);
            view.reset();

        } catch (Exception e) {
            new LogEventUseCase().logError(
                    "Unable to reconcile selection: " + e.getMessage());
        }
    }

    @Override
    public void onReconcileEvent(ReconcileEvent e) {
        new LogEventUseCase().logInfo(e.message);
    }

}
