package com.soccerparser.database;

import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBPoolCache {
    private static PGPoolingDataSource source;
    private static DBPoolCache poolCache;

    private DBPoolCache(String host, String database, String user, String password) {
        source = new PGPoolingDataSource();
        source.setDataSourceName("Data source");
        source.setServerName(host);
        source.setDatabaseName(database);
        source.setUser(user);
        source.setPassword(password);
        source.setMaxConnections(100);
        source.setInitialConnections(20);
    }

    public static DBPoolCache getInstance(String host, String database, String user, String password) {
        if (poolCache == null) {
            poolCache = new DBPoolCache(host, database, user, password);
        }

        return poolCache;
    }

    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    public void putConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
