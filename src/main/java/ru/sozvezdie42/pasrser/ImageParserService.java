package ru.sozvezdie42.pasrser;

import ru.sozvezdie42.iproperty.components.Image;

import java.util.ArrayList;

/**
 * @author Romancha
 */
public interface ImageParserService {
    public ArrayList<Image> parseImages(String propertyUrl);
}
