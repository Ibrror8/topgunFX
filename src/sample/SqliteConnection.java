package sample;

import java.sql.*;

public class SqliteConnection {

    //connects to database and send connection back when called
    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:topgun.db");

            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
