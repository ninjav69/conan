package org.ninjav.conan.io;

import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.account.MockAccountGateway;
import org.ninjav.conan.core.Context;
import org.ninjav.conan.debitorder.MockDebitOrderGateway;
import org.ninjav.conan.debitorder.model.DebitOrder;

import java.io.File;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import org.junit.Ignore;

/**
 * Created by ninjav on 6/3/16.
 */
public class ImportPaymentResultUseCaseTest {

    private ImportPaymentResultUseCase useCase;

    @Before
    public void setup() {
        Context.accountGateway = new MockAccountGateway();
        Context.debitOrderGateway = new MockDebitOrderGateway();
        useCase = new ImportPaymentResultUseCase();
    }

    @Test(expected = ImportPaymentResultUseCase.NotADebitOrderResultFile.class)
    public void whenFilenameBad_mustThrow() {
        useCase.importResult(new File("koos.txt"));
    }

    @Test(expected = DebitOrderResultReader.FileDoesNotExist.class)
    public void whenFileNotExists_mustThrow() {
        useCase.importResult(new File("01012016.xls"));
    }

    @Test
    @Ignore
    public void whenFileExists_mustRead() {
        useCase.importResult(new File("/tmp/15082015.xls"));
        assertDataImported();
    }

    private void assertDataImported() {
        assertThat(Context.accountGateway.findAllAccounts(), is(not(empty())));
        assertThat(Context.debitOrderGateway.findAllDebitOrders(), is(not(empty())));
        assertThat(Context.debitOrderGateway.findAllDebitOrders().size(), is(equalTo(94)));
    }
}