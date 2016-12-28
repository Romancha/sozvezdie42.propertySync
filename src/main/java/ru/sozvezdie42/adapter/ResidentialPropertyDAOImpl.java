package ru.sozvezdie42.adapter;

import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.ResidentialProperty;
import ru.sozvezdie42.iproperty.components.*;
import ru.sozvezdie42.iproperty.components.specifications.Bathroom;
import ru.sozvezdie42.iproperty.components.specifications.ResidentialSpecifications;
import ru.sozvezdie42.iproperty.components.specifications.State;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Roman on 12/7/2016.
 */
public class ResidentialPropertyDAOImpl implements PropertyDAO {

    private static final Integer SYNC_USER_ID = 57;

    private Connection connection;


    public ResidentialPropertyDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Property create(Property property) {
        String query =
                "INSERT INTO aj2or_iproperty (" +
                        "mls_id, type, stype, listing_office, street_num, street, apt, alias," +
                        "hide_address, show_map, short_description, description, city, country, latitude, longitude," +
                        "price, baths, sqft, lotsize, lot_acres, yearbuilt, heat, garage_type, roof, state, approved, " +
                        "terms, agent_notes, fuel, video, gbase_url, metadesc, metakey, metadata, language, " +
                        "listing_info, created, beds, modified, publish_up, created_by, modified_by, access) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Property prop = property;

        Location location = prop.getLocation();

        Size size = prop.getSize();
        PropertyType type = null;
        ResidentialSpecifications specifications = null;
        Bathroom bathroom = null;
        StringBuilder bathroomDescription = new StringBuilder();
        String balconyDescription = "";
        State state = null;
        int roomAmt = 0;

        if (property instanceof ResidentialProperty) {
            ResidentialProperty resProp = (ResidentialProperty) property;
            type = resProp.getType();
            specifications = (ResidentialSpecifications) resProp.getSpecifications();
            bathroom = specifications.getBathroom();

            if (bathroom != null) {
                bathroomDescription.append(bathroom.getType());
                if (bathroom.getAmt() > 0) {
                    bathroomDescription.append(" Количество: ").append(bathroom.getAmt());
                }
            }

            balconyDescription = specifications.getBalcony().getDescription();
            roomAmt = resProp.getRoomAmt();
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, AdapterUtils.prepare(prop.getRef()));
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, 1);
            preparedStatement.setInt(4, 1);
            preparedStatement.setString(5, AdapterUtils.prepare(location.getStreet()));
            preparedStatement.setString(6, AdapterUtils.prepare(location.getNumberHouse()));
            preparedStatement.setString(7, AdapterUtils.prepare(location.getRegion()));
            preparedStatement.setString(8, AdapterUtils.prepare(prop.getAlias()));
            preparedStatement.setInt(9, 0);
            preparedStatement.setInt(10, 1);
            preparedStatement.setString(11, AdapterUtils.prepare(prop.getShortDescription()));
            preparedStatement.setString(12, AdapterUtils.prepare(prop.getDescription()));
            preparedStatement.setString(13, AdapterUtils.prepare(location.getCity()));
            preparedStatement.setInt(14, District.getId(location.getDistrict()));
            preparedStatement.setDouble(15, location.getCoordinates()[0]);
            preparedStatement.setDouble(16, location.getCoordinates()[1]);
            preparedStatement.setDouble(17, prop.getFinance().getPrice());

            int storey = 0;
            if (prop.getStorey() != null) {
                storey = prop.getStorey().getStorey();
            }
            preparedStatement.setInt(18, storey);

            int sizeInt = 0;
            if (size != null) {
                sizeInt = (int) size.getTotal();
            }
            preparedStatement.setInt(19, sizeInt);

            if (specifications != null) {
                state = specifications.getState();
            }
            String st = "";
            if (state != null) {
                st = state.getDescription();
            }
            preparedStatement.setString(20, st);

            String typeStr = "";
            String layout = "";
            String material = "";
            if (type != null) {
                typeStr = type.getType();
                layout = type.getLayout();
                material = type.getMaterial();
            }
            preparedStatement.setString(21, typeStr);
            preparedStatement.setString(22, layout);
            preparedStatement.setString(23, material);
            preparedStatement.setString(24, AdapterUtils.prepare(bathroomDescription.toString()));
            preparedStatement.setString(25, AdapterUtils.prepare(balconyDescription));
            preparedStatement.setInt(26, 1);//Опубликовано
            preparedStatement.setInt(27, 1);

