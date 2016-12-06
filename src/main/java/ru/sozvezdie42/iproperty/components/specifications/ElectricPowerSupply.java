package ru.sozvezdie42.iproperty.components.specifications;

/**
 * Created by Roman on 12/5/2016.
 */
public class ElectricPowerSupply {
    private String voltage;

    public ElectricPowerSupply(String voltage) {
        this.voltage = voltage;
    }

    public String getVoltage() {
        return voltage;
    }
}
