package ru.sozvezdie42.iproperty;

import ru.sozvezdie42.iproperty.components.*;
import ru.sozvezdie42.iproperty.components.specifications.Specifications;

/**
 * Created by Roman on 12/5/2016.
 */
public class Property {

    private String id;
    private String code;
    private OperationType operationType;
    private String description;

    private Location location;
    private Storey storey;
    private Contacts contacts;
    private Size size;
    private Specifications specifications;

    private String comment;
    private Finance finance;

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStorey(Storey storey) {
        this.storey = storey;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setSpecifications(Specifications specifications) {
        this.specifications = specifications;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", operationType='" + operationType + '\'' +
                ", description='" + description + '\'' +
                ", location=" + location +
                ", storey=" + storey +
                ", agent=" + contacts +
                ", size=" + size +
                ", specifications=" + specifications +
                ", comment='" + comment + '\'' +
                ", price=" + finance +
                '}';
    }
}
