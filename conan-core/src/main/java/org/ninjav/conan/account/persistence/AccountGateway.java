package org.ninjav.conan.account.persistence;

import org.ninjav.conan.account.model.Account;
import org.ninjav.conan.core.persistence.Gateway;

import java.util.List;

/**
 * Created by ninjav on 6/3/16.
 */
public interface AccountGateway extends Gateway {
    Account save(Account account);

    void delete(Account account);

    Account findAccountByReference(String reference);

    List<Account> findAccountsWhereReferenceIn(List<String> references);

    List<Account> findAllAccounts();

    List<Account> findAccountsMatchingReference(String filterText);

    List<Account> findAccountsMatchingName(String filterText);

}
