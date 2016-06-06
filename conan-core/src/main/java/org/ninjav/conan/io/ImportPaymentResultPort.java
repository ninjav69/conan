package org.ninjav.conan.io;

import java.io.File;

/**
 * Created by ninjav on 6/3/16.
 */
public interface ImportPaymentResultPort {
    void importResult(File file);
    void attach(DebitOrderDataSink sink);
}
