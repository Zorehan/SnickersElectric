package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DatabaseConnector {
    private static final String configSettings = "config/config.settings";
    private SQLServerDataSource dataSource;

    public DatabaseConnector()
    {
        Properties databaseProperties = new Properties();
        try {
            databaseProperties.load(new FileInputStream(new File(configSettings)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dataSource = new SQLServerDataSource();
        dataSource.setServerName(databaseProperties.getProperty("Server"));
        dataSource.setDatabaseName(databaseProperties.getProperty("Database"));
        dataSource.setUser(databaseProperties.getProperty("User"));
        dataSource.setPassword(databaseProperties.getProperty("Password"));
        dataSource.setPortNumber(1433);
        dataSource.setTrustServerCertificate(true);
    }

    public Connection getConnection()
    {
        try {
            return dataSource.getConnection();
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
    }
}
