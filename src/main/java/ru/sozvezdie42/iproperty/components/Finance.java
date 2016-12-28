package ru.sozvezdie42.iproperty.components;

/**
 * @author Romancha on 12/6/2016.
 */
public class Finance {

    public static final String ID_PRICE = "Цена:";
    public static final String ID_QUAD_PRICE = "Цена за кв.м:";

    private double price;
    private double quadPrice;

    public Finance(double price, double quadPrice) {
        this.price = price;
        this.quadPrice = quadPrice;
    }

    public double getPrice() {
        return price;
    }

    public double getQuadPrice() {
        return quadPrice;
    }

    @Override
    public String toString() {
        return "Finance{" +
                "price=" + price +
                ", quadPrice=" + quadPrice +
                '}';
    }
}
