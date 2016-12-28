package ru.sozvezdie42.adapter;

import org.imgscalr.Scalr;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.components.Image;
import ru.sozvezdie42.res.PropertyResources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Romancha
 */
public class ImageDAOImpl implements ImageDAO {

    private java.sql.Connection connection;

    @Override
    public boolean writeImageFile(Image image) {

        if (image == null || image.getImageUrl().equals("")) {
            return false;
        }

        try {
            Connection.Response resultImageResponse = Jsoup.connect(image.getImageUrl()).ignoreContentType(true).execute();

            String fileOutName = image.getFileName() + image.getFileType();

            File imageFile = new java.io.File(PropertyResources.PICTURE_PATH, fileOutName);
            FileOutputStream out = (new FileOutputStream(imageFile));
            out.write(resultImageResponse.bodyAsBytes());
            out.close();

            BufferedImage bufImage = ImageIO.read(imageFile);
            BufferedImage thumbnail = Scalr.resize(bufImage, 200, 150);
            String thumbnailFileName = image.getFileName() + "_thumb.jpg";
            ImageIO.write(thumbnail, "jpg", new File(PropertyResources.PICTURE_PATH, thumbnailFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void executeImages(Property property) {
        ArrayList<Image> imagesList = property.getImages();

        if (imagesList != null && !imagesList.isEmpty()) {
            imagesList.forEach(image -> {
                boolean fileRecorded = writeImageFile(image);
                if (fileRecorded) {
                    fillImageBonds(image, property.getDbKey());
                }
            });
        }
    }

    @Override
    public boolean deleteImages(Property property) {

        int dbPropKey = new ResidentialPropertyDAOImpl(connection).getPropertyDbKey(property);


        String query = "SELECT * FROM aj2or_iproperty_images WHERE propid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dbPropKey);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String fname = rs.getString("fname");
                String type = rs.getString("type");

                File imageFile = new File(PropertyResources.PICTURE_PATH + "\\" + fname + type);
                File imageThumbFile = new File(PropertyResources.PICTURE_PATH + "\\" + fname + "_thumb" + type);

                try {
                    imageFile.delete();
                    imageThumbFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        deleteImagesBonds(property);

        return true;
    }

    private boolean deleteImagesBonds(Property property) {
        String deleteQuery = "DELETE FROM aj2or_iproperty_images WHERE propid = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, property.getDbKey());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean fillImageBonds(Image image, int dbPropKey) {
        String query = "INSERT INTO aj2or_iproperty_images " +
                "(propid, title, description, fname, type, path, remote, owner, ordering, state, language) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dbPropKey);
            preparedStatement.setString(2, AdapterUtils.prepare(image.getTitle()));
            preparedStatement.setString(3, AdapterUtils.prepare(image.getDescription()));
            preparedStatement.setString(4, image.getFileName());
            preparedStatement.setString(5, image.getFileType());
            preparedStatement.setString(6, PropertyResources.PICTURE_PATH_SHORT);
            preparedStatement.setInt(7, 0);
            preparedStatement.setInt(8, 55);
            preparedStatement.setInt(9, image.getOrdering());
            preparedStatement.setInt(10, 1);
            preparedStatement.setString(11, "");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public ImageDAOImpl(java.sql.Connection connection) {
        this.connection = connection;
    }
}
