package org.ninjav.conan.account;

import java.util.List;

/**
 * Created by ninjav on 6/3/16.
 */
public interface PresentAccountPort {

    List<PresentableAccount> presentAccounts();

    public void setFilterText(String filterText);
}
