/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction.persistence;

import org.ninjav.conan.transaction.model.BankStmtTx;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Alan.Pickard
 */
public class MockTransactionGatewayFactory {
    public TransactionGateway createGateway() {
        TransactionGateway gw = new MockTransactionGateway();
        try {
            System.out.println("Seeding mock gateway database");
        
            
            gw.save(createTransaction(createDate("1/1/2015"), "TXNREF01", toCents(100.00)));
            gw.save(createTransaction(createDate("2/1/2015"), "TXNREF02", toCents(200.00)));
            gw.save(createTransaction(createDate("3/1/2015"), "TXNREF03", toCents(300.00)));
        } catch (ParseException ex) {
            // Ignore
            System.out.println("Could not seed mock database: " + ex.getMessage());
        }
        return gw;
    }

    private static BankStmtTx createTransaction(Date date, String reference, int amount) {
        BankStmtTx tx = new BankStmtTx();
        tx.setTransactionDate(date);
        tx.setTransactionReference(reference);
        tx.setTransactionAmount(amount);
        return tx;
    }

    private static Date createDate(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        return df.parse(date);
    }

    private static int toCents(double amount) {
        return (int) amount * 100;
    }


}
