/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.dashboard;

import org.ninjav.conan.account.PresentableFinancials;
import org.ninjav.conan.account.persistence.FinancialsGateway;

/**
 *
 * @author ninjav
 */
public abstract class DashboardView {
    
    protected DashboardPresenter presenter;

    public void setPresenter(DashboardPresenter presenter) {
        this.presenter = presenter;
    }
    
    protected abstract void reset();
    
    protected abstract void presentFinancials(PresentableFinancials financials);
}
