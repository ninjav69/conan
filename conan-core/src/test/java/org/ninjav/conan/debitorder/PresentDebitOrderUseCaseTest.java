package org.ninjav.conan.debitorder;

import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.account.MockAccountGateway;
import org.ninjav.conan.account.model.Account;
import org.ninjav.conan.core.Context;
import org.ninjav.conan.debitorder.model.DebitOrder;

import java.util.Date;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by ninjav on 6/3/16.
 */
public class PresentDebitOrderUseCaseTest {
    private PresentDebitOrderUseCase useCase;

    @Before
    public void setup() {
        Context.accountGateway = new MockAccountGateway();
        Context.debitOrderGateway = new MockDebitOrderGateway();
        useCase = new PresentDebitOrderUseCase();
    }

    @Test
    public void whenNoDebitOrders_presentNoDebitOrders() {
        List<PresentableDebitOrder> result = useCase.presentDebitOrders();
        assertThat(result.size(), is(equalTo(0)));
    }

    @Test
    public void whenOneDebitOrder_presentOneDebitOrder() {
        Account a = Context.accountGateway.save(createAccount("0001", "Alan Pickard"));
        Context.debitOrderGateway.save(createDebitOrder(
                a, 1L, new Double(1.23), DebitOrder.ACCOUNT_FROZEN, new Date()));

        List<PresentableDebitOrder> result = useCase.presentDebitOrders();

        assertThat(result.size(), is(equalTo(1)));
    }

    @Test
    public void whenFilterByAccountReference_presentFilteredByAccountReference() {
        Account a = Context.accountGateway.save(createAccount("0001", "Alan Pickard"));
        Context.debitOrderGateway.save(createDebitOrder(
                a, 1L, new Double(1.23), DebitOrder.ACCOUNT_FROZEN, new Date()));

        Account b = Context.accountGateway.save(createAccount("0002", "Sannie de Nekker"));
        Context.debitOrderGateway.save(createDebitOrder(
                b, 2L, new Double(1.23), DebitOrder.ACCOUNT_FROZEN, new Date()));

        useCase.setFilterText("0002");
        List<PresentableDebitOrder> result = useCase.presentDebitOrders();

        assertThat(result.size(), is(equalTo(1)));
        assertThat(result.get(0).transactionId, is(equalTo(2L)));
    }

    private DebitOrder createDebitOrder(Account account, Long transactionId,
                                        Double amount, int unpaidCode, Date date) {
        DebitOrder d = new DebitOrder();
        d.setAccount(account);
        d.setTransactionId(transactionId);
        d.setAmount(amount);
        d.setResultCode(unpaidCode);
        d.setDate(date);
        return d;
    }

    private Account createAccount(String reference, String name) {
        Account a = new Account();
        a.setReference(reference);
        a.setName(name);
        return a;
    }
}
