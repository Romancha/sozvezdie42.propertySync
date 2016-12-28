package ru.sozvezdie42.iproperty;

import ru.sozvezdie42.iproperty.components.Category;

import java.util.List;

/**
 * @author Romancha on 12/5/2016.
 */
public class PropertyFactory {
    public Property createProperty(List<String> categories) {
        Property property = null;

        if (categories.contains(Category.APARTMENT) || categories.contains(Category.KGT)
                || categories.contains(Category.NEW) || categories.contains(Category.COTTAGE)) {
            property = new ResidentialProperty();
        } else if (categories.contains(Category.GARAGE) || categories.contains(Category.COUNTRY_HOUSE)) {
            property = new Property();
        } else if (categories.contains(Category.COMMERCIAL)) {
            property = new CommercialProperty();
        }
        return property;
    }
}
