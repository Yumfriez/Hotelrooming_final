package by.tr.hotelbooking.dao.pool;

import by.tr.hotelbooking.dao.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.MissingResourceException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

    private static ConnectionPool INSTANCE;
    private static AtomicBoolean isInitialized=new AtomicBoolean(false);
    private final static ReentrantLock singletonLocker=new ReentrantLock();

    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    private ReentrantLock retrieveLocker = new ReentrantLock();
    private ReentrantLock putBackLocker = new ReentrantLock();

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;


    private ConnectionPool() throws ConnectionPoolException {
        try {
            DBResourceManager dbResourceManager = DBResourceManager.getInstance();
            this.driverName = dbResourceManager.getValue(DBParametr.DB_DRIVER);
            this.user = dbResourceManager.getValue(DBParametr.DB_USER);
            this.url = dbResourceManager.getValue(DBParametr.DB_URL);
            this.password = dbResourceManager.getValue(DBParametr.DB_PASSWORD);
            this.poolSize = Integer.parseInt(dbResourceManager.getValue((DBParametr.DN_POOL_SIZE)));
            Class.forName(driverName);
        } catch (NumberFormatException e) {
            logger.error(e);
            poolSize = 5;
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Driver load exception: " + driverName, e);
        } catch (MissingResourceException e) {
            throw new ConnectionPoolException("Error of upload resource: " +  e);
        }

        connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
        givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connectionQueue.add(getConnection());
        }
    }
    private Connection getConnection()throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new ConnectionPoolException("unnable to get connection to DB from connection pool",e);
        }
        return connection;
    }
    public Connection retrieve() throws ConnectionPoolException {
        Connection connection = null;
        retrieveLocker.lock();
        try {
            if (connectionQueue.size() == 0) {
                connection = getConnection();
            } else {

                try {
                    connection = (Connection) connectionQueue.take();
                } catch (InterruptedException e) {
                    throw new ConnectionPoolException("unnable to get connection to DB from connection pool",e);
                }

            }
            givenAwayConQueue.add(connection);
            return connection;
        }finally {
            retrieveLocker.unlock();
        }
    }

    private void putBack(Connection connection) {

        if (connection != null) {
            putBackLocker.lock();
            try {
                givenAwayConQueue.remove(connection);
                connectionQueue.add(connection);
            }finally {
                putBackLocker.unlock();
            }

        }
    }
    public static ConnectionPool getInstance() throws ConnectionPoolException {
        if (!isInitialized.get()) {
            singletonLocker.lock();
            try {
                if (INSTANCE == null) {
                    INSTANCE = new ConnectionPool();
                    isInitialized.set(true);
                }

            } finally {
                singletonLocker.unlock();
            }
        }
        return INSTANCE;
    }


    public void putBackConnection(Connection con, Statement st, ResultSet resultSet)  {

        this.putBack(con);

        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }
    public void putBackConnection(Connection con, Statement st) {
        this.putBackConnection(con, st, null);
    }
}
