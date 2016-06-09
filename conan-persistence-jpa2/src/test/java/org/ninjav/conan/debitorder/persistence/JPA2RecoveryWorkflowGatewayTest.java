package org.ninjav.conan.debitorder.persistence;

import org.dbunit.PrepAndExpectedTestCase;
import org.dbunit.VerifyTableDefinition;
import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.debitorder.model.DebitOrder;
import org.ninjav.conan.debitorder.model.RecoveryWorkflow;
import org.ninjav.conan.util.MyDatabaseTester;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by ninjav on 6/9/16.
 */
public class JPA2RecoveryWorkflowGatewayTest {
    private EntityManagerFactory emf;
    private EntityManager em;

    private PrepAndExpectedTestCase tc;
    private JPA2RecoveryWorkflowGateway sut;

    @Before
    public void setup () throws ClassNotFoundException {
        emf = Persistence.createEntityManagerFactory("persistence-test");
        em = emf.createEntityManager();
        tc = new MyDatabaseTester();
        sut = new JPA2RecoveryWorkflowGateway(em);
    }


    @Test
    public void testSave() throws Exception {
        final String[] prepDataFiles = {"/debitorder/recovery/select-test-data.xml"};
        final String[] expectedDataFiles = {"/debitorder/recovery/after-save-expected.xml"};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("DebitOrder", new String[]{}),
                new VerifyTableDefinition("RecoveryWorkflow", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        DebitOrder d = em.find(DebitOrder.class, 4L);
        sut.save(createRecoveryWorkflow(d, RecoveryWorkflow.Status.NEW));
        sut.commitTransaction();

        tc.postTest();
    }

    @Test
    public void testDelete() throws Exception {
        final String[] prepDataFiles = {"/debitorder/recovery/select-test-data.xml"};
        final String[] expectedDataFiles = {"/debitorder/recovery/after-delete-expected.xml"};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("DebitOrder", new String[]{}),
                new VerifyTableDefinition("RecoveryWorkflow", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        RecoveryWorkflow r = em.find(RecoveryWorkflow.class, 2L);
        sut.delete(r);
        sut.commitTransaction();

        tc.postTest();
    }

    @Test
    public void findRecoveryByDebitOrderTransactionId() throws Exception {
        final String[] prepDataFiles = {"/debitorder/recovery/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("RecoveryWorkflow", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        RecoveryWorkflow result = sut.findRecoveryByDebitOrderTransactionId(2L);
        sut.commitTransaction();

        assertThat(result, is(notNullValue()));
        assertThat(result.getStatus(), is(equalTo(RecoveryWorkflow.Status.RECOVERED)));
    }

    @Test
    public void findRecoveryWhereDebitOrderTransactionIdIn() throws Exception {
        final String[] prepDataFiles = {"/debitorder/recovery/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("RecoveryWorkflow", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        List<RecoveryWorkflow> results = sut.findRecoveryWhereDebitOrderTransactionIdIn(Arrays.asList(2L, 3L));
        sut.commitTransaction();

        assertThat(results, is(notNullValue()));
        assertThat(results.size(), is(equalTo(2)));
    }

    @Test
    public void findAllRecoveries() throws Exception {
        final String[] prepDataFiles = {"/debitorder/recovery/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("RecoveryWorkflow", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        List<RecoveryWorkflow> results = sut.findAllRecoveries();
        sut.commitTransaction();

        assertThat(results, is(notNullValue()));
        assertThat(results.size(), is(equalTo(2)));
    }

    @Test
    public void findRecoveriesByStatus() throws Exception {
        final String[] prepDataFiles = {"/debitorder/recovery/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("RecoveryWorkflow", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        List<RecoveryWorkflow> results = sut.findRecoveriesByStatus(RecoveryWorkflow.Status.RECOVERED);
        sut.commitTransaction();

        assertThat(results, is(notNullValue()));
        assertThat(results.size(), is(equalTo(1)));
    }

    @Test
    public void findRecoveriesWhereStatusIn() throws Exception {
        final String[] prepDataFiles = {"/debitorder/recovery/select-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("RecoveryWorkflow", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        List<RecoveryWorkflow> results = sut.findRecoveriesWhereStatusIn(
                Arrays.asList(RecoveryWorkflow.Status.RECOVERED, RecoveryWorkflow.Status.NEW));
        sut.commitTransaction();

        assertThat(results, is(notNullValue()));
        assertThat(results.size(), is(equalTo(1)));
    }

    private RecoveryWorkflow createRecoveryWorkflow(DebitOrder d, RecoveryWorkflow.Status status) {
        RecoveryWorkflow r = new RecoveryWorkflow();
        r.setDebitOrder(d);
        r.setStatus(status);
        return r;
    }

}
