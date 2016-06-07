package org.ninjav.conan.account;

import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.account.persistence.FinancialsGateway;
import org.ninjav.conan.core.Context;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by ninjav on 6/7/16.
 */
public class PresentFinancialsUseCaseTest {


    @Test
    public void givenNoStatistics_hasZeroStatistics() {
        MockNoFinancialsGateway gateway = new MockNoFinancialsGateway();
        Context.financialsGateway = gateway;
        PresentFinancialsUseCase useCase = new PresentFinancialsUseCase();
        PresentableFinancials fin = useCase.presentFinancials();
        assertThat(fin, is(notNullValue()));
        assertThat(fin.numberOfAccounts, is(equalTo(0L)));
    }

    @Test
    public void givenStatistics_hasStatistics() {
        MockSomeFinancialsGateway gateway = new MockSomeFinancialsGateway();
        Context.financialsGateway = gateway;
        PresentFinancialsUseCase useCase = new PresentFinancialsUseCase();
        PresentableFinancials fin = useCase.presentFinancials();
        assertThat(fin, is(notNullValue()));
        assertThat(fin.numberOfAccounts, is(equalTo(4L)));
        assertThat(gateway.findTotalFundsCalled, is(equalTo(1)));
    }
}
