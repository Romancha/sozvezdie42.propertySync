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

    public static final String LAND_SALE = "land/sale";

    public static final String TRADE_SALE = "trade/sale";
    public static final String TRADE_RENT = "trade/rent";

    public static final String OFFICE_SALE = "office/sale";
    public static final String OFFICE_RENT = "office/rent";

    public static final String PRODUCT_SALE = "product/sale";
    public static final String PRODUCT_RENT = "product/rent";

    public static final String LANDCOM_SALE = "landcom/sale";
    public static final String LANDCOM_RENT = "landcom/rent";

    public static final String OTHER_SALE = "other/sale";
    public static final String OTHER_RENT = "other/rent";


    public static String getOperationTypeFromPropUrl(String propUrl) {
        if (propUrl.contains(RESIDENTIAL_SALE)) return RESIDENTIAL_SALE;
        if (propUrl.contains(RESIDENTIAL_PURCHASE)) return RESIDENTIAL_PURCHASE;
        if (propUrl.contains(RESIDENTIAL_RENT)) return RESIDENTIAL_RENT;
        if (propUrl.contains(RESIDENTIAL_RENTUSE)) return RESIDENTIAL_RENTUSE;
        if (propUrl.contains(RESIDENTIAL_EXCHANGE)) return RESIDENTIAL_EXCHANGE;
        if (propUrl.contains(RESIDENTIAL_NEW)) return RESIDENTIAL_NEW;
        if (propUrl.contains(HOUSE_SALE)) return HOUSE_SALE;
        if (propUrl.contains(HOUSE_EXCHANGE)) return HOUSE_EXCHANGE;
        if (propUrl.contains(GARAGE_SALE)) return GARAGE_SALE;
        if (propUrl.contains(LAND_SALE)) return LAND_SALE;
        if (propUrl.contains(TRADE_SALE)) return TRADE_SALE;
        if (propUrl.contains(TRADE_RENT)) return TRADE_RENT;
        if (propUrl.contains(OFFICE_SALE)) return OFFICE_SALE;
        if (propUrl.contains(OFFICE_RENT)) return OFFICE_RENT;
        if (propUrl.contains(PRODUCT_SALE)) return PRODUCT_SALE;
        if (propUrl.contains(PRODUCT_RENT)) return PRODUCT_RENT;
        if (propUrl.contains(LANDCOM_SALE)) return LANDCOM_SALE;
        if (propUrl.contains(LANDCOM_RENT)) return LANDCOM_RENT;
        if (propUrl.contains(OTHER_SALE)) return OTHER_SALE;
        if (propUrl.contains(OTHER_RENT)) return OTHER_RENT;
        return "";
    }
}
