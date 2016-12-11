import ru.sozvezdie42.adapter.*;
import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.pasrser.ParseService;
import ru.sozvezdie42.pasrser.ParseServiceImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Roman on 12/5/2016.
 */
public class Main {
    public static void main(String[] args) throws IOException, SQLException {



        ArrayList<Property> propList  = new ParseServiceImpl().parseFlatSaleFromCompany("sozvezdie42");
        System.out.println(propList);

        MytSqlDaoFactory factory = new MytSqlDaoFactory();

        Connection connection = factory.getConnection();
        /*PropertyDAO propertyDAO =  factory.getPropertyDao(connection);

        Property property = propertyDAO.get("11-1");*/

        ParseService parseService = new ParseServiceImpl();
        PropertyDAO propertyDAO = new ResidentialPropertyDAOImpl(connection);
        //System.out.println("PROP REF: " + prop.getRef() + " - " + propertyDAO.propertyExists(prop));
        propList.forEach(propertyDAO::executeProperty);

        connection.close();
        //Property property = parseService.parseProperty("http://sibestate.ru/flat/sale/89120000187");



        System.out.println("OK");

    }
}
