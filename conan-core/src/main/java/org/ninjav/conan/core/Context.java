package org.ninjav.conan.core;

import org.ninjav.conan.account.persistence.AccountGateway;
import org.ninjav.conan.core.persistence.CoreGateway;
import org.ninjav.conan.debitorder.persistence.DebitOrderGateway;
import org.ninjav.conan.transaction.persistence.TransactionGateway;


public class Context {
  public static CoreGateway coreGateway;
  public static TransactionGateway transactionGateway;
  public static AccountGateway accountGateway;
  public static DebitOrderGateway debitOrderGateway;
}