            preparedStatement.setString(28, "");//Default
            preparedStatement.setString(29, "");//Default
            preparedStatement.setString(30, "");//Default
            preparedStatement.setString(31, "");//Default
            preparedStatement.setString(32, "");//Default
            preparedStatement.setString(33, "");//Default
            preparedStatement.setString(34, "");//Default
            preparedStatement.setString(35, "");//Default
            preparedStatement.setString(36, "");//Default
            preparedStatement.setString(37, "");//Default

            Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
            preparedStatement.setDate(38, currentDate);

            preparedStatement.setInt(39, roomAmt);

            preparedStatement.setDate(40, currentDate);
            preparedStatement.setDate(41, currentDate);
            preparedStatement.setInt(42, SYNC_USER_ID);
            preparedStatement.setInt(43, SYNC_USER_ID);
            preparedStatement.setInt(44, 1);

            preparedStatement.executeUpdate();

            PropertyDAO propertyDAO = new ResidentialPropertyDAOImpl(connection);
            property.setDbKey(propertyDAO.getPropertyDbKey(property));

            Agent agent = property.getAgent();
            if (agent != null) {
                AgentDAO agentDAO = new AgentDAOImpl(connection);
                agentDAO.createPropAgentBonds(property);
            }

            CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
            categoryDAO.executePropCategory(property);

