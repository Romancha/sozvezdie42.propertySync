package ru.sozvezdie42.adapter;

import ru.sozvezdie42.res.PropertyResources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Romancha
 */
public class MytSqlDaoFactory {

    public Connection getConnection() {
        Connection connection = null;
        try {
            Properties properties=new Properties();
            properties.setProperty("user", PropertyResources.DB_USER);
            properties.setProperty("password", PropertyResources.DB_PASSWORD);
            properties.setProperty("useUnicode","true");
            properties.setProperty("characterEncoding","UTF-8");
            connection = DriverManager.getConnection(PropertyResources.DB_URL, properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public PropertyDAO getPropertyDAO(Connection connection, ImageDAO imageDAO) {
        return new ResidentialPropertyDAOImpl(connection, imageDAO);
    }

    public ImageDAO getImageDAO(Connection connection) {
        return new ImageDAOImpl(connection);
    }

    public AgentDAO getAgentDAO(Connection connection) {
        return new AgentDAOImpl(connection);
    }
}
