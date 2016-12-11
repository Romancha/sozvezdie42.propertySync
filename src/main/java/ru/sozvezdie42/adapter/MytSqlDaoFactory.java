package ru.sozvezdie42.adapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Roman on 12/7/2016.
 */
public class MytSqlDaoFactory {
    private static final String url = "jdbc:mysql://localhost/u0061205_Soz1";
    private static final String user = "root";
    private static final String password = "";

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
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
