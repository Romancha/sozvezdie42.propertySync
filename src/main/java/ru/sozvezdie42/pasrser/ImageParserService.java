package ru.sozvezdie42.pasrser;

import ru.sozvezdie42.iproperty.components.Image;

import java.util.ArrayList;

/**
 * Created by Roman on 12/22/2016.
 */
public interface ImageParserService {
    public ArrayList<Image> parseImages(String propertyUrl);
}
