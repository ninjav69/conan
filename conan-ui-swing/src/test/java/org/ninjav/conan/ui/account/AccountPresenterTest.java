/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.debitorder.PresentDebitOrderPort;
import org.ninjav.conan.debitorder.PresentableDebitOrder;
import org.ninjav.conan.transaction.PresentCreditTransactionPort;
import org.ninjav.conan.transaction.PresentableTransaction;
import static org.junit.Assert.assertThat;
import org.ninjav.conan.account.PresentAccountPort;
import org.ninjav.conan.account.PresentableAccount;

/**
 *
 * @author ninjav
 */
public class AccountPresenterTest {

    AccountViewSpy viewSpy;
    AccountPresenter presenter;
    
    @Before
    public void setup() {
        viewSpy = new AccountViewSpy();
        presenter = new AccountPresenter(viewSpy);
    }

    @Test
    public void givenNullTransactionList_clearTransactionsOnly() {
        presenter.setAccountsPort(new PresentAccountPort() {
            @Override
            public List<PresentableAccount> presentAccounts() {
                return null;
            }

            @Override
            public void setFilterText(String string) {
            }
        });
        presenter.findAccountsForFilter("Alan");

        assertThat(viewSpy.clearAccountsCalled, is(1));
        assertThat(viewSpy.appendAccountsCalled, is(0));
    }
    
    @Test
    public void givenEmptyAccountList_clearAccountsOnly() {
        presenter.setAccountsPort(new PresentAccountPort() {
            @Override
            public List<PresentableAccount> presentAccounts() {
                return new ArrayList<>();
            }

            @Override
            public void setFilterText(String string) {
            }
        });
        presenter.findAccountsForFilter("Alan");

        assertThat(viewSpy.clearAccountsCalled, is(1));
        assertThat(viewSpy.appendAccountsCalled, is(0));
    }
    
    @Test
    public void givenNonEmptyAccountList_clearAndAppendAccounts() {
        presenter.setAccountsPort(new PresentAccountPort() {
            @Override
            public List<PresentableAccount> presentAccounts() {
                List<PresentableAccount> results = new ArrayList<>();
                results.add(createPresentableAccount("reference1", "name1"));
                results.add(createPresentableAccount("reference2", "name2"));
                return results;
            }

            @Override
            public void setFilterText(String string) {
            }

            private PresentableAccount createPresentableAccount(String reference, String name) {
                PresentableAccount a = new PresentableAccount();
                a.reference = reference;
                a.name = name;
                return a;
            }
        });
        presenter.findAccountsForFilter("Alan");

        assertThat(viewSpy.clearAccountsCalled, is(1));
        assertThat(viewSpy.appendAccountsCalled, is(1));
    }
}
