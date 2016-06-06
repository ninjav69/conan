/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.account;

import java.awt.event.ActionEvent;
import java.util.List;
import org.ninjav.conan.account.PresentableAccount;

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
}
