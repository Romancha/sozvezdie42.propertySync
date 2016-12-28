package ru.sozvezdie42.adapter;

import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.components.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Romancha on 12/11/2016.
 */
public class CategoryDAOImpl implements CategoryDAO {

    private Connection connection;

    @Override
    public boolean createPropCategoryBonds(int dbKeyProp, int dbKeyCategory) {
        String query = "INSERT INTO aj2or_iproperty_propmid (prop_id, cat_id) VALUES (?, ?);";


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dbKeyProp);
            preparedStatement.setInt(2, dbKeyCategory);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return true;
    }

    @Override
    public boolean executePropCategory(Property property) {
        int dbKeyProp = property.getDbKey();
        List<Integer> dbCategoryKeys = new ArrayList<>();

        List<String> dbCategory = property.getCategory();
        dbCategory.forEach(category -> dbCategoryKeys.add(Category.getCategoryId(category)));

        dbCategoryKeys.forEach(dbCategoryKey -> {
            boolean bondsExists = bondsExists(dbKeyProp, dbCategoryKey);
            if (!bondsExists) {
                try {
                    createPropCategoryBonds(dbKeyProp, dbCategoryKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        return true;
    }

    @Override
    public boolean bondsExists(int dbKeyProp, int dbKeyCategory) {
        String query = "SELECT id FROM aj2or_iproperty_propmid WHERE prop_id = ? AND cat_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dbKeyProp);
            preparedStatement.setInt(2, dbKeyCategory);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBondsCategory(Property property) {
        String deleteQuery = "DELETE FROM aj2or_iproperty_propmid WHERE prop_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, property.getDbKey());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public CategoryDAOImpl(Connection connection) {
        this.connection = connection;
    }
}
