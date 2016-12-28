package ru.sozvezdie42.adapter;

import ru.sozvezdie42.iproperty.Property;

/**
 * Created by Roman on 12/8/2016.
 */
public interface AgentDAO {
    boolean createPropAgentBonds(Property property);
    boolean updatePropAgentBonds(Property property);
    boolean executeAgent(Property property);
    boolean agentPropBondExists(Property property);
    boolean deleteBondsAgent(Property property);
}
