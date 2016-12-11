package ru.sozvezdie42.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Roman on 12/11/2016.
 */
public class CategoryDAOImpl implements CategoryDAO {

    private Connection connection;

    @Override
    public boolean fillPropCategoryBonds(int dbPropKey, int dbCategoryKey) {
        String query = "INSERT INTO aj2or_iproperty_propmid (prop_id, cat_id) VALUES (?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dbPropKey);
            preparedStatement.setInt(2, dbCategoryKey);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public CategoryDAOImpl(Connection connection) {
        this.connection = connection;
    }
}
