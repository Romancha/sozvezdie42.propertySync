import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.pasrser.ParseServiceImpl;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Roman on 12/5/2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {



        ArrayList<Property> propList  = new ParseServiceImpl().parseResidentialPropertyFromCompany("sozvezdie42");

        System.out.println(propList);

    }
}
