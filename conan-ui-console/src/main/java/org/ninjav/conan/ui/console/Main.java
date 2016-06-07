/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.console;

import java.io.File;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.ninjav.conan.account.persistence.JPA2AccountGateway;
import org.ninjav.conan.account.persistence.JPA2AccountGatewayFactory;
import org.ninjav.conan.core.Context;
import org.ninjav.conan.core.persistence.JPA2CoreGateway;
import org.ninjav.conan.core.persistence.JPA2CoreGatewayFactory;
import org.ninjav.conan.debitorder.persistence.JPA2DebitOrderGateway;
import org.ninjav.conan.debitorder.persistence.JPA2DebitOrderGatewayFactory;
import org.ninjav.conan.io.ImportPaymentResultPort;
import org.ninjav.conan.io.ImportPaymentResultUseCase;
import org.ninjav.conan.transaction.persistence.JPA2TransactionGateway;
import org.ninjav.conan.transaction.persistence.JPA2TransactionGatewayFactory;

/**
 *
 * @author ninjav
 */
public class Main {
    private ImportPaymentResultPort importPort;
    private EntityManagerFactory emf;
    private EntityManager em;

    public Main() {
        emf = Persistence.createEntityManagerFactory("persistence");
        em = emf.createEntityManager();
        Context.accountGateway = new JPA2AccountGateway(em);
        Context.coreGateway = new JPA2CoreGateway(em);
        Context.debitOrderGateway = new JPA2DebitOrderGateway(em);
        Context.transactionGateway = new JPA2TransactionGateway(em);
        
        importPort = new ImportPaymentResultUseCase();
    }
    
    public void importFiles(String[] fileNames) {
        for (String fileName : fileNames) {
            importPort.importResult(new File(fileName));
        }
        em.close();
        emf.close();
    }
    
    public static void main(String[] args) {
        new Main().importFiles(args);
    }
}
