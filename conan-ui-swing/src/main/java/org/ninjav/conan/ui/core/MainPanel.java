/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.core;

import org.ninjav.conan.ui.logger.LoggerPanel;
import org.ninjav.conan.ui.reconciler.ReconcilePanel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author ninjav
 */
public class MainPanel extends javax.swing.JPanel {

    /**
     * Creates new form MainForm
     */
    public MainPanel() {
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

        headerPanel = new javax.swing.JPanel();
        userPanel = new UserPanel();
        headerLabel = new javax.swing.JLabel();
        welcomeLabel = new javax.swing.JLabel();
        mainSplitPanel = new javax.swing.JSplitPane();
        loggerPanel = new LoggerPanel();
        moduleTab = new javax.swing.JTabbedPane();
        homePanel = new HomePanel();
        reconcilePanel = new ReconcilePanel();

        headerPanel.setBackground(new java.awt.Color(255, 255, 255));

        headerLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        headerLabel.setText("DCM Smart");

        welcomeLabel.setForeground(new java.awt.Color(102, 102, 102));
        welcomeLabel.setText("Not signed in");

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(welcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(headerPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(headerLabel)
                            .addComponent(welcomeLabel))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainSplitPanel.setDividerLocation(500);
        mainSplitPanel.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        mainSplitPanel.setRightComponent(loggerPanel);

        moduleTab.addTab("Home", homePanel);

        reconcilePanel.setName("Reconciler"); // NOI18N
        moduleTab.addTab("Reconcile", reconcilePanel);

        mainSplitPanel.setLeftComponent(moduleTab);
        moduleTab.getAccessibleContext().setAccessibleName("homeTab");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainSplitPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1131, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainSplitPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel headerPanel;
    private HomePanel homePanel;
    private LoggerPanel loggerPanel;
    private javax.swing.JSplitPane mainSplitPanel;
    private javax.swing.JTabbedPane moduleTab;
    private ReconcilePanel reconcilePanel;
    private UserPanel userPanel;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables

    public void reset() {
        reconcilePanel.reset();
    }

    public JLabel getWelcomeLabel() {
        return welcomeLabel;
    }

    public String getUserName() {
        return userPanel.getUserName();
    }

    public String getPassword() {
        return userPanel.getPassword();
    }

    public JButton getSignInButton() {
        return userPanel.getSignInButton();
    }
    
    public JButton getSignOutButton() {
        return userPanel.getSignOutButton();
    }

    public JTabbedPane getModuleTab() {
        return moduleTab;
    }
    
    public UserPanel getUserPanel() {
        return userPanel;
    }
    
    public ReconcilePanel getReconcilePanel() {
        return reconcilePanel;
    }

    public LoggerPanel getLoggerPanel() {
        return loggerPanel;
    }

    public JTextField getPasswordField() {
        return userPanel.getPasswordField();
    }

}
