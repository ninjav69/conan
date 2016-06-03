/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coza.npda.dcm.smart.core.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.exparity.hamcrest.date.DateMatchers;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.ninjav.conan.core.model.User;
import org.ninjav.conan.transaction.model.*;

/**
 *
 * @author Alan.Pickard
 */
public class PersistentBankStmtTxTest extends AbstractDbUnitJpaTest {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy");

    @Test
    public void shouldFindAllTransactions() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("transaction-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        List<BankStmtTx> transactions = entityManager.createQuery("from BankStmtTx t").getResultList();

        assertNotNull(transactions);
        assertThat(transactions.size(), is(equalTo(2)));
    }

    @Test
    public void shouldFindOneTransaction() throws DataSetException, DatabaseUnitException, SQLException, ParseException {
        loadDataFile("transaction-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        BankStmtTx transaction = entityManager.find(BankStmtTx.class, 1);

        assertNotNull(transaction);
        assertThat(transaction.getBankStmtTxpk(), is(equalTo(1)));
        assertThat(transaction.getTransactionAmount(), is(equalTo(100)));
        assertThat(transaction.getTransactionReference(), is(equalTo("TXNREF01")));
        assertThat(transaction.getTransactionDate(), DateMatchers.sameDay(dateFormat.parse("1-1-2015")));
    }

    @Test
    public void shouldFindSomeTransactions() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("transaction-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        List<BankStmtTx> transactions = entityManager.createQuery("from BankStmtTx t where t.bankStmtTxpk in :ids", BankStmtTx.class)
                .setParameter("ids", Arrays.asList(1, 2)).getResultList();

        assertNotNull(transactions);
        assertThat(transactions.size(), is(equalTo(2)));
    }

    @Test
    public void shouldFindReconEntriesForTransaction() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("transaction-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        List<BankStmtTxRecon> recons = entityManager.createQuery("from BankStmtTxRecon r where r.bankStmtTxfk.bankStmtTxpk = :id", BankStmtTxRecon.class)
                .setParameter("id", 1)
                .getResultList();

        assertNotNull(recons);
        assertThat(recons.size(), is(equalTo(1)));
    }

    @Test
    public void shouldRereconcileTransactionPair() throws DataSetException, DatabaseUnitException, SQLException, ParseException {

        loadDataFile("transaction-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        entityManager.getTransaction().begin();

        // Find the currently logged in user
        User loggedInUser = entityManager.find(User.class, 1L);
        assertThat(loggedInUser.getUserName(), is(equalTo("userTest")));

        // Find the transaction entries
        BankStmtTx credit = entityManager.find(BankStmtTx.class, 1);
        BankStmtTx debit = entityManager.find(BankStmtTx.class, 2);

        // Check sanity
        assertThat(credit.getTransactionDate(), DateMatchers.sameOrBefore(debit.getTransactionDate()));
        assertThat((credit.getTransactionAmount() + debit.getTransactionAmount()), is(equalTo(0)));

        // Make sure transaction amount equals summed recon amount (Both legs?)
        long totalCreditReconAmount = 0;
        for (BankStmtTxRecon r : credit.getBankStmtTxReconSet()) {
            totalCreditReconAmount += r.getAmount();
        }
        assertThat(totalCreditReconAmount, is(equalTo((long) credit.getTransactionAmount())));

        long totalDebitReconAmount = 0;
        for (BankStmtTxRecon r : debit.getBankStmtTxReconSet()) {
            totalDebitReconAmount += r.getAmount();
        }
        assertThat(totalDebitReconAmount, is(equalTo((long) debit.getTransactionAmount())));

        // Change recon audit status (Both legs)
        BankStmtTxReconAuditStatus newStatus = entityManager.createQuery(
                "from BankStmtTxReconAuditStatus s where s.status = :statusText", BankStmtTxReconAuditStatus.class)
                .setParameter("statusText", "Status 2")
                .getSingleResult();

        assertThat(newStatus.getBankStmtTxReconAuditStatuspk(), is(equalTo(2)));

        for (BankStmtTxRecon r : credit.getBankStmtTxReconSet()) {
            for (BankStmtTxReconAudit ra : r.getBankStmtTxReconAuditSet()) {
                ra.setBankStmtTxReconAuditStatusfk(newStatus);
            }
        }

        for (BankStmtTxRecon r : debit.getBankStmtTxReconSet()) {
            for (BankStmtTxReconAudit ra : r.getBankStmtTxReconAuditSet()) {
                ra.setBankStmtTxReconAuditStatusfk(newStatus);
            }
        }

        // Insert recon contra entry for this pair (on all recon entries anyways)
        for (BankStmtTxRecon cr : credit.getBankStmtTxReconSet()) {
            for (BankStmtTxRecon dr : debit.getBankStmtTxReconSet()) {
                BankStmtTxReconContra contraEntry = new BankStmtTxReconContra();
                contraEntry.setAuditDate(dateFormat.parse("3-1-2015"));
                contraEntry.setUserId(loggedInUser.getUserName());
                contraEntry.setBankStmtTxReconfk(dr);
                contraEntry.setOrigBankStmtTxReconToContrafk(cr);
                cr.getBankStmtTxReconContraSet().add(contraEntry);
                dr.getBankStmtTxReconContraSet().add(contraEntry);

                entityManager.persist(contraEntry);
            }
        }

        // Insert recon note entry for each recon entry (Both legs?)
        for (BankStmtTxRecon r : credit.getBankStmtTxReconSet()) {
            BankStmtTxReconNote newNote = new BankStmtTxReconNote();
            newNote.setAuditDate(dateFormat.parse("3-1-2015"));
            newNote.setBankStmtTxReconfk(r);
            newNote.setUserId(loggedInUser.getUserName());
            newNote.setNote("Recon Note 3");
            entityManager.persist(newNote);
        }

        for (BankStmtTxRecon r : debit.getBankStmtTxReconSet()) {
            BankStmtTxReconNote newNote = new BankStmtTxReconNote();
            newNote.setAuditDate(dateFormat.parse("3-1-2015"));
            newNote.setBankStmtTxReconfk(r);
            newNote.setUserId(loggedInUser.getUserName());
            newNote.setNote("Recon Note 3");
            entityManager.persist(newNote);
        }

        entityManager.persist(credit);
        entityManager.persist(debit);

        entityManager.getTransaction().commit();

        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        flatXmlDataSetBuilder.setColumnSensing(true);
        InputStream expectedDataSet = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("transaction-persistence-expected-test-data.xml");
        IDataSet expectedData = flatXmlDataSetBuilder.build(expectedDataSet);

        IDataSet actualDataset = new FilteredDataSet(
                new String[]{
                    "AppUser",
                    "BankStmtTx", 
                    "BankStmtTxRecon", 
                    "BankStmtTxReconAudit",  
                    "BankStmtTxReconAuditStatus", 
                    "BankStmtTxReconContra", 
                    "BankStmtTxReconNote"
                },
                new CachedDataSet(connection.createDataSet())
        );

        try {
            createDataDump(actualDataset);
        } catch (IOException ex) {
            Logger.getLogger(PersistentBankStmtTxTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Assertion.assertEquals(expectedData, actualDataset);
    }

    private void createDataDump(final IDataSet dbContent) throws IOException, DataSetException {
        FlatXmlDataSet.write(dbContent, System.out);
    }
}
