/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction.fixtures;

import org.ninjav.conan.core.Context;
import org.ninjav.conan.transaction.model.BankStmtTx;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author ninjav
 */
public class GivenTransactions {

    private String id;
    private String date;
    private String reference;
    private String amount;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void execute() throws ParseException {
        BankStmtTx transaction = new BankStmtTx();
        transaction.setTransactionDate(dateFormat.parse(date));
        transaction.setTransactionReference(reference);
        transaction.setTransactionAmount(toCents(amount));
        Context.transactionGateway.save(transaction);
    }

    private int toCents(String amount) {
        return (int) Double.parseDouble(amount) * 100;
    }
}
