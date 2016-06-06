/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.account;

import java.util.List;
import org.ninjav.conan.account.PresentableAccount;

/**
 *
 * @author ninjav
 */
public class AccountViewSpy extends AccountView {

    public int resetCalled = 0;
    public int clearAccountsCalled = 0;
    public int appendAccountsCalled = 0;
    
    @Override
    protected void reset() {
        resetCalled++;
    }

    @Override
    protected void clearAccounts() {
        clearAccountsCalled++;
    }

    @Override
    protected void appendAccounts(List<PresentableAccount> accounts) {
        appendAccountsCalled++;
    }
}
