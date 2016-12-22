package ru.sozvezdie42.iproperty.components;

/**
 * Created by Roman on 12/22/2016.
 */
public class Image {
    private String id;
    private String propId;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String title;
    private String description;

    private String fileName;
    private String fileType;
    private String filePath;

    private int ordering;
    private int owner;
    private int state = 1;
    private int remote = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getRemote() {
        return remote;
    }

    public void setRemote(int remote) {
        this.remote = remote;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", propId='" + propId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", filePath='" + filePath + '\'' +
                ", ordering=" + ordering +
                ", owner=" + owner +
                ", state=" + state +
                ", remote=" + remote +
                '}';
    }
}
