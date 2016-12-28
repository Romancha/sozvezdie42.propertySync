package ru.sozvezdie42;

import ru.sozvezdie42.res.PropertyResources;
import ru.sozvezdie42.synchronizer.Synchronizer;

import java.io.IOException;
import java.sql.SQLException;


/**
 * @author Romancha on 12/5/2016.
 */
public class SyncStarter {
    public static void main(String[] args) throws IOException, SQLException {
        new PropertyResources();
        Synchronizer synchronizer = new Synchronizer();
        synchronizer.synchronizeCompany(PropertyResources.COMPANY);
    }
}
