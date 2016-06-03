/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.reconciler;

import org.ninjav.conan.transaction.PresentableTransaction;

import java.util.List;

/**
 *
 * @author Alan.Pickard
 */
public class ReconcileViewSpy extends ReconcileView {

    private int appendCreditTranssactionsCalled;
    private int appendDebitTransactionsCalled;
    private int clearCreditTransactionsCalled;
    private int clearDebitTransactionsCalled;
    private int updateSelectedCreditsLabelCalled;
    private int updateSelectedDebitsLabelCalled;
    private int updateSelectedBalanceLabelCalled;
    private int updateSelectedIsChronologicalCalled;
    private int enableReconcileButtonCalled;
    private int disableReconcileButtonCalled;
    private int resetCalled;

    public int getAppendCreditTranssactionsCalled() {
        return appendCreditTranssactionsCalled;
    }

    public int getAppendDebitTransactionsCalled() {
        return appendDebitTransactionsCalled;
    }

    public int getClearCreditTransactionsCalled() {
        return clearCreditTransactionsCalled;
    }

    public int getClearDebitTransactionsCalled() {
        return clearDebitTransactionsCalled;
    }

    public int getUpdateSelectedCreditsLabelCalled() {
        return updateSelectedCreditsLabelCalled;
    }

    public int getUpdateSelectedDebitsLabelCalled() {
        return updateSelectedDebitsLabelCalled;
    }

    public int getUpdateSelectedBalanceLabelCalled() {
        return updateSelectedBalanceLabelCalled;
    }

    public int getUpdateSelectedIsChronologicalCalled() {
        return updateSelectedIsChronologicalCalled;
    }

    public int getEnableReconcileButtonCalled() {
        return enableReconcileButtonCalled;
    }

    public int getDisableReconcileButtonCalled() {
        return disableReconcileButtonCalled;
    }

    public int getResetCalled() {
        return resetCalled;
    }

    @Override
    protected void appendCreditTransactions(List<PresentableTransaction> transactions) {
        appendCreditTranssactionsCalled++;
    }

    @Override
    protected void appendDebitTransactions(List<PresentableTransaction> transactions) {
        appendDebitTransactionsCalled++;
    }

    @Override
    protected void clearCreditTransactions() {
        clearCreditTransactionsCalled++;
    }

    @Override
    protected void clearDebitTransactions() {
        clearDebitTransactionsCalled++;
    }

    @Override
    protected void updateSelectedCreditsLabel(String formatRandAmount) {
        updateSelectedCreditsLabelCalled++;
    }

    @Override
    protected void updateSelectedDebitsLabel(String formatRandAmount) {
        updateSelectedDebitsLabelCalled++;
    }

    @Override
    protected void updateSelectedBalanceLabel(String amountBalance) {
        updateSelectedBalanceLabelCalled++;
    }

    @Override
    protected void updateSelectedIsChronological(String chronological) {
        updateSelectedIsChronologicalCalled++;
    }

    @Override
    protected void enableReconcileButton() {
        enableReconcileButtonCalled++;
    }

    @Override
    protected void disableReconcileButton() {
        disableReconcileButtonCalled++;
    }

    @Override
    protected void reset() {
        resetCalled++;
    }

}
