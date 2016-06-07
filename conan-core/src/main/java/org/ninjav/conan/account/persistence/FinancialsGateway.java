package org.ninjav.conan.account.persistence;

import org.ninjav.conan.core.persistence.Gateway;

/**
 * Created by ninjav on 6/7/16.
 */
public interface FinancialsGateway extends Gateway {
    double findTotalFunds();
    double findOwedFunds();
    double findPaidFunds();
    long findNumberOfAccounts();
    long findNumberOfAccountsInArrears();
    long findTotalDebitOrders();
    long findTotalPaidDebitOrders();
    long findTotalUnpaidDebitOrders();
}
