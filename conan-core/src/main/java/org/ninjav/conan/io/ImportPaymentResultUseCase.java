package org.ninjav.conan.io;

import org.ninjav.conan.account.model.Account;
import org.ninjav.conan.core.Context;
import org.ninjav.conan.debitorder.model.DebitOrder;
import org.ninjav.conan.debitorder.model.RecoveryWorkflow;

import javax.persistence.NoResultException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by ninjav on 6/3/16.
 */
public class ImportPaymentResultUseCase implements ImportPaymentResultPort, DebitOrderDataSink {

    private Logger log = Logger.getLogger(ImportPaymentResultUseCase.class.getName());
    
    private static final SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");

    private Date currentDate;
    private Account currentAccount;
    private DebitOrderResultReader reader = new DebitOrderResultReader();

    @Override
    public void importResult(File file) {
        parseDate(extractDateFromFilename(file));

        reader.attach(this);
        reader.importResult(file);
    }

    @Override
    public void attach(DebitOrderDataSink sink) {
        reader.attach(sink);
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
        if (data.paymentCode != DebitOrder.PAID) {
            attachRecoveryWorkflow(data);
        }
    }

    private void attachRecoveryWorkflow(DebitOrderResultReader.DebitOrderData data) {
        Context.recoveryWorkflowGateway.beginTransaction();
        RecoveryWorkflow existingRecovery = findExistingRecovery(data);
        if (existingRecovery == null) {
            DebitOrder existing = Context.debitOrderGateway
                    .findDebitOrderByTransactionId(data.transactionId);
            Context.recoveryWorkflowGateway.save(createRecoveryWorkflow(existing));
        }
        Context.recoveryWorkflowGateway.commitTransaction();
    }

    private RecoveryWorkflow findExistingRecovery(DebitOrderResultReader.DebitOrderData data) {
        try {
            return Context.recoveryWorkflowGateway.findRecoveryByDebitOrderTransactionId(data.transactionId);
        } catch (NoResultException e) {
            return null;
        }
    }

    private RecoveryWorkflow createRecoveryWorkflow(DebitOrder existing) {
        RecoveryWorkflow r = new RecoveryWorkflow();
        r.setDebitOrder(existing);
        r.setStatus(RecoveryWorkflow.Status.NEW);
        return r;
    }

    private void updateAccount(DebitOrderResultReader.DebitOrderData data) {
        Context.accountGateway.beginTransaction();
        currentAccount = findExistingAccount(data.accountReference);
        if (currentAccount == null) {
            currentAccount = createAccount(data);
            log.info("Creating new account: " + currentAccount.getReference());
        } else {
            log.info("Reusing old account: " + currentAccount.getReference());
        }
        currentAccount = Context.accountGateway.save(currentAccount);
        Context.accountGateway.commitTransaction();
    }

    private Account findExistingAccount(String accountReference) {
        try {
            return Context.accountGateway.findAccountByReference(accountReference);
        } catch (NoResultException e) {
            return null;
        }
    }

    private Account createAccount(DebitOrderResultReader.DebitOrderData data) {
        Account a = new Account();
        a.setReference(data.accountReference);
        a.setName(data.accountName);
        return a;
    }

    private void updateDebitOrder(DebitOrderResultReader.DebitOrderData data) {
        Context.debitOrderGateway.beginTransaction();
        
        Account a = Context.accountGateway.findAccountByReference(data.accountReference);
        DebitOrder d = Context.debitOrderGateway.findDebitOrderByTransactionId(data.transactionId);
        if (d == null) {
            d = createDebitOrder(data);
        }
        d.setAccount(a);

        log.info("Saving new debit order: " + d.toString());
        Context.debitOrderGateway.save(d);

        Context.debitOrderGateway.commitTransaction();
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
