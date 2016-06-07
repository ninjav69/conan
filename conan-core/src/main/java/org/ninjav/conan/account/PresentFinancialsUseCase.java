package org.ninjav.conan.account;

import org.ninjav.conan.account.persistence.FinancialsGateway;
import org.ninjav.conan.core.Context;

/**
 * Created by ninjav on 6/7/16.
 */
public class PresentFinancialsUseCase implements PresentFinancialsPort {
    @Override
    public PresentableFinancials presentFinancials() {
        PresentableFinancials p = new PresentableFinancials();
        p.numberOfAccounts = findNumberOfAcounts();
        p.numberOfAccountsInArrears = findNumberOfAccountsInArrears();
        p.owedFunds = findOwedFunds();
        p.paidFunds = findPaidFunds();
        p.totalDebitOrders = findTotalDebitOrders();
        p.totalFunds = findTotalFunds();
        p.totalPaidDebitOrders = findTotalPaidDebitOrders();
        p.totalUnpaidDebitOrders = findTotalUnpaidDebitOrders();
        return p;
    }

    private long findTotalUnpaidDebitOrders() { return Context.financialsGateway.findTotalUnpaidDebitOrders(); }

    private long findTotalPaidDebitOrders() {
        return Context.financialsGateway.findTotalPaidDebitOrders();
    }

    private double findTotalFunds() {
        return Context.financialsGateway.findTotalFunds();
    }

    private long findTotalDebitOrders() {
        return Context.financialsGateway.findTotalDebitOrders();
    }

    private double findPaidFunds() {
        return Context.financialsGateway.findPaidFunds();
    }

    private double findOwedFunds() {
        return Context.financialsGateway.findOwedFunds();
    }

    private long findNumberOfAccountsInArrears() {
        return Context.financialsGateway.findNumberOfAccountsInArrears();
    }

    private long findNumberOfAcounts() {
        return Context.financialsGateway.findNumberOfAccounts();
    }
}
