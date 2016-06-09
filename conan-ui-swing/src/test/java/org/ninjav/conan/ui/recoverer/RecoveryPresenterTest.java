/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.recoverer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.debitorder.PresentRecoveryWorkflowPort;
import org.ninjav.conan.debitorder.PresentableDebitOrder;
import org.ninjav.conan.debitorder.PresentableRecoveryWorkflow;

/**
 *
 * @author ninjav
 */
public class RecoveryPresenterTest {
    RecoveryViewSpy viewSpy;
    RecoveryPresenter presenter;
    
    @Before
    public void setup() {
        viewSpy = new RecoveryViewSpy();
        presenter = new RecoveryPresenter(viewSpy);
    }
    
    @Test
    public void givenNullRecoveryList_clearRecoveries() {
        presenter.setRecoveryPort(new PresentRecoveryWorkflowPort() {
            @Override
            public List<PresentableRecoveryWorkflow> presentRecoveryWorkflows() {
                return null;
            }

            @Override
            public void setFilter(List<String> filter) {
            }

            @Override
            public void clearFilter() {
            }
        });
        presenter.findRecoveryWorkflowsForSelection();
        assertThat(viewSpy.clearRecoveryWorkflowsCalled, is(equalTo(1)));
        assertThat(viewSpy.appendRecoveryWorkflowsCalled, is(equalTo(0)));
    }
    
    @Test
    public void givenEmptyRecoveryList_clearRecoveries() {
        presenter.setRecoveryPort(new PresentRecoveryWorkflowPort() {
            @Override
            public List<PresentableRecoveryWorkflow> presentRecoveryWorkflows() {
                return new ArrayList<>();
            }

            @Override
            public void setFilter(List<String> filter) {
            }

            @Override
            public void clearFilter() {
            }
        });
        presenter.findRecoveryWorkflowsForSelection();
        assertThat(viewSpy.clearRecoveryWorkflowsCalled, is(equalTo(1)));
        assertThat(viewSpy.appendRecoveryWorkflowsCalled, is(equalTo(0)));
    }
    
    @Test
    public void givenNonEmptyRecoveryList_clearAndAppendRecoveries() {
        presenter.setRecoveryPort(new PresentRecoveryWorkflowPort() {
            @Override
            public List<PresentableRecoveryWorkflow> presentRecoveryWorkflows() {
                List<PresentableRecoveryWorkflow> results = new ArrayList<>();
                results.add(createPresentableRecovery(1L, 12.34, PresentableDebitOrder.Result.PAID, new Date(), "NEW"));
                return results;
            }

            @Override
            public void setFilter(List<String> filter) {
            }

            @Override
            public void clearFilter() {
            }

            private PresentableRecoveryWorkflow createPresentableRecovery(
                    long transactionId, double amount, PresentableDebitOrder.Result result, Date date, String status) {
                
                PresentableDebitOrder d = new PresentableDebitOrder();
                d.transactionId = transactionId;
                d.amount = amount;
                d.date = date;
                PresentableRecoveryWorkflow r = new PresentableRecoveryWorkflow();
                r.debitOrder = d;
                r.status = status;
                return r;
            }
        });
        presenter.findRecoveryWorkflowsForSelection();
        assertThat(viewSpy.clearRecoveryWorkflowsCalled, is(equalTo(1)));
        assertThat(viewSpy.appendRecoveryWorkflowsCalled, is(equalTo(1)));
    }
}
