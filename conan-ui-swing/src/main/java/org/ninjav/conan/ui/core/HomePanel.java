/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.core;

/**
 *
 * @author ninjav
 */
public class HomePanel extends javax.swing.JPanel {

    /**
     * Creates new form HomePanel
     */
    public HomePanel() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        homeScreen = new javax.swing.JTextPane();

        setBackground(new java.awt.Color(255, 255, 255));

        homeScreen.setContentType("text/html"); // NOI18N
        homeScreen.setText("<html>\n  <head>\n\n  </head>\n  <body>\n    <h1 style=\"margin-left: 5px\">Welcome</h1>\n    <p style=\"margin-left: 10px\">\n       If you are not already logged in, please enter your credentials in the top right corner and hit the <em>sign-in</em> button.\n\n    </p>\n    <p style=\"margin-left: 10px\">\n        Once signed in, you'll get new tabs on the tab bar which will host the modules you have access to.\n    </p>\n  </body>\n</html>\n");
        jScrollPane1.setViewportView(homeScreen);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane homeScreen;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
