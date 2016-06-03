package coza.npda.dcm.smart.core.model;

import java.sql.SQLException;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.operation.DatabaseOperation;

import org.junit.Assert;
import org.junit.Test;
import org.ninjav.conan.core.model.User;

public class PersistentUserTest extends AbstractDbUnitJpaTest {

    @Test
    public void shouldFindById() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("user-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        User user = entityManager.find(User.class, 1L);
        Assert.assertNotNull(user);
        Assert.assertEquals("userTest", user.getUserName());
    }

    @Test
    public void shouldInsert() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("user-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        User newUser = new User("newUser");
        newUser.setPassword("newPassword");

        entityManager.getTransaction().begin();

        entityManager.persist(newUser);
        long id = newUser.getId();

        entityManager.getTransaction().commit();

        User user = entityManager.find(User.class, id);
        Assert.assertNotNull(user);
        Assert.assertEquals("newUser", user.getUserName());
    }

    @Test(expected = Exception.class)
    public void shouldNotInsertDuplicateUser() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("user-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);
        
        User duplicateUser = new User("userTest");
        
        entityManager.getTransaction().begin();

        entityManager.persist(duplicateUser);

        entityManager.getTransaction().commit();
    }

    @Test
    public void shouldFindAll() throws DataSetException, DatabaseUnitException, SQLException {
        loadDataFile("user-persistence-given-test-data.xml");
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

        List<User> allUsers = entityManager.createQuery("from User u").getResultList();
        Assert.assertEquals(2, allUsers.size());
    }
}
