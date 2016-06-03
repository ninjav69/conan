package org.ninjav.conan.transaction.fixtures;

import org.ninjav.conan.transaction.PresentDebitTransactionUseCase;
import org.ninjav.conan.transaction.PresentableTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Query
public class OfDebitTransactions {

    private List<Object> list(Object... objects) {
        return Arrays.asList(objects);
    }

    public List<Object> query() {
        PresentDebitTransactionUseCase useCase = new PresentDebitTransactionUseCase();
        // NOTE: not liking the global filter. Shall we externalize it like the GateKeeper?
        useCase.setFilterText(BankAccountTransactionPresentation.filterText);
        List<PresentableTransaction> presentableTransactions = useCase.presentLegacyTransactions();
        List<Object> queryResponse = new ArrayList<>();
        presentableTransactions.stream().forEach((pt) -> {
            queryResponse.add(makeRow(pt.date, pt.reference, pt.amount));
        });
        return queryResponse;

    }

    private List<Object> makeRow(String date, String reference, String amount) {
        return list(
                new Object[]{
                    list("date", date),
                    list("reference", reference),
                    list("amount", amount)}
        );
    }
}
