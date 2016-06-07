package org.ninjav.conan.debitorder;

import java.util.Date;

/**
 * Created by ninjav on 6/3/16.
 */
public class PresentableDebitOrder {
    public long transactionId;
    public double amount;
    public Date date;
    public Result result;

    public enum Result {
        PAID,
        INSUFFICIENT_FUNDS,
        NOT_A_DEBIT_ACCOUNT,
        PAYMENT_STOPPED,
        ACCOUNT_FROZEN,
        ACCOUNT_IN_SEQUISTRATION,
        OTHER
    }
}
