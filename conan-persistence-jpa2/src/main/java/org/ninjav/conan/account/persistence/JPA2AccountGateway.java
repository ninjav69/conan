package org.ninjav.conan.account.persistence;

import org.ninjav.conan.account.model.Account;
import org.ninjav.conan.core.persistence.JPA2Gateway;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by ninjav on 6/4/16.
 */
public class JPA2AccountGateway extends JPA2Gateway implements AccountGateway {

    public JPA2AccountGateway(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Account save(Account account) {
        entityManager.persist(account);
        //entityManager.refresh(account);
        return account;
    }

    @Override
    public void delete(Account account) {
        entityManager.merge(account);
        entityManager.remove(account);
    }

    @Override
    public Account findAccountByReference(String reference) {
        return entityManager.createQuery(
                "select a from Account a where a.reference = :accountReference", Account.class)
                .setParameter("accountReference", reference)
                .getSingleResult();
    }

    @Override
    public List<Account> findAccountsWhereReferenceIn(List<String> references) {
        return entityManager.createQuery(
                "select a from Account a where a.reference IN(:references)", Account.class)
                .setParameter("references", references)
                .getResultList();
    }

    @Override
    public List<Account> findAllAccounts() {
        return entityManager.createQuery(
                "select a from Account a", Account.class)
                .getResultList();
    }

    @Override
    public List<Account> findAccountsMatchingReference(String partialReference) {
        return entityManager.createQuery(
                "select a from Account a where a.reference like :partialReference", Account.class)
                .setParameter("partialReference", "%" + partialReference + "%")
                .getResultList();
    }

    @Override
    public List<Account> findAccountsMatchingName(String partialName) {
        return entityManager.createQuery(
                "select a from Account a where a.name like :partialName", Account.class)
                .setParameter("partialName", "%" + partialName + "%")
                .getResultList();
    }
}
