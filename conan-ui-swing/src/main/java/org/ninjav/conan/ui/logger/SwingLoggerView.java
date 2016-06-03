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
public class SwingLoggerView extends LoggerView {

    private LoggerPanel panel;

    public SwingLoggerView(LoggerPanel panel) {
        this.panel = panel;
    }

    @Override
    public void log(PresentableLogEvent event) {
        panel.log(event.level, event.date, event.line);
    }

}
