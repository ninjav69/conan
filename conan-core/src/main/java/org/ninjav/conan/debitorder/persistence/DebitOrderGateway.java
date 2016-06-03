package org.ninjav.conan.debitorder.persistence;

import org.ninjav.conan.account.model.Account;
import org.ninjav.conan.core.persistence.Gateway;
import org.ninjav.conan.debitorder.model.DebitOrder;

import java.util.List;

/**
 * Created by ninjav on 6/3/16.
 */
public interface DebitOrderGateway  extends Gateway{
    DebitOrder save(DebitOrder debitOrder);

    void delete(DebitOrder debitOrder);

    DebitOrder findDebitOrderByTransactionId(Long transactionId);

    List<DebitOrder> findAccountsWhereTransactionIdIn(List<Long> transactionIds);

    List<DebitOrder> findAllDebitOrders();

    List<DebitOrder> findDebitOrdersMatchingAccountReference(String filterText);
}
