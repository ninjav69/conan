package org.ninjav.conan.debitorder;

import java.util.List;

/**
 * Created by ninjav on 6/9/16.
 */
public interface UpdateRecoveryWorkflowStatusPort {
    void setSelection(List<Long> selecttion);
    void clearSelection();
    void updateStatus(String status);
}
