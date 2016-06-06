/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.statement;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author ninjav
 */
public class StatementPresenterTest {
    @Test
    public void canCreatePresenter() {
        StatementPresenter p = new StatementPresenter(new StatementViewSpy());
        assertThat(p, is(notNullValue()));
    }
}
