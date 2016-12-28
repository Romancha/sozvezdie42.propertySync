package ru.sozvezdie42.iproperty.components.specifications;

/**
 * @author Romancha on 12/5/2016.
 */
public class ResidentialSpecifications implements Specifications {
    public static final String ID_STATE = "Состояние:";
    public static final String ID_COMMUNICATION = "Коммуникации:";

    private Bathroom bathroom;
    private Balcony balcony;
    private String communications;
    private State state;

    public ResidentialSpecifications(Bathroom bathroom, Balcony balcony, String communications, State state) {
        this.bathroom = bathroom;
        this.balcony = balcony;
        this.communications = communications;
        this.state = state;
    }

    public Bathroom getBathroom() {
        return bathroom;
    }

    public Balcony getBalcony() {
        return balcony;
    }

    public String getCommunications() {
        return communications;
    }

    public State getState() {
        return state;
    }

    @Override
    public String toString() {
        return "ResidentialSpecifications{" +
                "bathroom=" + bathroom +
                ", balcony=" + balcony +
                ", communications='" + communications + '\'' +
                ", state=" + state +
                '}';
    }
}
