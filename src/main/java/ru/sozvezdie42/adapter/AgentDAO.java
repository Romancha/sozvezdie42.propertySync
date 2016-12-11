package ru.sozvezdie42.adapter;

import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.components.Agent;

import java.util.List;

/**
 * Created by Roman on 12/8/2016.
 */
public interface AgentDAO {
    Agent getAgent(int dbAgentId);
    boolean createAgent(Agent agent);
    boolean deleteAgent(Agent agent);
    boolean updateAgent(Agent agent);
    boolean createPropAgentBonds(Property property);
    boolean updatePropAgentBonds(Property property);
    boolean executeAgent(Property property);
    boolean agentPropBondExists(Property property);
}
