package org.ninjav.conan.core.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@javax.persistence.Entity
@Table(name = "AppLicense",
        uniqueConstraints = @UniqueConstraint(columnNames = {"AppUser_fk", "AppModule_fk"}))
public class License extends Entity {

    @JoinColumn(name = "AppUser_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "AppModule_fk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Module module;

    public License() {
    }

    public License(User user, Module module) {
        this.user = user;
        this.module = module;
    }

    public User getUser() {
        return user;
    }

    public Module getModule() {
        return module;
    }
}
