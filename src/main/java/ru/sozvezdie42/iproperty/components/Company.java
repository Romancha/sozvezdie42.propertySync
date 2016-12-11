package ru.sozvezdie42.iproperty.components;

/**
 * Created by Roman on 12/9/2016.
 */
public class Company {
    public static final int SOZVEZDIE = 1;

    public static int getCompanyId(String companyName) {
        if (companyName.equals("АН Созвездие")) {
            return Company.SOZVEZDIE;
        } else {
            return 0;
        }
    }
}
