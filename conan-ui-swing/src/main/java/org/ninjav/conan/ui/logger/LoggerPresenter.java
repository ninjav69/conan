/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.logger;


import org.ninjav.conan.logger.LoggerListener;
import org.ninjav.conan.logger.PresentableLogEvent;

/**
 *
 * @author ninjav
 */
public class LoggerPresenter implements LoggerListener {
    
    private LoggerView view;

    public LoggerPresenter(LoggerView view) {
        this.view = view;
    }

    @Override
    public void eventLogged(PresentableLogEvent event) {
        view.log(event);
    }
}
