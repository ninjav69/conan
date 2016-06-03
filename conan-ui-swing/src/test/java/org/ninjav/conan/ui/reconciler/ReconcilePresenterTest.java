/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.reconciler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.transaction.*;

/**
 *
 * @author Alan.Pickard
 */
public class ReconcilePresenterTest {
    private ReconcileViewSpy reconcileViewSpy;
    private ReconcilePresenter presenter;
    
    @Before
    public void setup() {
        reconcileViewSpy = new ReconcileViewSpy();    
        presenter = new ReconcilePresenter(reconcileViewSpy);
    }

    @Test
    public void givenNullTransactionList_clearTransactionsOnly() {
        presenter.setCreditsPort(new PresentCreditTransactionPort() {

            @Override
            public List<PresentableTransaction> presentLegacyTransactions() {
                return null;
            }

            @Override
            public void setFilterText(String filterText) {
                
            }
        });
        presenter.findCreditTransactionsForFilter(Arrays.asList(new String[]{"TXN"}));

        assertThat(reconcileViewSpy.getClearCreditTransactionsCalled(), is(1));
        assertThat(reconcileViewSpy.getAppendCreditTranssactionsCalled(), is(0));
    }
    
    @Test
    public void givenEmptyTransactionList_clearTransactionsOnly() {
        presenter.setCreditsPort(new PresentCreditTransactionPort() {

            @Override
            public List<PresentableTransaction> presentLegacyTransactions() {
                return new ArrayList<>();
            }

            @Override
            public void setFilterText(String filterText) {
            }
        });
        presenter.findCreditTransactionsForFilter(Arrays.asList(new String[]{"TXN"}));

        assertThat(reconcileViewSpy.getClearCreditTransactionsCalled(), is(1));
        assertThat(reconcileViewSpy.getAppendCreditTranssactionsCalled(), is(0));
    }
    
    @Test
    public void givenNonEmptyTrasnactionList_clearAndAppendTransactions() {
        presenter.setCreditsPort(new PresentCreditTransactionPort() {

            @Override
            public List<PresentableTransaction> presentLegacyTransactions() {
                List<PresentableTransaction> transactions = new ArrayList<>();
                transactions.add(createTransaction(1, "1/2/2015", "TXNREF01", "R 10.00"));
                return transactions;
            }

            @Override
            public void setFilterText(String filterText) {
            }
        });
        presenter.findCreditTransactionsForFilter(Arrays.asList(new String[]{"TXN"}));

        assertThat(reconcileViewSpy.getClearCreditTransactionsCalled(), is(1));
        assertThat(reconcileViewSpy.getAppendCreditTranssactionsCalled(), is(1));
    }
    
    @Test
    public void whenCreditSelectionValid_mustUpdateReconcileControlsWithReconcileEnabled() {
        presenter.setReconcilePort(new ReconcileTransactionPort() {

            @Override
            public void addListener(ReconcileTransactionListener listener) {
            }

            @Override
            public int getBalanceForSelection(ReconcileTransactionSelection selection) {
                return 0;
            }

            @Override
            public int getTotalCreditsForSelection(ReconcileTransactionSelection selection) {
                return 100;
            }

            @Override
            public int getTotalDebitsForSelection(ReconcileTransactionSelection selection) {
                return -100;
            }

            @Override
            public boolean reconcileIsPossible(ReconcileTransactionSelection selection) {
                return true;
            }

            @Override
            public void reconcileSelection(String userName, String note, ReconcileTransactionSelection selection) {
            }

            @Override
            public void removeListener(ReconcileTransactionListener listener) {
            }

            @Override
            public boolean selectionIsBalancing(ReconcileTransactionSelection selection) {
                return true;
            }

            @Override
            public boolean selectionIsChronological(ReconcileTransactionSelection selection) {
                return true;
            }
        });
        presenter.setNotes("A note");
        presenter.setCreditSelection(Arrays.asList(new Integer[]{1}));
        presenter.setDebitSelection(Arrays.asList(new Integer[]{1}));
        assertThat(reconcileViewSpy.getUpdateSelectedCreditsLabelCalled(), is(1));
        assertThat(reconcileViewSpy.getUpdateSelectedBalanceLabelCalled(), is(1));
        assertThat(reconcileViewSpy.getUpdateSelectedIsChronologicalCalled(), is(1));
        assertThat(reconcileViewSpy.getEnableReconcileButtonCalled(), is(1));
    }
        
    @Test
    public void whenCreditSelectionInvalid_mustUpdateReconcileControlsWithReconcileDisabled() {
        presenter.setReconcilePort(new ReconcileTransactionPort() {

            @Override
            public void addListener(ReconcileTransactionListener listener) {
            }

            @Override
            public int getBalanceForSelection(ReconcileTransactionSelection selection) {
                return 50;
            }

            @Override
            public int getTotalCreditsForSelection(ReconcileTransactionSelection selection) {
                return 100;
            }

            @Override
            public int getTotalDebitsForSelection(ReconcileTransactionSelection selection) {
                return -50;
            }

            @Override
            public boolean reconcileIsPossible(ReconcileTransactionSelection selection) {
                return false;
            }

            @Override
            public void reconcileSelection(String userName, String note, ReconcileTransactionSelection selection) {
            }

            @Override
            public void removeListener(ReconcileTransactionListener listener) {
            }

            @Override
            public boolean selectionIsBalancing(ReconcileTransactionSelection selection) {
                return false;
            }

            @Override
            public boolean selectionIsChronological(ReconcileTransactionSelection selection) {
                return true;
            }
        });
        presenter.setNotes("A note");
        presenter.setCreditSelection(Arrays.asList(new Integer[]{1}));
        presenter.setDebitSelection(Arrays.asList(new Integer[]{1}));
        assertThat(reconcileViewSpy.getUpdateSelectedDebitsLabelCalled(), is(1));
        assertThat(reconcileViewSpy.getUpdateSelectedBalanceLabelCalled(), is(1));
        assertThat(reconcileViewSpy.getUpdateSelectedIsChronologicalCalled(), is(1));
        assertThat(reconcileViewSpy.getDisableReconcileButtonCalled(), is(1));
    }

    
    private PresentableTransaction createTransaction(int id, String date, String reference, String amount) {
        PresentableTransaction p = new PresentableTransaction();
        p.id = id;
        p.date = date;
        p.reference = reference;
        p.amount = amount;
        return p;
    }
}
