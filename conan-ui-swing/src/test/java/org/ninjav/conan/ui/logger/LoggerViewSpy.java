/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.logger;


import org.ninjav.conan.logger.PresentableLogEvent;

/**
 *
 * @author ninjav
 */
public class LoggerViewSpy extends LoggerView {

    private int logCalled;

    public int getLogCalled() {
        return logCalled;
    }

    @Override
    public void log(PresentableLogEvent event) {
        logCalled++;
    }

}
