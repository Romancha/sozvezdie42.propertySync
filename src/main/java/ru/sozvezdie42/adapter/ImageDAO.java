package ru.sozvezdie42.adapter;

import org.apache.commons.net.ftp.FTPClient;
import ru.sozvezdie42.iproperty.Property;
import ru.sozvezdie42.iproperty.components.Image;

/**
 * @author Romancha
 */
public interface ImageDAO {
    public boolean writeImageFile(Image image);
    public void executeImages(Property property);
    public boolean deleteImages(Property property);
    public FTPClient getFtpClient();
}
