/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction;

import java.util.List;

/**
 *
 * @author Alan.Pickard
 */
public interface PresentDebitTransactionPort {

    public List<PresentableTransaction> presentLegacyTransactions();

    public void setFilterText(String filterText);

}
