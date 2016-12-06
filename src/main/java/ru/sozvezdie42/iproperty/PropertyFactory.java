package ru.sozvezdie42.iproperty;

/**
 * Created by Roman on 12/5/2016.
 */
public class PropertyFactory {
    public Property createProperty(String type) {
        Property property = null;

        if (type.equals("residential")) {
            property = new ResidentialProperty();
        }

        return property;
    }
}
