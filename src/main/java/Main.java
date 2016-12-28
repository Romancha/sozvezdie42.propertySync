import ru.sozvezdie42.synchronizer.Synchronizer;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Roman on 12/5/2016.
 */
public class Main {
    public static void main(String[] args) throws IOException, SQLException {

        Synchronizer synchronizer = new Synchronizer();
        synchronizer.synchronizeCompany("sozvezdie42");

    }
}
