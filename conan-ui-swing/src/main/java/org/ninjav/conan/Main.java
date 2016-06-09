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

import java.text.ParseException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.ninjav.conan.account.PresentAccountUseCase;
import org.ninjav.conan.account.PresentFinancialsUseCase;
import org.ninjav.conan.account.persistence.JPA2AccountGateway;
import org.ninjav.conan.account.persistence.JPA2FinancialsGateway;
import org.ninjav.conan.core.persistence.JPA2CoreGateway;
import org.ninjav.conan.debitorder.PresentDebitOrderUseCase;
import org.ninjav.conan.debitorder.PresentRecoveryWorkflowUseCase;
import org.ninjav.conan.debitorder.UpdateRecoveryWorkflowStatusPort;
import org.ninjav.conan.debitorder.UpdateRecoveryWorkflowStatusUseCase;
import org.ninjav.conan.debitorder.persistence.JPA2DebitOrderGateway;
import org.ninjav.conan.debitorder.persistence.JPA2RecoveryWorkflowGateway;
import org.ninjav.conan.io.ImportPaymentResultUseCase;
import org.ninjav.conan.transaction.persistence.JPA2TransactionGateway;
import org.ninjav.conan.ui.account.AccountPresenter;
import org.ninjav.conan.ui.account.AccountView;
import org.ninjav.conan.ui.account.SwingAccountView;
import org.ninjav.conan.ui.dashboard.DashboardPresenter;
import org.ninjav.conan.ui.dashboard.DashboardView;
import org.ninjav.conan.ui.dashboard.SwingDashboardView;
import org.ninjav.conan.ui.recoverer.RecoveryPanel;
import org.ninjav.conan.ui.recoverer.RecoveryPresenter;
import org.ninjav.conan.ui.recoverer.RecoveryView;
import org.ninjav.conan.ui.recoverer.SwingRecoveryView;
import org.ninjav.conan.ui.statement.StatementPresenter;
import org.ninjav.conan.ui.statement.SwingStatementView;

/**
 *
 * @author ninjav
 */
public class Main {
    
    private static EntityManagerFactory emf;
    private static EntityManager em;

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

        emf = Persistence.createEntityManagerFactory("persistence");
        em = emf.createEntityManager();

        Context.coreGateway = new JPA2CoreGateway(em);
        Context.transactionGateway = new JPA2TransactionGateway(em);
        Context.accountGateway = new JPA2AccountGateway(em);
        Context.debitOrderGateway = new JPA2DebitOrderGateway(em);
        Context.financialsGateway = new JPA2FinancialsGateway(em);
        Context.recoveryWorkflowGateway = new JPA2RecoveryWorkflowGateway(em);
        
        initDatabase();

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


            // Statement stuff
            SwingStatementView statementView = new SwingStatementView(frame.getStatementPanel());
            StatementPresenter statementPresenter = new StatementPresenter(statementView);
            ImportPaymentResultUseCase importUseCase = new ImportPaymentResultUseCase();
            statementPresenter.setImportPort(importUseCase);
            statementView.setPresenter(statementPresenter);
            
            // Account stuff
            AccountView accountView = new SwingAccountView(frame.getAccountPanel());
            AccountPresenter accountPressenter = new AccountPresenter(accountView);
            PresentAccountUseCase presentAccountUseCase = new PresentAccountUseCase();
            accountPressenter.setAccountsPort(presentAccountUseCase);
            PresentDebitOrderUseCase presentDebitOrderUseCase = new PresentDebitOrderUseCase();
            accountPressenter.setDebitOrderPort(presentDebitOrderUseCase);
            accountView.setPresenter(accountPressenter);
            
            // Dashboard stuff
            DashboardView dashboardView = new SwingDashboardView(frame.getDashboardPanel());
            DashboardPresenter dashboardPresenter = new DashboardPresenter(dashboardView);
            PresentFinancialsUseCase presentFinancialsUseCase = new PresentFinancialsUseCase();
            dashboardPresenter.setPresentFinancialsPort(presentFinancialsUseCase);
            dashboardView.setPresenter(dashboardPresenter);
            mainPesenter.attach(dashboardPresenter);
            
            // Recovery stuff
            RecoveryView recoveryView = new SwingRecoveryView(frame.getRecoveryPanel());
            RecoveryPresenter recoveryPresenter = new RecoveryPresenter(recoveryView);
            PresentRecoveryWorkflowUseCase presentRecoveryUseCase = new PresentRecoveryWorkflowUseCase();
            recoveryPresenter.setRecoveryPort(presentRecoveryUseCase);
            UpdateRecoveryWorkflowStatusUseCase updateRecoveryUseCase = new UpdateRecoveryWorkflowStatusUseCase();
            recoveryPresenter.setWorkflowPort(updateRecoveryUseCase);
            recoveryView.setPresenter(recoveryPresenter);
            
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

        User newUser = Context.coreGateway.save(createUser("chris", "secret"));
        Module statementModule = Context.coreGateway.save(createModule("Statement"));
        Context.coreGateway.save(new License(newUser, statementModule));
        Module accountModule = Context.coreGateway.save(createModule("Account"));
        Context.coreGateway.save(new License(newUser, accountModule));
        Module dashboardModule = Context.coreGateway.save(createModule("Dashboard"));
        Context.coreGateway.save(new License(newUser, dashboardModule));
        Module recoveryModule = Context.coreGateway.save(createModule("Recovery"));
        Context.coreGateway.save(new License(newUser, recoveryModule));

        Context.coreGateway.commitTransaction();
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
