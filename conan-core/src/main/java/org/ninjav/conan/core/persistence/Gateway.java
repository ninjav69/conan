package org.ninjav.conan.core.persistence;

public interface Gateway {

    void beginTransaction();
    void commitTransaction();
    void rollbackTransaction();
    
}
