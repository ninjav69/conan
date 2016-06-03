/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.logger;

/**
 *
 * @author Alan.Pickard
 */
public interface Logger {

    public void logError(String message);

    public void logInfo(String message);

    public void logSuccess(String message);
    
    public void logDefault(String message);
    
    public void log(LogLevel level, String message);

    public enum LogLevel {

        SUCCESS,
        ERROR,
        INFO
    }
}
