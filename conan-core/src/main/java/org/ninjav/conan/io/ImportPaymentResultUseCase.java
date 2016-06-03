package org.ninjav.conan.io;

import org.ninjav.conan.account.model.Account;
import org.ninjav.conan.core.Context;
import org.ninjav.conan.debitorder.model.DebitOrder;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ninjav on 6/3/16.
 */
public class ImportPaymentResultUseCase implements ImportPaymentResultPort, DebitOrderDataSink {

    private static final SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");

    private Date currentDate;
    private Account currentAccount;

    @Override
    public void importResult(File file) {
        parseDate(extractDateFromFilename(file));

        DebitOrderResultReader reader = new DebitOrderResultReader();
        reader.attach(this);
        reader.importResult(file);
    }

    private String extractDateFromFilename(File file) {
        return file.getName().replace(".xls", "");
    }

    private void parseDate(String dateString) {
        try {
            currentDate = df.parse(dateString);
        } catch (ParseException e) {
            throw new NotADebitOrderResultFile(e.getMessage());
        }
    }

    @Override
    public void handle(DebitOrderResultReader.DebitOrderData data) {
        updateAccount(data);
        updateDebitOrder(data);
    }

    private void updateAccount(DebitOrderResultReader.DebitOrderData data) {
        currentAccount = Context.accountGateway.save(createAccount(data));
    }

    private Account createAccount(DebitOrderResultReader.DebitOrderData data) {
        Account a = new Account();
        a.setReference(data.accountReference);
        a.setName(data.accountName);
        return a;
    }

    private void updateDebitOrder(DebitOrderResultReader.DebitOrderData data) {
        Context.debitOrderGateway.save(createDebitOrder(data));
    }

    private DebitOrder createDebitOrder(DebitOrderResultReader.DebitOrderData data) {
        DebitOrder d = new DebitOrder();
        d.setTransactionId(data.transactionId);
        d.setAmount(data.debitAmount);
        d.setDate(currentDate);
        d.setResultCode(data.paymentCode);
        d.setAccount(currentAccount);
        return d;
    }

    public class NotADebitOrderResultFile extends RuntimeException {
        public NotADebitOrderResultFile(String message) {
            super("Offense: " + message);
        }
    }
}
