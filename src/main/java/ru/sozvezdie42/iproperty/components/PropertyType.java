package ru.sozvezdie42.iproperty.components;

/**
 * @author Romancha on 12/5/2016.
 */
public class PropertyType {

    public static final String TYPE_APARTMENT = "Тип квартиры:";
    public static final String TYPE_LAYOUT = "Планировка:";
    public static final String TYPE_MATERIAL = "Материал строения:";

    private String type;
    private String layout;
    private String material;

    public PropertyType(String type, String layout, String material) {
        this.type = type;
        this.layout = layout;
        this.material = material;
    }

    public String getType() {
        return type;
    }

    public String getLayout() {
        return layout;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String toString() {
        return "PropertyType{" +
                "type='" + type + '\'' +
                ", layout='" + layout + '\'' +
                ", material='" + material + '\'' +
                '}';
    }
}
