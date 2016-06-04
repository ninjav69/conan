package org.ninjav.conan.debitorder.persistence;

import org.dbunit.PrepAndExpectedTestCase;
import org.dbunit.VerifyTableDefinition;
import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.account.model.Account;
import org.ninjav.conan.account.persistence.JPA2AccountGateway;
import org.ninjav.conan.debitorder.model.DebitOrder;
import org.ninjav.conan.util.MyDatabaseTester;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by ninjav on 6/5/16.
 */
public class JPA2DebitOrderGatewayTest {
    private EntityManagerFactory emf;
    private EntityManager em;

    private PrepAndExpectedTestCase tc;
    private JPA2DebitOrderGateway sut;

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setup () throws ClassNotFoundException {
        emf = Persistence.createEntityManagerFactory("persistence-test");
        em = emf.createEntityManager();
        tc = new MyDatabaseTester();
        sut = new JPA2DebitOrderGateway(em);
    }

    @Test
    public void testSave() throws Exception {
        final String[] prepDataFiles = {"/debitorder/select-test-data.xml"};
        final String[] expectedDataFiles = {"/debitorder/after-save-expected.xml"};
        final VerifyTableDefinition[] tables = {
            new VerifyTableDefinition("DebitOrder", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        Account a = em.find(Account.class, "0033");
        sut.save(createDebitOrder(4L, 10.12, 0, df.parse("2016-04-15"), a));
        sut.commitTransaction();

        tc.postTest();
    }

    private DebitOrder createDebitOrder(long trasactionId, double amount, int resultCode, Date date, Account account) {
        DebitOrder d = new DebitOrder();
        d.setTransactionId(trasactionId);
        d.setAmount(amount);
        d.setResultCode(resultCode);
        d.setDate(date);
        d.setAccount(account);
        return d;
    }

    @Test
    public void testDelete() throws Exception {
        final String[] prepDataFiles = {"/debitorder/select-test-data.xml"};
        final String[] expectedDataFiles = {"/debitorder/after-delete-expected.xml"};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("DebitOrder", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        DebitOrder d = em.find(DebitOrder.class, 1L);
        sut.delete(d);
        sut.commitTransaction();

        tc.postTest();
    }

    @Test
    public void testFindDebitOrderByTransactionId() throws Exception {
        final String[] prepDataFiles = {"/debitorder/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        DebitOrder d = sut.findDebitOrderByTransactionId(3L);
        sut.commitTransaction();

        assertThat(d, is(notNullValue()));
        assertThat(d.getAmount(), is(equalTo(7.89)));
    }

    @Test
    public void testFindDebitOrdersWhereTransactionIdIn() throws Exception {
        final String[] prepDataFiles = {"/debitorder/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        List<DebitOrder> d = sut.findDebitOrdersWhereTransactionIdIn(Arrays.asList(1L, 2L));
        sut.commitTransaction();

        assertThat(d, is(notNullValue()));
        assertThat(d.size(), is(equalTo(2)));
    }

    @Test
    public void testFindAllDebitOrders() throws Exception {
        final String[] prepDataFiles = {"/debitorder/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        List<DebitOrder> d = sut.findAllDebitOrders();
        sut.commitTransaction();

        assertThat(d, is(notNullValue()));
        assertThat(d.size(), is(equalTo(3)));
    }

    @Test
    public void testFindDebitOrdersMatchingAccountReference() throws Exception {
        final String[] prepDataFiles = {"/debitorder/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        List<DebitOrder> d = sut.findDebitOrdersMatchingAccountReference("0001");
        sut.commitTransaction();

        assertThat(d, is(notNullValue()));
        assertThat(d.size(), is(equalTo(2)));
    }
}