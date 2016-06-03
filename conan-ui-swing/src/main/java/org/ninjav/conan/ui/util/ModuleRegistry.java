/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.util;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

/**
 *
 * @author Alan.Pickard
 */
public class ModuleRegistry {

    private final Map<String, Component> moduleRegistry = new HashMap<>();
    private JTabbedPane moduleContainer = null;

    public ModuleRegistry(JTabbedPane moduleContainer) {
        this.moduleContainer = moduleContainer;
    }

    public void registerModule(String moduleName, JComponent module) {
        moduleRegistry.put(moduleName, module);
    }

    public void removeAllModules() {
        moduleRegistry.keySet().stream().map(
                (moduleName) -> moduleRegistry.get(moduleName)).forEach(
                        (module) -> {
                            moduleContainer.remove(module);
                        });
    }

    public void showModule(String moduleName) {
        Component module = moduleRegistry.get(moduleName);
        moduleContainer.add(module);
    }
}
