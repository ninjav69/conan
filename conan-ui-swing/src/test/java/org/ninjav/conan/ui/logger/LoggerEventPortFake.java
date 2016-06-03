/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.logger;


import org.ninjav.conan.logger.LogEventPort;
import org.ninjav.conan.logger.Logger;
import org.ninjav.conan.logger.LoggerListener;
import org.ninjav.conan.logger.PresentableLogEvent;

/**
 *
 * @author Alan.Pickard
 */
public class LoggerEventPortFake implements LogEventPort {

    private LoggerListener listener;

    @Override
    public void addListener(LoggerListener listener) {
        this.listener = listener;
    }

    @Override
    public void removeListener(LoggerListener listener) {
        this.listener = null;
    }

    @Override
    public void logError(String message) {
        PresentableLogEvent p = new PresentableLogEvent();
        p.level = Logger.LogLevel.ERROR;
        p.line = message;
        listener.eventLogged(p);
    }

    @Override
    public void logInfo(String message) {
        PresentableLogEvent p = new PresentableLogEvent();
        p.level = Logger.LogLevel.INFO;
        p.line = message;
        listener.eventLogged(p);
    }

    @Override
    public void logSuccess(String message) {
        PresentableLogEvent p = new PresentableLogEvent();
        p.level = Logger.LogLevel.SUCCESS;
        p.line = message;
        listener.eventLogged(p);
    }

    @Override
    public void logDefault(String message) {
        PresentableLogEvent p = new PresentableLogEvent();
        p.level = Logger.LogLevel.INFO;
        p.line = message;
        listener.eventLogged(p);
    }

    @Override
    public void log(Logger.LogLevel level, String message) {
        PresentableLogEvent p = new PresentableLogEvent();
        p.level = Logger.LogLevel.INFO;
        p.line = message;
        listener.eventLogged(p);
    }

}
