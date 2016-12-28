package ru.sozvezdie42.pasrser;

import ru.sozvezdie42.iproperty.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Romancha on 12/6/2016.
 */
public interface ParseService {
    public Property parseProperty(String propertyUrl);

    public Map<String, ArrayList<Property>> parseCompany (String companyId);

    public HashMap<String, ArrayList<Property>> parseResidentialFromCompany(String companyId);

}
