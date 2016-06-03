/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.logger;

/**
 *
 * @author ninjav
 */
public interface LogEventPort extends Logger {

    void addListener(LoggerListener listener);

    void removeListener(LoggerListener listener);
    
}
