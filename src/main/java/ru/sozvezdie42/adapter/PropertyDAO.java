package ru.sozvezdie42.adapter;

import ru.sozvezdie42.iproperty.Property;

import java.util.List;


/**
 * @author Romancha
 */
public interface PropertyDAO {
    Property create(Property property);
    boolean update(Property property, boolean force);
    boolean delete(Property property);
    List<Integer> getIPropertyCategories(int propDbKey);
    List<Property> getAll();
    int getPropertyDbKey(Property property);
    public boolean executeProperty(Property property);
    public boolean propertyExists(Property property);
}
