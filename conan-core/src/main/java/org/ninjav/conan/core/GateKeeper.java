package org.ninjav.conan.core;

import org.ninjav.conan.core.model.User;

public class GateKeeper {
  private User loggedInUser;

  public void setLoggedInUser(User loggedInUser) {
    this.loggedInUser = loggedInUser;
  }

  public User getLoggedInUser() {
    return loggedInUser;
  }
}
