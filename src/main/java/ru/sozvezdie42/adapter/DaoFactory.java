package ru.sozvezdie42.adapter;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import ru.sozvezdie42.res.PropertyResources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Romancha
 */
public class DaoFactory {

    private final static Logger log = Logger.getLogger(DaoFactory.class);

    public Connection getConnection() {
        Connection connection = null;
        try {
            Properties properties=new Properties();
            properties.setProperty("user", PropertyResources.DB_USER);
            properties.setProperty("password", PropertyResources.DB_PASSWORD);
            properties.setProperty("useUnicode","true");
            properties.setProperty("characterEncoding","UTF-8");
            connection = DriverManager.getConnection(PropertyResources.DB_URL, properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public PropertyDAO getPropertyDAO(Connection connection, ImageDAO imageDAO) {
        return new ResidentialPropertyDAOImpl(connection, imageDAO);
    }

    public ImageDAO getImageDAO(Connection connection, FTPClient ftpClient) {
        return new ImageDAOImpl(connection, ftpClient);
    }

    public AgentDAO getAgentDAO(Connection connection) {
        return new AgentDAOImpl(connection);
    }

    public FTPClient getConnectedFtpClient() {
        FTPClient ftpClient = new FTPClient();

        try {
            FTPClientConfig config = new FTPClientConfig();
            ftpClient.configure(config);

            int reply;
            String server = PropertyResources.FTP_SERVER;
            ftpClient.connect(server);
            ftpClient.login(PropertyResources.FTP_USER, PropertyResources.FTP_PASSWORD);

            log.info("Connected to " + server + ".");
            log.info(ftpClient.getReplyString());

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.info("FTP server refused connection.");
            }
        } catch (IOException e) {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
            e.printStackTrace();
        }

        return ftpClient;
    }
}
