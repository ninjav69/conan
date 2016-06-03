package org.ninjav.conan.debitorder;

import org.ninjav.conan.account.model.Account;
import org.ninjav.conan.debitorder.model.DebitOrder;
import org.ninjav.conan.debitorder.persistence.DebitOrderGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ninjav on 6/3/16.
 */
public class MockDebitOrderGateway implements DebitOrderGateway {
    private Map<Long, DebitOrder> debitOrders = new HashMap<>();

    @Override
    public DebitOrder save(DebitOrder debitOrder) {
        debitOrders.put(debitOrder.getTransactionId(), debitOrder);
        return debitOrder;
    }

    @Override
    public void delete(DebitOrder debitOrder) {
        debitOrders.remove(debitOrder.getTransactionId());
    }

    @Override
    public DebitOrder findDebitOrderByTransactionId(Long transactionId) {
        return debitOrders.get(transactionId);
    }

    @Override
    public List<DebitOrder> findAccountsWhereTransactionIdIn(List<Long> transactionIds) {
        List<DebitOrder> result = new ArrayList<>();
        for (Long id : transactionIds) {
            if (debitOrders.containsKey(id)) {
                result.add(debitOrders.get(id));
            }
        }
        return result;
    }

    @Override
    public List<DebitOrder> findAllDebitOrders() {
        return new ArrayList<>(debitOrders.values());
    }

    @Override
    public List<DebitOrder> findDebitOrdersMatchingAccountReference(String filterText) {
        List<DebitOrder> result = new ArrayList<>();
        for (DebitOrder d : debitOrders.values()) {
            if (d.getAccount().getReference().contains(filterText)) {
                result.add(d);
            }
        }
        return result;
    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void commitTransaction() {

    }

    @Override
    public void rollbackTransaction() {

    }
}
