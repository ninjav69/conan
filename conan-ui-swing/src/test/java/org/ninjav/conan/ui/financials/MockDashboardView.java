/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.financials;

import org.ninjav.conan.account.PresentableFinancials;
import org.ninjav.conan.ui.dashboard.DashboardView;

/**
 *
 * @author ninjav
 */
public class MockDashboardView extends DashboardView {

    public int resetCalled = 0;
    public int presentFinancialsCalled = 0;

    @Override
    protected void reset() {
        resetCalled++;
    }

    @Override
    protected void presentFinancials(PresentableFinancials financials) {
        presentFinancialsCalled++;
    }
}
