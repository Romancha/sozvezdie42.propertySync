package ru.sozvezdie42.iproperty;

import ru.sozvezdie42.iproperty.components.Category;

/**
 * Created by Roman on 12/5/2016.
 */
public class PropertyFactory {
    public Property createProperty(String category) {
        Property property = null;

        switch (category) {
            case Category.APARTMENT:
            case Category.KGT:
            case Category.NEW:
                property = new ResidentialProperty();
                break;

        }
        return property;
    }
}
