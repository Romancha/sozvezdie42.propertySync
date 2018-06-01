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
    public static String PICTURE_LOCALE_PATH;
    public static String PICTURE_PATH_SHORT_DB;

    public static boolean FTP_ENABLED;
    public static String FTP_SERVER;
    public static String FTP_USER;
    public static String FTP_PASSWORD;

    public static String COMPANY;

    public static boolean EXECUTE_CRON_ENABLED;
    public static String EXECUTE_CRON;

    public PropertyResources() {
        Properties prop = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("soz42sync.properties");

            prop.load(inputStream);

            DB_URL = prop.getProperty("db.url");
            DB_USER = prop.getProperty("db.user");
            DB_PASSWORD = prop.getProperty("db.password");

            PICTURE_PATH = prop.getProperty("picture.path");
            PICTURE_LOCALE_PATH = prop.getProperty("picture.locale.path");
            PICTURE_PATH_SHORT_DB = prop.getProperty("picture.path.short.db");

            FTP_ENABLED = Boolean.parseBoolean(prop.getProperty("ftp.enabled"));
            FTP_SERVER = prop.getProperty("ftp.server");
            FTP_USER = prop.getProperty("ftp.user");
            FTP_PASSWORD = prop.getProperty("ftp.password");

            COMPANY = prop.getProperty("company");

            EXECUTE_CRON_ENABLED = Boolean.parseBoolean(prop.getProperty("execute.cron.enabled"));
            EXECUTE_CRON = prop.getProperty("execute.cron");

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
