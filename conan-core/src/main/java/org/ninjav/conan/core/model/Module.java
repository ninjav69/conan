package org.ninjav.conan.core.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@javax.persistence.Entity
@Table(name = "AppModule")
public class Module extends Entity {

    @Size(max = 80)
    @Column(nullable = false, unique = true)
    private String name;

    @Size(max = 255)
    private String description;

    public Module() {
    }

    public Module(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
