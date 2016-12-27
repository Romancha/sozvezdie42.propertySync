package ru.sozvezdie42.iproperty.components;

/**
 * Created by Roman on 12/5/2016.
 */
public class CommercialSize implements Size {
    public static final String ID_AREA = "Площадь помещений:";
    public static final String ID_AREA_STEAD = "Площадь земельного участка:";

    private double area;
    private double areaStead;

    @Override
    public double getTotal() {
        return area;
    }

    public double getAreaStead() {
        return areaStead;
    }

    public CommercialSize(double area, double areaStead) {
        this.area = area;
        this.areaStead = areaStead;
    }
}
