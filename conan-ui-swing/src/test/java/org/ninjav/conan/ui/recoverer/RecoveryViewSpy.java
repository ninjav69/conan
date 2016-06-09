/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.recoverer;

import java.util.List;
import org.ninjav.conan.debitorder.PresentableRecoveryWorkflow;

/**
 *
 * @author ninjav
 */
public class RecoveryViewSpy extends RecoveryView {

    public int resetCalled = 0;
    public int clearRecoveryWorkflowsCalled = 0;
    public int appendRecoveryWorkflowsCalled = 0;
    public int disableWorkflowControlsCalled = 0;
    public int enableWorkflowControlsCalled = 0;
    
    @Override
    protected void reset() {
        resetCalled++;
    }

    @Override
    protected void clearRecoveryWorkflows() {
        clearRecoveryWorkflowsCalled++;
    }

    @Override
    protected void appendRecoveryWorkflows(List<PresentableRecoveryWorkflow> r) {
        appendRecoveryWorkflowsCalled++;
    }

    @Override
    protected void disableWorkflowControls() {
        disableWorkflowControlsCalled++;
    }

    @Override
    protected void enableWorkflowControls() {
        enableWorkflowControlsCalled++;
    }
    
}
