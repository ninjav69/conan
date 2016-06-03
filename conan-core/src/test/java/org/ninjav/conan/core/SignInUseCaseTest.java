/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.core;

import org.ninjav.conan.core.user.SignInUseCase;
import org.ninjav.conan.core.model.User;
import org.ninjav.conan.core.persistence.MockCoreGateway;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author ninjav
 */
public class SignInUseCaseTest {
    
    private SignInUseCase useCase;

    @Before
    public void setUp() {
        Context.coreGateway = new MockCoreGateway();
//        useCase = new SignInUseCase();
    }
    
    @Test
    @Ignore
    public void givenNoUsers_cannotSignIn() {
        String username = "userName";
        String password = "password";
        
        //User loggedInUser = useCase.signIn(username, password);
        
        //assertThat(loggedInUser, is(nullValue()));
    }
    
    @Test
    public void givenWrongCredentials_cannotSignIn() {
        createUsersInDatabase();
        
        String username = "foo";
        String password = "bar";
        
//        User loggedInUser = useCase.signIn(username, password);
        
//        assertThat(loggedInUser, is(nullValue()));
    }
    
    @Test
    public void givenCorrectCredentials_canSignIn() {
        createUsersInDatabase();
        
        String username = "userName1";
        String password = "password1";
        
//        User loggedInUser = useCase.signIn(username, password);
        
//        assertThat(loggedInUser, is(notNullValue()));
//        assertThat(loggedInUser.getUserName(), is(equalTo(username)));
//        assertThat(loggedInUser.getPassword(), is(equalTo(password)));
    }

    private void createUsersInDatabase() {
        Context.coreGateway.save(createUser("userName1", "password1"));
        Context.coreGateway.save(createUser("userName2", "password2"));
        Context.coreGateway.save(createUser("userName3", "password3"));
    }

    private User createUser(String userName, String password) {
        User u = new User();
        u.setUserName(userName);
        u.setPassword(password);
        return u;
    }
}
