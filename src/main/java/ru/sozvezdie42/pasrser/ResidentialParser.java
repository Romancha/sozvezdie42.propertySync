package ru.sozvezdie42.pasrser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.sozvezdie42.iproperty.components.Location;
import ru.sozvezdie42.iproperty.components.PropertyType;
import ru.sozvezdie42.iproperty.components.ResidentialSize;
import ru.sozvezdie42.iproperty.components.Storey;
import ru.sozvezdie42.iproperty.components.specifications.Balcony;
import ru.sozvezdie42.iproperty.components.specifications.Bathroom;
import ru.sozvezdie42.iproperty.components.specifications.ResidentialSpecifications;
import ru.sozvezdie42.iproperty.components.specifications.State;



/**
 * @author Romancha on 12/5/2016.
 */
public class ResidentialParser extends Parser {

    public int getRoomAmt(Document document) {
        Elements element = document.getElementsByClass("domstor_object_rooms");
        String roomsText = element.select("p").first().text();
        String[] spittedText = roomsText.split(" ");
        return Integer.parseInt(spittedText[0]);
    }

    public PropertyType getPropertyType(Document document) {
        Elements elements = document.getElementsByClass("domstor_object_type");
        Elements typeElements = elements.select("tr");

        String apartment = "";
        String layout = "";
        String material = "";

        for (Element element : typeElements) {
            String th = element.select("th").first().text();
            String td = element.select("td").first().text();

            switch (th) {
                case PropertyType.TYPE_APARTMENT:
                    apartment = td;
                    break;
                case PropertyType.TYPE_LAYOUT:
                    layout = td;
                    break;
                case PropertyType.TYPE_MATERIAL:
                    material = td;
                    break;
            }
        }
        return new PropertyType(apartment, layout, material);
    }

    public Location getLocation (String locationStr, Document document) {
        String[] locationElements = locationStr.split(",");

        String city = "";
        String street = "";
        String house = "";
        String district = "";

        for (int i = 0; i < locationElements.length; i ++) {
            switch (i) {
                case 0:
                    street = locationElements[i];
                    break;
                case 1:
                    house = locationElements[i].replaceAll("\\s", "");
                    break;
                case 2:
                    district = locationElements[i].replaceAll("\\s", "");
                    break;
                case 3:
                    city = locationElements[i].replaceAll("\\s", "");
                    break;
            }
        }

        double[] coordinates = getCoordinates(document);

        Location location = new Location(street, city, district, house, coordinates);
        location.setLocationStr(locationStr);
        return location;
    }

    public ResidentialSize getSize(Document document) {
        Elements elements = document.getElementsByClass("domstor_object_size");
        Elements sizeElements = elements.select("tr");

        double total = 0;
        double livingSpace = 0;
        double kitchen = 0;

        for (Element element : sizeElements) {
            String th = element.select("th").first().text();
            th = th.replace("\u00a0", "");
            String td = element.select("td").first().text();

            switch (th) {
                case ResidentialSize.ID_TOTAL:
                    total = Double.parseDouble(td);
                    break;
                case ResidentialSize.ID_LIVING_SPACE:
                    livingSpace = Double.parseDouble(td);
                    break;
                case ResidentialSize.ID_KITCHEN:
                    kitchen = Double.parseDouble(td);
                    break;
            }
        }
        return new ResidentialSize(total, livingSpace, kitchen);
    }

    public ResidentialSpecifications getResidentialSpecifications(Document document) {
        Elements elements = document.getElementsByClass("domstor_object_technic");
        Elements tableElements = elements.select("tr");

        Bathroom bathroom;
        String type = "";
        int amt = 0;

        Balcony balcony;
        int balconyAmt = 0;
        int loggiaAmt = 0;
        String decoration = "";

        String communications = "";
        State state = null;

        for (Element element : tableElements) {
            String th = element.select("th").first().text();
            th = th.replace("\u00a0", "");
            String td = element.select("td").first().text();

            switch (th) {
                case Bathroom.ID_BATHROOM:
                    type = td;
                    break;
                case Bathroom.ID_BATHROOM_AMT:
                    amt = Integer.parseInt(td);
                    break;
                case Balcony.ID_BALCONY_AMT:
                    balconyAmt = Integer.parseInt(td);
                    break;
                case Balcony.ID_DECORATION:
                    decoration = td;
                    break;
                case Balcony.ID_LOGGIE_AMT:
                    loggiaAmt = Integer.parseInt(td);
                    break;
                case ResidentialSpecifications.ID_COMMUNICATION:
                    communications = td;
                    break;
                case ResidentialSpecifications.ID_STATE:
                    state = new State(td);
                    break;
            }
        }

        bathroom = new Bathroom(type, amt);
        balcony = new Balcony(balconyAmt, loggiaAmt, decoration);

        return new ResidentialSpecifications(bathroom, balcony, communications, state);
    }

    public Storey getStorey(Document document) {
        Storey st = new Storey();

        Element element = document.getElementsByClass("domstor_object_floor").first();

        if (element !=null) {
            Elements pElements = element.select("p");

            if (pElements != null && !pElements.isEmpty()) {
                Element pElement = pElements.first();
                String storeyStr = pElement.text();
                String[] splitStoreys = storeyStr.split("/");
                int storey = Integer.parseInt(splitStoreys[0]);
                int maxStorey = Integer.parseInt(splitStoreys[1]);

                st.setStorey(storey);
                st.setMaxStorey(maxStorey);
            }
        }
        return st;
    }

}
