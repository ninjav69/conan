package org.ninjav.conan.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninjav on 6/4/16.
 */
public class DebitOrderResultReader implements DebitOrderDataSource {
    public static final int POS_TRANSACTION_ID = 0;
    public static final int POS_ACCOUNT_NAME = 1;
    public static final int POS_ACCOUNT_REFERENCE = 2;
    public static final int POS_DEBIT_AMOUNT = 6;
    public static final int POS_PAYMENT_CODE = 7;

    private List<DebitOrderDataSink> sinks = new ArrayList<>();

    public void importResult(File file) {
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new FileDoesNotExist(e.getMessage());
        }
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            throw new FileCannotBeRead(e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processLine(String line) {
        if (line.startsWith("TR Id")) {
            return;
        }
        if (line.isEmpty()) {
            return;
        }
        emit(parseDebitOrderData(line));
    }

    private DebitOrderData parseDebitOrderData(String line) {
        DebitOrderData d = new DebitOrderData();
        String[] tokens = line.split("\t");
        if (tokens.length < 9) {
            throw new InvalidRecord(line);
        }
        d.transactionId = Long.parseLong(tokens[POS_TRANSACTION_ID]);
        d.accountName = tokens[POS_ACCOUNT_NAME];
        d.accountReference = tokens[POS_ACCOUNT_REFERENCE];
        d.debitAmount = Double.parseDouble(tokens[POS_DEBIT_AMOUNT]);
        d.paymentCode = Integer.parseInt(tokens[POS_PAYMENT_CODE]);
        return d;
    }

    @Override
    public void emit(DebitOrderData data) {
        for (DebitOrderDataSink s : sinks) {
            s.handle(data);
        }
    }

    @Override
    public void attach(DebitOrderDataSink sink) {
        sinks.add(sink);
    }

    @Override
    public void detach(DebitOrderDataSink sink) {
        sinks.remove(sink);
    }

    public class FileDoesNotExist extends RuntimeException {
        public FileDoesNotExist(String message) {
            super(message);
        }
    }

    public class FileCannotBeRead extends RuntimeException {
        public FileCannotBeRead(String message) {
            super(message);
        }
    }

    public class InvalidRecord extends RuntimeException {
        public InvalidRecord(String line) {
            super("Offending line: " + line);
        }
    }

    public static class DebitOrderData {
        public Long transactionId;
        public String accountName;
        public String accountReference;
        public Double debitAmount;
        public int paymentCode;

        @Override
        public String toString() {
            return "DebitOrderData{" +
                    "transactionId=" + transactionId +
                    ", accountName='" + accountName + '\'' +
                    ", accountReference='" + accountReference + '\'' +
                    ", debitAmount=" + debitAmount +
                    ", paymentCode=" + paymentCode +
                    '}';
        }
    }
}