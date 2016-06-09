package org.ninjav.conan.debitorder;

import java.util.List;

/**
 * Created by ninjav on 6/9/16.
 */
public interface PresentRecoveryWorkflowPort {
    List<PresentableRecoveryWorkflow> presentRecoveryWorkflows();

    void setFilter(List<String> filter);
    void clearFilter();
}
