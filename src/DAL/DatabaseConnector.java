package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

/*
DatabaseConnector er utility klasse som har 2 funktioner, den opretter forbindelse til databasen og giver den forbindelse videre til
klassen der kalder getConnection metoden. Dette bliver gjort ved at oprette en datasource med de forskellige loginoplysninger fra vores configfil
 */

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
