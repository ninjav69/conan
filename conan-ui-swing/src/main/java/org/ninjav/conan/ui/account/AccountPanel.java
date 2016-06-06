/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.account;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.ninjav.conan.account.PresentableAccount;

/**
 *
 * @author ninjav
 */
public class AccountPanel extends javax.swing.JPanel {

    /**
     * Creates new form DebitOrderPanel
     */
    public AccountPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new javax.swing.JSplitPane();
        accountSelectorPanel = new org.ninjav.conan.ui.account.AccountSelectorPanel();
        debitOrderSelectorPanel = new org.ninjav.conan.ui.account.DebitOrderSelectorPanel();

        splitPane.setLeftComponent(accountSelectorPanel);
        splitPane.setRightComponent(debitOrderSelectorPanel);
        debitOrderSelectorPanel.getAccessibleContext().setAccessibleName("Account");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.ninjav.conan.ui.account.AccountSelectorPanel accountSelectorPanel;
    private org.ninjav.conan.ui.account.DebitOrderSelectorPanel debitOrderSelectorPanel;
    private javax.swing.JSplitPane splitPane;
    // End of variables declaration//GEN-END:variables

    void reset() {
        accountSelectorPanel.reset();
        //debitOrderSelectorPanel1.reset();
    }
    
    public JButton getAccountSearchButton() {
        return accountSelectorPanel.getSearchButton();
    }

    public void clearAccounts() {
        accountSelectorPanel.clearAccounts();
    }
    
    public void addAccounts(List<PresentableAccount> accounts) {
        accounts.stream().forEach((a) -> {
            accountSelectorPanel.addAccount(a);
        });
    }
    
    public DefaultTableModel getAccountsModel() {
        return accountSelectorPanel.getTableModel();
    }
    
    public JTextField getAccountSearchField() {
        return accountSelectorPanel.getSearchForField();
    }
    
    public int[] getSelectedAccountRows() {
        return accountSelectorPanel.getSelectedRows();
    }
}
