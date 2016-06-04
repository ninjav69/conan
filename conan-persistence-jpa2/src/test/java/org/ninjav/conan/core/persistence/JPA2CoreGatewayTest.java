package org.ninjav.conan.core.persistence;

import org.dbunit.PrepAndExpectedTestCase;
import org.dbunit.VerifyTableDefinition;
import org.junit.Before;
import org.junit.Test;
import org.ninjav.conan.core.model.License;
import org.ninjav.conan.core.model.Module;
import org.ninjav.conan.core.model.User;
import org.ninjav.conan.util.MyDatabaseTester;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by ninjav on 6/4/16.
 */
public class JPA2CoreGatewayTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    private PrepAndExpectedTestCase tc;
    private JPA2CoreGateway sut;

    @Before
    public void setup () throws ClassNotFoundException {
        emf = Persistence.createEntityManagerFactory("persistence-test");
        em = emf.createEntityManager();
        tc = new MyDatabaseTester();
        sut = new JPA2CoreGateway(em);
    }

    @Test
    public void testFindAllModules() throws Exception {
        final String[] prepDataFiles = {"/core/select-module-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        List<Module> results = sut.findAllModules();
        assertThat(results.size(), is(equalTo(2)));
    }

    @Test
    public void testDeleteModule() throws Exception {
        final String[] prepDataFiles = {"/core/select-module-test-data.xml"};
        final String[] expectedDataFiles = {"/core/after-delete-module-expected.xml"};
        final VerifyTableDefinition[] tables = {
            new VerifyTableDefinition("AppModule", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        Module m = sut.findModuleByName("moduleTest");
        sut.delete(m);
        sut.commitTransaction();

        tc.postTest();
    }

    @Test
    public void testSaveModule() throws Exception {
        final String[] prepDataFiles = {"/core/select-module-test-data.xml"};
        final String[] expectedDataFiles = {"/core/after-save-module-expected.xml"};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("AppModule", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        sut.save(createModule("moduleTest3", "moduleTest3 description"));
        sut.commitTransaction();

        tc.postTest();
    }

    private Module createModule(String name, String description) {
        Module m = new Module();
        m.setName(name);
        m.setDescription(description);
        return m;
    }

    @Test
    public void testSaveUser() throws Exception {
        final String[] prepDataFiles = {"/core/select-user-test-data.xml"};
        final String[] expectedDataFiles = {"/core/after-save-user-expected.xml"};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("AppUser", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        sut.save(createUser("userTest3", "password3"));
        sut.commitTransaction();

        tc.postTest();
    }

    private User createUser(String username, String password) {
        User u = new User();
        u.setUserName(username);
        u.setPassword(password);
        return u;
    }

    @Test
    public void testSaveLicense() throws Exception {
        final String[] prepDataFiles = {"/core/select-license-test-data.xml"};
        final String[] expectedDataFiles = {"/core/after-save-license-expected.xml"};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("AppUser", new String[]{}),
                new VerifyTableDefinition("AppModule", new String[]{}),
                new VerifyTableDefinition("AppLicense", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();

        User u = sut.findUser("userTest");
        Module m = sut.findModuleByName("moduleTest2");

        sut.save(createLicense(u, m));
        sut.commitTransaction();

        tc.postTest();
    }

    private License createLicense(User u, Module m) {
        License l = new License(u, m);
        return l;
    }

    @Test
    public void testFindUser() throws Exception {
        final String[] prepDataFiles = {"/core/select-user-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        User u = sut.findUser("userTest");
        sut.commitTransaction();

        assertThat(u, is(notNullValue()));
        assertThat(u.getUserName(), is(equalTo("userTest")));
    }

    @Test
    public void testFindModuleByName() throws Exception {
        final String[] prepDataFiles = {"/core/select-module-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        Module m = sut.findModuleByName("moduleTest");
        sut.commitTransaction();

        assertThat(m, is(notNullValue()));
        assertThat(m.getDescription(), is(equalTo("moduleTest description")));
    }

    @Test
    public void testFindLicensesForUserAndModule() throws Exception {
        final String[] prepDataFiles = {"/core/select-license-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        User u = sut.findUser("userTest");
        Module m = sut.findModuleByName("moduleTest");
        List<License> l = sut.findLicensesForUserAndModule(u, m);
        sut.commitTransaction();

        assertThat(l, is(notNullValue()));
        assertThat(l.size(), is(equalTo(1)));
    }

    @Test
    public void testFindAllUsers() throws Exception {
        final String[] prepDataFiles = {"/core/select-user-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        List<User> u = sut.findAllUsers();
        sut.commitTransaction();

        assertThat(u, is(notNullValue()));
        assertThat(u.size(), is(equalTo(2)));
    }

    @Test
    public void testDeleteUser() throws Exception {
        final String[] prepDataFiles = {"/core/select-user-test-data.xml"};
        final String[] expectedDataFiles = {"/core/after-delete-user-expected.xml"};
        final VerifyTableDefinition[] tables = {
                new VerifyTableDefinition("AppUser", new String[]{})
        };
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        User u = sut.findUser("userTest");
        sut.delete(u);
        sut.commitTransaction();

        tc.postTest();
    }

    @Test
    public void testFindUserByCredentials() throws Exception {
        final String[] prepDataFiles = {"/core/select-user-test-data.xml"};
        final String[] expectedDataFiles = {};
        final VerifyTableDefinition[] tables = {};
        tc.preTest(tables, prepDataFiles, expectedDataFiles);

        sut.beginTransaction();
        User u = sut.findUserByCredentials("userTest", "password");
        sut.commitTransaction();

        assertThat(u, is(notNullValue()));
    }
}