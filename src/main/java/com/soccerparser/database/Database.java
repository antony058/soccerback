package com.soccerparser.database;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;

public abstract class Database {
    protected static Connection connection;
    private String databaseURL;
    private String userName;
    private String userPassword;

    public Database(String userName, String userPassword, String databaseURL) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.databaseURL = databaseURL;

        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(databaseURL, userName, userPassword);
        } catch (PSQLException ex) {
            DBPoolCache poolCache = DBPoolCache.getInstance("127.0.0.1:5432", "soccerdata", userName, userPassword);
            try {
                connection = poolCache.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
