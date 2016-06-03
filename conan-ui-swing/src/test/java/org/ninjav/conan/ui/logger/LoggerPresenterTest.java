/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.ui.logger;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.logger.LogEventPort;

/**
 *
 * @author ninjav
 */
public class LoggerPresenterTest {
    private LoggerViewSpy loggerViewSpy;
    private LoggerPresenter presenter;
    private LogEventPort fakeEventPort;

    @Before
    public void setup() {
        loggerViewSpy = new LoggerViewSpy();
        presenter = new LoggerPresenter(loggerViewSpy);
        fakeEventPort = new LoggerEventPortFake();
        fakeEventPort.addListener(presenter);
    }
    
    @Test
    public void givenLogEvent_showLogEvent() {
        fakeEventPort.logInfo("event");
        
        assertThat(loggerViewSpy.getLogCalled(), is(1));
    }
}
