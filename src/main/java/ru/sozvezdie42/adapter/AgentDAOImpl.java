package ru.sozvezdie42.adapter;

import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.components.Agent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 12/8/2016.
 */
public class AgentDAOImpl implements AgentDAO {

    private Connection connection;

    @Override
    public Agent getAgent(int dbAgentId) {

        String query = "SELECT * FROM aj2or_iproperty_agents WHERE id = ?;";
        Agent agent = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dbAgentId);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            agent = new Agent();
            String name = rs.getString("fname");
            String lastName = rs.getString("lname");
            String telephone = rs.getString("phone");
            String email = rs.getString("email");

            agent.setName(name + " " + lastName);
            agent.setTelephone(telephone);
            agent.setEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return agent;
    }

    @Override
    public boolean createAgent(Agent agent) {
        return false;
    }

    @Override
    public boolean deleteAgent(Agent agent) {
        return false;
    }

    @Override
    public boolean updateAgent(Agent agent) {
        return false;
    }

    @Override
    public boolean updatePropAgentBonds(Property property) {
        String query = "UPDATE aj2or_iproperty_agentmid SET agent_id = ? WHERE prop_id = ?;";

        int dbAgentKey = property.getAgent().getId();
        int dbPropKey = property.getDbKey();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dbAgentKey);
            preparedStatement.setInt(2, dbPropKey);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean createPropAgentBonds(Property property) {
        String query = "INSERT INTO aj2or_iproperty_agentmid (prop_id, agent_id) VALUES (?, ?);";

        int dbAgentKey = property.getAgent().getId();
        int dbPropKey = property.getDbKey();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dbPropKey);
            preparedStatement.setInt(2, dbAgentKey);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean executeAgent(Property property) {
        Boolean agentExists = agentPropBondExists(property);
        try {
            if (agentExists) {
                updatePropAgentBonds(property);
            } else {
                createPropAgentBonds(property);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean agentPropBondExists(Property property) {

        String query = "SELECT id FROM aj2or_iproperty_agentmid WHERE prop_id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, property.getDbKey());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public AgentDAOImpl(Connection connection) {
        this.connection = connection;
    }
}
