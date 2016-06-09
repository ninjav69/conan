package org.ninjav.conan.debitorder;

import org.ninjav.conan.core.Context;
import org.ninjav.conan.debitorder.model.DebitOrder;
import org.ninjav.conan.debitorder.model.RecoveryWorkflow;
import org.ninjav.conan.debitorder.persistence.RecoveryWorkflowGateway;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninjav on 6/9/16.
 */
public class PresentRecoveryWorkflowUseCase implements PresentRecoveryWorkflowPort {

    private List<String> filter = new ArrayList<>();

    @Override
    public List<PresentableRecoveryWorkflow> presentRecoveryWorkflows() {
        List<PresentableRecoveryWorkflow> results = new ArrayList<>();
        for (RecoveryWorkflow f : findFilteredRecoveries()) {
            PresentableRecoveryWorkflow p = new PresentableRecoveryWorkflow();
            p.debitOrder = makePresentable(f.getDebitOrder());
            p.status = makeStatusText(f.getStatus());
            results.add(p);
        }
        return results;
    }

    private List<RecoveryWorkflow> findFilteredRecoveries() {
        if (filter.isEmpty()) {
            return Context.recoveryWorkflowGateway.findAllRecoveries();
        } else {
            return Context.recoveryWorkflowGateway.findRecoveriesWhereStatusIn(makeStatusFilter());
        }
    }

    private List<RecoveryWorkflow.Status> makeStatusFilter() {
        List<RecoveryWorkflow.Status> results = new ArrayList<>();
        for (String f : filter) {
            if (f.equals("NEW")) {
                results.add(RecoveryWorkflow.Status.NEW);
            } else if (f.equals("HANDED_OVER")) {
                results.add(RecoveryWorkflow.Status.HANDED_OVER);
            } else if (f.equals("RECOVERED")) {
                results.add(RecoveryWorkflow.Status.RECOVERED);
            } else if (f.equals("WRITTEN_OFF")) {
                results.add(RecoveryWorkflow.Status.WRITTEN_OFF);
            }
        }
        return results;
    }

    @Override
    public void setFilter(List<String> filter) {
        this.filter = filter;
    }

    @Override
    public void clearFilter() {
        this.filter.clear();
    }

    private String makeStatusText(RecoveryWorkflow.Status status) {
        return status.toString();
    }

    private PresentableDebitOrder makePresentable(DebitOrder debitOrder) {
        PresentableDebitOrder d = new PresentableDebitOrder();
        d.transactionId = debitOrder.getTransactionId();
        d.amount = debitOrder.getAmount();
        d.date = debitOrder.getDate();
        d.result = mapResult(debitOrder.getResultCode());
        return d;
    }

    private PresentableDebitOrder.Result mapResult(Integer resultCode) {
        switch (resultCode) {
            case DebitOrder.ACCOUNT_FROZEN:
                return PresentableDebitOrder.Result.ACCOUNT_FROZEN;
            case DebitOrder.ACCOUNT_IN_SEQUISTRATION:
                return PresentableDebitOrder.Result.ACCOUNT_IN_SEQUISTRATION;
            case DebitOrder.INSUFFICIENT_FUNDS:
                return PresentableDebitOrder.Result.INSUFFICIENT_FUNDS;
            case DebitOrder.NOT_A_DEBIT_ACCOUNT:
                return PresentableDebitOrder.Result.NOT_A_DEBIT_ACCOUNT;
            case DebitOrder.PAID:
                return PresentableDebitOrder.Result.PAID;
            case DebitOrder.PAYMENT_STOPPED:
                return PresentableDebitOrder.Result.PAYMENT_STOPPED;
            default:
                return PresentableDebitOrder.Result.OTHER;
        }
    }

}
