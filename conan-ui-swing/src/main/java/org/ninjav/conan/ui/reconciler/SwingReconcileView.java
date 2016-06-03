/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.reconciler;

import org.ninjav.conan.transaction.PresentableTransaction;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ninjav
 */
public class SwingReconcileView extends ReconcileView {

    private ReconcilePanel reconcilePanel;

    public SwingReconcileView(ReconcilePanel reconcilePanel) {
        this.reconcilePanel = reconcilePanel;
        initView();
    }

    public void initView() {
        reconcilePanel.getCreditSearchButton().addActionListener((ActionEvent e) -> {
            searchCredits();
        });
        reconcilePanel.getDebitSearchButton().addActionListener((ActionEvent e) -> {
            searchDebits();
        });

        reconcilePanel.getCreditSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                return;
            }

            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            if (lsm.isSelectionEmpty()) {
                presenter.clearCreditSelection();

            } else {
                DefaultTableModel model = reconcilePanel.getCreditsTransactionModel();
                List<Integer> selectedIds = new ArrayList<>();
                for (int i : reconcilePanel.getSelectedCreditTransactionRows()) {
                    selectedIds.add((Integer) model.getValueAt(i, 0));
                }
                presenter.setCreditSelection(selectedIds);
            }
        });

        reconcilePanel.getDebitSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                return;
            }

            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            if (lsm.isSelectionEmpty()) {
                presenter.clearDebitSelection();

            } else {
                DefaultTableModel model = reconcilePanel.getDebitsTransactionModel();
                List<Integer> selectedIds = new ArrayList<>();
                for (int i : reconcilePanel.getSelectedDebitTransactionRows()) {
                    selectedIds.add((Integer) model.getValueAt(i, 0));
                }
                presenter.setDebitSelection(selectedIds);
            }
        });

        reconcilePanel.getNotesField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                presenter.setNotes(reconcilePanel.getNotesField().getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                presenter.setNotes(reconcilePanel.getNotesField().getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                presenter.setNotes(reconcilePanel.getNotesField().getText());
            }
        });

        reconcilePanel.getReconcileButton().addActionListener((ActionEvent e) -> {
            presenter.reconcileSelection();
        });

        reconcilePanel.reset();
    }

    @Override
    protected void reset() {
        reconcilePanel.reset();
    }

    private void searchDebits() {
        String filterText = reconcilePanel.getDebitSearchForText();
        presenter.clearDebitSelection();
        presenter.findDebitTransactionsForFilter(extractIndividualFiltersFrom(filterText));
    }

    private void searchCredits() {
        String filterText = reconcilePanel.getCreditSearchForText();
        presenter.clearCreditSelection();
        presenter.findCreditTransactionsForFilter(extractIndividualFiltersFrom(filterText));
    }
    
    private List<String> extractIndividualFiltersFrom(String filterText) {
        List<String> results = new ArrayList<>();
        String[] filters = filterText.split("\\n");
        for (String f : filters) {
            if (!f.isEmpty()) {
                results.add(f);
            }
        }
        return results;
    }

    public void setReconcilePanel(ReconcilePanel reconcilePanel) {
        this.reconcilePanel = reconcilePanel;
    }

    @Override
    public void appendCreditTransactions(List<PresentableTransaction> transactions) {
        reconcilePanel.addCreditTransactions(transactions);
    }

    @Override
    public void appendDebitTransactions(List<PresentableTransaction> transactions) {
        reconcilePanel.addDebitTransactions(transactions);
    }

    @Override
    protected void clearCreditTransactions() {
        reconcilePanel.clearCreditTransactions();
    }

    @Override
    protected void clearDebitTransactions() {
        reconcilePanel.clearDebitTransactions();
    }

    @Override
    public void updateSelectedCreditsLabel(String amount) {
        reconcilePanel.updateCreditsLabel(amount);
    }

    @Override
    public void updateSelectedDebitsLabel(String amount) {
        reconcilePanel.updateDebitsLabel(amount);
    }

    @Override
    public void updateSelectedBalanceLabel(String amountBalance) {
        reconcilePanel.updateBalanceLabel(amountBalance);
    }

    @Override
    protected void updateSelectedIsChronological(String chronological) {
        reconcilePanel.updateChronologicalLabel(chronological);
    }

    @Override
    protected void enableReconcileButton() {
        reconcilePanel.getReconcileButton().setEnabled(true);
    }

    @Override
    protected void disableReconcileButton() {
        reconcilePanel.getReconcileButton().setEnabled(false);
    }
}
