package org.ninjav.conan.debitorder;

import java.util.List;

/**
 * Created by ninjav on 6/3/16.
 */
public interface PresentDebitOrderPort {
    List<PresentableDebitOrder> presentDebitOrders();

    void setFilterText(String filterText);
}
