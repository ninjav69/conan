/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.core;

import org.ninjav.conan.transaction.PresentableTransaction;
import org.ninjav.conan.transaction.PresentDebitTransactionUseCase;
import org.ninjav.conan.transaction.model.BankStmtTx;
import org.ninjav.conan.transaction.persistence.MockTransactionGateway;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ninjav
 */
public class PresentTransactionUseCaseTest {
    private PresentDebitTransactionUseCase useCase;
    
    @Before
    public void setUp() {
        Context.transactionGateway = new MockTransactionGateway();
        useCase = new PresentDebitTransactionUseCase();
    }
    
    @Test
    public void whenNoTransactions_presentNoTransactions() {
        List<PresentableTransaction> transactions = useCase.presentLegacyTransactions();
        assertTrue(transactions.isEmpty());
    }
    
    @Test
    public void whenInadequateFilter_presntNoTransactions() {
        List<PresentableTransaction> transactions = useCase.presentLegacyTransactions();
        assertTrue(transactions.isEmpty());
    }
    
    @Test
    public void whenAdequateFilter_presentTransactions() throws ParseException {
        Context.transactionGateway.save(createTransaction("1/1/2015", "TXNREF01", -100.0));
        Context.transactionGateway.save(createTransaction("2/1/2015", "TXNREF02", -200.0));
        Context.transactionGateway.save(createTransaction("3/1/2015", "TXNREF03", -300.0));
        
        useCase.setFilterText("F01");
        List<PresentableTransaction> transactions = useCase.presentLegacyTransactions();
        
        assertThat(transactions.size(), is(1));
    }

    private BankStmtTx createTransaction(String date, String reference, double amount) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        BankStmtTx tx = new BankStmtTx();
        tx.setTransactionDate(df.parse(date));
        tx.setTransactionReference(reference);
        tx.setTransactionAmount(toCents(amount));
        return tx;
    }

    private int toCents(double amount) {
        return (int)(amount * 100);
    }
    
}
