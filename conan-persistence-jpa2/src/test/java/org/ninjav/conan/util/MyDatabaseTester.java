package org.ninjav.conan.util;

import org.dbunit.DefaultPrepAndExpectedTestCase;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

/**
 * Created by ninjav on 6/4/16.
 */
public class MyDatabaseTester extends DefaultPrepAndExpectedTestCase {
    public MyDatabaseTester() throws ClassNotFoundException {
        setDatabaseTester(new JdbcDatabaseTester(
                "org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:standalone-test", "sa", ""));
        setDataFileLoader(new FlatXmlDataFileLoader());
    }
}
