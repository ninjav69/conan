package org.ninjav.conan.debitorder;

import org.ninjav.conan.core.Context;
import org.ninjav.conan.debitorder.model.RecoveryWorkflow;
import java.util.List;

/**
 * Created by ninjav on 6/9/16.
 */
public class UpdateRecoveryWorkflowStatusUseCase implements UpdateRecoveryWorkflowStatusPort {
    private List<Long> selection;

    @Override
    public void setSelection(List<Long> selecttion) {
        this.selection = selecttion;
    }

    @Override
    public void clearSelection() {
        selection.clear();
    }

    @Override
    public void updateStatus(String status) {
        Context.recoveryWorkflowGateway.beginTransaction();
        for (RecoveryWorkflow r : findFilteredRecoveries()) {
            r.setStatus(mapRecoveryStatus(status));
        }
        Context.recoveryWorkflowGateway.commitTransaction();
    }

    private RecoveryWorkflow.Status mapRecoveryStatus(String status) {
        if (status.equals("NEW")) {
            return RecoveryWorkflow.Status.NEW;
        } else if (status.equals("HANDED_OVER")) {
            return RecoveryWorkflow.Status.HANDED_OVER;
        } else if (status.equals("RECOVERED")) {
            return RecoveryWorkflow.Status.RECOVERED;
        } else if (status.equals("WRITTEN_OFF")) {
            return RecoveryWorkflow.Status.WRITTEN_OFF;
        } else {
            return RecoveryWorkflow.Status.NEW;
        }
    }

    private List<RecoveryWorkflow> findFilteredRecoveries() {
        return Context.recoveryWorkflowGateway.findRecoveryWhereDebitOrderTransactionIdIn(selection);
    }
}
