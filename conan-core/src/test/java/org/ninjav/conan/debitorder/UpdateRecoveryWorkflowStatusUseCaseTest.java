package org.ninjav.conan.debitorder;

import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.core.Context;
import org.ninjav.conan.debitorder.model.DebitOrder;
import org.ninjav.conan.debitorder.model.RecoveryWorkflow;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by ninjav on 6/9/16.
 */
public class UpdateRecoveryWorkflowStatusUseCaseTest {

    private UpdateRecoveryWorkflowStatusUseCase useCase;

    @Before
    public void setup() {
        Context.debitOrderGateway = new MockDebitOrderGateway();
        Context.recoveryWorkflowGateway = new MockRecoveryWorkflowGateway();

        DebitOrder d1 = Context.debitOrderGateway.save(createDebitOrder(1L, 1.23, new Date(), DebitOrder.PAYMENT_STOPPED));
        Context.recoveryWorkflowGateway.save(createRecovery(d1, RecoveryWorkflow.Status.NEW));

        DebitOrder d2 = Context.debitOrderGateway.save(createDebitOrder(2L, 1.23, new Date(), DebitOrder.PAYMENT_STOPPED));
        Context.recoveryWorkflowGateway.save(createRecovery(d2, RecoveryWorkflow.Status.HANDED_OVER));

        DebitOrder d3 = Context.debitOrderGateway.save(createDebitOrder(3L, 1.23, new Date(), DebitOrder.PAYMENT_STOPPED));
        Context.recoveryWorkflowGateway.save(createRecovery(d3, RecoveryWorkflow.Status.RECOVERED));

        useCase = new UpdateRecoveryWorkflowStatusUseCase();
    }

    @Test
    public void whenUpdatingRecoveryStatus_mustUpdateRecoveryStatus() {
        useCase.setSelection(Arrays.asList(1L));
        useCase.updateStatus("RECOVERED");

        List<RecoveryWorkflow> results =
                Context.recoveryWorkflowGateway.findRecoveriesByStatus(RecoveryWorkflow.Status.RECOVERED);
        assertThat(results.size(), is(equalTo(2)));
    }

    private RecoveryWorkflow createRecovery(DebitOrder d, RecoveryWorkflow.Status status) {
        RecoveryWorkflow r = new RecoveryWorkflow();
        r.setDebitOrder(d);
        r.setStatus(status);
        return r;
    }

    private DebitOrder createDebitOrder(long transactionId, double amount, Date date, int resultCode) {
        DebitOrder d = new DebitOrder();
        d.setTransactionId(transactionId);
        d.setAccount(null);
        d.setAmount(amount);
        d.setDate(date);
        d.setResultCode(resultCode);
        return d;
    }
}
