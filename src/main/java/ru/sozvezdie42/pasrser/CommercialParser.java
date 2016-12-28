package ru.sozvezdie42.pasrser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.sozvezdie42.iproperty.components.CommercialSize;
import ru.sozvezdie42.iproperty.components.Location;
import ru.sozvezdie42.iproperty.components.ResidentialSize;

import java.util.Arrays;

/**
 * @author Romancha on 12/26/2016.
 */
public class CommercialParser extends Parser {

    @Override
    public String getRef(Document document) {
        Element pElement = document.getElementsByClass("breadcrumbs").get(0);
        String elementText = pElement.child(0).text();
        String[] text = elementText.split(" / ");
        String objectStr = text[2];
        text = objectStr.split("\\s+");
        return text[1];
    }

    @Override
    public String getId(Document document) {
        Element checkBox = document.getElementsByClass("checkbox btn").get(0);
        return checkBox.child(0).attr("data-id");
    }

    @Override
    public String getComment(Document document) {
        Elements elements = document.getElementsByClass("domstor_object_comments");
        if (elements.isEmpty()) return "";
        return elements.get(0).select("td").first().text();
    }

    @Override
    public String getLocationStr(Document document) {
        Element element = document.getElementsByClass("domstor_object_place").get(0);
        Elements pElements = element.select("p");
        return pElements.get(pElements.size()-1).text();
    }

    public CommercialSize getSize(Document document) {
        Elements elements = document.getElementsByClass("domstor_object_size");
        Elements sizeElements = elements.select("tr");

        double area = 0;
        double areaStead = 0;

        for (Element element : sizeElements) {
            String th = element.select("th").first().text();
            th = th.replace("\u00a0", "");
            String td = element.select("td").first().text();
            td = td.replaceAll("\\D+","");
            switch (th) {
                case CommercialSize.ID_AREA:
                    area = Double.parseDouble(td);
                    break;
                case CommercialSize.ID_AREA_STEAD:
                    areaStead = Double.parseDouble(td);
                    break;
            }
        }
        return new CommercialSize(area, areaStead);
    }

    @Override
    public double[] getCoordinates(Document document) {
        double[] coordinates = new double[]{0, 0};

        Element yaMapsElement = document.getElementsByClass("domstor_object_common2").get(0);
        Element scriptElement = yaMapsElement.select("script").first();
        String scriptText = scriptElement.data();

        if (scriptText.contains(Location.ID_COORDINATES)) {
            int coordStrIndex = scriptText.indexOf(Location.ID_COORDINATES);
            int coordStart = scriptText.indexOf("[", coordStrIndex);
            int coordEnd = scriptText.indexOf("]", coordStrIndex);

            String coords = scriptText.substring(coordStart + 1, coordEnd);
            String[] coordElements = coords.split(",");

            try {
                double equator = Double.parseDouble(coordElements[0]);
                double meridian = Double.parseDouble(coordElements[1]);

                coordinates = new double[]{equator, meridian};
            } catch (NumberFormatException e) {
                System.out.println("WARNING! coordinates not paring " + Arrays.toString(coordElements));
            }
        }

        return coordinates;
    }
}
