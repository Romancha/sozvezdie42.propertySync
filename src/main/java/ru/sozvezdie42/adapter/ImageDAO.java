package ru.sozvezdie42.adapter;

import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.components.Image;

/**
 * Created by Roman on 12/22/2016.
 */
public interface ImageDAO {
    public boolean writeImageFile(Image image);
    public void executeImages(Property property);
    public boolean deleteImages(Property property);
}
