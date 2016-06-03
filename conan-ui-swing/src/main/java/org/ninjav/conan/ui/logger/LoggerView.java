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
public abstract class LoggerView {
    public abstract void log(PresentableLogEvent event);
}
