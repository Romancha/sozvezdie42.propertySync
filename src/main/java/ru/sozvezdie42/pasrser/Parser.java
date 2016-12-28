package ru.sozvezdie42.pasrser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.sozvezdie42.iproperty.components.Agent;
import ru.sozvezdie42.iproperty.components.Company;
import ru.sozvezdie42.iproperty.components.Finance;
import ru.sozvezdie42.iproperty.components.Location;

import java.util.Arrays;


/**
 * @author Romancha on 12/5/2016.
 */
public class Parser {

    public String getRef(Document document) {
        Element pElement = document.getElementsByClass("navysearch").get(0);
        String elementText = pElement.child(0).text();
        String[] text = elementText.split(" / ");
        String objectStr = text[2];
        return objectStr.substring(7);
    }

    public String getId(Document document) {
        Element elementPrevBlock = document.getElementsByClass("prevblock").get(0);
        Element elementP = elementPrevBlock.child(1).select("a").first();
        return elementP.attr("oid");
    }

    public String getShortDescription(Document document) {
        Element pElement = document.getElementsByClass("domstor_object_head").get(0);
        return pElement.child(0).text();
    }

    public String getLocationStr(Document document) {
        Element element = document.getElementsByClass("domstor_object_place").get(0);
        return element.select("p").first().text();
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

    public Agent getAgent(Document document) {
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
            if (pText.contains(Agent.ID_AGENT)) agentName = pText.replace(Agent.ID_AGENT + " ", "");
            if (pText.contains(Agent.ID_COMPANY)) company = pText.replace(Agent.ID_COMPANY + " ", "");
            if (pText.contains(Agent.ID_EMAIL)) email = pText.replace(Agent.ID_EMAIL + " ", "");
            if (pText.contains(Agent.ID_TELEPHONE)) telephone = pText.replace(Agent.ID_TELEPHONE + " ", "");
            if (pText.contains(Agent.ID_UPDATE)) update = pText.replace(Agent.ID_UPDATE + " ", "");
            if (pText.contains(Agent.ID_WATCHERS)) watchers = Integer.parseInt(pText.replaceAll("\\D", ""));
        }

        int companyId = Company.getCompanyId(company);

        Agent agent = new Agent();
        agent.setName(agentName);
        agent.setTelephone(telephone);
        agent.setEmail(email);
        agent.setCompany(companyId);
        agent.setUpdate(update);
        agent.setWatchers(watchers);
        return agent;
    }



}
