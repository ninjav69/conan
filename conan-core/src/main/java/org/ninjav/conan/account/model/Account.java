package org.ninjav.conan.account.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ninjav on 6/3/16.
 */
@Entity
public class Account {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private String reference;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @OneToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    private Customer holder;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getHolder() {
        return holder;
    }

    public void setHolder(Customer holder) {
        this.holder = holder;
    }
}

