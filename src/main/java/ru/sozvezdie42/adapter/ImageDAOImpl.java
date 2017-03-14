package ru.sozvezdie42.adapter;

import org.apache.commons.net.ftp.*;
import org.imgscalr.Scalr;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.components.Image;
import ru.sozvezdie42.res.PropertyResources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Romancha
 */
public class ImageDAOImpl implements ImageDAO {

    private java.sql.Connection connection;
    private FTPClient ftpClient;

    @Override
    public boolean writeImageFile(Image image) {
        if (image == null || image.getImageUrl().equals("")) {
            System.out.println("ERROR! Image or image URL is NULL");
            return false;
        }

        if (PropertyResources.FTP_ENABLED) {
            return writeOnLocale(image) && writeOnFTP(image);
        } else {
            return writeOnLocale(image);
        }
    }

    private boolean writeOnLocale(Image image) {
        try {
            Connection.Response resultImageResponse = Jsoup.connect(image.getImageUrl()).ignoreContentType(true)
                    .timeout(10*1000).execute();
            String fileOutName = image.getFileName() + image.getFileType();

            File imageFile = new java.io.File(PropertyResources.PICTURE_LOCALE_PATH, fileOutName);
            FileOutputStream out = (new FileOutputStream(imageFile));
            out.write(resultImageResponse.bodyAsBytes());
            out.close();

            BufferedImage bufImage = ImageIO.read(imageFile);
            BufferedImage buffThumbnail = Scalr.resize(bufImage, 200, 150);
            String thumbnailFileName = image.getFileName() + "_thumb.jpg";
            ImageIO.write(buffThumbnail, "jpg", new File(PropertyResources.PICTURE_LOCALE_PATH, thumbnailFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean writeOnFTP(Image image) {
        if (!ftpClient.isConnected()) {
            System.out.println("FTP: ERROR! Cannot write image: " + image.getImageUrl() + ", because ftp client doesn't connected");
            return false;
        }

        if (ftpClient == null) {
            System.out.println("FTP: ERROR! FTP client not exists");
            return false;
        }

        boolean error = false;

        try {
            String imageFullName = image.getFileName() + image.getFileType();
            String thumbnailFullName = image.getFileName() + "_thumb.jpg";

            File imageFile = new File(PropertyResources.PICTURE_LOCALE_PATH + "/" + imageFullName);
            File thumbnailFile = new File(PropertyResources.PICTURE_LOCALE_PATH + "/" + thumbnailFullName);

            InputStream imageInputStream = new FileInputStream(imageFile);
            InputStream thumbnailInputStream = new FileInputStream(thumbnailFile);

            String imagePath = PropertyResources.PICTURE_PATH + imageFullName;
            String thumbnailPath = PropertyResources.PICTURE_PATH + thumbnailFullName;

            boolean doneImage = ftpClient.storeFile(imagePath, imageInputStream);

            if (doneImage) {
                boolean doneThumbnail = ftpClient.storeFile(thumbnailPath, thumbnailInputStream);
                System.out.println("FTP: Transfer image: " + imagePath + " completed");
                if (!doneThumbnail) {
                    System.out.println("FTP: ERROR! transfer thumbnail: " + thumbnailPath + " + didn't complete");
                    error = true;
                } else {
                    System.out.println("FTP: Transfer thumbnail: " + thumbnailPath + " completed");
                }
            } else {
                System.out.println("FTP: ERROR! transfer image: " + thumbnailPath + " + didn't complete");
            }
        } catch (IOException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            System.out.println(errors.toString());
        }
        return !error;
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
        if (PropertyResources.FTP_ENABLED) {
            return deleteImageOnLocale(property) && deleteImagesOnFTP(property) && deleteImagesBonds(property);
        } else {
            return deleteImageOnLocale(property);
        }
    }

    public boolean deleteImagesOnFTP(Property property) {
        if (!ftpClient.isConnected()) {
            System.out.println("FTP: ERROR! Cannot delete image from property: " + property.getAlias()
                    + ", because ftl client doesn't connected");
            return false;
        }

        if (ftpClient == null) {
            System.out.println("FTP: ERROR! FTP client not exists");
            return false;
        }

        boolean success = true;

        int dbPropKey = new ResidentialPropertyDAOImpl(connection, this).getPropertyDbKey(property);

        String query = "SELECT * FROM aj2or_iproperty_images WHERE propid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dbPropKey);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String fname = rs.getString("fname");
                String type = rs.getString("type");

                String imagePath = PropertyResources.PICTURE_PATH + fname + type;
                String thumbnailPath = PropertyResources.PICTURE_PATH + fname + "_thumb" + type;

                if (!deleteFile(imagePath)) {
                    success = false;
                }
                if (!deleteFile(thumbnailPath)) {
                    success = false;
                }

                if (success) {
                    deleteImagesBonds(property);
                }
            }
        } catch (SQLException | IOException e) {
            success = false;
            System.out.println("Cannot delete images from property: " + property.getAlias());
            e.printStackTrace();
        }
        return success;
    }

    private boolean deleteFile(String path) throws IOException {
        if (ftpClient.deleteFile(path)) {
            System.out.println("FTP: File success deleted: " + path);
            return true;
        } else {
            System.out.println("FTP: ERROR! cannot delete file: " + path);
            return false;
        }
    }

    private boolean deleteImageOnLocale(Property property) {
        int dbPropKey = new ResidentialPropertyDAOImpl(connection, this).getPropertyDbKey(property);


        String query = "SELECT * FROM aj2or_iproperty_images WHERE propid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, dbPropKey);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String fname = rs.getString("fname");
                String type = rs.getString("type");

                File imageFile = new File(PropertyResources.PICTURE_LOCALE_PATH + fname + type);
                File imageThumbFile = new File(PropertyResources.PICTURE_LOCALE_PATH + fname + "_thumb" + type);

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
            preparedStatement.setString(6, PropertyResources.PICTURE_PATH_SHORT_DB);
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

    private boolean connectToFTP() {
        boolean error = false;

        this.ftpClient = new FTPClient();

        try {
            FTPClientConfig config = new FTPClientConfig();
            ftpClient.configure(config);

            int reply;
            String server = PropertyResources.FTP_SERVER;
            ftpClient.connect(server);
            ftpClient.login(PropertyResources.FTP_USER, PropertyResources.FTP_PASSWORD);

            System.out.println("Connected to " + server + ".");
            System.out.print(ftpClient.getReplyString());

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.out.println("FTP server refused connection.");
            }
        } catch (IOException e) {
            error = true;

            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
            e.printStackTrace();
        }

        return error;
    }

    public ImageDAOImpl(java.sql.Connection connection) {
        this.connection = connection;
        connectToFTP();
    }

    public FTPClient getFtpClient() {
        return this.ftpClient;
    }
}
