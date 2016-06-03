package org.ninjav.conan.core.fixtures;

import org.ninjav.conan.core.Context;
import org.ninjav.conan.core.model.Module;

public class GivenModules {
    private String name;
    private String desscription;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.desscription = description;
    }

    public void execute() {
        Module module = new Module();
        module.setName(name);
        module.setDescription(desscription);
        Context.coreGateway.save(module);
    }
}
