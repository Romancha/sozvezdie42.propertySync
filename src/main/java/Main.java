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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Roman on 12/5/2016.
 */
public class Main {
    public static void main(String[] args) throws IOException, SQLException {



        HashMap<String, ArrayList<Property>> props = new ParseServiceImpl().parseResidentialFromCompany("sozvezdie42");
        System.out.println(props);


        MytSqlDaoFactory factory = new MytSqlDaoFactory();
        Connection connection = factory.getConnection();
        PropertyDAO propertyDAO = new ResidentialPropertyDAOImpl(connection);

        props.forEach((k, v) -> v.forEach(propertyDAO::executeProperty));

        connection.close();



        System.out.println("OK");

        /*String propUrl = "http://sibestate.ru/garage/sale/89570000002";

        Property property = new ParseServiceImpl().parseProperty(propUrl);

        if (property != null) {
            System.out.println("OK");
        }*/

        /*ResidentialParser parser = new ResidentialParser();

        Document doc = null;

        try {
            doc = Jsoup.connect(propUrl).header("Accept-Encoding", "gzip, deflate")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                    .maxBodySize(0)
                    .timeout(600000)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Parser
        Agent agent  = parser.getAgent(doc);
        String comment = parser.getComment(doc);
        double[] c = parser.getCoordinates(doc);
        Finance fin = parser.getFinance(doc);
        String id = parser.getId(doc);
        String locStr = parser.getLocationStr(doc);
        String shortD = parser.getShortDescription(doc);

        //Need realizate
        Location loc = parser.getLocation(locStr, doc);





        Property property = new Property();*/




    }
}
