package org.ninjav.conan.debitorder.persistence;

import org.ninjav.conan.core.persistence.Gateway;
import org.ninjav.conan.debitorder.model.DebitOrder;
import org.ninjav.conan.debitorder.model.RecoveryWorkflow;

import java.util.List;

/**
 * Created by ninjav on 6/9/16.
 */
public interface RecoveryWorkflowGateway extends Gateway {
    RecoveryWorkflow save(RecoveryWorkflow workflow);

    void delete(RecoveryWorkflow workflow);

    RecoveryWorkflow findRecoveryByDebitOrderTransactionId(Long transactionId);

    List<RecoveryWorkflow> findRecoveryWhereDebitOrderTransactionIdIn(List<Long> transactionIds);

    List<RecoveryWorkflow> findAllRecoveries();

    List<RecoveryWorkflow> findRecoveriesByStatus(RecoveryWorkflow.Status status);

    List<RecoveryWorkflow> findRecoveriesWhereStatusIn(List<RecoveryWorkflow.Status> statuses);
}
