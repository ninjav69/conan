package org.ninjav.conan.io;

/**
 * Created by ninjav on 6/4/16.
 */
public interface DebitOrderDataSink {
    void handle(DebitOrderResultReader.DebitOrderData data);
}
