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
public abstract class RecoveryView {
    protected RecoveryPresenter presenter;

    public void setPresenter(RecoveryPresenter presenter) {
        this.presenter = presenter;
    }

    protected abstract void reset();
    
    protected abstract void clearRecoveryWorkflows();
    protected abstract void appendRecoveryWorkflows(List<PresentableRecoveryWorkflow> r);

    protected abstract void disableWorkflowControls();
    protected abstract void enableWorkflowControls();
}
