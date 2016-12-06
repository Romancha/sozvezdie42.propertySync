package ru.sozvezdie42.iproperty;


import ru.sozvezdie42.iproperty.components.PropertyType;

/**
 * Created by Roman on 12/5/2016.
 */
public class ResidentialProperty extends Property {
    private PropertyType type;
    private int roomAmt;

    public void setType(PropertyType type) {
        this.type = type;
    }

    public void setRoomAmt(int roomAmt) {
        this.roomAmt = roomAmt;
    }

    @Override
    public String toString() {
        return "ResidentialProperty{" +
                "type=" + type +
                ", roomAmt=" + roomAmt +
                "} " + super.toString();
    }
}
