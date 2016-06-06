/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.statement;

import java.io.File;
import org.ninjav.conan.debitorder.model.DebitOrder;
import org.ninjav.conan.io.DebitOrderDataSink;
import org.ninjav.conan.io.DebitOrderResultReader;
import org.ninjav.conan.io.ImportPaymentResultPort;
import org.ninjav.conan.io.ImportPaymentResultUseCase;
import org.ninjav.conan.logger.LogEventUseCase;

/**
 *
 * @author ninjav
 */
public class StatementPresenter implements DebitOrderDataSink {
    private StatementView view;
    
    private ImportPaymentResultPort importPort;
    
    public StatementPresenter(StatementView view) {
        this.view = view;
    }

    public void setImportPort(ImportPaymentResultPort importPort) {
        this.importPort = importPort;
        this.importPort.attach(this);
    }

    @Override
    public void handle(DebitOrderResultReader.DebitOrderData dod) {
        new LogEventUseCase().logInfo(makeLogLine(dod));
    }

    private String makeLogLine(DebitOrderResultReader.DebitOrderData dod) {
        StringBuilder sb = new StringBuilder();
        sb.append("New ");
        switch (dod.paymentCode) {
            case DebitOrder.PAID:
                sb.append("paid ");
            default:
                sb.append("unpaid ");
        }
        sb.append("debit order for ");
        sb.append(dod.accountName).append(" ");
        sb.append("valued at ");
        sb.append(dod.debitAmount);
        return sb.toString();
    }

    public void importStatement(String fileName) {
        File statement = new File(fileName);
        if (statement.canRead()) {
            try {
                importPort.importResult(statement);
            } catch (ImportPaymentResultUseCase.NotADebitOrderResultFile ex) {
                new LogEventUseCase().logError(ex.getMessage());
            } catch (Exception ex) {
                new LogEventUseCase().logError("File cound not be imported.");
                ex.printStackTrace();
            }
        } else {
            new LogEventUseCase().logError(fileName + " cannot be read.");
        }
    }
}
