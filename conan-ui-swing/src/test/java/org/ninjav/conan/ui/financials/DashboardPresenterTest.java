/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.financials;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.ninjav.conan.account.PresentFinancialsPort;
import org.ninjav.conan.account.PresentableFinancials;
import org.ninjav.conan.ui.dashboard.DashboardPresenter;

/**
 *
 * @author ninjav
 */
public class DashboardPresenterTest {
    @Test
    public void whenFindingFinancials_mustPresentFinancials() {
        MockDashboardView v = new MockDashboardView();
        DashboardPresenter p = new DashboardPresenter(v);
        p.setPresentFinancialsPort(new PresentFinancialsPort() {
            @Override
            public PresentableFinancials presentFinancials() {
                return new PresentableFinancials();
            }
        });
        p.findFinancials();
        assertThat(v.presentFinancialsCalled, is(equalTo(1)));
    }
}
