package ru.sozvezdie42.pasrser;

import org.jsoup.nodes.Document;
import ru.sozvezdie42.iproperty.components.Location;

/**
 * Created by Roman on 12/23/2016.
 */
public class CottageParser extends ResidentialParser {
    @Override
    public Location getLocation(String locationStr, Document document) {
        return new Location(locationStr);
    }
}
