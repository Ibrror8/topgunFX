package sample;

import org.sqlite.SQLiteConnection;

import java.sql.*;

public class LoginModel {
    //get connection
    Connection connection;

    public LoginModel() {
        connection = SqliteConnection.Connector();
        if(connection == null) {
            System.exit(1);
        }
    }

    //when db is connected database returns true
    public boolean isDBConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //called on login try
    public  boolean isLogin(String user, String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from users where name = ? and password = ?;";
        String query2 = "insert into activity_log(userid, activity) values (?, 'login');";

        try {
            //see's if there is an user with that password
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                //yes there is a user, here we say to the database who logged in
                preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setInt(1, resultSet.getInt("id"));

                preparedStatement.execute();

                //returns true so LoginController can open new window
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    //gets userdata
    //1. parameter is for username
    //2. parameter for the which column data to return
    public String getUserData(String name, String column) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select " + column + " from users where name = ?;";

        try {
            //query sselects
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();
            String wantedData = "";

            if (resultSet.next()) {
                wantedData = resultSet.getString(column);

                //returns wanted data
                return wantedData;
            } else {
                return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        } finally {
            preparedStatement.close();
            resultSet.close();

        }
    }

    //gets called when app closed, sends database who logged out
    public void logoutActivity(String name) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "insert into activity_log(userid, activity) values (?, 'logout');";

        try {
            preparedStatement = connection.prepareStatement(sql);

            int userid = Integer.parseInt(getUserData(name, "id"));
            preparedStatement.setInt(1, userid);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            preparedStatement.close();
        }
    }


































}
