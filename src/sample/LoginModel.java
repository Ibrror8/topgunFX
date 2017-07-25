package sample;

import org.sqlite.SQLiteConnection;

import java.sql.*;

public class LoginModel {
    Connection connection;

    public LoginModel() {
        connection = SqliteConnection.Connector();
        if(connection == null) {
            System.exit(1);
        }
    }

    public boolean isDBConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public  boolean isLogin(String user, String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from users where name = ? and password = ?;";
        String query2 = "insert into activity_log(userid, activity) values (?, 'login');";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setInt(1, resultSet.getInt("id"));

                preparedStatement.execute();
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

    public String getUserData(String name, String column) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select " + column + " from users where name = ?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String wantedData = Integer.toString(resultSet.getInt("id"));
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
