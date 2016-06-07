/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.dashboard;

import org.ninjav.conan.account.PresentFinancialsPort;
import org.ninjav.conan.account.PresentableFinancials;
import org.ninjav.conan.account.persistence.FinancialsGateway;
import org.ninjav.conan.ui.core.ModuleEventSink;

/**
 *
 * @author ninjav
 */
public class DashboardPresenter implements ModuleEventSink {
    private DashboardView view;

    private PresentFinancialsPort presentFinancialsPort;
    
    public DashboardPresenter(DashboardView view) {
        this.view = view;
    }

    public void setPresentFinancialsPort(PresentFinancialsPort presentFinancialsPort) {
        this.presentFinancialsPort = presentFinancialsPort;
    }

    public void findFinancials() {
        PresentableFinancials p = presentFinancialsPort.presentFinancials();
        view.presentFinancials(p);
    }

    @Override
    public void handleModuleSelect(String moduleName) {
        if (moduleName.equals("Dashboard")) {
            findFinancials();
        }
    }
}
