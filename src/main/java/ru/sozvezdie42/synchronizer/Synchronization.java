package ru.sozvezdie42.synchronizer;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import ru.sozvezdie42.SyncStarter;
import ru.sozvezdie42.adapter.DaoFactory;
import ru.sozvezdie42.adapter.ImageDAO;
import ru.sozvezdie42.adapter.PropertyDAO;
import ru.sozvezdie42.adapter.ResidentialPropertyDAOImpl;
import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.pasrser.ParseServiceImpl;
import ru.sozvezdie42.res.PropertyResources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Romancha
 */
public class Synchronization {

    private final static Logger log = Logger.getLogger(Synchronization.class);

    public static final int SYNCHRONIZER_USER_ID = 57;

    private PropertyDAO propertyDAO;
    private ImageDAO imageDAO;

    private Connection connection;
    private FTPClient ftpClient;

    public Synchronization() {
        DaoFactory factory = new DaoFactory();

        connection = factory.getConnection();

        ftpClient = factory.getConnectedFtpClient();
        if (PropertyResources.FTP_ENABLED) {
            if (ftpClient == null || !ftpClient.isConnected()) {
                throw new IllegalStateException("Ftl client is not connected");
            }
        }

        imageDAO = factory.getImageDAO(connection, ftpClient);
        propertyDAO = factory.getPropertyDAO(connection, imageDAO);
    }

    public void synchronizeCompany(String companyId) throws SQLException {
        Instant start = Instant.now();

        HashMap<String, ArrayList<Property>> props = new ParseServiceImpl().
                parseResidentialFromCompany(companyId);
        HashSet<String> incomeAliasList = new HashSet<>();

        propertyDAO = new ResidentialPropertyDAOImpl(connection, imageDAO);

        props.forEach((k, v) -> v.forEach(property -> {
            propertyDAO.executeProperty(property);
            incomeAliasList.add(property.getAlias());
        }));

        deleteNotRelevantProperty(incomeAliasList);

        connection.close();

        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("Disconnect ftp...");
        }

        Instant end = Instant.now();

        log.info("Sync end in: " + Duration.between(start, end));
    }



    private void deleteNotRelevantProperty(HashSet<String> incomeAliasList) {
        HashMap<Map<String, Integer>, Integer> allProperty = getAllPropertyMap(connection);

        allProperty.forEach((k, createdBy) -> k.forEach((alias, dbKey) -> {
            if (createdBy == SYNCHRONIZER_USER_ID && !incomeAliasList.contains(alias)) {
                Property property = new Property();
                property.setAlias(alias);
                property.setDbKey(dbKey);
                propertyDAO.delete(property);
                log.info("Not relevant property deleted - alias: " + property.getAlias() + " db key: "
                        + property.getDbKey());
            }
        }));
    }

    private HashMap<Map<String, Integer>, Integer> getAllPropertyMap(Connection connection) {
        HashMap<Map<String, Integer>, Integer> allProperty = new HashMap<>();

        String query = "SELECT id, alias, created_by FROM aj2or_iproperty";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
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
