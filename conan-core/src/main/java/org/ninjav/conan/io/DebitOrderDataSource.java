package org.ninjav.conan.io;

/**
 * Created by ninjav on 6/4/16.
 */
public interface DebitOrderDataSource {
    void emit(DebitOrderResultReader.DebitOrderData data);
    void attach(DebitOrderDataSink sink);
    void detach(DebitOrderDataSink sink);
}
