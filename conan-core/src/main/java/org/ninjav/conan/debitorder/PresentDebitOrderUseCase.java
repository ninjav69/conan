package org.ninjav.conan.debitorder;

import org.ninjav.conan.core.Context;
import org.ninjav.conan.debitorder.PresentDebitOrderPort;
import org.ninjav.conan.debitorder.PresentableDebitOrder;
import org.ninjav.conan.debitorder.model.DebitOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ninjav on 6/3/16.
 */
public class PresentDebitOrderUseCase implements PresentDebitOrderPort {

    private String filterText;

    @Override
    public List<PresentableDebitOrder> presentDebitOrders() {
        List<PresentableDebitOrder> result = new ArrayList<>();
        for (DebitOrder d : findDebitOrders()) {
            PresentableDebitOrder p = new PresentableDebitOrder();
            p.transactionId = d.getTransactionId();
            p.amount = d.getAmount();
            p.date = d.getDate();
            p.result = extractUnpaidType(d.getResultCode());
            result.add(p);
        }
        return result;
    }

    private List<DebitOrder> findDebitOrders() {
        if (filterText == null || filterText.isEmpty()) {
            return Context.debitOrderGateway.findAllDebitOrders();
        } else {
            return Context.debitOrderGateway.findDebitOrdersMatchingAccountReference(filterText);
        }
    }

    private PresentableDebitOrder.Result extractUnpaidType(Integer resultCode) {
        switch (resultCode) {
            case DebitOrder.PAID:
                return PresentableDebitOrder.Result.PAID;
            case DebitOrder.ACCOUNT_FROZEN:
                return PresentableDebitOrder.Result.ACCOUNT_FROZEN;

            case DebitOrder.ACCOUNT_IN_SEQUISTRATION:
                return PresentableDebitOrder.Result.ACCOUNT_IN_SEQUISTRATION;

            case DebitOrder.INSUFFICIENT_FUNDS:
                return PresentableDebitOrder.Result.INSUFFICIENT_FUNDS;

            case DebitOrder.NOT_A_DEBIT_ACCOUNT:
                return PresentableDebitOrder.Result.NOT_A_DEBIT_ACCOUNT;

            case DebitOrder.PAYMENT_STOPPED:
                return PresentableDebitOrder.Result.PAYMENT_STOPPED;
            default:
                return PresentableDebitOrder.Result.OTHER;
        }
    }

    @Override
    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
}
