package ru.sozvezdie42.iproperty.components;

import java.util.List;

/**
 * Created by Roman on 12/7/2016.
 */
public class Category {
    public static final String SWAP = "Обмен";
    public static final String APARTMENT = "Квартиры";
    public static final String KGT = "КГТ";
    public static final String COTTAGE = "Дома и коттеджи";
    public static final String COUNTRY_HOUSE = "Дачи и земельные участки";
    public static final String GARAGE = "Гаражи и парковки";
    public static final String COMMERCIAL = "Коммерческая недвижимость";
    public static final String NEW = "Новостройки";
    public static final String RENT = "Аренда";

    private List<Integer> categories;

    public static int getCategoryId(String categoryName) {
        switch (categoryName) {
            case SWAP:
                return 10;
            case APARTMENT:
                return 2;
            case KGT:
                return 3;
            case COTTAGE:
                return 4;
            case COUNTRY_HOUSE:
                return 5;
            case GARAGE:
                return 6;
            case COMMERCIAL:
                return 7;
            case NEW:
                return 8;
            case RENT:
                return 9;
        }
        return 0;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }
}
