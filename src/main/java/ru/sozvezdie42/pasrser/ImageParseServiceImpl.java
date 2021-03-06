package ru.sozvezdie42.pasrser;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ru.sozvezdie42.iproperty.components.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Romancha
 */
public class ImageParseServiceImpl implements ImageParserService {

    private final static Logger log = Logger.getLogger(ImageParseServiceImpl.class);

    @Override
    public ArrayList<Image> parseImages(String propertyUrl) {
        ArrayList<Image> images = new ArrayList<>();
        Document doc = null;

        try {
            doc = Jsoup.connect(propertyUrl).timeout(10*1000).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (doc == null) {
            return null;
        }

        Elements domstorPhoto = doc.getElementsByClass("domstor_photo");
        if (domstorPhoto.isEmpty()) {
            return null;
        }

        List<String> imagesUrl = new ArrayList<>();
        Elements imagesElements = domstorPhoto.get(0).select("a");
        imagesElements.forEach(imageEl -> {
            String url = imageEl.select("img").first().attr("src");
            if (url.length() > 0) {
                imagesUrl.add(url);

            }
        });

        log.debug("Images: " + imagesUrl);

        imagesUrl.forEach(imageUrl -> {
            String[] urlData = imageUrl.split("/");
            String fileOutName = urlData[urlData.length - 1];

            Image image = new Image();

            String[] splData = fileOutName.split("\\.");
            String fileName = splData[0];
            String type = "." + splData[1].toLowerCase();
            int ordering = imagesUrl.indexOf(imageUrl) + 1;

            image.setFileName(fileName);
            image.setFileType(type);
            image.setOrdering(ordering);
            image.setImageUrl(imageUrl);

            images.add(image);
        });
        return images;
    }
}
