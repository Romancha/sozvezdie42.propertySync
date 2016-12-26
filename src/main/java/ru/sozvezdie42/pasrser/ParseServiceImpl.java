package ru.sozvezdie42.pasrser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.sozvezdie42.adapter.AdapterUtils;
import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.PropertyFactory;
import ru.sozvezdie42.iproperty.ResidentialProperty;
import ru.sozvezdie42.iproperty.components.*;
import ru.sozvezdie42.iproperty.components.specifications.ResidentialSpecifications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            case OperationType.RESIDENTIAL_EXCHANGE:
            case OperationType.RESIDENTIAL_NEW:
            case OperationType.HOUSE_SALE:
            case OperationType.HOUSE_EXCHANGE:
                property = parseResidentialProperty(propertyUrl);
                if (property != null) {
                    property.setOperationType(operationType);
                    property.setCategory(AdapterUtils.getPropertyCategoryByOperationType(operationType));
                }
                break;
            case OperationType.GARAGE_SALE:
                property = parseAbstractProperty(propertyUrl);
                if (property != null) {
                    property.setOperationType(operationType);
                    property.setCategory(AdapterUtils.getPropertyCategoryByOperationType(operationType));
                }
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

        List<String> propCategories = AdapterUtils.
                getPropertyCategoryByOperationType(OperationType.getOperationTypeFromPropUrl(propertyUrl));

        if (propCategories == null) return null;

        ResidentialProperty property = (ResidentialProperty) new PropertyFactory().createProperty(propCategories);
        ResidentialParser parser = new ResidentialParser();

        if (property == null) return null;


        String ref = parser.getRef(doc);
        String shortDescription = parser.getShortDescription(doc);

        property.setRef(ref);
        property.setId(parser.getId(doc));
        property.setDescription(parser.getComment(doc));
        property.setShortDescription(shortDescription);
        property.setLocation(parser.getLocation(parser.getLocationStr(doc), doc));
        property.setStorey(parser.getStorey(doc));
        property.setRoomAmt(parser.getRoomAmt(doc));
        property.setType(parser.getPropertyType(doc));
        property.setSize(parser.getSize(doc));
        property.setSpecifications(parser.getResidentialSpecifications(doc));
        property.setFinance(parser.getFinance(doc));
        property.setComment(parser.getComment(doc));
        property.setAgent(parser.getAgent(doc));

        if (propCategories.contains(Category.COTTAGE)) {
            ResidentialParser cottageParser = new CottageParser();
            property.setLocation(cottageParser.getLocation(parser.getLocationStr(doc), doc));
        }

        ArrayList<Image> images = new ImageParseServiceImpl().parseImages(propertyUrl);
        property.setImages(images);

        return property;
    }

    private Property parseAbstractProperty(String propertyUrl) {
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

        List<String> propCategories = AdapterUtils.
                getPropertyCategoryByOperationType(OperationType.getOperationTypeFromPropUrl(propertyUrl));

        if (propCategories == null) return null;

        Property property = new PropertyFactory().createProperty(propCategories);
        Parser parser = new Parser();

        if (property == null) return null;

        String locationStr = parser.getLocationStr(doc);
        Location location = new Location(locationStr);
        location.setLocationStr(locationStr);

        String ref = parser.getRef(doc);
        String shortDescription = parser.getShortDescription(doc);

        property.setRef(ref);
        property.setId(parser.getId(doc));
        property.setDescription(parser.getComment(doc));
        property.setShortDescription(shortDescription);

        location.setCoordinates(parser.getCoordinates(doc));
        location.setCity("-");
        location.setRegion("-");
        property.setLocation(location);

        property.setFinance(parser.getFinance(doc));
        property.setComment(parser.getComment(doc));
        property.setAgent(parser.getAgent(doc));

        ArrayList<Image> images = new ImageParseServiceImpl().parseImages(propertyUrl);
        property.setImages(images);

        return property;
    }

    @Override
    public Map<String, ArrayList<Property>> parseCompany(String companyId) {
        return null;
    }

    @Override
    public HashMap<String, ArrayList<Property>> parseResidentialFromCompany(String companyId) {
        HashMap<String, ArrayList<Property>> result = new HashMap<>();
        final String ID_OBJECT = "object/";

        List<String> categoryLinks = new ArrayList<>();
        categoryLinks.add(OperationType.RESIDENTIAL_SALE);
        categoryLinks.add(OperationType.RESIDENTIAL_EXCHANGE);
        categoryLinks.add(OperationType.RESIDENTIAL_NEW);
        categoryLinks.add(OperationType.HOUSE_EXCHANGE);
        categoryLinks.add(OperationType.HOUSE_SALE);
        categoryLinks.add(OperationType.GARAGE_SALE);

        categoryLinks.forEach(categoryLink -> {
            String parseUrl = "http://sibestate.ru/" + companyId + "/" + categoryLink;

            Document doc = null;

            try {
                doc = Jsoup.connect(parseUrl).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (doc != null) {
                Element tableElement = doc.getElementsByClass("table-responsive").get(0);
                Element tableBody = tableElement.select("tbody").get(0);
                Elements tableElements = tableBody.select("tr");

                ArrayList<Property> propList = new ArrayList<>();

                for (Element element : tableElements) {
                    Elements ref = element.getElementsByClass("domstor_code");
                    String href = ref.select("a").first().attr("href");
                    int codeStartIndex = href.indexOf(ID_OBJECT);
                    String code = href.substring(codeStartIndex + ID_OBJECT.length());

                    String propUrl = "http://sibestate.ru/" + categoryLink + "/" + code;
                    System.out.println(propUrl);
                    Property property = parseProperty(propUrl);
                    propList.add(property);
                }
                result.put(categoryLink, propList);
            }

        });
        return result;
    }
}
