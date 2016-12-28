package ru.sozvezdie42.res;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Romancha on 12/28/2016.
 */
public class PropertyResources {
    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;

    public static String PICTURE_PATH;
    public static String PICTURE_PATH_SHORT;

    public static String COMPANY;

    public PropertyResources() {
        Properties prop = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("soz42sync.properties");

            prop.load(inputStream);

            DB_URL = prop.getProperty("db.url");
            DB_USER = prop.getProperty("db.user");
            DB_PASSWORD = prop.getProperty("db.pass");

            PICTURE_PATH = prop.getProperty("picture.path");
            PICTURE_PATH_SHORT = prop.getProperty("picture.path.short");

            COMPANY = prop.getProperty("company");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
