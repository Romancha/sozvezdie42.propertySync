package ru.sozvezdie42.adapter;

import ru.sozvezdie42.res.PropertyResources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
            connection = DriverManager.getConnection(PropertyResources.DB_URL, PropertyResources.DB_USER,
                    PropertyResources.DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public PropertyDAO getPropertyDao(Connection connection) {
        return new ResidentialPropertyDAOImpl(connection);
    }

    public AgentDAO getAgentDAO(Connection connection) {
        return new AgentDAOImpl(connection);
    }
}
