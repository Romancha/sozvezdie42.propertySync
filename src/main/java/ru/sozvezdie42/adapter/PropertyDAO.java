package ru.sozvezdie42.adapter;

import ru.sozvezdie42.iproperty.Property;

import java.util.List;


/**
 * Created by Roman on 12/7/2016.
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
