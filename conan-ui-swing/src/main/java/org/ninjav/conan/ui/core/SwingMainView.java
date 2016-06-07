package org.ninjav.conan.ui.core;

import org.ninjav.conan.ui.util.ModuleRegistry;
import org.ninjav.conan.core.module.PresentableModule;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author ninjav
 */
public class SwingMainView extends MainView {

    private MainPanel mainPanel;
    private ModuleRegistry moduleRegistry;

    public SwingMainView(MainPanel panel) {
        mainPanel = panel;
        initView();
    }

    private void initView() {
        mainPanel.getSignInButton().addActionListener((ActionEvent e) -> {
            signIn();
        });
        mainPanel.getPasswordField().addActionListener((ActionEvent e) -> {
            signIn();
        });
        mainPanel.getSignOutButton().addActionListener((ActionEvent e) -> {
            presenter.signOut();
        });
        mainPanel.getModuleTab().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                presenter.changeModuleSelection(sourceTabbedPane.getTitleAt(index));
            }
        });

        moduleRegistry = new ModuleRegistry(mainPanel.getModuleTab());

        moduleRegistry.registerModule(
                "Reconciler", mainPanel.getReconcilePanel());
        moduleRegistry.registerModule(
                "Statement", mainPanel.getStatementPanel());
        moduleRegistry.registerModule(
                "Account", mainPanel.getAccountPanel());
        moduleRegistry.registerModule(
                "Dashboard", mainPanel.getDashboardPanel());
        moduleRegistry.removeAllModules();
    }

    @Override
    public void reset() {
        mainPanel.reset();
    }

    private void signIn() {
        String userName = mainPanel.getUserName();
        String password = mainPanel.getPassword();
        presenter.signIn(userName, password);
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void resetUserControls() {
        mainPanel.getUserPanel().reset();
    }

    @Override
    public void updateWelcomeLabel(String string) {
        mainPanel.getWelcomeLabel().setText(string);
    }

    @Override
    public void showUserLogoutPanel() {
        mainPanel.getUserPanel().showView(UserPanel.LOGOUT_VIEW);
    }

    @Override
    public void showUserLoginPanel() {
        mainPanel.getUserPanel().showView(UserPanel.LOGIN_VIEW);
    }

    @Override
    public void updateModules() {
        presenter.getModulesForLoggedInUser();
    }

    @Override
    public void refreshModules(List<PresentableModule> modules) {
        moduleRegistry.removeAllModules();
        modules.stream().forEach((m) -> {
            if (m.isViewable) {
                moduleRegistry.showModule(m.name);
            }
        });
    }

}
