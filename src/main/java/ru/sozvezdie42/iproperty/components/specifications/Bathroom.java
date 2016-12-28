package ru.sozvezdie42.iproperty.components.specifications;

/**
 * @author Romancha on 12/5/2016.
 */
public class Bathroom {

    public static final String ID_BATHROOM = "Санузел:";
    public static final String ID_BATHROOM_AMT = "Количество санузлов:";

    private String type;
    private int amt;

    public Bathroom(String type, int amt) {
        this.type = type;
        this.amt = amt;
    }

    public String getType() {
        return type;
    }

    public int getAmt() {
        return amt;
    }

    @Override
    public String toString() {
        return "Bathroom{" +
                "type='" + type + '\'' +
                ", amt=" + amt +
                '}';
    }
}
