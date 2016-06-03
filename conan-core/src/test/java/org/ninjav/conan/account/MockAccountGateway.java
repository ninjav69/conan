package org.ninjav.conan.account;

import org.ninjav.conan.account.model.Account;
import org.ninjav.conan.account.persistence.AccountGateway;

import java.util.*;

/**
 * Created by ninjav on 6/3/16.
 */
public class MockAccountGateway implements AccountGateway {
    private Map<String, Account> accounts = new HashMap<>();

    @Override
    public Account save(Account account) {
        accounts.put(account.getReference(), account);
        return account;
    }

    @Override
    public void delete(Account account) {
        accounts.remove(account.getReference());
    }

    @Override
    public Account findAccountByReference(String reference) {
        return accounts.get(reference);
    }

    @Override
    public List<Account> findAccountsWhereReferenceIn(List<String> references) {
        List<Account> result = new ArrayList<>();
        for (String r : references) {
            if (accounts.containsKey(r)) {
                result.add(accounts.get(r));
            }
        }
        return result;
    }

    @Override
    public List<Account> findAllAccounts() {
        return new ArrayList(accounts.values());
    }

    @Override
    public List<Account> findAccountsMatchingReference(String filterText) {
        List<Account> results = new ArrayList<>();
        for (String k : accounts.keySet()) {
            if (k.matches(filterText)) {
                results.add(accounts.get(k));
            }
        }
        return results;
    }

    @Override
    public List<Account> findAccountsMatchingName(String filterText) {
        List<Account> results = new ArrayList<>();
        for (Account a : accounts.values()) {
            if (a.getName().contains(filterText)) {
                results.add(a);
            }
        }
        return results;
    }


    @Override
    public void beginTransaction() {

    }

    @Override
    public void commitTransaction() {

    }

    @Override
    public void rollbackTransaction() {

    }
}
