/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.core.user;

import org.ninjav.conan.core.Context;
import org.ninjav.conan.core.model.User;

/**
 *
 * @author ninjav
 */
public class SignInUseCase implements SignInPort {

    @Override
    public PresentableUser signIn(String userName, String password) {
        User user = Context.coreGateway.findUserByCredentials(userName, password);
        return createPresentableUser(user);
    }

    private PresentableUser createPresentableUser(User userEntity) {
        PresentableUser presentableUser = null;
        if (userEntity != null) {
            presentableUser = new PresentableUser();
            presentableUser.userId = userEntity.getId();
            presentableUser.userName = userEntity.getUserName();
        }
        return presentableUser;
    }
}
