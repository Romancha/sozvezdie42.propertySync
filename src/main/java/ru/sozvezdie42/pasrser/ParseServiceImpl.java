package ru.sozvezdie42.pasrser;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.sozvezdie42.adapter.AdapterUtils;
import ru.sozvezdie42.iproperty.CommercialProperty;
import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.PropertyFactory;
import ru.sozvezdie42.iproperty.ResidentialProperty;
import ru.sozvezdie42.iproperty.components.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Romancha on 12/6/2016.
 */
public class ParseServiceImpl implements ParseService {

    private final static Logger log = Logger.getLogger(ParseServiceImpl.class);

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
                    property = fillCategories(property, operationType);
                }
                break;
            case OperationType.GARAGE_SALE:
            case OperationType.LAND_SALE:
                property = parseAbstractProperty(propertyUrl);
                if (property != null) {
                    property = fillCategories(property, operationType);
                }
                break;
            case OperationType.TRADE_SALE:
            case OperationType.TRADE_RENT:
            case OperationType.OFFICE_SALE:
            case OperationType.OFFICE_RENT:
            case OperationType.PRODUCT_SALE:
            case OperationType.PRODUCT_RENT:
            case OperationType.LANDCOM_SALE:
            case OperationType.LANDCOM_RENT:
            case OperationType.OTHER_SALE:
            case OperationType.OTHER_RENT:
                property = parseCommercialProperty(propertyUrl);
                if (property != null) {
                    property = fillCategories(property, operationType);
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

        String alias = property.getRef() + "-" + property.getId();
        property.setAlias(alias);

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

        String alias = property.getRef() + "-" + property.getId();
        property.setAlias(alias);

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

    private Property parseCommercialProperty(String propertyUrl) {
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

        CommercialProperty property = (CommercialProperty) new PropertyFactory().createProperty(propCategories);
        CommercialParser parser = new CommercialParser();

        if (property == null) return null;

        String locationStr = parser.getLocationStr(doc);
        Location location = new Location(locationStr);
        location.setLocationStr(locationStr);

        String ref = parser.getRef(doc);
        String shortDescription = parser.getShortDescription(doc);

        property.setRef(ref);
        property.setId(parser.getId(doc));

        String alias = property.getRef() + "-" + property.getId();
        property.setAlias(alias);

        property.setDescription(parser.getComment(doc));
        property.setShortDescription(shortDescription);

        location.setCoordinates(parser.getCoordinates(doc));
        location.setCity("-");
        location.setRegion("-");
        property.setLocation(location);

        property.setSize(parser.getSize(doc));

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
        categoryLinks.add(OperationType.LAND_SALE);
        categoryLinks.add(OperationType.OFFICE_SALE);
        categoryLinks.add(OperationType.OFFICE_RENT);
        categoryLinks.add(OperationType.TRADE_SALE);
        categoryLinks.add(OperationType.TRADE_RENT);
        categoryLinks.add(OperationType.PRODUCT_SALE);
        categoryLinks.add(OperationType.PRODUCT_RENT);
        categoryLinks.add(OperationType.LANDCOM_SALE);
        categoryLinks.add(OperationType.LANDCOM_RENT);
        categoryLinks.add(OperationType.OTHER_SALE);
        categoryLinks.add(OperationType.OTHER_RENT);


        categoryLinks.forEach(categoryLink -> {
            String parseUrl = "http://sibestate.ru/" + companyId + "/" + categoryLink;

            Document doc = null;

            try {
                doc = Jsoup.connect(parseUrl).timeout(10*1000).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (doc != null) {
                Element tableElement = doc.getElementsByClass("table-responsive").get(0);
                Elements tableBodyElements = tableElement.select("tbody");

                if (tableBodyElements.size() == 0) {
                    return;
                }

                Element tableBody = tableBodyElements.get(0);
                Elements tableElements = tableBody.select("tr");

                ArrayList<Property> propList = new ArrayList<>();

                for (Element element : tableElements) {
                    Elements ref = element.getElementsByClass("domstor_code");
                    String href = ref.select("a").first().attr("href");
                    int codeStartIndex = href.indexOf(ID_OBJECT);
                    String code = href.substring(codeStartIndex + ID_OBJECT.length());

                    String site = "http://sibestate.ru/";
                    String operationType = OperationType.getOperationTypeFromPropUrl(categoryLink);

                    switch (operationType) {
                        case OperationType.TRADE_SALE:
                        case OperationType.TRADE_RENT:
                        case OperationType.OFFICE_SALE:
                        case OperationType.OFFICE_RENT:
                        case OperationType.PRODUCT_SALE:
                        case OperationType.PRODUCT_RENT:
                        case OperationType.LANDCOM_SALE:
                        case OperationType.LANDCOM_RENT:
                        case OperationType.OTHER_SALE:
                        case OperationType.OTHER_RENT:
                            site = "http://kem.brekom.ru/";
                    }

                    String propUrl = site + categoryLink + "/" + code;

                    log.info("Parse property: " + propUrl);
                    Property property = parseProperty(propUrl);
                    if (property != null) {
                        propList.add(property);
                    }
                }
                result.put(categoryLink, propList);
            }

        });
        return result;
    }

    private Property fillCategories(Property property, String operationType) {
        final String KGT = "КГТ (Гостинка)";
        property.setOperationType(operationType);

        List<String> categories = AdapterUtils.getPropertyCategoryByOperationType(operationType);
        if (property instanceof ResidentialProperty) {
            ResidentialProperty resProp = (ResidentialProperty) property;
            if (resProp.getType().getType().equals(KGT)) {
                categories.add(Category.KGT);
            }
        }

        property.setCategory(categories);
        return property;
    }
}
