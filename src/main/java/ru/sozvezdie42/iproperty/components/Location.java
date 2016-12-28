package ru.sozvezdie42.iproperty.components;

import java.util.Arrays;

/**
 * @author Romancha on 12/5/2016.
 */
public class Location {

    public static final String ID_COORDINATES = "coords:";
    public static final String ID_COORDINATES_2 = "jsapi&ll=";

    private String street;
    private String city;
    private String region;
    private String district;
    private String numberHouse;
    private String apartment;
    private String locationStr;
    private double[] coordinates;

    public Location(String street, String city, String district, String numberHouse, double[] coordinates) {
        this.street = street;
        this.city = city;
        this.district = district;
        this.numberHouse = numberHouse;
        this.coordinates = coordinates;
    }

    public Location(String street) {
        this.street = street;
    }

    public Location() {
    }

    public String getLocationStr() {
        return locationStr;
    }

    public void setLocationStr(String locationStr) {
        this.locationStr = locationStr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNumberHouse() {
        return numberHouse;
    }

    public void setNumberHouse(String numberHouse) {
        this.numberHouse = numberHouse;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public double[] getCoordinates() {
        if (coordinates == null) {
            double[] def = new double[2];
            def[0] = 0.0;
            def[1] = 0.0;
            return def;
        }
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "Location{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", district='" + district + '\'' +
                ", numberHouse='" + numberHouse + '\'' +
                ", apartment='" + apartment + '\'' +
                ", locationStr='" + locationStr + '\'' +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
