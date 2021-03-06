/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.account;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.ninjav.conan.account.PresentableAccount;
import org.ninjav.conan.debitorder.PresentableDebitOrder;
import org.ninjav.conan.ui.renderer.CurrencyCellRenderer;
import org.ninjav.conan.ui.renderer.DateCellRenderer;

/**
 *
 * @author ninjav
 */
public class DebitOrderSelectorPanel extends javax.swing.JPanel {
    private DefaultTableModel debitOrderModel = null;

    /**
     * Creates new form DebitOrderSelectorPanel
     */
    public DebitOrderSelectorPanel() {
        initComponents();

        debitOrderTable.getColumnModel().getColumn(1).setCellRenderer(new DateCellRenderer());
        debitOrderTable.getColumnModel().getColumn(2).setCellRenderer(new CurrencyCellRenderer());

        debitOrderModel = (DefaultTableModel) debitOrderTable.getModel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        debitOrderTable = new javax.swing.JTable();

        debitOrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Transaction ID", "Date", "Amount", "Result Code"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Object.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        debitOrderTable.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(debitOrderTable);
        debitOrderTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable debitOrderTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void reset() {
        clearDebitOrders();
    }

    public void addDebitOrder(PresentableDebitOrder debitOrder) {
        debitOrderModel.addRow(new Object[]{
            debitOrder.transactionId, debitOrder.date, debitOrder.amount, debitOrder.result
        });
    }
    
    public void clearDebitOrders() {
        debitOrderModel.setRowCount(0);
    }
    
    public ListSelectionModel getSelectionModel() {
        return debitOrderTable.getSelectionModel();
    }
    
    public DefaultTableModel getTableModel() {
        return debitOrderModel;
    }

    public int[] getSelectedRows() {
        return debitOrderTable.getSelectedRows();
    }
}
