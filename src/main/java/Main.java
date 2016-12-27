import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.sozvezdie42.adapter.*;
import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.components.*;
import ru.sozvezdie42.iproperty.components.specifications.ResidentialSpecifications;
import ru.sozvezdie42.pasrser.ParseService;
import ru.sozvezdie42.pasrser.ParseServiceImpl;
import ru.sozvezdie42.pasrser.Parser;
import ru.sozvezdie42.pasrser.ResidentialParser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Roman on 12/5/2016.
 */
public class Main {
    public static void main(String[] args) throws IOException, SQLException {

        Instant start = Instant.now();

        HashMap<String, ArrayList<Property>> props = new ParseServiceImpl().parseResidentialFromCompany("sozvezdie42");
        System.out.println(props);


        MytSqlDaoFactory factory = new MytSqlDaoFactory();
        Connection connection = factory.getConnection();
        PropertyDAO propertyDAO = new ResidentialPropertyDAOImpl(connection);

        props.forEach((k, v) -> v.forEach(propertyDAO::executeProperty));

        connection.close();


        Instant end = Instant.now();


        System.out.println("OK: " + Duration.between(start, end));
        System.out.println("length: " + props.size());

    }
}
