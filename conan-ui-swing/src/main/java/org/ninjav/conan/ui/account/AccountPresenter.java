/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.account;

import java.util.List;
import org.ninjav.conan.account.PresentAccountPort;
import org.ninjav.conan.account.PresentableAccount;
import org.ninjav.conan.debitorder.PresentDebitOrderPort;
import org.ninjav.conan.debitorder.PresentableDebitOrder;

/**
 *
 * @author ninjav
 */
public class AccountPresenter {
    private AccountView view;

    private PresentAccountPort accountsPort;
    private PresentDebitOrderPort debitOrderPort;
    
    private String selectedAccountReference;
    
    public AccountPresenter(AccountView view) {
        this.view = view;
    }

    public void setAccountsPort(PresentAccountPort accountPort) {
        this.accountsPort = accountPort;
    }

    public void setDebitOrderPort(PresentDebitOrderPort debitOrderPort) {
        this.debitOrderPort = debitOrderPort;
    }
    
    public void clearAccountSelection() {
        selectedAccountReference = null;
    }
    
    public void findAccountsForFilter(String filterText) {
        view.clearAccounts();
        view.clearDebitOders();
        accountsPort.setFilterText(filterText);
        List<PresentableAccount> a = accountsPort.presentAccounts();
        if (a != null && !a.isEmpty()) {
            view.appendAccounts(a);
        }
    }

    void setAccountSelection(List<String> selectedIds) {
        if (selectedIds != null & !selectedIds.isEmpty()) {
            selectedAccountReference = selectedIds.get(0);
        } else {
            selectedAccountReference = null;
        }
        findDebitOrdersForSelection();
    }

    private void findDebitOrdersForSelection() {
        view.clearDebitOders();
        debitOrderPort.setFilterText(selectedAccountReference);
        List<PresentableDebitOrder> d = debitOrderPort.presentDebitOrders();
        if (d != null && !d.isEmpty()) {
            view.appendDebitOrders(d);
        }
    }
}
