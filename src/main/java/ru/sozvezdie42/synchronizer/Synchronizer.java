package ru.sozvezdie42.synchronizer;

import ru.sozvezdie42.adapter.MytSqlDaoFactory;
import ru.sozvezdie42.adapter.PropertyDAO;
import ru.sozvezdie42.adapter.ResidentialPropertyDAOImpl;
import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.pasrser.ParseServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * @author Romancha
 */
public class Synchronizer {

    private PropertyDAO propertyDAO;
    private Connection connection;

    public void synchronizeCompany(String companyId) throws SQLException {

        System.out.println("Start sync. Date: " + new Date());

        Instant start = Instant.now();

        MytSqlDaoFactory factory = new MytSqlDaoFactory();
        connection = factory.getConnection();

        if (connection != null) {
            HashMap<String, ArrayList<Property>> props = new ParseServiceImpl().
                    parseResidentialFromCompany(companyId);
            HashSet<String> incomeAliasList = new HashSet<>();


            propertyDAO = new ResidentialPropertyDAOImpl(connection);

            props.forEach((k, v) -> v.forEach(property -> {
                propertyDAO.executeProperty(property);
                incomeAliasList.add(property.getAlias());
            }));

            deleteNotRelevantProperty(incomeAliasList);

            connection.close();

            Instant end = Instant.now();

            System.out.println("Sync end in: " + Duration.between(start, end));
        } else {
            System.out.println("ERROR! Couldn't get connection");
        }
    }

    private void deleteNotRelevantProperty (HashSet<String> incomeAliasList) {
        HashMap<Map<String, Integer>, Integer> allProperty = getAllPropertyMap(connection);

        allProperty.forEach((k, createdBy) -> k.forEach((alias, dbKey) ->{
            if (createdBy == 57 && !incomeAliasList.contains(alias)) {
                Property property = new Property();
                property.setAlias(alias);
                property.setDbKey(dbKey);
                propertyDAO.delete(property);
                System.out.println("Not relevant property deleted - alias: " + property.getAlias() + " db key: " + property.getDbKey());
            }
        }));
    }

    private  HashMap<Map<String, Integer>, Integer> getAllPropertyMap(Connection connection) {
        HashMap<Map<String, Integer>, Integer> allProperty = new HashMap<>();

        String query = "SELECT id, alias, created_by FROM aj2or_iproperty";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Map<String, Integer> aliasToDbKey = new HashMap<>();
                aliasToDbKey.put(rs.getString("alias"), rs.getInt("id"));
                allProperty.put(aliasToDbKey, rs.getInt("created_by"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allProperty;
    }
}
