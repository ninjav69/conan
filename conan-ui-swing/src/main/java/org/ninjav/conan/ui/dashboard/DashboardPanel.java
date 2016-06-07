/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.dashboard;

import org.ninjav.conan.account.PresentableFinancials;

/**
 *
 * @author ninjav
 */
public class DashboardPanel extends javax.swing.JPanel {

    /**
     * Creates new form DashboardPanel
     */
    public DashboardPanel() {
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        totalFundsLabel = new javax.swing.JLabel();
        owedFundsLabel = new javax.swing.JLabel();
        paidFundsLabel = new javax.swing.JLabel();
        numberOfAccountsInArrearsLabel = new javax.swing.JLabel();
        numberOfAccountsLabel = new javax.swing.JLabel();
        totalUnpaidDebitOrdersLabel = new javax.swing.JLabel();
        totalPaidDebitOrdersLabel = new javax.swing.JLabel();
        totalDebitOrdersLabel = new javax.swing.JLabel();

        jLabel1.setText("Total Funds:");

        jLabel2.setText("Owed Funds:");

        jLabel3.setText("Paid Funds:");

        jLabel4.setText("Number of Accounts:");

        jLabel5.setText("Number of Accounts in Arrears:");

        jLabel6.setText("Total Debit Orders:");

        jLabel7.setText("Total Paid Debit Orders:");

        jLabel8.setText("Total Unpaid Debit Orders:");

        totalFundsLabel.setText("0.00");

        owedFundsLabel.setText("0.00");

        paidFundsLabel.setText("0.00");

        numberOfAccountsInArrearsLabel.setText("0");

        numberOfAccountsLabel.setText("0");

        totalUnpaidDebitOrdersLabel.setText("0");

        totalPaidDebitOrdersLabel.setText("0");

        totalDebitOrdersLabel.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(totalFundsLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(paidFundsLabel)
                            .addComponent(owedFundsLabel)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalDebitOrdersLabel)
                            .addComponent(totalPaidDebitOrdersLabel)
                            .addComponent(totalUnpaidDebitOrdersLabel)
                            .addComponent(numberOfAccountsLabel)
                            .addComponent(numberOfAccountsInArrearsLabel))))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(totalFundsLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(owedFundsLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(paidFundsLabel))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(numberOfAccountsLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(numberOfAccountsInArrearsLabel))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(totalDebitOrdersLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(totalPaidDebitOrdersLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(totalUnpaidDebitOrdersLabel))
                .addContainerGap(75, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel numberOfAccountsInArrearsLabel;
    private javax.swing.JLabel numberOfAccountsLabel;
    private javax.swing.JLabel owedFundsLabel;
    private javax.swing.JLabel paidFundsLabel;
    private javax.swing.JLabel totalDebitOrdersLabel;
    private javax.swing.JLabel totalFundsLabel;
    private javax.swing.JLabel totalPaidDebitOrdersLabel;
    private javax.swing.JLabel totalUnpaidDebitOrdersLabel;
    // End of variables declaration//GEN-END:variables

    public void reset() {
        numberOfAccountsInArrearsLabel.setText("0");
        numberOfAccountsLabel.setText("0");
        owedFundsLabel.setText("0.00");
        paidFundsLabel.setText("0.00");
        totalDebitOrdersLabel.setText("0");
        totalFundsLabel.setText("0.00");
        totalPaidDebitOrdersLabel.setText("0");
        totalUnpaidDebitOrdersLabel.setText("0");
    }

    public void presentFinancials(PresentableFinancials financials) {
        numberOfAccountsInArrearsLabel.setText(Long.toString(financials.numberOfAccountsInArrears));
        numberOfAccountsLabel.setText(Long.toString(financials.numberOfAccounts));
        owedFundsLabel.setText(formatAmount(financials.owedFunds));
        paidFundsLabel.setText(formatAmount(financials.paidFunds));
        totalDebitOrdersLabel.setText(Long.toString(financials.totalDebitOrders));
        totalFundsLabel.setText(formatAmount(financials.totalFunds));
        totalPaidDebitOrdersLabel.setText(Long.toString(financials.totalPaidDebitOrders));
        totalUnpaidDebitOrdersLabel.setText(Long.toString(financials.totalUnpaidDebitOrders));
    }

    private String formatAmount(double owedFunds) {
        return String.format("%.2f", owedFunds);
    }
}