            ImageDAO imageDAO = new ImageDAOImpl(connection);
            imageDAO.deleteImages(property);
            imageDAO.executeImages(property);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean update(Property property, boolean force) {

        String query = "UPDATE aj2or_iproperty SET type = ?, stype = ?, listing_office = ?, street_num = ?, street = ?, apt = ?, alias = ?," +
                "hide_address = ?, show_map = ?, short_description = ?, description = ?, city = ?, country = ?, latitude = ?, longitude = ?," +
                "price = ?, baths = ?, sqft = ?, lotsize = ?, lot_acres = ?, yearbuilt = ?, heat = ?, garage_type = ?, roof = ?, state = ?, approved = ?, " +
                "terms = ?, agent_notes = ?, fuel = ?, video = ?, gbase_url = ?, metadesc = ?, metakey = ?, metadata = ?, language = ?, " +
                "listing_info = ?, beds = ?, modified = ?, created_by = ?, modified_by = ?, access = ?" +
                " WHERE id = ?;";

        Property prop = property;

        Location location = prop.getLocation();

        Size size = prop.getSize();
        PropertyType type = null;
        ResidentialSpecifications specifications = null;
        Bathroom bathroom = null;
        StringBuilder bathroomDescription = new StringBuilder();
        String balconyDescription = "";
        State state = null;
        int roomAmt = 0;

        if (property instanceof ResidentialProperty) {
            ResidentialProperty resProp = (ResidentialProperty) property;
            type = resProp.getType();
            specifications = (ResidentialSpecifications) resProp.getSpecifications();
            bathroom = specifications.getBathroom();

            if (bathroom != null) {
                bathroomDescription.append(bathroom.getType());
                if (bathroom.getAmt() > 0) {
                    bathroomDescription.append(" Количество: ").append(bathroom.getAmt());
                }
            }

            balconyDescription = specifications.getBalcony().getDescription();
            roomAmt = resProp.getRoomAmt();

        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, 1);
            preparedStatement.setInt(3, 1);
            preparedStatement.setString(4, AdapterUtils.prepare(location.getStreet()));
            preparedStatement.setString(5, AdapterUtils.prepare(location.getNumberHouse()));
            preparedStatement.setString(6, AdapterUtils.prepare(location.getRegion()));
            preparedStatement.setString(7, AdapterUtils.prepare(prop.getAlias()));
            preparedStatement.setInt(8, 0);
            preparedStatement.setInt(9, 1);
            preparedStatement.setString(10, AdapterUtils.prepare(prop.getShortDescription()));
            preparedStatement.setString(11, AdapterUtils.prepare(prop.getDescription()));
            preparedStatement.setString(12, AdapterUtils.prepare(location.getCity()));
            preparedStatement.setInt(13, District.getId(location.getDistrict()));
            preparedStatement.setDouble(14, location.getCoordinates()[0]);
            preparedStatement.setDouble(15, location.getCoordinates()[1]);
            preparedStatement.setDouble(16, prop.getFinance().getPrice());

            int storey = 0;
            if (prop.getStorey() != null) {
                storey = prop.getStorey().getStorey();
            }
            preparedStatement.setInt(17, storey);

            int sizeInt = 0;
            if (size != null) {
                sizeInt = (int) size.getTotal();
            }
            preparedStatement.setInt(18, sizeInt);

            if (specifications != null) {
                state = specifications.getState();
            }
            String st = "";
            if (state != null) {
                st = state.getDescription();
            }
            preparedStatement.setString(19, st);

            String typeStr = "";
            String layout = "";
            String material = "";
            if (type != null) {
                typeStr = type.getType();
                layout = type.getLayout();
                material = type.getMaterial();
            }
            preparedStatement.setString(20, typeStr);
            preparedStatement.setString(21, layout);
            preparedStatement.setString(22, material);

            preparedStatement.setString(23, AdapterUtils.prepare(bathroomDescription.toString()));
            preparedStatement.setString(24, AdapterUtils.prepare(balconyDescription));
            preparedStatement.setInt(25, 1);//Опубликовано
            preparedStatement.setInt(26, 1);

            preparedStatement.setString(27, "");//Default
            preparedStatement.setString(28, "");//Default
            preparedStatement.setString(29, "");//Default
            preparedStatement.setString(30, "");//Default
            preparedStatement.setString(31, "");//Default
            preparedStatement.setString(32, "");//Default
            preparedStatement.setString(33, "");//Default
            preparedStatement.setString(34, "");//Default
            preparedStatement.setString(35, "");//Default
            preparedStatement.setString(36, "");//Default

            Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
            preparedStatement.setInt(37, roomAmt);
            preparedStatement.setDate(38, currentDate);
            preparedStatement.setInt(39, SYNC_USER_ID);
            preparedStatement.setInt(40, SYNC_USER_ID);
            preparedStatement.setInt(41, 1);

            PropertyDAO propertyDAO = new ResidentialPropertyDAOImpl(connection);
            preparedStatement.setInt(42, propertyDAO.getPropertyDbKey(property));

            preparedStatement.executeUpdate();
            property.setDbKey(propertyDAO.getPropertyDbKey(property));

            AgentDAO agentDAO = new AgentDAOImpl(connection);
            agentDAO.executeAgent(property);

            CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
            categoryDAO.executePropCategory(property);

            ImageDAO imageDAO = new ImageDAOImpl(connection);
            imageDAO.deleteImages(property);
            imageDAO.executeImages(property);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Property property) {
        String deleteQuery = "DELETE FROM aj2or_iproperty WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, property.getDbKey());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        new AgentDAOImpl(connection).deleteBondsAgent(property);
        new CategoryDAOImpl(connection).deleteBondsCategory(property);
        new ImageDAOImpl(connection).deleteImages(property);

        return true;
    }

    @Override
    public List<Integer> getIPropertyCategories(int propDbKey) {

        List<Integer> categories = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(getIPropertyCategoriesQuery())) {
            statement.setInt(1, propDbKey);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                categories.add(rs.getInt("cat_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }


    private String getCategoryTitle(int categoryId) {
        try (PreparedStatement statement = connection.prepareStatement(getCategoryTitleQuery())) {
            statement.setInt(1, categoryId);
            ResultSet rs = statement.executeQuery();
            rs.next();

            return rs.getString("title");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Property> getAll() {
        return null;
    }

    @Override
    public int getPropertyDbKey(Property property) {
        String query = "SELECT id FROM aj2or_iproperty WHERE alias = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, property.getAlias());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean executeProperty(Property property) {

        boolean propExists = propertyExists(property);

        try {
            if (propExists) {
                update(property, false);
            } else {
                create(property);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean propertyExists(Property property) {
        String query = "SELECT * FROM aj2or_iproperty WHERE alias=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, property.getAlias());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getIPropertyCategoriesQuery() {
        return "SELECT cat_id FROM aj2or_iproperty_propmid WHERE prop_id = ?;";
    }

    public String getCategoryTitleQuery() {
        return "SELECT title FROM aj2or_iproperty_categories WHERE id = ?;";
    }


}
