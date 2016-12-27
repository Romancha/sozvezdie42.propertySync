package ru.sozvezdie42.iproperty.components;

/**
 * Created by Roman on 12/5/2016.
 */
public class ResidentialSize implements Size {

    public static final String ID_TOTAL = "Общая:";
    public static final String ID_LIVING_SPACE = "Жилая:";
    public static final String ID_KITCHEN = "Кухня:";

    private double total;
    private double livingSpace;
    private double kitchen;

    public ResidentialSize(double total, double livingSpace, double kitchen) {
        this.total = total;
        this.livingSpace = livingSpace;
        this.kitchen = kitchen;
    }

    @Override
    public double getTotal() {
        return total;
    }

    public double getLivingSpace() {
        return livingSpace;
    }

    public double getKitchen() {
        return kitchen;
    }

    @Override
    public String toString() {
        return "ResidentialSize{" +
                "total=" + total +
                ", livingSpace=" + livingSpace +
                ", kitchen=" + kitchen +
                '}';
    }
}
