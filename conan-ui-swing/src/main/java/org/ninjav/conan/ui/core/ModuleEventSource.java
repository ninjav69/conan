/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.core;

/**
 *
 * @author ninjav
 */
public interface ModuleEventSource {
    void attach(ModuleEventSink sink);
    void detach(ModuleEventSink sink);
    
    void changeModuleSelection(String moduleName);
    
}
