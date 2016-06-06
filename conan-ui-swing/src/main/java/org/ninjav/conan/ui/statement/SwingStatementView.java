/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.statement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author ninjav
 */
public class SwingStatementView extends StatementView {
    private StatementPanel statementPanel;

    public SwingStatementView(StatementPanel statementPanel) {
        this.statementPanel = statementPanel;
        initView();

    }
    
    public void initView() {
        statementPanel.getFileChooser().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importStatement(statementPanel.getFileChooser().getSelectedFile().getAbsolutePath());
            }
        });
    }

    public void importStatement(String fileName) {
        presenter.importStatement(fileName);
    }
}
