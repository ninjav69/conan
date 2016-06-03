package coza.npda.dcm.smart.core.model;

import java.sql.SQLException;
import java.util.List;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.operation.DatabaseOperation;

import org.junit.Assert;
import org.junit.Test;
import org.ninjav.conan.core.model.License;
import org.ninjav.conan.core.model.Module;
import org.ninjav.conan.core.model.User;

public class PersistentLicenseTest extends AbstractDbUnitJpaTest {

    @Test
    public void shouldFindById() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("license-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        License license = entityManager.find(License.class, 1L);
        Assert.assertNotNull(license);
        Assert.assertEquals("userTest", license.getUser().getUserName());
        Assert.assertEquals("moduleTest", license.getModule().getName());
    }

    @Test
    public void shouldInsert() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("license-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        User user = entityManager.find(User.class, 1L);
        Module module = entityManager.find(Module.class, 2L);

        License newLicense = new License(user, module);

        entityManager.getTransaction().begin();

        entityManager.persist(newLicense);
        long id = newLicense.getId();

        entityManager.getTransaction().commit();

        License license = entityManager.find(License.class, id);
        Assert.assertNotNull(license);
    }

    @Test(expected = Exception.class)
    public void shouldNotInsertDuplicateLicense() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("license-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        User user = entityManager.find(User.class, 1L);
        Module module = entityManager.find(Module.class, 1L);

        License duplicateLicense = new License(user, module);

        entityManager.getTransaction().begin();

        try {
            entityManager.persist(duplicateLicense);
        } finally {
            entityManager.getTransaction().rollback();
        }
        entityManager.getTransaction().commit();
    }

    @Test
    public void shouldFindAll() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("license-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        List<License> allLicenses = entityManager.createQuery("from License u").getResultList();
        Assert.assertEquals(2, allLicenses.size());
    }
}
