/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction;

/**
 *
 * @author Alan.Pickard
 */
public interface ReconcileTransactionListener {
    void onReconcileEvent(ReconcileEvent e);
}
