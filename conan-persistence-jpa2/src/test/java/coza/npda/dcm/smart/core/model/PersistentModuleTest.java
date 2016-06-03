package coza.npda.dcm.smart.core.model;

import java.sql.SQLException;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.operation.DatabaseOperation;

import org.junit.Assert;
import org.junit.Test;
import org.ninjav.conan.core.model.Module;

public class PersistentModuleTest extends AbstractDbUnitJpaTest {

    @Test
    public void shouldFindById() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("module-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        Module module = entityManager.find(Module.class, 1L);
        Assert.assertNotNull(module);
        Assert.assertEquals("moduleTest", module.getName());
    }

    @Test
    public void shouldInsert() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("module-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        Module newModule = new Module("newModule");

        entityManager.getTransaction().begin();

        entityManager.persist(newModule);
        long id = newModule.getId();

        entityManager.getTransaction().commit();

        Module module = entityManager.find(Module.class, id);
        Assert.assertNotNull(module);
        Assert.assertEquals("newModule", module.getName());
    }

    @Test(expected = Exception.class)
    public void shouldNotInsertDuplicateModule() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("module-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);
        
        Module duplicateModule = new Module("moduleTest");
        
        entityManager.getTransaction().begin();

        entityManager.persist(duplicateModule);

        entityManager.getTransaction().commit();
    }

    @Test
    public void shouldFindAll() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("module-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        List<Module> allModules = entityManager.createQuery("from Module u").getResultList();
        Assert.assertEquals(2, allModules.size());
    }
}
