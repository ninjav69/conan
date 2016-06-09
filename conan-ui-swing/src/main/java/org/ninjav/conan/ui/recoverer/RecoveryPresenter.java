/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.recoverer;

import java.util.ArrayList;
import java.util.List;
import org.ninjav.conan.debitorder.PresentRecoveryWorkflowPort;
import org.ninjav.conan.debitorder.PresentableRecoveryWorkflow;
import org.ninjav.conan.debitorder.UpdateRecoveryWorkflowStatusPort;

/**
 *
 * @author ninjav
 */
public class RecoveryPresenter {
    private RecoveryView view;
    
    private PresentRecoveryWorkflowPort recoveryPort;
    private UpdateRecoveryWorkflowStatusPort workflowPort;
    
    private List<String> statusFilter = new ArrayList<>();
    
    private List<Long> selectedDebitOrderReferences = new ArrayList<>();

    public RecoveryPresenter(RecoveryView view) {
        this.view = view;
    }

    public void setRecoveryPort(PresentRecoveryWorkflowPort recoveryPort) {
        this.recoveryPort = recoveryPort;
    }

    public void setWorkflowPort(UpdateRecoveryWorkflowStatusPort workflowPort) {
        this.workflowPort = workflowPort;
    }

    public void setStatusFilter(List<String> statusFilter) {
        this.statusFilter = statusFilter;
    }
    
    public void addStatusToFilter(String filter) {
        if (!statusFilter.contains(filter)) {
            statusFilter.add(filter);
        }
    }
    
    public void removeStatusFromFilter(String filter) {
        if (statusFilter.contains(filter)) {
            statusFilter.remove(filter);
        }
    }

    public void clearStatusFilter() {
        statusFilter.clear();
    }
    
    
    public void setSelectedDebitOrderReferences(List<Long> selectedDebitOrderReferences) {
        this.selectedDebitOrderReferences = selectedDebitOrderReferences;
    }
    
    public void clearSelectedDebitOrderReferences() {
        selectedDebitOrderReferences.clear();
    }
    
    public void findRecoveryWorkflowsForSelection() {
        view.clearRecoveryWorkflows();
        recoveryPort.setFilter(statusFilter);
        List<PresentableRecoveryWorkflow> r = recoveryPort.presentRecoveryWorkflows();
        if (r != null && !r.isEmpty()) {
            view.appendRecoveryWorkflows(r);
        }
    }
    
    public void updateRecoveryWorkflowStatusForSelection(String status) {
        workflowPort.setSelection(selectedDebitOrderReferences);
        workflowPort.updateStatus(status);
        findRecoveryWorkflowsForSelection();
    }

    public void disableWorkflowUpdates() {
        view.disableWorkflowControls();
    }

    public void enableWorkflowUpdates() {
        view.enableWorkflowControls();
    }
}
