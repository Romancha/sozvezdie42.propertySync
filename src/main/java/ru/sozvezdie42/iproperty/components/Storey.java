package ru.sozvezdie42.iproperty.components;

/**
 * @author Romancha on 12/5/2016.
 */
public class Storey {
    private int storey;
    private int maxStorey;

    public void setStorey(int storey) {
        this.storey = storey;
    }

    public void setMaxStorey(int maxStorey) {
        this.maxStorey = maxStorey;
    }

    public int getStorey() {
        return storey;
    }

    public int getMaxStorey() {
        return maxStorey;
    }

    @Override
    public String toString() {
        return "Storey{" +
                "storey=" + storey +
                ", maxStorey=" + maxStorey +
                '}';
    }
}
