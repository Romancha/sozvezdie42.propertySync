package ru.sozvezdie42.iproperty.components;

/**
 * Created by Roman on 12/6/2016.
 */
public class OperationType {
    public static final String RESIDENTIAL_SALE = "flat/sale";
    public static final String RESIDENTIAL_PURCHASE = "flat/purchase";
    public static final String RESIDENTIAL_RENT = "flat/rent";
    public static final String RESIDENTIAL_RENTUSE = "flat/rentuse";
    public static final String RESIDENTIAL_EXCHANGE = "flat/exchange";
    public static final String RESIDENTIAL_NEW = "flat/new";

    public static final String HOUSE_SALE = "house/sale";
    public static final String HOUSE_EXCHANGE = "house/exchange";

    public static final String GARAGE_SALE = "garage/sale";

    public static String getOperationTypeFromPropUrl(String propUrl) {
        if (propUrl.contains(RESIDENTIAL_SALE)) return RESIDENTIAL_SALE;
        if (propUrl.contains(RESIDENTIAL_PURCHASE)) return RESIDENTIAL_PURCHASE;
        if (propUrl.contains(RESIDENTIAL_RENT)) return RESIDENTIAL_RENT ;
        if (propUrl.contains(RESIDENTIAL_RENTUSE)) return RESIDENTIAL_RENTUSE;
        if (propUrl.contains(RESIDENTIAL_EXCHANGE)) return RESIDENTIAL_EXCHANGE;
        if (propUrl.contains(RESIDENTIAL_NEW)) return RESIDENTIAL_NEW;
        if (propUrl.contains(HOUSE_SALE)) return HOUSE_SALE;
        if (propUrl.contains(HOUSE_EXCHANGE)) return HOUSE_EXCHANGE;
        if (propUrl.contains(GARAGE_SALE)) return GARAGE_SALE;
        return "";
    }
}
