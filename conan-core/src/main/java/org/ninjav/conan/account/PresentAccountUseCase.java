package org.ninjav.conan.account;

import org.ninjav.conan.account.model.Account;
import org.ninjav.conan.account.model.Customer;
import org.ninjav.conan.core.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ninjav on 6/3/16.
 */
public class PresentAccountUseCase implements PresentAccountPort {
    private String filterText;

    @Override
    public List<PresentableAccount> presentAccounts() {
        List<PresentableAccount> result = new ArrayList<>();

        for (Account a : findAccounts()) {
            PresentableAccount p = new PresentableAccount();
            p.reference = a.getReference();
            p.name = a.getName();
            p.holder = extractHolder(a.getHolder());
            result.add(p);
        }
        return result;
    }

    private List<Account> findAccounts() {
        if (null == filterText || filterText.isEmpty()) {
            return Context.accountGateway.findAllAccounts();
        } else {
            return findFilteredAccounts();
        }
    }

    private List<Account> findFilteredAccounts() {
        Map<String, Account> collector = new HashMap<>();
        for (Account a : Context.accountGateway.findAccountsMatchingReference(filterText)) {
            collector.put(a.getReference(), a);
        }
        for (Account a : Context.accountGateway.findAccountsMatchingName(filterText)) {
            collector.put(a.getReference(), a);
        }
        return new ArrayList(collector.values());
    }

    private PresentableAccountHolder extractHolder(Customer holder) {
        PresentableAccountHolder result = new PresentableAccountHolder();
        if (holder != null) {
            result.firstName = holder.getFirstName();
            result.lastName = holder.getLastName();
            result.mobile = holder.getMobile();
            result.email = holder.getEmail();
        }
        return result;
    }

    @Override
    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
}
