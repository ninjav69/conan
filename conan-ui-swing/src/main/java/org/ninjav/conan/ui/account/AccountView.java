/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.account;

import java.util.List;
import org.ninjav.conan.account.PresentableAccount;
import org.ninjav.conan.transaction.PresentableTransaction;

/**
 *
 * @author ninjav
 */
public abstract class AccountView {
    protected AccountPresenter presenter;

    public void setPresenter(AccountPresenter presenter) {
        this.presenter = presenter;
    }
    
    protected abstract void reset();

    protected abstract void clearAccounts();
    protected abstract void appendAccounts(List<PresentableAccount> accounts);
}
