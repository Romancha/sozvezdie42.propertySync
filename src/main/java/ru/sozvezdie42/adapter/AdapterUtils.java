package ru.sozvezdie42.adapter;

import ru.sozvezdie42.iproperty.components.Category;
import ru.sozvezdie42.iproperty.components.OperationType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 12/9/2016.
 */
public class AdapterUtils {
    public static String prepare(String string) {
        if (string == null) {
            return "";
        }
        return string;
    }

    public static List<String> getPropertyCategoryByOperationType(String operationType) {
        List<String> categoryList = new ArrayList<>();

        switch (operationType) {
            case OperationType.RESIDENTIAL_SALE:
                categoryList.add(Category.APARTMENT);
                return categoryList;
            case OperationType.RESIDENTIAL_EXCHANGE:
                categoryList.add(Category.APARTMENT);
                categoryList.add(Category.SWAP);
                return categoryList;
        }
        return null;
    }
}
