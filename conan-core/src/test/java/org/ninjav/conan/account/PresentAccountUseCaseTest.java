package org.ninjav.conan.account;

import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.account.model.Account;
import org.ninjav.conan.core.Context;

import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by ninjav on 6/3/16.
 */
public class PresentAccountUseCaseTest {

    PresentAccountUseCase useCase;

    @Before
    public void setup() {
        Context.accountGateway = new MockAccountGateway();
        useCase = new PresentAccountUseCase();
    }

    @Test
    public void whenNoAccounts_presentNoAccounts() {
        List<PresentableAccount> result = useCase.presentAccounts();
        assertThat(result.size(), is(equalTo(0)));
    }

    @Test
    public void whenOneAccount_presentOneAccount() {
        Context.accountGateway.save(createAccount("0001", "Alan Pickard"));

        List<PresentableAccount> result = useCase.presentAccounts();

        assertThat(result.size(), is(equalTo(1)));
    }

    @Test
    public void whenFilteredByReference_presentFilteredByReference() {
        Context.accountGateway.save(createAccount("0001", "Alan Pickard"));
        Context.accountGateway.save(createAccount("0002", "Koos Komkommer"));
        Context.accountGateway.save(createAccount("0003", "Jan Fiskaal"));

        useCase.setFilterText("0001");
        List<PresentableAccount> result = useCase.presentAccounts();

        assertThat(result.size(), is(equalTo(1)));
        assertThat(result.get(0).reference, is(equalTo("0001")));
    }

    @Test
    public void whenFilteredByName_presentFileredByName() {
        Context.accountGateway.save(createAccount("0001", "Alan Pickard"));
        Context.accountGateway.save(createAccount("0002", "Koos Komkommer"));
        Context.accountGateway.save(createAccount("0003", "Jan Fiskaal"));

        useCase.setFilterText("Pickard");
        List<PresentableAccount> result = useCase.presentAccounts();

        assertThat(result.size(), is(equalTo(1)));
        assertThat(result.get(0).name, is(equalTo("Alan Pickard")));
    }

    private Account createAccount(String reference, String name) {
        Account a = new Account();
        a.setReference(reference);
        a.setName(name);
        return a;
    }
}
