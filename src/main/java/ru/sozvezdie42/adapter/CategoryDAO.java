package ru.sozvezdie42.adapter;

import ru.sozvezdie42.iproperty.Property;

/**
 * Created by Roman on 12/11/2016.
 */
public interface CategoryDAO {
    public boolean createPropCategoryBonds(int dbKeyProp, int dbKeyCategory);
    public boolean executePropCategory(Property property);
    public boolean bondsExists(int dbKeyProp, int dbKeyCategory);
}
