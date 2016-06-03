package org.ninjav.conan.ui.reconciler;

import org.ninjav.conan.transaction.PresentableTransaction;

import java.util.List;

/**
 *
 * @author Alan.Pickard
 */
public abstract class ReconcileView {
    protected ReconcilePresenter presenter;

    public void setPresenter(ReconcilePresenter presenter) {
        this.presenter = presenter;
    }

    protected abstract void reset();

    protected abstract void clearCreditTransactions();
    protected abstract void appendCreditTransactions(List<PresentableTransaction> transactions);

    protected abstract void clearDebitTransactions();
    protected abstract void appendDebitTransactions(List<PresentableTransaction> transactions);

    protected abstract void updateSelectedCreditsLabel(String creditAmount);
    protected abstract void updateSelectedDebitsLabel(String debitAmount);
    protected abstract void updateSelectedBalanceLabel(String amountBalance);
    protected abstract void updateSelectedIsChronological(String chronological);
    protected abstract void enableReconcileButton();
    protected abstract void disableReconcileButton();
}
