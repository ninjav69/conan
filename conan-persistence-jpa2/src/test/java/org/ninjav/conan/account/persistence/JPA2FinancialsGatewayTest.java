package org.ninjav.conan.account.persistence;

import org.dbunit.PrepAndExpectedTestCase;
import org.dbunit.VerifyTableDefinition;
import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.util.MyDatabaseTester;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ninjav on 6/7/16.
 */
public class JPA2FinancialsGatewayTest {
    private EntityManagerFactory emf;
    private EntityManager em;

    private PrepAndExpectedTestCase tc;
    private JPA2FinancialsGateway sut;

    @Before
    public void setup () throws ClassNotFoundException {
        emf = Persistence.createEntityManagerFactory("persistence-test");
        em = emf.createEntityManager();
        tc = new MyDatabaseTester();
        sut = new JPA2FinancialsGateway(em);
    }

    @Test
    public void givenSomeData_presentFinancials() throws Exception {
        final String[] prepDataFiles = {"/financials/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        long numberOfAccounts = sut.findNumberOfAccounts();
        long numberOfAccountsInArrears = sut.findNumberOfAccountsInArrears();
        double totalFunds = sut.findTotalFunds();
        double owedFunds = sut.findOwedFunds();
        double paidFunds = sut.findPaidFunds();
        double writtenOffFunds = sut.findWrittenOffFunds();
        double recoveredFunds = sut.findRecoveredFunds();
        long totalDebitOrders = sut.findTotalDebitOrders();
        long totalPaidDebitOrders = sut.findTotalPaidDebitOrders();
        long totalUnpaidDebitOrders = sut.findTotalUnpaidDebitOrders();

        assertThat(numberOfAccounts, is(equalTo(4L)));
        assertThat(numberOfAccountsInArrears, is(equalTo(2L)));
        assertThat(owedFunds, is(equalTo(5.0)));
        assertThat(paidFunds, is(equalTo(1.0)));
        assertThat(totalDebitOrders, is(equalTo(3L)));
        assertThat(totalFunds, is(equalTo(6.0)));
        assertThat(totalPaidDebitOrders, is(equalTo(1L)));
        assertThat(totalUnpaidDebitOrders, is(equalTo(2L)));
        assertThat(writtenOffFunds, is(equalTo(3.0)));
        assertThat(recoveredFunds, is(equalTo(2.0)));
    }
}
