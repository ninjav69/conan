package org.ninjav.conan.account;

import org.ninjav.conan.account.persistence.FinancialsGateway;

/**
 * Created by ninjav on 6/7/16.
 */
public class MockNoFinancialsGateway implements FinancialsGateway {

    public int findTotalFundsCalled = 0;
    public int findOwedFundsCalled = 0;
    public int findPaidFundsCalled = 0;
    public int fundNumberOfAccountsCalled = 0;
    public int fundNumberOfAccountsInArrearsCalled = 0;
    public int findTotalDebitOrdersCalled = 9;
    public int findTotalUnpaidDebitOrdersCalled = 0;
    public int findWrittenOffFundsCalled = 0;
    public int findRecoveredFundsCalled = 0;

    @Override
    public double findTotalFunds() {
        findTotalFundsCalled++;
        return 0.0;
    }

    @Override
    public double findOwedFunds() {
        findOwedFundsCalled++;
        return 0.0;
    }

    @Override
    public double findPaidFunds() {
        findPaidFundsCalled++;
        return 0.0;
    }

    @Override
    public double findWrittenOffFunds() {
        findWrittenOffFundsCalled++;
        return 0;
    }

    @Override
    public double findRecoveredFunds() {
        findRecoveredFundsCalled++;
        return 0;
    }

    @Override
    public long findNumberOfAccounts() {
        fundNumberOfAccountsCalled++;
        return 0;
    }

    @Override
    public long findNumberOfAccountsInArrears() {
        fundNumberOfAccountsInArrearsCalled++;
        return 0;
    }

    @Override
    public long findTotalDebitOrders() {
        findTotalDebitOrdersCalled++;
        return 0;
    }

    @Override
    public long findTotalPaidDebitOrders() {
        findTotalUnpaidDebitOrdersCalled++;
        return 0;
    }

    @Override
    public long findTotalUnpaidDebitOrders() {
        findTotalUnpaidDebitOrdersCalled++;
        return 0;
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
