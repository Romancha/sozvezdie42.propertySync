package ru.sozvezdie42.iproperty;

import ru.sozvezdie42.iproperty.components.*;
import ru.sozvezdie42.iproperty.components.specifications.Specifications;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Romancha on 12/5/2016.
 */
public class Property {

    private int dbKey;
    private String id;
    private String ref;
    private String alias;
    private String operationType;
    private String description;
    private String shortDescription;

    private Location location;
    private Storey storey;
    private Agent agent;
    private Size size;
    private ArrayList<Image> images;
    private Specifications specifications;

    private String comment;
    private Finance finance;
    private List<String> category;

    public void setId(String id) {
        this.id = id;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStorey(Storey storey) {
        this.storey = storey;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
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

    public void setDbKey(int dbKey) {
        this.dbKey = dbKey;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getDbKey() {
        return dbKey;
    }

    public String getId() {
        return id;
    }

    public String getRef() {
        return ref;
    }

    public String getOperationType() {
        return operationType;
    }

    public String getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
    }

    public Storey getStorey() {
        return storey;
    }

    public Agent getAgent() {
        return agent;
    }

    public Size getSize() {
        return size;
    }

    public Specifications getSpecifications() {
        return specifications;
    }

    public String getComment() {
        return comment;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getCategory() {
        return category;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "Property{" +
                "dbKey=" + dbKey +
                ", id='" + id + '\'' +
                ", ref='" + ref + '\'' +
                ", operationType='" + operationType + '\'' +
                ", description='" + description + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", location=" + location +
                ", storey=" + storey +
                ", agent=" + agent +
                ", size=" + size +
                ", specifications=" + specifications +
                ", comment='" + comment + '\'' +
                ", finance=" + finance +
                ", category=" + category +
                '}';
    }
}
