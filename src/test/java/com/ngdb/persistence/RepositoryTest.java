package com.ngdb.persistence;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.impl.SessionImpl;
import org.junit.After;
import org.junit.Before;
import org.springframework.test.util.ReflectionTestUtils;

public abstract class RepositoryTest<T> {

	private static IDatabaseConnection connection;

	protected T repository;

	private static EntityManagerFactory entityManagerFactory;
	protected static EntityManager entityManager;
	private EntityTransaction transaction;

	protected RepositoryTest(T dao) {
		this.repository = dao;
	}

	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("MyPersistenceUnit");
		connectToDatabase();
		attachDataTypeFactory();
	}

	private static void connectToDatabase() {
		entityManager = entityManagerFactory.createEntityManager();
		try {
			connection = new DatabaseConnection(((SessionImpl) (entityManager.getDelegate())).connection());
		} catch (HibernateException e) {
			fail("Cannot connect to the database");
		} catch (DatabaseUnitException e) {
			fail("Cannot connect to the database");
		}
	}

	private static void attachDataTypeFactory() {
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
	}

	@Before
	public void setUp() throws Exception {
		initializeDao();
		transaction = entityManager.getTransaction();
		transaction.begin();
		loadDataset();
	}

	@After
	public void tearDown() {
		transaction.rollback();
	}

	private void initializeDao() {
		ReflectionTestUtils.setField(repository, "entityManager", entityManager, EntityManager.class);
	}

	private void loadDataset() throws IOException, DataSetException, DatabaseUnitException, SQLException {
		String dataSetName = repository.getClass().getName().replaceAll("\\.", "/") + "Test-dataset.xml";

		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(dataSetName);
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		IDataSet dataSet = builder.build(stream);

		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}

	public static SessionFactory newSessionFactory(final String pathToHibernateCfgXml) {
		Configuration hibernateConfiguration = new Configuration();
		hibernateConfiguration.configure(pathToHibernateCfgXml);
		return hibernateConfiguration.buildSessionFactory();
	}

}
