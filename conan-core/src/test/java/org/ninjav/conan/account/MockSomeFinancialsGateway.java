package org.ninjav.conan.account;

import org.ninjav.conan.account.persistence.FinancialsGateway;

/**
 * Created by ninjav on 6/7/16.
 */
public class MockSomeFinancialsGateway implements FinancialsGateway {

    public int findTotalFundsCalled = 0;
    public int findOwedFundsCalled = 0;
    public int findPaidFundsCalled = 0;
    public int fundNumberOfAccountsCalled = 0;
    public int fundNumberOfAccountsInArrearsCalled = 0;
    public int findTotalDebitOrdersCalled = 9;
    public int findTotalUnpaidDebitOrdersCalled = 0;

    @Override
    public double findTotalFunds() {
        findTotalFundsCalled++;
        return 1.1;
    }

    @Override
    public double findOwedFunds() {
        findOwedFundsCalled++;
        return 2.2;
    }

    @Override
    public double findPaidFunds() {
        findPaidFundsCalled++;
        return 3.3;
    }

    @Override
    public long findNumberOfAccounts() {
        fundNumberOfAccountsCalled++;
        return 4;
    }

    @Override
    public long findNumberOfAccountsInArrears() {
        fundNumberOfAccountsInArrearsCalled++;
        return 5;
    }

    @Override
    public long findTotalDebitOrders() {
        findTotalDebitOrdersCalled++;
        return 6;
    }

    @Override
    public long findTotalPaidDebitOrders() {
        findTotalUnpaidDebitOrdersCalled++;
        return 7;
    }

    @Override
    public long findTotalUnpaidDebitOrders() {
        findTotalUnpaidDebitOrdersCalled++;
        return 8;
    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void commitTransaction() {

    }

    @Override
    public void rollbackTransaction() {

    }
}
