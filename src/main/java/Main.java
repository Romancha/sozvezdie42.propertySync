import ru.sozvezdie42.adapter.*;
import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.components.Image;
import ru.sozvezdie42.pasrser.ParseService;
import ru.sozvezdie42.pasrser.ParseServiceImpl;

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

    }
}
