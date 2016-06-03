package coza.npda.dcm.smart.core.model;

import java.io.InputStream;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.HibernateException;
import org.hibernate.internal.SessionImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Abstract unit test case class. This will load the test-data.xml dataset
 * before each test case and will clean the database before each test
 */
public abstract class AbstractDbUnitJpaTest {

    private static EntityManagerFactory entityManagerFactory;
    protected static IDatabaseConnection connection;
    protected static IDataSet dataset;
    protected static EntityManager entityManager;

    /**
     * Will load test-dataset.xml before each test case
     *
     * @throws DatabaseUnitException
     * @throws HibernateException
     */
    @BeforeClass
    public static void initEntityManager() throws HibernateException, DatabaseUnitException {
        entityManagerFactory = Persistence.createEntityManagerFactory("persistence-test");
        entityManager = entityManagerFactory.createEntityManager();
        connection = new DatabaseConnection(((SessionImpl) (entityManager.getDelegate())).connection());
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        loadDataFile("empty-test-data.xml");
    }

    public static void loadDataFile(String dataFile) throws DataSetException {
        FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
        flatXmlDataSetBuilder.setColumnSensing(true);
        InputStream dataSet = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(dataFile);
        dataset = flatXmlDataSetBuilder.build(dataSet);
    }

    @AfterClass
    public static void closeEntityManager() throws SQLException, DatabaseUnitException {
        entityManager.close();
        entityManagerFactory.close();
    }

//    @Before
//    public void cleanDB() throws DatabaseUnitException, SQLException {
//        DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);
//    }

    @After
    public void tearDown() throws DatabaseUnitException, SQLException {
        DatabaseOperation.DELETE_ALL.execute(connection, dataset);
    }
}
