package ru.sozvezdie42.pasrser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.sozvezdie42.iproperty.components.Contacts;
import ru.sozvezdie42.iproperty.components.Finance;
import ru.sozvezdie42.iproperty.components.Location;

import java.util.Arrays;


/**
 * Created by Roman on 12/5/2016.
 */
public class Parser {

    public String getId(Document document) {
        Element pElement = document.getElementsByClass("navysearch").get(0);
        String elementText = pElement.child(0).text();
        String[] text = elementText.split(" / ");
        String objectStr = text[2];
        return objectStr.substring(7);
    }

    public String getDescription(Document document) {
        Element pElement = document.getElementsByClass("domstor_object_head").get(0);
        return pElement.child(0).text();
    }

    public String getLocationStr(Document document) {
        Element element = document.getElementsByClass("domstor_object_place").get(0);
        return element.select("p").first().text();
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
        System.out.println("COOR: " + coordinates[0] + ", " + coordinates[1]);

        return new Location(locationStr ,street, city, district, house, coordinates);
    }

    //TODO try change to google\yandex API
    public double[] getCoordinates (Document document) {
        double[] coordinates = new double[]{0, 0};

        Element yaMapsElement = document.getElementsByClass("object_tables_map").get(0);
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

    public Finance getFinance(Document document) {
        Element element = document.getElementsByClass("domstor_object_finance").get(0);
        Elements tableElements = element.select("tr");

        double price = 0;
        double quadPrice = 0;

        for (Element tabElement : tableElements) {
            String th = tabElement.select("th").first().text();
            String td = tabElement.select("td").first().text();
            td = td.replaceAll("\\D", "");

            switch (th) {
                case Finance.ID_PRICE:
                    price = Double.parseDouble(td);
                    break;
                case Finance.ID_QUAD_PRICE:
                    quadPrice = Double.parseDouble(td);
                    break;
            }
        }
        return new Finance(price, quadPrice);
    }

    public String getComment(Document document) {
        Elements elements = document.getElementsByClass("domstor_object_comments");
        if (elements.isEmpty()) return "";
        return elements.get(0).select("p").first().text();
    }

    public Contacts getAgent(Document document) {
        Element element = document.getElementsByClass("domstor_object_contacts").get(0);
        Elements pElements = element.select("p");

        String agentName = "";
        String telephone = "";
        String email = "";
        String company = "";
        String update = "";
        int watchers = 0;

        for (Element p : pElements) {
            String pText = p.text();
            if (pText.contains(Contacts.ID_AGENT)) agentName = pText.replace(Contacts.ID_AGENT + " ", "");
            if (pText.contains(Contacts.ID_COMPANY)) company = pText.replace(Contacts.ID_COMPANY + " ", "");
            if (pText.contains(Contacts.ID_EMAIL)) email = pText.replace(Contacts.ID_EMAIL + " ", "");
            if (pText.contains(Contacts.ID_TELEPHONE)) telephone = pText.replace(Contacts.ID_TELEPHONE + " ", "");
            if (pText.contains(Contacts.ID_UPDATE)) update = pText.replace(Contacts.ID_UPDATE + " ", "");
            if (pText.contains(Contacts.ID_WATCHERS)) watchers = Integer.parseInt(pText.replaceAll("\\D", ""));
        }
        return new Contacts(agentName, telephone, email, company, update, watchers);
    }



}
