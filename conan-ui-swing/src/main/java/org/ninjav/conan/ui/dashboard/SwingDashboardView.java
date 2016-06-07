/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.dashboard;

import org.ninjav.conan.account.PresentableFinancials;

/**
 *
 * @author ninjav
 */
public class SwingDashboardView extends DashboardView {
    private DashboardPanel panel;

    public SwingDashboardView(DashboardPanel panel) {
        this.panel = panel;
    }
    
    @Override
    protected void reset() {
        panel.reset();
    }

    @Override
    protected void presentFinancials(PresentableFinancials financials) {
        panel.presentFinancials(financials);
    }
    
}
