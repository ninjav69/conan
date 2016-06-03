/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.core;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.core.user.PresentableUser;
import org.ninjav.conan.core.user.SignInPort;
import org.ninjav.conan.core.user.SignOutPort;

/**
 *
 * @author Alan.Pickard
 */
public class MainPresenterTest {

    private MainViewSpy mainViewSpy;
    private MainModelSpy mainModelSpy;
    private MainPresenter presenter;

    @Before
    public void setup() {
        mainViewSpy = new MainViewSpy();
        mainModelSpy = new MainModelSpy();
        presenter = new MainPresenter(mainViewSpy, mainModelSpy);
    }

    @Test
    public void canCreatePresenter() {
        assertThat(presenter, is(notNullValue()));
    }

    @Test
    public void whenSignInFail_mustShowLockedDisplay() {
        presenter.setSignInPort(new SignInPort() {

            @Override
            public PresentableUser signIn(String userName, String password) {
                return null;
            }
        });
        presenter.signIn("userName", "password");

        assertThat(mainModelSpy.getSetLoggedInUserCalled(), is(1));
        assertThat(mainModelSpy.getSetLoggedInUserIdCalled(), is(1));
        assertThat(mainViewSpy.getResetUserControlsCalled(), is(0));
        assertThat(mainViewSpy.getShowUserLogoutPanelCalled(), is(0));
        assertThat(mainViewSpy.getShowUserLoginPanelCalled(), is(1));
        assertThat(mainViewSpy.getUpdateWelcomeLabelCalled(), is(1));
        assertThat(mainViewSpy.getUpdateModulesCalled(), is(1));
    }

    @Test
    public void whenSignInSuccess_mustShowUnlockedDisplay() {
        presenter.setSignInPort(new SignInPort() {

            @Override
            public PresentableUser signIn(String userName, String password) {
                PresentableUser u = new PresentableUser();
                u.userId = 1L;
                u.userName = "userName";
                return u;
            }
        });
        presenter.signIn("userName", "password");

        assertThat(mainModelSpy.getSetLoggedInUserCalled(), is(1));
        assertThat(mainModelSpy.getSetLoggedInUserIdCalled(), is(1));
        assertThat(mainViewSpy.getResetUserControlsCalled(), is(1));
        assertThat(mainViewSpy.getShowUserLogoutPanelCalled(), is(1));
        assertThat(mainViewSpy.getUpdateWelcomeLabelCalled(), is(1));
        assertThat(mainViewSpy.getUpdateModulesCalled(), is(1));
    }

    @Test
    public void whenSignOut_mustShowLockedDisplay() {
        presenter.setSignOutPort(new SignOutPort() {

            @Override
            public void signOut(String userName) {

            }
        });
        presenter.signOut();

        assertThat(mainModelSpy.getSetLoggedInUserCalled(), is(1));
        assertThat(mainModelSpy.getSetLoggedInUserIdCalled(), is(1));
        assertThat(mainViewSpy.getResetUserControlsCalled(), is(0));
        assertThat(mainViewSpy.getShowUserLogoutPanelCalled(), is(0));
        assertThat(mainViewSpy.getShowUserLoginPanelCalled(), is(1));
        assertThat(mainViewSpy.getUpdateWelcomeLabelCalled(), is(1));
        assertThat(mainViewSpy.getUpdateModulesCalled(), is(1));
    }
}
