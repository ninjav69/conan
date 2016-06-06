/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.account;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import org.ninjav.conan.account.PresentableAccount;
import org.ninjav.conan.debitorder.PresentableDebitOrder;

/**
 *
 * @author ninjav
 */
public class SwingAccountView extends AccountView {

    private AccountPanel accountPanel;

    public SwingAccountView(AccountPanel accountPanel) {
        this.accountPanel = accountPanel;
        initView();
    }

    private void initView() {
        accountPanel.getAccountSearchButton().addActionListener((ActionEvent e) -> {
            searchAccounts();
        });
        
        // Enable selection
        accountPanel.getAccountSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            if (lsm.isSelectionEmpty()) {
                presenter.clearAccountSelection();

            } else {
                DefaultTableModel model = accountPanel.getAccountsModel();
                List<String> selectedIds = new ArrayList<>();
                for (int i : accountPanel.getSelectedAccountRows()) {
                    selectedIds.add((String) model.getValueAt(i, 0));
                }
                presenter.setAccountSelection(selectedIds);
            }
        });
        
        accountPanel.reset();
    }
    
    @Override
    protected void reset() {
        accountPanel.reset();
    }

    @Override
    protected void clearAccounts() {
        accountPanel.clearAccounts();
    }

    @Override
    protected void appendAccounts(List<PresentableAccount> accounts) {
        accountPanel.addAccounts(accounts);
    }

    private void searchAccounts() {
        String filterText = accountPanel.getAccountSearchField().getText();
        presenter.findAccountsForFilter(filterText);
    }

    @Override
    protected void clearDebitOders() {
        accountPanel.clearDebitOrders();
    }

    @Override
    protected void appendDebitOrders(List<PresentableDebitOrder> debitOrders) {
        accountPanel.addDebitOrders(debitOrders);
    }
}
