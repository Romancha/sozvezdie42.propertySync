package ru.sozvezdie42.pasrser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.PropertyFactory;
import ru.sozvezdie42.iproperty.ResidentialProperty;
import ru.sozvezdie42.iproperty.components.OperationType;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Roman on 12/6/2016.
 */
public class ParseServiceImpl implements ParseService {

    @Override
    public Property parseProperty(String propertyUrl) {

        String operationType = OperationType.getOperationTypeFromPropUrl(propertyUrl);
        Property property = null;

        switch (operationType) {
            case OperationType.RESIDENTIAL_SALE:
                property = parseResidentialProperty(propertyUrl);
                break;
        }
        return property;
    }


    private ResidentialProperty parseResidentialProperty(String propertyUrl) {
        Document doc = null;

        try {
            doc = Jsoup.connect(propertyUrl).header("Accept-Encoding", "gzip, deflate")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                    .maxBodySize(0)
                    .timeout(600000)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (doc == null) return null;

        ResidentialProperty property = (ResidentialProperty) new PropertyFactory().createProperty("residential");
        ResidentialParser parser = new ResidentialParser();
        String id = parser.getId(doc);
        String description = parser.getDescription(doc);

        property.setId(id);
        property.setDescription(description);
        property.setLocation(parser.getLocation(parser.getLocationStr(doc), doc));
        property.setRoomAmt(parser.getRoomAmt(doc));
        property.setType(parser.getPropertyType(doc));
        property.setSize(parser.getSize(doc));
        property.setSpecifications(parser.getResidentialSpecifications(doc));
        property.setFinance(parser.getFinance(doc));
        property.setComment(parser.getComment(doc));
        property.setContacts(parser.getAgent(doc));

        System.out.println(property);

        return property;
    }

    @Override
    public Map<String, ArrayList<Property>> parseCompany(String companyId) {
        return null;
    }

    @Override
    public ArrayList<Property> parseResidentialPropertyFromCompany(String companyId) {
        final String ID_OBJECT = "object/";

        String parseUrl = "http://sibestate.ru/" + companyId + "/flat/sale";

        Document doc = null;

        try {
            doc = Jsoup.connect(parseUrl).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (doc == null) return null;

        Element tableElement = doc.getElementsByClass("table-responsive").get(0);
        Element tableBody = tableElement.select("tbody").get(0);
        Elements tableElements = tableBody.select("tr");

        ArrayList<Property> propList = new ArrayList<>();

        for (Element element : tableElements) {
            Elements ref = element.getElementsByClass("domstor_code");
            String href = ref.select("a").first().attr("href");
            int codeStartIndex = href.indexOf(ID_OBJECT);
            String code = href.substring(codeStartIndex + ID_OBJECT.length());

            String propUrl = "http://sibestate.ru/flat/sale/" + code;
            System.out.println(propUrl);
            propList.add(parseProperty(propUrl));
        }
        return propList;
    }
}
