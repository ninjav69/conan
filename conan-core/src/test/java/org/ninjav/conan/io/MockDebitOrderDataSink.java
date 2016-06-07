package org.ninjav.conan.io;

/**
 * Created by ninjav on 6/7/16.
 */
public class MockDebitOrderDataSink implements DebitOrderDataSink {
    public static int handleCalled = 0;

    @Override
    public void handle(DebitOrderResultReader.DebitOrderData data) {
        handleCalled++;
    }
}
