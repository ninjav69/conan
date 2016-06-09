package org.ninjav.conan.debitorder;

import org.ninjav.conan.debitorder.model.DebitOrder;
import org.ninjav.conan.debitorder.model.RecoveryWorkflow;
import org.ninjav.conan.debitorder.persistence.RecoveryWorkflowGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ninjav on 6/9/16.
 */
public class MockRecoveryWorkflowGateway implements RecoveryWorkflowGateway {
    private Map<Long, RecoveryWorkflow> recoveries = new HashMap<>();

    @Override
    public RecoveryWorkflow save(RecoveryWorkflow workflow) {
        recoveries.put(workflow.getDebitOrder().getTransactionId(), workflow);
        return workflow;
    }

    @Override
    public void delete(RecoveryWorkflow workflow) {
        recoveries.remove(workflow.getDebitOrder().getTransactionId());
    }

    @Override
    public RecoveryWorkflow findRecoveryByDebitOrderTransactionId(Long transactionId) {
        return recoveries.get(transactionId);
    }

    @Override
    public List<RecoveryWorkflow> findRecoveryWhereDebitOrderTransactionIdIn(List<Long> transactionIds) {
        List<RecoveryWorkflow> results = new ArrayList<>();
        for (Long id : transactionIds) {
            if (recoveries.containsKey(id)) {
                results.add(recoveries.get(id));
            }
        }
        return results;
    }

    @Override
    public List<RecoveryWorkflow> findAllRecoveries() {
        return new ArrayList<>(recoveries.values());
    }

    @Override
    public List<RecoveryWorkflow> findRecoveriesByStatus(RecoveryWorkflow.Status status) {
        List<RecoveryWorkflow> results = new ArrayList<>();
        for (RecoveryWorkflow r : recoveries.values()) {
            if (r.getStatus().equals(status)) {
                results.add(r);
            }
        }
        return results;
    }

    @Override
    public List<RecoveryWorkflow> findRecoveriesWhereStatusIn(List<RecoveryWorkflow.Status> statuses) {
        List<RecoveryWorkflow> results = new ArrayList<>();
        for (RecoveryWorkflow r : recoveries.values()) {
            if (statuses.contains(r.getStatus())) {
                results.add(r);
            }
        }
        return results;
    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void commitTransaction() {

    }

    @Override
    public void rollbackTransaction() {

    }
}
