/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.recoverer;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import org.ninjav.conan.debitorder.PresentableRecoveryWorkflow;

/**
 *
 * @author ninjav
 */
public class SwingRecoveryView extends RecoveryView {
    private RecoveryPanel recovererPanel;

    public SwingRecoveryView(RecoveryPanel recovererPanel) {
        this.recovererPanel = recovererPanel;
        initView();
    }
    
    private void initView() {
        recovererPanel.getNewCheckBox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (recovererPanel.getNewCheckBox().isSelected()) {
                    presenter.addStatusToFilter("NEW");
                } else {
                    presenter.removeStatusFromFilter("NEW");
                }
                presenter.findRecoveryWorkflowsForSelection();;
            }
        });
        recovererPanel.getHandedOverCheckBox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (recovererPanel.getHandedOverCheckBox().isSelected()) {
                    presenter.addStatusToFilter("HANDED_OVER");
                } else {
                    presenter.removeStatusFromFilter("HANDED_OVER");
                }
                presenter.findRecoveryWorkflowsForSelection();;
            }
        });
        recovererPanel.getRecoveredCheckBox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (recovererPanel.getRecoveredCheckBox().isSelected()) {
                    presenter.addStatusToFilter("RECOVERED");
                } else {
                    presenter.removeStatusFromFilter("RECOVERED");
                }
                presenter.findRecoveryWorkflowsForSelection();;
            }
        });
        recovererPanel.getWrittenOffCheckBox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (recovererPanel.getWrittenOffCheckBox().isSelected()) {
                    presenter.addStatusToFilter("WRITTEN_OFF");
                } else {
                    presenter.removeStatusFromFilter("WRITTEN_OFF");
                }
                presenter.findRecoveryWorkflowsForSelection();;
            }
        });
        // Enable selection
        recovererPanel.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            if (lsm.isSelectionEmpty()) {
                presenter.disableWorkflowUpdates();

            } else {
                DefaultTableModel model = recovererPanel.getTableModel();
                List<Long> selectedIds = new ArrayList<>();
                for (int i : recovererPanel.getSelectedRows()) {
                    selectedIds.add((Long) model.getValueAt(i, 1));
                }
                presenter.setSelectedDebitOrderReferences(selectedIds);
                presenter.enableWorkflowUpdates();
            }
        });
        
        recovererPanel.getResetButton().addActionListener((ActionEvent e) -> {
            presenter.updateRecoveryWorkflowStatusForSelection("NEW");
        });
        recovererPanel.getHandOverButton().addActionListener((ActionEvent e) -> {
            presenter.updateRecoveryWorkflowStatusForSelection("HANDED_OVER");
        });
        recovererPanel.getRecoverButton().addActionListener((ActionEvent e) -> {
            presenter.updateRecoveryWorkflowStatusForSelection("RECOVERED");
        });
        recovererPanel.getRecoverButton().addActionListener((ActionEvent e) -> {
            presenter.updateRecoveryWorkflowStatusForSelection("WRITTEN_OFF");
        });
    }

    @Override
    protected void reset() {
        recovererPanel.reset();
    }

    @Override
    protected void clearRecoveryWorkflows() {
        recovererPanel.clearRecoveryWorkflows();
    }

    @Override
    protected void appendRecoveryWorkflows(List<PresentableRecoveryWorkflow> recoveries) {
        recovererPanel.addRecoveryWorkflows(recoveries);
    }

    @Override
    protected void disableWorkflowControls() {
        recovererPanel.disableWorkflowControls();
    }

    @Override
    protected void enableWorkflowControls() {
        recovererPanel.enableWorkflowControls();
    }
}
