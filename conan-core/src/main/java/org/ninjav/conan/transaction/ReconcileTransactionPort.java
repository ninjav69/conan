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
public interface ReconcileTransactionPort {

    void addListener(ReconcileTransactionListener listener);

    int getBalanceForSelection(ReconcileTransactionSelection selection);

    int getTotalCreditsForSelection(ReconcileTransactionSelection selection);

    int getTotalDebitsForSelection(ReconcileTransactionSelection selection);

    boolean reconcileIsPossible(ReconcileTransactionSelection selection);

    void reconcileSelection(String userName, String note, ReconcileTransactionSelection selection);

    void removeListener(ReconcileTransactionListener listener);

    boolean selectionIsBalancing(ReconcileTransactionSelection selection);

    boolean selectionIsChronological(ReconcileTransactionSelection selection);
    
}
