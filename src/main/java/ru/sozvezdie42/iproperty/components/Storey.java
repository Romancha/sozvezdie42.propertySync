package ru.sozvezdie42.iproperty.components;

/**
 * Created by Roman on 12/5/2016.
 */
public class Storey {
    private int storey;
    private int maxStorey;

    public Storey(int storey, int maxStorey) {
        this.storey = storey;
        this.maxStorey = maxStorey;
    }

    public int getStorey() {
        return storey;
    }

    public int getMaxStorey() {
        return maxStorey;
    }
}
