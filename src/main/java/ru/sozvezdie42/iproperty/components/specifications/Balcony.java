package ru.sozvezdie42.iproperty.components.specifications;

/**
 * Created by Roman on 12/5/2016.
 */
public class Balcony {

    public static final String ID_BALCONY_AMT = "Количество балконов:";
    public static final String ID_LOGGIE_AMT = "Количество лоджий:";
    public static final String ID_DECORATION = "Обустройство:";

    private int balconyAmt;
    private int loggiaAmt;
    private String decoration;

    public Balcony(int balconyAmt, int loggiaAmt, String decoration) {
        this.balconyAmt = balconyAmt;
        this.loggiaAmt = loggiaAmt;
        this.decoration = decoration;
    }

    public int getBalconyAmt() {
        return balconyAmt;
    }

    public int getLoggiaAmt() {
        return loggiaAmt;
    }

    public String getDecoration() {
        return decoration;
    }

    @Override
    public String toString() {
        return "Balcony{" +
                "balconyAmt=" + balconyAmt +
                ", loggiaAmt=" + loggiaAmt +
                ", decoration='" + decoration + '\'' +
                '}';
    }
}
