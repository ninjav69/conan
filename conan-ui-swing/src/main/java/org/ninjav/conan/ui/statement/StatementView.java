/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.statement;

/**
 *
 * @author ninjav
 */
public abstract class StatementView {
    protected StatementPresenter presenter;

    public void setPresenter(StatementPresenter presenter) {
        this.presenter = presenter;
    }
}
