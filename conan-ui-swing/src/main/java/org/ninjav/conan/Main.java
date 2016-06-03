/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan;

import org.ninjav.conan.ui.AppFrame;
import org.ninjav.conan.ui.core.MainModel;
import org.ninjav.conan.ui.core.MainPresenter;
import org.ninjav.conan.ui.core.SwingMainView;
import org.ninjav.conan.ui.logger.LoggerPresenter;
import org.ninjav.conan.ui.logger.SwingLoggerView;
import org.ninjav.conan.ui.reconciler.ReconcilePresenter;
import org.ninjav.conan.ui.reconciler.SwingReconcileView;
import org.ninjav.conan.core.Context;
import org.ninjav.conan.core.model.License;
import org.ninjav.conan.core.model.Module;
import org.ninjav.conan.core.model.User;
import org.ninjav.conan.core.module.PresentModuleUseCase;
import org.ninjav.conan.core.persistence.JPA2CoreGatewayFactory;
import org.ninjav.conan.core.user.SignInUseCase;
import org.ninjav.conan.core.user.SignOutUseCase;
import org.ninjav.conan.logger.LogEventUseCase;
import org.ninjav.conan.transaction.PresentCreditTransactionUseCase;
import org.ninjav.conan.transaction.PresentDebitTransactionUseCase;
import org.ninjav.conan.transaction.ReconcileTransactionUseCase;
import org.ninjav.conan.transaction.model.BankStmtTx;
import org.ninjav.conan.transaction.persistence.JPA2TransactionGatewayFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author ninjav
 */
public class Main {

    public static void main(String args[]) throws ParseException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        Context.coreGateway = new JPA2CoreGatewayFactory().createGateway();
        Context.transactionGateway = new JPA2TransactionGatewayFactory().createGateway();
        initDatabase();

//        String URL = "jdbc:hsqldb:mem:standalone-test";
//        String USERNAME = "sa";
//        String PASSWORD = "";
//                
//        org.hsqldb.util.DatabaseManagerSwing.main(new String[] { "--url", URL, "--user", USERNAME, "--password", PASSWORD});        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            AppFrame frame = new AppFrame();

            // Core stuff
            SwingMainView mainView = new SwingMainView(frame.getMainPanel());
            MainModel mainModel = new MainModel();
            MainPresenter mainPesenter = new MainPresenter(mainView, mainModel);
            SignInUseCase signInUseCase = new SignInUseCase();
            SignOutUseCase signOutUseCase = new SignOutUseCase();
            PresentModuleUseCase presentModuleUseCase = new PresentModuleUseCase();
            mainPesenter.setSignInPort(signInUseCase);
            mainPesenter.setSignOutPort(signOutUseCase);
            mainPesenter.setModulesPort(presentModuleUseCase);
            mainView.setPresenter(mainPesenter);

            // Reconcile stuff
            SwingReconcileView reconcileView = new SwingReconcileView(frame.getReconcilePanel());
            ReconcilePresenter reconcilePresenter = new ReconcilePresenter(reconcileView);
            PresentCreditTransactionUseCase presentCreditUseCase = new PresentCreditTransactionUseCase();
            PresentDebitTransactionUseCase presentDebitUseCase = new PresentDebitTransactionUseCase();
            ReconcileTransactionUseCase reconcileUseCase = new ReconcileTransactionUseCase();
            reconcilePresenter.setCreditsPort(presentCreditUseCase);
            reconcilePresenter.setDebitsPort(presentDebitUseCase);
            reconcilePresenter.setReconcilePort(reconcileUseCase);
            reconcileView.setPresenter(reconcilePresenter);

            // Logger
            SwingLoggerView loggerView = new SwingLoggerView(frame.getLoggerPanel());
            LoggerPresenter loggerPresenter = new LoggerPresenter(loggerView);
            LogEventUseCase logEventUseCase = new LogEventUseCase();
            logEventUseCase.addListener(loggerPresenter);
            
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void initDatabase() throws ParseException {
        Context.coreGateway.beginTransaction();

        User newUser = Context.coreGateway.save(createUser("alan.pickard", "secret"));
        Module newModule = Context.coreGateway.save(createModule("Reconciler"));
        Context.coreGateway.save(new License(newUser, newModule));

        Context.coreGateway.commitTransaction();

        Context.transactionGateway.beginTransaction();

        Context.transactionGateway.save(createTransaction("1/1/2015", "TXNREF001", 100.0));
        Context.transactionGateway.save(createTransaction("1/1/2015", "TXNREF002", 200.0));
        Context.transactionGateway.save(createTransaction("2/1/2015", "TXNREF003", -100.0));
        Context.transactionGateway.save(createTransaction("2/1/2015", "TXNREF004", -300.0));

        Context.transactionGateway.commitTransaction();
    }

    private static BankStmtTx createTransaction(String date, String reference, double amount) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        BankStmtTx tx = new BankStmtTx();
        tx.setTransactionDate(df.parse(date));
        tx.setTransactionReference(reference);
        tx.setTransactionAmount(toCents(amount));
        return tx;
    }

    private static int toCents(double amount) {
        return (int) (amount * 100);
    }

    private static User createUser(String username, String password) {
        User u = new User();
        u.setUserName(username);
        u.setPassword(password);
        return u;
    }

    private static Module createModule(String name) {
        Module m = new Module();
        m.setName(name);
        m.setDescription(name + "'s description");
        return m;
    }
}
