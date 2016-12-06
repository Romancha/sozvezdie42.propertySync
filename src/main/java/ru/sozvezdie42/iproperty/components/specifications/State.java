package ru.sozvezdie42.iproperty.components.specifications;

/**
 * Created by Roman on 12/5/2016.
 */
public class State {
    private String description;

    public State(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "State{" +
                "description='" + description + '\'' +
                '}';
    }
}
