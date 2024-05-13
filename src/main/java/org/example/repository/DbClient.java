package org.example.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.App;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.example.helper.ConfigHelper.appConfig;
import static org.slf4j.LoggerFactory.getLogger;

public class DbClient {

    private static final Logger log = getLogger(DbClient.class);

    private static final String URL = appConfig.getString("client.db.contact.points");
    private static final String USER = appConfig.getString("client.db.user");
    private static final String PASSWORD = appConfig.getString("client.db.password");

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //    private final HikariDataSource dictDS;
//
//    public DbClient() {
//        log.info("Init dict DB client...");
//        final HikariConfig dictConfig = new HikariConfig();
//        dictConfig.setJdbcUrl(URL);
//        dictConfig.setUsername(USER);
//        dictConfig.setPassword(PASSWORD);
//        dictConfig.addDataSourceProperty("cachePrepStmts", "true");
//        dictConfig.addDataSourceProperty("prepStmtCacheSize", "250");
//        dictDS = new HikariDataSource(dictConfig);
//        log.info("Init dict DB client success");
//    }

//    public Connection getConnection() throws SQLException {
//        return dictDS.getConnection();
//    }

}
