package ru.sozvezdie42.iproperty.components;

/**
 * Created by Roman on 12/7/2016.
 */
public class District {
    public static final String LN = "Ленинский";
    public static final String ZD = "Заводской";
    public static final String KR = "Кировский";
    public static final String RD = "Рудничный";
    public static final String CR = "Центральный";
    public static final String LP = "Лесная поляна";
    public static final String KD = "Кедровка";
    public static final String IG = "Ягуновский";
    public static final String UG = "Южный";

    public static String getDistrict(int id) {
        switch (id) {
            case 1:
                return LN;
            case 2:
                return ZD;
            case 3:
                return KR;
            case 4:
                return RD;
            case 5:
                return CR;
            case 6:
                return LP;
            case 7:
                return KD;
            case 8:
                return IG;
            case 9:
                return UG;
        }
        return null;
    }

    public static int getId(String district) {
        if (district == null) return 0;
        switch (district) {
            case LN:
                return 1;
            case ZD:
                return 2;
            case KR:
                return 3;
            case RD:
                return 4;
            case CR:
                return 5;
            case LP:
                return 6;
            case KD:
                return 7;
            case IG:
                return 8;
            case UG:
                return 9;
        }
        return 0;
    }


}
