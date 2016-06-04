package org.ninjav.conan.core.persistence;

import org.dbunit.*;
import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.account.model.Account;
import org.ninjav.conan.account.model.Customer;
import org.ninjav.conan.account.persistence.JPA2AccountGateway;
import org.ninjav.conan.util.MyDatabaseTester;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by ninjav on 6/4/16.
 */
public class JPA2AccountGatewayTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    private PrepAndExpectedTestCase tc;
    private JPA2AccountGateway sut;

    @Before
    public void setup () throws ClassNotFoundException {
        emf = Persistence.createEntityManagerFactory("persistence-test");
        em = emf.createEntityManager();
        tc = new MyDatabaseTester();
        sut = new JPA2AccountGateway(em);
    }

    @Test
    public void whenHaveAccounts_mustFindAccounts() throws Exception {
        final String[] prepDataFiles = {"/account/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        List<Account> results = sut.findAllAccounts();
        assertThat(results.size(), is(equalTo(4)));
    }

    @Test
    public void whenUpdateAccount_mustUpdateAccount() throws Exception {
        final String[] prepDataFiles = {"/account/select-test-data.xml"};
        final String[] expectedDataFiles = {"/account/after-update-expected.xml"};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("Account", new String[]{"customerId"})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        Account toUpdate = sut.findAccountByReference("0001");
        toUpdate.setName("Sannie de Nekker");
        sut.commitTransaction();

        tc.postTest();
    }


    @Test
    public void whenSavingAccounts_mustStoreAccounts() throws Exception
    {
        final String[] prepDataFiles = {"/empty-test-data.xml"};
        final String[] expectedDataFiles = {"/account/after-insert-expected.xml"};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("Account", new String[]{"customerId"})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        sut.save(createAccount("0001", "Alan Pickard"));
        sut.save(createAccount("0002", "Jan Fiskaal"));
        sut.commitTransaction();

        tc.postTest();
    }

    @Test
    public void whenAddingCustomerData_mustStoreCustomerData() throws Exception {
        final String[] prepDataFiles = {"/account/select-test-data.xml"};
        final String[] expectedDataFiles = {"/account/after-update-customer-expected.xml"};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("Account", new String[]{}),
                new VerifyTableDefinition("Customer", new String[]{"firstName", "lastName", "email"})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();

        Account toUpdate = sut.findAccountByReference("0001");
        Customer c = new Customer();
        c.setMobile("0123456789");
        toUpdate.setHolder(c);

        sut.save(toUpdate);
        sut.commitTransaction();

        tc.postTest();
    }

    @Test
    public void whenLookupByReferences_mustFindByReferences() throws Exception {
        final String[] prepDataFiles = {"/account/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();

        List<Account> result = sut.findAccountsWhereReferenceIn(Arrays.asList("0001", "0002"));

        sut.commitTransaction();

        assertThat(result.size(), is(equalTo(2)));
    }

    @Test
    public void whenSearchingByName_mustFindByName() throws Exception {
        final String[] prepDataFiles = {"/account/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();

        List<Account> result = sut.findAccountsMatchingName("Pic");

        sut.commitTransaction();

        assertThat(result.size(), is(equalTo(1)));
        assertThat(result.get(0).getName(), is(equalTo("Alan Pickard")));
    }

    private Account createAccount(String reference, String name) {
        Account a = new Account();
        a.setReference(reference);
        a.setName(name);
        return a;
    }
}
